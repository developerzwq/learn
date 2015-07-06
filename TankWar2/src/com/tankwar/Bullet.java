package com.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;

import com.tankwar.util.ImageUtil;

public class Bullet {
	/*
	 * 获得子弹图片
	 */
	private Image bullet_u = null;
	private Image bullet_d = null;
	private Image bullet_l = null;
	private Image bullet_r = null;
	// 子弹图片
	private Image bulletImage = null;
	// 子弹位置，以x、y表示
	private int x;
	private int y;
	// 子弹方向
	private Direction direction = null;
	// 子弹速度
	public static final int BULLETSPEED = 10;
	// 炮弹的阵营
	private boolean isEnemy;

	public Bullet(int x, int y, Direction direction, boolean isEnemy) {
		try {
			bullet_u = ImageUtil.getImageFromFile("image/bullet_u.png");
			bullet_d = ImageUtil.getImageFromFile("image/bullet_d.png");
			bullet_l = ImageUtil.getImageFromFile("image/bullet_l.png");
			bullet_r = ImageUtil.getImageFromFile("image/bullet_r.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.direction = direction;
		// 设置子弹的位置
		switch (direction) {
		case UP:
			this.x = x + 11;
			this.y = y - 10;
			bulletImage = bullet_u;
			break;
		case DOWN:
			this.x = x + 11;
			this.y = y + 32;
			bulletImage = bullet_d;
			break;
		case LEFT:
			this.x = x - 10;
			this.y = y + 11;
			bulletImage = bullet_l;
			break;
		case RIGHT:
			this.x = x + 32;
			this.y = y + 11;
			bulletImage = bullet_r;
			break;
		default:
			break;
		}
		this.isEnemy = isEnemy;
	}

	/**
	 * 绘制方法
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.drawImage(bulletImage, x, y, 10, 10, null);
		g.setColor(c);
		move();
	}

	/**
	 * 子弹移动
	 */
	private void move() {
		switch (direction) {
		case UP:
			y -= BULLETSPEED;
			bulletImage = bullet_u;
			break;
		case DOWN:
			y += BULLETSPEED;
			bulletImage = bullet_d;
			break;
		case LEFT:
			x -= BULLETSPEED;
			bulletImage = bullet_l;
			break;
		case RIGHT:
			x += BULLETSPEED;
			bulletImage = bullet_r;
			break;
		default:
			break;
		}
		if (x < 0 || y < 0 || x > TankClient.PANEL_WIDTH || y > TankClient.PANEL_HEIGHT) {
			TankPanel.deadBulletList.add(this);
		}
	}

	/**
	 * 获得子弹边界矩形
	 * 
	 * @return
	 */
	private Rectangle getBulletRect() {
		return new Rectangle(x, y, 10, 10);
	}

	/**
	 * 是否击中坦克
	 * 
	 * @param tank
	 */
	protected void isHitTank(Tank tank) {
		if (this.getBulletRect().intersects(tank.getTankRec()) && this.isEnemy != tank.isEnemy()) {
			tank.setAlive(false);
			TankPanel.deadTankList.add(tank);
			TankPanel.deadBulletList.add(this);
			Explode explode = new Explode(tank.getTankPosition().x, tank.getTankPosition().y);
			TankPanel.explodeList.add(explode);
		}
	}

	/**
	 * 是否击中所有的坦克
	 * 
	 * @param tank
	 * @return
	 */
	public void isHitTanks(List<Tank> tankList) {
		for (Tank tank : tankList) {
			isHitTank(tank);
		}
	}

}
