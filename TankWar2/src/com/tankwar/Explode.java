package com.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import com.tankwar.util.ImageUtil;

/**
 * ±¨’®¿‡
 * 
 * @author Developer
 * 
 */
public class Explode {
	/*
	 * ªÒµ√±¨’®Õº∆¨
	 */
	private Image explodeImage = null;
	/*
	 * ±¨’®Œª÷√
	 */
	private int x;
	private int y;
	private int step = 0;

	public Explode(int x, int y) {
		this.x = x + 1;
		this.y = y + 1;
		try {
			explodeImage = ImageUtil.getImageFromFile("image/boom.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		if (step != 2) {
			g.drawImage(explodeImage, x, y, null);
		} else {
			TankPanel.deadExplodeList.add(this);
			step = 0;
		}
		step++;
		g.setColor(c);
	}

}
