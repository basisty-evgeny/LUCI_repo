package com.luci.ui.activity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.luci.AppPreferences;
import com.luci.R;
import com.luci.model.StationModel;
import com.luci.ui.viewholder.IconTreeItemViewHolder;
import com.luci.ui.viewholder.LargeCategoryViewHolder;
import com.luci.ui.viewholder.NothingViewHolder;
import com.luci.ui.viewholder.SmallCategoryViewHolder;
import com.luci.util.Constant;
import com.luci.util.LUCIHttpClient;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ChooseStationActivity extends BaseActionBarActivity implements
        View.OnClickListener {
    public static ChooseStationActivity instance;

    // UI
    CheckBox chk_sort;
    TextInputEditText edt_search;
    ViewGroup layout_stations;
    AndroidTreeView tree_view;

    // local data
    ArrayList<StationModel> m_channels;
//    StationModel local = null;

    TreeNode channel_selected = null;
    TreeNode root;

    // Data
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_choose_station);

        initActionBar();
        setTitle(R.string.Choose_station);

        mSavedInstanceState = savedInstanceState;

        m_channels = new ArrayList<StationModel>();

        chk_sort = (CheckBox)findViewById(R.id.chk_sort);
        edt_search = (TextInputEditText)findViewById(R.id.edt_search);
        layout_stations = (ViewGroup)findViewById(R.id.layout_stations);

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_done).setOnClickListener(this);

