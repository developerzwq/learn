package com.tankwar.util;

import java.awt.Point;

public class PointUtil {
	private Point[][] pointArray = new Point[13][13];
	private static PointUtil pointUtil = null;

	/**
	 * ��ʼ��Point����
	 */
	private PointUtil() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				Point point = new Point(x, y);
				pointArray[i][j] = point;
				x += 32;
			}
			y += 32;
			x = 0;
		}
	}

	/**
	 * ����ģʽ
	 * @return
	 */
	public static PointUtil getPointUtil() {
		if (pointUtil == null) {
			return new PointUtil();
		}
		return pointUtil;
	}

	/**
	 * ���Point����
	 * @return
	 */
	public Point[][] getPointArray() {
		return pointArray;
	}
}
