package com.chnghx.algorithm.redpacket;

import java.util.Random;

public class RandomMoneyTest {

	public static void main(String[] args) {
		double totalMoney = 10;
		int count = 10;
		LeftRedPackage leftRedPackage = new LeftRedPackage(totalMoney, count);
		
		while (leftRedPackage.count > 0) {
			double red_package = getRandomMoney(leftRedPackage);
			System.out.println(red_package);
		}
	}

	public static double getRandomMoney(LeftRedPackage _LeftRedPackage) {
		if (_LeftRedPackage.count == 1) {
			_LeftRedPackage.count--;
			return (double) Math.round(_LeftRedPackage.leftMoney * 100) / 100;
		}
		Random r = new Random();
		double min = 0.01;
		double max = _LeftRedPackage.leftMoney / _LeftRedPackage.count * 2;
		double money = r.nextDouble() * max;
		money = money < min ? min : money;
		money = (double) Math.floor(money * 100) / 100;
		_LeftRedPackage.count--;
		_LeftRedPackage.leftMoney -= money;
		return money;
	}

}

class LeftRedPackage {
	int count;
	double leftMoney;
	double totalMoney;

	public LeftRedPackage(double totalMoney, int count) {
		this.totalMoney = totalMoney;
		this.leftMoney = totalMoney;
		this.count = count;
	}
}