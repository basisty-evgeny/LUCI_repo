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

public class NothingViewHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemViewHolder.IconTreeItem> {

    public NothingViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItemViewHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.treenode_nothing, null, false);

        int normal_padding = (int)view.getResources().getDimension(R.dimen.margin_normal);
        int large_padding = (int)view.getResources().getDimension(R.dimen.margin_large);
        view.setPadding(large_padding * 2, normal_padding, normal_padding, normal_padding);

        return view;
    }
}
