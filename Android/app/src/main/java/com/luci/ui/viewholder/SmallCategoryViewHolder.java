package com.luci.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luci.R;
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

		TextView tvValue = view.findViewById(R.id.txt_value);
		tvValue.setText(value.model.name);
		
		ImageView img_right_arrow = view.findViewById(R.id.img_right_arrow);
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
					IconTreeItemViewHolder.IconTreeItem itemValue = (IconTreeItemViewHolder.IconTreeItem)value;

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
}
