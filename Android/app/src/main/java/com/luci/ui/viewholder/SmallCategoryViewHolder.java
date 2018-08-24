package com.luci.ui.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luci.AppPreferences;
import com.luci.R;
import com.luci.model.StationModel;
import com.luci.ui.activity.ChooseStationActivity;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.model.TreeNode.TreeNodeClickListener;

/**
 * Created by Bogdan Melnychuk on 2/13/15.
 */
public class SmallCategoryViewHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemViewHolder.IconTreeItem> {

    public SmallCategoryViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItemViewHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.treenode_small_category, null, false);

        int normal_padding = (int)view.getResources().getDimension(R.dimen.margin_normal);
        int large_padding = (int)view.getResources().getDimension(R.dimen.margin_large);
        view.setPadding(large_padding*(value.model.depth-1), normal_padding, normal_padding, normal_padding);

        TextView tvValue = (TextView) view.findViewById(R.id.txt_value);
        tvValue.setText(value.model.name);

        TextView tv_last_used = (TextView) view.findViewById(R.id.txt_last_used);
        tv_last_used.setText(value.model.created_at);

        ImageView img_current = (ImageView) view.findViewById(R.id.img_current);
        if (value.getModel().id != AppPreferences.KEY.CURRENT_CHANNEL_ID) {
            img_current.setVisibility(View.INVISIBLE);
        } else {
            img_current.setVisibility(View.VISIBLE);
        }

        ImageView img_right_arrow = (ImageView) view.findViewById(R.id.img_right_arrow);
        if (mNode.isLeaf()) {
            img_right_arrow.setVisibility(View.VISIBLE);
        } else {
            img_right_arrow.setVisibility(View.INVISIBLE);
        }

        mNode.setClickListener(new TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                // TODO Auto-generated method stub
                if (node.isLeaf()) {
                    TreeNode prev_selected = ChooseStationActivity.instance.getSelectedTreeNode();

                    ChooseStationActivity.instance.selectTreeNode(node);

                    if (prev_selected != null)
                        ((SmallCategoryViewHolder)prev_selected.getViewHolder()).setSelected(false);

                    ((SmallCategoryViewHolder)node.getViewHolder()).setSelected(true);

//                    IconTreeItemViewHolder.IconTreeItem itemValue = (IconTreeItemViewHolder.IconTreeItem)value;
//
//					Intent intent = new Intent(context, ProductListActivity.class);
//					intent.putExtra(AppConstant.EXTRA_MAIN_CATEGORY_ID, itemValue.mMainCategoryId);
//					intent.putExtra(AppConstant.EXTRA_STORE_ID, itemValue.mStoreId);
//					intent.putExtra(AppConstant.EXTRA_CATEGORY_ID, itemValue.model.id);
//					intent.putExtra("categoryName", itemValue.model.name);
//					context.startActivity(intent);
                }
            }
        });
        return view;
    }

    public void setSelected(boolean selected) {
        View view = this.getView();

        if (selected) {
            view.setBackgroundColor(Color.rgb(137, 201, 237));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
    }

    public void setVisible(int visible) {
        this.getView().setVisibility(visible);
    }
}
