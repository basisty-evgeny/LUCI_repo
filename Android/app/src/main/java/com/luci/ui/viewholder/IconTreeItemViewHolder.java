package com.luci.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.luci.R;
import com.luci.model.StationModel;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */

public class IconTreeItemViewHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemViewHolder.IconTreeItem> {
	public IconTreeItemViewHolder(Context context) {
		super(context);
	}

	@Override
	public View createNodeView(final TreeNode node, IconTreeItem value) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(R.layout.treenode_icon, null, false);

		TextView tvValue = (TextView) view.findViewById(R.id.txt_value);
		tvValue.setText(value.model.name);

		view.findViewById(R.id.btn_add_folder).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TreeNode newFolder = new TreeNode(new IconTreeItem(0, 0, new StationModel()));
				getTreeView().addNode(node, newFolder);
			}
		});

		view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getTreeView().removeNode(node);
			}
		});

		//if My computer
		if (node.getLevel() == 1) {
			view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
		}

		return view;
	}

	@Override
	public void toggle(boolean active) {

	}

	public static class IconTreeItem {
		int mMainCategoryId;
		int mStoreId;
		StationModel model;

		public IconTreeItem(int main_category_id, int store_id, StationModel model) {
			mMainCategoryId = main_category_id;
			mStoreId = store_id;
			this.model = model;
		}

		public StationModel getModel() {
			return model;
		}
	}
}
