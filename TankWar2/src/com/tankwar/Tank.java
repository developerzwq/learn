package com.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import com.tankwar.util.ImageUtil;

public class Tank {
	/*
	 * ���̹��ͼƬ
	 */
	private Image tank_u = null;
	private Image tank_d = null;
	private Image tank_l = null;
	private Image tank_r = null;
	private Image enemy_u = null;
	private Image enemy_d = null;
	private Image enemy_l = null;
	private Image enemy_r = null;
	// ̹��ͼƬ
	private Image tankImage = null;

	// ̹��λ�ã���x��y��ʾ
	private int x;
	private int y;

	// ̹���ƶ��ٶ�
	public static final int TANKSPEED = 5;

	// ̹�˷���
	private Direction direction = Direction.STOP;
	// ��Ͳ ����
	private Direction barrelDirection = Direction.UP;
	// ̹�˷������
	private boolean bU = false;
	private boolean bD = false;
	private boolean bL = false;
	private boolean bR = false;
	// ����̹����Ӫ
	private boolean isEnemy;
	// �˶�����
	int step = 0;
	// �����
	private Random random = new Random();
	// ̹��״̬
	private boolean isAlive = true;
	// ̹����ʷλ��
	private int oldX;
	private int oldY;

	public Tank(int x, int y, boolean isEnemy) {
		this.x = x;
		this.y = y;
		this.isEnemy = isEnemy;
		try {
			tank_u = ImageUtil.getImageFromFile("image/myTank_u.png");
			tank_d = ImageUtil.getImageFromFile("image/myTank_d.png");
			tank_l = ImageUtil.getImageFromFile("image/myTank_l.png");
			tank_r = ImageUtil.getImageFromFile("image/myTank_r.png");
			enemy_u = ImageUtil.getImageFromFile("image/light_tank.png");
			enemy_d = ImageUtil.rotate180("image/light_tank.png");
			enemy_l = ImageUtil.rotate90L("image/light_tank.png");
			enemy_r = ImageUtil.rotate90R("image/light_tank.png");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (isEnemy) {
			tankImage = enemy_d;
		} else {
			tankImage = tank_u;
		}
	}

	/**
	 * ���Ʒ���
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.drawImage(tankImage, x, y, null);
		g.setColor(c);
		move();
	}

	/**
	 * ��������
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_UP:
			bU = true;
			tankImage = tank_u;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			tankImage = tank_d;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			tankImage = tank_l;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			tankImage = tank_r;
			break;
		default:
			break;
		}
		locateDirection();
	}

	/**
	 * ̹�����
	 */
	private void fire() {
		if (isAlive) {
			Bullet bullet = new Bullet(x, y, barrelDirection, isEnemy);
			TankPanel.bulletList.add(bullet);
		}
	}

	/**
	 * �����ͷż���
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		}
		locateDirection();
	}

	/**
	 * ȷ��̹���˶�����
	 */
	private void locateDirection() {
		if (bR && !bL && !bU && !bD) {
			direction = Direction.RIGHT;
		} else if (!bR && bL && !bU && !bD) {
			direction = Direction.LEFT;
		} else if (!bR && !bL && bU && !bD) {
			direction = Direction.UP;
		} else if (!bR && !bL && !bU && bD) {
			direction = Direction.DOWN;
		} else {
			direction = Direction.STOP;
		}
	}

	/**
	 * ̹���ƶ�
	 */
	private void move() {
		oldX = x;
		oldY = y;
		// ������Ͳ�ķ���
		if (direction != Direction.STOP) {
			barrelDirection = direction;
		}
		switch (direction) {
		case UP:
			y -= TANKSPEED;
			if (isEnemy)
				tankImage = enemy_u;
			break;
		case DOWN:
			y += TANKSPEED;
			if (isEnemy)
				tankImage = enemy_d;
			break;
		case LEFT:
			x -= TANKSPEED;
			if (isEnemy)
				tankImage = enemy_l;
			break;
		case RIGHT:
			x += TANKSPEED;
			if (isEnemy)
				tankImage = enemy_r;
			break;
		case STOP:
			break;
		default:
			break;
		}
		/*
		 * ̹�˲��ܳ���
		 */
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x > TankPanel.POINTARRAY[12][12].x)
			x = TankPanel.POINTARRAY[12][12].x;
		if (y > TankPanel.POINTARRAY[12][12].y)
			y = TankPanel.POINTARRAY[12][12].y;
		/*
		 * �о�̹������˶����ҷ����ڵ�
		 */
		if (isEnemy) {
			Direction directions[] = Direction.values();
			if (step == 0) {
				step = random.nextInt(15) + 3;
				direction = directions[random.nextInt(directions.length)];
			}
			step--;
			if (random.nextInt(40) > 38)
				fire();
		}
	}

	/**
	 * ���̹�˱߽����
	 * 
	 * @return
	 */
	protected Rectangle getTankRec() {
		return new Rectangle(x, y, 32, 32);
	}

	/**
	 * ���̹�˵�ǰλ��
	 * 
	 * @return
	 */
	protected Point getTankPosition() {
		return new Point(x, y);
	}

	/**
	 * ���̹����Ӫ
	 * 
	 * @return
	 */
	public boolean isEnemy() {
		return isEnemy;
	}

	/**
	 * ̹��״̬
	 * 
	 * @param isAlive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * �Ƿ���̹����ײ
	 * 
	 * @param tank
	 * @return
	 */
	public boolean isHitTank(Tank tank) {
		if (!this.equals(tank) && isAlive && this.getTankRec().intersects(tank.getTankRec())) {
			stay();
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ���̹����ײ
	 * 
	 * @param tankList
	 * @return
	 */
	public boolean isHitTanks(List<Tank> tankList) {
		for (Tank tank : tankList) {
			isHitTank(tank);
		}
		return false;
	}

	/**
	 * ̹��ֹͣ
	 */
	private void stay() {
		this.x = oldX;
		this.y = oldY;
	}

}
