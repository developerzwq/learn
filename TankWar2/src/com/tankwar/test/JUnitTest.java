package com.tankwar.test;

import java.awt.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.tankwar.util.PointUtil;

@RunWith(BlockJUnit4ClassRunner.class)
public class JUnitTest {
	@Test
	public void testPointUtil(){
		PointUtil pointUtility = PointUtil.getPointUtil();
		Point pointArray[][] = pointUtility.getPointArray();
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				Point point = pointArray[i][j];
				System.out.println("x-->" + point.x + " y-->" + point.y);
			}
		}
	}
}
