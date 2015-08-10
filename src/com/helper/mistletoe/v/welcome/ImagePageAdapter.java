package com.helper.mistletoe.v.welcome;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.R;
import com.helper.mistletoe.v.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class ImagePageAdapter extends PagerAdapter {
	private Context context;
	private LayoutInflater inflater;
	public ViewHolder holder;

	private ArrayList<String> imageResources;
	private DisplayImageOptions displayOpt;

	public ImagePageAdapter(Context context, ArrayList<String> image) {
		try {
			this.context = context;
			this.inflater = LayoutInflater.from(context);
			this.imageResources = image;
			displayOpt = new DisplayImageOptions.Builder().resetViewBeforeLoading(true).showImageOnLoading(R.drawable.welcompage_welcome1).showImageForEmptyUri(R.drawable.welcompage_welcome1)
					.showImageOnFail(R.drawable.welcompage_welcome1).displayer(new SimpleBitmapDisplayer()).considerExifParams(true).cacheInMemory(true).cacheOnDisk(false).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		try {
			container.removeView((View) object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("InflateParams")
	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View result = null;
		try {
			holder = new ViewHolder();
			View imageLayout = inflater.inflate(R.layout.welcompage_imagepage_item_layout, null);
			holder.imageView00 = (ImageView) imageLayout.findViewById(R.id.zImage);
			holder.poition = position;
			updateShow(position);
			view.addView(imageLayout, 0);
			result = imageLayout;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Parcelable saveState() {
		Parcelable result = null;
		try {
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getCount() {
		int result = 0;
		try {
			result = imageResources.size();
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		boolean result = false;
		try {
			result = arg0.equals(arg1);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	private void updateShow(int poti) throws Exception {
		try {
			MistletoeApplication.getInstance(context).getAUIL().displayImage(imageResources.get(poti), holder.imageView00, displayOpt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}