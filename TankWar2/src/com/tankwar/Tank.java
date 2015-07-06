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
	 * 获得坦克图片
	 */
	private Image tank_u = null;
	private Image tank_d = null;
	private Image tank_l = null;
	private Image tank_r = null;
	private Image enemy_u = null;
	private Image enemy_d = null;
	private Image enemy_l = null;
	private Image enemy_r = null;
	// 坦克图片
	private Image tankImage = null;

	// 坦克位置，以x、y表示
	private int x;
	private int y;

	// 坦克移动速度
	public static final int TANKSPEED = 5;

	// 坦克方向
	private Direction direction = Direction.STOP;
	// 炮筒 方向
	private Direction barrelDirection = Direction.UP;
	// 坦克方向变量
	private boolean bU = false;
	private boolean bD = false;
	private boolean bL = false;
	private boolean bR = false;
	// 定义坦克阵营
	private boolean isEnemy;
	// 运动步骤
	int step = 0;
	// 随机数
	private Random random = new Random();
	// 坦克状态
	private boolean isAlive = true;
	// 坦克历史位置
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
	 * 绘制方法
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
	 * 按键监听
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
	 * 坦克射击
	 */
	private void fire() {
		if (isAlive) {
			Bullet bullet = new Bullet(x, y, barrelDirection, isEnemy);
			TankPanel.bulletList.add(bullet);
		}
	}

	/**
	 * 按键释放监听
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
	 * 确定坦克运动方向
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
	 * 坦克移动
	 */
	private void move() {
		oldX = x;
		oldY = y;
		// 调整炮筒的方向
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
		 * 坦克不能出界
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
		 * 敌军坦克随机运动并且发射炮弹
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
	 * 获得坦克边界矩形
	 * 
	 * @return
	 */
	protected Rectangle getTankRec() {
		return new Rectangle(x, y, 32, 32);
	}

	/**
	 * 获得坦克当前位置
	 * 
	 * @return
	 */
	protected Point getTankPosition() {
		return new Point(x, y);
	}

	/**
	 * 获得坦克阵营
	 * 
	 * @return
	 */
	public boolean isEnemy() {
		return isEnemy;
	}

	/**
	 * 坦克状态
	 * 
	 * @param isAlive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * 是否与坦克碰撞
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
	 * 是否与坦克碰撞
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
	 * 坦克停止
	 */
	private void stay() {
		this.x = oldX;
		this.y = oldY;
	}

}