//        chk_sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                instance.showStations();
//            }
//        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showStations();
            }
        });

        loadStations();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add: {
                Intent intent = new Intent(instance, EditStationActivity.class);
                EditStationActivity.mStation = null;
                startActivity(intent);
            }
            break;

            case R.id.btn_edit: {
                TreeNode channel = getSelectedTreeNode();
                if (channel == null) {
                    Toasty.warning(instance, getString(R.string.station_not_selected), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(instance, EditStationActivity.class);
                EditStationActivity.mStation = ((IconTreeItemViewHolder.IconTreeItem) channel.getValue())
                                                    .getModel();
                startActivity(intent);
            }
            break;

            case R.id.btn_delete: {
                TreeNode channel = getSelectedTreeNode();
                if (channel == null) {
                    Toasty.warning(instance, getString(R.string.station_not_selected), Toast.LENGTH_SHORT).show();
                    return;
                }

                final StationModel station = ((IconTreeItemViewHolder.IconTreeItem) channel.getValue())
                        .getModel();

                new AlertDialog.Builder(instance)
                        .setMessage(String.format(getString(R.string.dialog_remove), station.name))
                        .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {

                                RequestParams params = new RequestParams();
                                params.add("channel_id",Integer.toString(station.id));

                                LUCIHttpClient.post(instance, Constant.URL_DELETECHANNEL, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject object) {
                                        if (object != null) {
                                            try {
                                                Boolean status = object.getBoolean("success");
                                                if (status) {
                                                    Toasty.success(instance, getString(R.string.deletechannel_success), Toast.LENGTH_SHORT).show();

                                                    instance.selectTreeNode(null);
                                                    instance.loadStations();
                                                }
//                                                else {
//                                                    // TODO: Catch reason_code, and alert appropriate toast.
//                                                    Toasty.error(instance, getString(R.string.deletechannel_fail), Toast.LENGTH_SHORT).show();
//                                                }
                                            } catch (Exception e) {
                                                Toasty.error(instance, getString(R.string.response_parse_error), Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onSuccess(JSONArray array) {

                                    }

                                    @Override
                                    public void onFailure(int statusCode, Throwable throwable) {
                                    }
                                });

                            }
                        })
                        .setNegativeButton(R.string.Cancel, null)
                        .show();
            }
            break;

            case R.id.btn_refresh: {
                loadStations();
            }
            break;

            case R.id.btn_done: {
                TreeNode channel = getSelectedTreeNode();
                if (channel != null) {
                    final StationModel station = ((IconTreeItemViewHolder.IconTreeItem) channel.getValue())
                            .getModel();

                    AppPreferences.setInt("current_channel_id", station.id);
                    AppPreferences.KEY.CURRENT_CHANNEL_ID = station.id;
                }

                onBackPressed();
            }
            break;
        }
    }

    private void loadStations() {

        RequestParams params = new RequestParams();

        LUCIHttpClient.post(instance, Constant.URL_LISTCHANNEL, params, new LUCIHttpClient.SimpleHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {
                if (object != null) {
                    try {
                        Boolean status = object.getBoolean("success");
                        if (status) {
                            int count = object.getInt("count");
                            JSONArray channel_list = object.getJSONArray("channels");

                            m_channels.clear();

                            for (int i = 0 ; i < channel_list.length() ; i ++) {
                                JSONObject channel = channel_list.getJSONObject(i);

                                StationModel model = new StationModel();
                                model.id = channel.getInt("id");
                                model.name = channel.getString("channel_name");
                                model.protocol = channel.getString("protocol");
                                model.destination = channel.getString("destination");
                                model.jitter_buffers = channel.getInt("jitter_buffers");
                                model.dynamic_jitter_buffers = channel.getInt("dynamic_jitter_buffers");
                                model.format = channel.getString("format");
                                model.bitrate = channel.getInt("bitrate");
                                model.samplerate = channel.getInt("samplerate");
                                model.created_at = channel.getString("created_at");
                                model.updated_at = channel.getString("updated_at");
                                model.stereo = channel.getInt("stereo") == 1;
                                model.talk_mode = channel.getInt("talk_mode") == 1;

                                model.depth = 2;

                                m_channels.add(model);
                            }

                            showStations();
                        } else {
                            // TODO: Catch reason_code, and alert appropriate toast.
                            Toasty.normal(instance, getString(R.string.channel_list_error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toasty.error(instance, getString(R.string.response_parse_error), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onSuccess(JSONArray array) {

            }

            @Override
            public void onFailure(int statusCode, Throwable throwable) {
            }
        });
    }

    private void showStations() {
        String search_key = edt_search.getText().toString().trim();

        StationModel stations = new StationModel();
        stations.name = "Local";
        stations.children = new ArrayList<>();
        stations.depth = 0;

        for (int i = 0 ; i < m_channels.size() ; i ++) {
            StationModel channel = m_channels.get(i);

            if (channel.name.contains(search_key)) {
                stations.children.add(channel);
            }
        }

        if (chk_sort.isChecked()) {
            for (int i = 0 ; i < stations.children.size() ; i ++) {
                for (int j = i ; j < stations.children.size() ; j ++) {
                    StationModel left = stations.children.get(i);
                    StationModel right = stations.children.get(j);

                    if (left.name.compareToIgnoreCase(right.name) > 0) {
                        stations.children.remove(j);
                        stations.children.remove(i);

                        stations.children.add(i, right);
                        stations.children.add(j, left);
                    }
                }
            }
        }

        instance.selectTreeNode(null);

        TreeNode parentNode = new TreeNode(new IconTreeItemViewHolder.IconTreeItem(0, 0, stations))
                .setViewHolder(new LargeCategoryViewHolder(instance));
        parentNode.setSelectable(true);

        ArrayList<TreeNode> parentNodeList = new ArrayList<>();
        parentNodeList.add(parentNode);

        addChildNode(parentNode, stations);

        if (stations.children.size() == 0) {
            ArrayList<TreeNode> nothingNodeList = new ArrayList<>();
            TreeNode nothingNode = new TreeNode(
                    new IconTreeItemViewHolder.IconTreeItem(0, 0, null)
            ).setViewHolder(new NothingViewHolder(instance));
            nothingNode.setSelectable(false);

            nothingNodeList.add(nothingNode);

            parentNode.addChildren(nothingNodeList);
        }

        root = TreeNode.root();
        root.addChildren(parentNodeList);

        tree_view = new AndroidTreeView(this, root);
        tree_view.setDefaultAnimation(true);
        tree_view.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);

        ViewGroup layout_stations = (ViewGroup) findViewById(R.id.layout_stations);
        layout_stations.removeAllViews();
        layout_stations.addView(tree_view.getView());

        tree_view.expandAll();

        if (mSavedInstanceState != null) {
            String state = mSavedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tree_view.restoreState(state);
            }
        }
    }

    private void addChildNode(TreeNode parentNode, StationModel model) {
        if (model.children != null && model.children.size() > 0) {
            ArrayList<TreeNode> childNodeList = new ArrayList<>();
            for (int i = 0; i < model.children.size(); i++) {
                StationModel childCategory = model.children.get(i);
                TreeNode childNode = new TreeNode(
                        new IconTreeItemViewHolder.IconTreeItem(0, 0, childCategory)
                ).setViewHolder(new SmallCategoryViewHolder(instance));
                childNode.setSelectable(true);

                childNodeList.add(childNode);
                addChildNode(childNode, childCategory);
            }

            parentNode.addChildren(childNodeList);
        }
    }

    public TreeNode getSelectedTreeNode() {
        return channel_selected;
    }

    public void selectTreeNode(TreeNode selected) {
        channel_selected = selected;
    }
}
