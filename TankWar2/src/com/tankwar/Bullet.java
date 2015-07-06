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
	 * ����ӵ�ͼƬ
	 */
	private Image bullet_u = null;
	private Image bullet_d = null;
	private Image bullet_l = null;
	private Image bullet_r = null;
	// �ӵ�ͼƬ
	private Image bulletImage = null;
	// �ӵ�λ�ã���x��y��ʾ
	private int x;
	private int y;
	// �ӵ�����
	private Direction direction = null;
	// �ӵ��ٶ�
	public static final int BULLETSPEED = 10;
	// �ڵ�����Ӫ
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
		// �����ӵ���λ��
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
	 * ���Ʒ���
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
	 * �ӵ��ƶ�
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
	 * ����ӵ��߽����
	 * 
	 * @return
	 */
	private Rectangle getBulletRect() {
		return new Rectangle(x, y, 10, 10);
	}

	/**
	 * �Ƿ����̹��
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
	 * �Ƿ�������е�̹��
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
