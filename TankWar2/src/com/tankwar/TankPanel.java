package com.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.tankwar.util.PointUtil;

/**
 * 游戏界面
 * 
 * @author Developer
 * 
 */
public class TankPanel extends Panel {
	private static final long serialVersionUID = 1L;

	// Point数组
	public static Point POINTARRAY[][] = null;

	// 缓冲图片
	Image bufferedImage = null;

	// 子弹列表
	public static List<Bullet> bulletList = new ArrayList<Bullet>();
	public static List<Bullet> deadBulletList = new ArrayList<Bullet>();

	//我的坦克
	private Tank myTank = null;
	
	// 坦克列表
	public static List<Tank> tankList = new ArrayList<Tank>();
	public static List<Tank> deadTankList = new ArrayList<Tank>();

	// 爆炸列表
	public static List<Explode> explodeList = new ArrayList<Explode>();
	public static List<Explode> deadExplodeList = new ArrayList<Explode>();

	public TankPanel() {
		// 初始化Point数组
		PointUtil pointUtility = PointUtil.getPointUtil();
		POINTARRAY = pointUtility.getPointArray();

		// 添加按键监听器
		this.addKeyListener(new KeyMonitor());
		
		// 添加10个敌人坦克
		for (int i = 0; i < 5; i++) {
			Tank tank = new Tank(40 * i + 1, 50, true);
			tankList.add(tank);
		}
		// 初始化一辆坦克
		myTank = new Tank(POINTARRAY[12][4].x, POINTARRAY[12][4].y, false);
		tankList.add(myTank);
	}

	/*
	 * 绘制方法
	 */
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		// 绘制坦克
		for (Tank tank : tankList) {
			tank.isHitTanks(tankList);
			tank.draw(g);
		}
		tankList.removeAll(deadTankList);

		// 绘制子弹
		for (Bullet bullet : bulletList) {
			bullet.isHitTanks(tankList);
			bullet.draw(g);
		}
		bulletList.removeAll(deadBulletList);

		// 绘制爆炸
		for (Explode explode : explodeList) {
			explode.draw(g);
		}
		explodeList.removeAll(deadExplodeList);

		g.setColor(c);
	}

	/*
	 * 双缓冲
	 */
	@Override
	public void update(Graphics g) {
		// 在图片上绘制，绘制完成后整体绘制到Panel中
		if (bufferedImage == null)
			bufferedImage = this.createImage(TankClient.PANEL_WIDTH, TankClient.PANEL_HEIGHT);
		Graphics imageGraphics = bufferedImage.getGraphics();
		Color c = imageGraphics.getColor();
		// 绘制背景
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.fillRect(0, 0, TankClient.PANEL_WIDTH, TankClient.PANEL_HEIGHT);
		imageGraphics.setColor(c);
		// 绘制前景
		this.paint(imageGraphics);
		g.drawImage(bufferedImage, 0, 0, null);
	}

	/**
	 * 按键监听类
	 * 
	 * @author Developer
	 * 
	 */
	private class KeyMonitor extends KeyAdapter {

		/*
		 * 按键监听
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		/*
		 * 按键释放监听
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

	}
}
