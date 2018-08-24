package com.luci.ui.viewholder;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.luci.R;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.model.TreeNode.TreeNodeClickListener;

public class LargeCategoryViewHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemViewHolder.IconTreeItem> {
	private View mView;

	public LargeCategoryViewHolder(Context context) {
		super(context);
	}

	@Override
	public View createNodeView(TreeNode node, IconTreeItemViewHolder.IconTreeItem value) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		mView = inflater.inflate(R.layout.treenode_large_category, null, false);

		TextView tvValue = (TextView) mView.findViewById(R.id.txt_value);
		tvValue.setText(value.model.name);

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
				// scroll to screen center
//				new Handler().postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						if (tView.getView() instanceof ScrollView) {
//							ScrollView scrollTreeView = (ScrollView) tView.getView();
//
//							int[] xy =new int[2];
//						    mView.getLocationOnScreen(xy);
//						    int diff = xy[1] - 300;
//
//						    scrollTreeView.smoothScrollBy(0, diff);
//						}
//					}
//				}, 500);
			}
		});

		return mView;
	}
}
