package com.helper.mistletoe.roll;

import com.helper.mistletoe.MistletoeApplication;

import android.media.MediaPlayer;
import android.util.Log;

public class Result {
	private int[] faceValue;
	private int[] count;

	private MediaPlayer mp;

	public Result(Dice[] dice) {
		// TODO Auto-generated constructor stub
		// faceValue = new int[Dices.NUM];// 结果点数
		faceValue = new int[] { -1, -1, -1, -1, -1, -1 };// 结果点数
		count = new int[6];// 结果数量
		// int i = 0;
		int DicesNum = MistletoeApplication.getInstance().getDiceCount();
		for (int i = 0; i < DicesNum; i++) {
			count[dice[i].getFace().getFaceValue()]++;
			faceValue[i] = dice[i].getFace().getFaceValue();
		}
		// for (Dice d : dice) {
		// count[d.getFace().getFaceValue()]++;
		// faceValue[i++] = d.getFace().getFaceValue();
		// }
		mp = null;
		sort();
	}

	private void sort() {
		// 在此机制中1最大，在排序前先将0变为6,再排序
		for (int i = 0; i < Dices.NUM; i++) {
			if (faceValue[i] == 0) {
				faceValue[i] = 6;
			}
		}
		int temp;
		for (int i = 0; i < Dices.NUM; i++) {
			for (int j = i + 1; j < Dices.NUM; j++) {
				if (getCount(faceValue[i]) < getCount(faceValue[j])) {
					temp = faceValue[j];
					faceValue[j] = faceValue[i];
					faceValue[i] = temp;
				}
			}
		}
		for (int i = 0; i < Dices.NUM; i++) {
			for (int j = i + 1; j < Dices.NUM; j++) {
				if (getCount(faceValue[i]) == getCount(faceValue[j])) {
					if (faceValue[i] < faceValue[j]) {
						temp = faceValue[j];
						faceValue[j] = faceValue[i];
						faceValue[i] = temp;
					}
				}
			}
		}
		// 在排序后再将6变为0
		for (int i = 0; i < Dices.NUM; i++) {
			if (faceValue[i] == 6) {
				faceValue[i] = 0;
			}
		}

		for (int i = 0; i < Dices.NUM; i++) {
			Log.i("排序后的" + i, "" + faceValue[i]);
		}
	}

	//
	private int getCount(int i) {
		int quantity = 0;
		if (i == 6) {
			quantity = count[0];
		} else if (i == -1) {
			quantity = -1;
		} else {
			quantity = count[i];
		}
		return quantity;
	}

	public int[] getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(int[] faceValue) {
		this.faceValue = faceValue;
	}

	public MediaPlayer getMp() {
		return mp;
	}
}
