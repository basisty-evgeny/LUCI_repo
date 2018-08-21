package com.luci.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.luci.R;
import com.luci.model.StationModel;
import com.luci.ui.viewholder.IconTreeItemViewHolder;
import com.luci.ui.viewholder.LargeCategoryViewHolder;
import com.luci.ui.viewholder.SmallCategoryViewHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;

public class ChooseStationActivity extends BaseActionBarActivity implements
        View.OnClickListener {
    public static ChooseStationActivity instance;

    // UI
    CheckBox chk_sort;
    TextInputEditText edt_search;
    ViewGroup layout_stations;
    AndroidTreeView tree_view;

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

        chk_sort = findViewById(R.id.chk_sort);
        edt_search = findViewById(R.id.edt_search);
        layout_stations = findViewById(R.id.layout_stations);

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_done).setOnClickListener(this);

        showStations();
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
                Intent intent = new Intent(instance, EditStationActivity.class);
                EditStationActivity.mStation = new StationModel();
                startActivity(intent);
            }
            break;

            case R.id.btn_delete: {
                new AlertDialog.Builder(instance)
                        .setMessage(String.format(getString(R.string.dialog_remove), "Echo Server"))
                        .setPositiveButton(R.string.Ok, null)
                        .setNegativeButton(R.string.Cancel, null)
                        .show();
            }
            break;

            case R.id.btn_refresh: {
                showStations();
            }
            break;

            case R.id.btn_done: {
                onBackPressed();
            }
            break;
        }
    }

    private void showStations() {
        StationModel local = new StationModel();
        local.name = "Local";
        local.children = new ArrayList<>();
        local.depth = 0;

        StationModel child0 = new StationModel();
        child0.name = "Echo server";
        child0.depth = 2;
        local.children.add(child0);

        StationModel child1 = new StationModel();
        child1.name = "Call the Luci Office";
        child1.depth = 2;
        local.children.add(child1);

        TreeNode parentNode = new TreeNode(new IconTreeItemViewHolder.IconTreeItem(0, 0, local))
                .setViewHolder(new LargeCategoryViewHolder(instance));

        ArrayList<TreeNode> parentNodeList = new ArrayList<>();
        parentNodeList.add(parentNode);

        addChildNode(parentNode, local);

        final TreeNode root = TreeNode.root();
        root.addChildren(parentNodeList);

        tree_view = new AndroidTreeView(this, root);
        tree_view.setDefaultAnimation(true);
        tree_view.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);

        ViewGroup containerView = findViewById(R.id.layout_stations);
        containerView.removeAllViews();
        containerView.addView(tree_view.getView());

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
                ).setViewHolder(new SmallCategoryViewHolder(this));

                childNodeList.add(childNode);
                addChildNode(childNode, childCategory);
            }

            parentNode.addChildren(childNodeList);
        }
    }
}
