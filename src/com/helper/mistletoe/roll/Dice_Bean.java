package com.helper.mistletoe.roll;

import android.os.Parcel;
import android.os.Parcelable;

public class Dice_Bean implements Parcelable {
	int firstDice;//
	int secondDice;//
	int thirdDice;//
	int fourthDice;//
	int fifthDice;//
	int sixthDice;//
	Long diceTime;// 目标的最后更新时间

	private Dice_Bean(Parcel in) {
		firstDice = in.readInt();
		secondDice = in.readInt();
		thirdDice = in.readInt();
		fourthDice = in.readInt();
		fifthDice = in.readInt();
		sixthDice = in.readInt();
		diceTime = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(firstDice);
		dest.writeInt(secondDice);
		dest.writeInt(thirdDice);
		dest.writeInt(fourthDice);
		dest.writeInt(fifthDice);
		dest.writeInt(sixthDice);
		dest.writeLong(diceTime);
	}

	public Dice_Bean() {
		firstDice = -1;
		secondDice = -1;
		thirdDice = -1;
		fourthDice = -1;
		fifthDice = -1;
		sixthDice = -1;
		diceTime = 0L;
	}

	public int getFirstDice() {
		return firstDice;
	}

	public void setFirstDice(int firstDice) {
		this.firstDice = firstDice;
	}

	public int getSecondDice() {
		return secondDice;
	}

	public void setSecondDice(int secondDice) {
		this.secondDice = secondDice;
	}

	public int getThirdDice() {
		return thirdDice;
	}

	public void setThirdDice(int thirdDice) {
		this.thirdDice = thirdDice;
	}

	public int getFourthDice() {
		return fourthDice;
	}

	public void setFourthDice(int fourthDice) {
		this.fourthDice = fourthDice;
	}

	public int getFifthDice() {
		return fifthDice;
	}

	public void setFifthDice(int fifthDice) {
		this.fifthDice = fifthDice;
	}

	public int getSixthDice() {
		return sixthDice;
	}

	public void setSixthDice(int sixthDice) {
		this.sixthDice = sixthDice;
	}

	public void setDiceTime(Long diceTime) {
		this.diceTime = diceTime;
	}

	public Long getDiceTime() {
		if (diceTime == null) {
			diceTime = 0L;
		}
		return diceTime;
	}

	public void setDiceTime(long diceTime) {
		this.diceTime = diceTime;
	}

	public static final Parcelable.Creator<Dice_Bean> CREATOR = new Parcelable.Creator<Dice_Bean>() {
		@Override
		public Dice_Bean createFromParcel(Parcel in) {
			return new Dice_Bean(in);
		}

		@Override
		public Dice_Bean[] newArray(int size) {
			return new Dice_Bean[size];
		}
	};

	public static Parcelable.Creator<Dice_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

}