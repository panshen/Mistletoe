package com.helper.mistletoe.roll;

import java.util.Random;

public class Move {
	Dice[] dice;
	private Random random = new Random();

	public Move(Dice[] dice) {
		// TODO Auto-generated constructor stub
		this.dice = dice;
		init();
	}

	public void init() {
		for (int i = 0; i < Dices.NUM; i++) {
			dice[i].setOrienX(random.nextInt(2) == 0 ? 1 : -1);
			dice[i].setOrienY(random.nextInt(2) == 0 ? 1 : -1);
			dice[i].setPathX(random.nextInt(30) + 10);
			dice[i].setPathY(random.nextInt(30) + 10);
			dice[i].getFace().setFaceValue(random.nextInt(6));
			dice[i].setState(true);
		}
	}

	public void start() {
		for (int i = 0; i < Dices.NUM; i++) {
			// pathX<=0 && pathY<=0 Ϊ������־
			if (dice[i].getPathX() <= 0 && dice[i].getPathY() <= 0) {
				if (!isValid(i)) {
					if (dice[i].getPathX() == 0) {
						dice[i].setPathX(1);
					}
					if (dice[i].getPathY() == 0) {
						dice[i].setPathY(1);
					}
				} else {
					dice[i].setState(false);
				}
			}
			if (!dice[i].isState()) {
				continue;
			} else {
				moveX(i);
				if (!insideX(i)) {
					if (dice[i].getLeft() <= 0) {
						dice[i].setOrienX(1);
					} else if (dice[i].getLeft() >= dice[i].getScreenW() - dice[i].getPicWidth()) {
						dice[i].setOrienX(-1);
					}
				} else if (indice(i) != -1) {
					if (dice[i].getLeft() <= dice[indice(i)].getLeft()) {
						dice[i].setOrienX(-1);
					} else {
						dice[i].setOrienX(1);
					}
				}
				moveY(i);
				if (!insideY(i)) {
					if (dice[i].getTop() <= 0) {
						dice[i].setOrienY(1);
					} else if (dice[i].getTop() >= dice[i].getScreenH() - dice[i].getPicHeight()) {
						dice[i].setOrienY(-1);
					}
				} else if (indice(i) != -1) {
					if (dice[i].getTop() <= dice[indice(i)].getTop()) {
						dice[i].setOrienY(-1);
					} else {
						dice[i].setOrienY(1);
					}
				}
			}
		}
	}

	private void moveX(int i) {
		dice[i].setLeft(dice[i].getLeft() + dice[i].getOrienX() * dice[i].getPathX());
		if (dice[i].getPathX() < 1) {
			dice[i].setPathX(0);
		} else {
			dice[i].setPathX(dice[i].getPathX() - 1);
		}
	}

	private void moveY(int i) {
		dice[i].setTop(dice[i].getTop() + dice[i].getOrienY() * dice[i].getPathY());
		if (dice[i].getPathY() < 1) {
			dice[i].setPathY(0);
		} else {
			dice[i].setPathY(dice[i].getPathY() - 1);
		}
	}

	public boolean insideX(int i) {
		if (dice[i].getLeft() <= dice[i].getScreenW() - dice[i].getPicWidth() && dice[i].getLeft() >= 0) {
			return true;
		}
		return false;
	}

	public boolean insideY(int i) {
		if (dice[i].getTop() <= dice[i].getScreenH() - dice[i].getPicHeight() && dice[i].getTop() >= 0) {
			return true;
		}
		return false;
	}

	public int indice(int i) {
		for (int j = 0; j < Dices.NUM; j++) {
			if (!dice[i].isValid(dice[j]) && i != j) {
				return j;
			}
		}
		return -1;
	}

	private boolean isValid(int i) {
		return (insideX(i) && insideY(i) && indice(i) == -1);
	}

	public boolean isOk() {
		for (int i = 0; i < Dices.NUM; i++) {
			if (insideX(i) && insideY(i) && indice(i) == -1) {
				continue;
			}
			return false;
		}
		return true;
	}
}
