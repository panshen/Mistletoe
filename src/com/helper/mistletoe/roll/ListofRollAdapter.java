package com.helper.mistletoe.roll;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ListofRollAdapter extends BaseAdapter {
	private List<Dice_Bean> list = null;
	private Context mContext;
	private Bitmap[] bitmap = new Bitmap[6];

	public ListofRollAdapter(Context mContext, List<Dice_Bean> list) {
		this.mContext = mContext;
		inint();
		if (list == null) {
			this.list = new ArrayList<Dice_Bean>();
		} else {
			this.list = list;
		}
	}

	private void inint() {
		// if (bitmap != null) {
		// for(int i=0;i<bitmap.length-1;i++){
		// if(bitmap[i]!=null&&bitmap[i].isRecycled()){
		// bitmap[i].recycle();
		// }
		// }
		// bitmap = null;
		// }
		bitmap[0] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice1);
		bitmap[1] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice2);
		bitmap[2] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice3);
		bitmap[3] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice4);
		bitmap[4] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice5);
		bitmap[5] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dice6);
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(List<Dice_Bean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Dice_Bean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.roll_list_item, null);
			viewHolder.picfirst = (ImageView) view.findViewById(R.id.roll_list_item_imageView_first);
			viewHolder.picsecond = (ImageView) view.findViewById(R.id.roll_list_item_imageView_second);
			viewHolder.picthird = (ImageView) view.findViewById(R.id.roll_list_item_imageView_third);
			viewHolder.picfourth = (ImageView) view.findViewById(R.id.roll_list_item_imageView_fourth);
			viewHolder.picfifth = (ImageView) view.findViewById(R.id.roll_list_item_imageView_fifth);
			viewHolder.picsixth = (ImageView) view.findViewById(R.id.roll_list_item_imageView_sixth);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if (list.get(position).getFirstDice() == -1) {
			viewHolder.picfirst.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picfirst.setVisibility(ImageView.VISIBLE);
			viewHolder.picfirst.setImageBitmap(bitmap[list.get(position).getFirstDice()]);
		}
		if (list.get(position).getSecondDice() == -1) {
			viewHolder.picsecond.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picsecond.setVisibility(ImageView.VISIBLE);
			viewHolder.picsecond.setImageBitmap(bitmap[list.get(position).getSecondDice()]);
		}
		if (list.get(position).getThirdDice() == -1) {
			viewHolder.picthird.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picthird.setVisibility(ImageView.VISIBLE);
			viewHolder.picthird.setImageBitmap(bitmap[list.get(position).getThirdDice()]);
		}
		if (list.get(position).getFourthDice() == -1) {
			viewHolder.picfourth.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picfourth.setVisibility(ImageView.VISIBLE);
			viewHolder.picfourth.setImageBitmap(bitmap[list.get(position).getFourthDice()]);
		}
		if (list.get(position).getFifthDice() == -1) {
			viewHolder.picfifth.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picfifth.setVisibility(ImageView.VISIBLE);
			viewHolder.picfifth.setImageBitmap(bitmap[list.get(position).getFifthDice()]);
		}
		if (list.get(position).getSixthDice() == -1) {
			viewHolder.picsixth.setVisibility(ImageView.GONE);
		} else {
			viewHolder.picsixth.setVisibility(ImageView.VISIBLE);
			viewHolder.picsixth.setImageBitmap(bitmap[list.get(position).getSixthDice()]);
		}
		return view;
	}

	final static class ViewHolder {
		ImageView picfirst;
		ImageView picsecond;
		ImageView picthird;
		ImageView picfourth;
		ImageView picfifth;
		ImageView picsixth;
	}

}