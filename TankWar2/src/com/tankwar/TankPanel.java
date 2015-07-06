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
 * ��Ϸ����
 * 
 * @author Developer
 * 
 */
public class TankPanel extends Panel {
	private static final long serialVersionUID = 1L;

	// Point����
	public static Point POINTARRAY[][] = null;

	// ����ͼƬ
	Image bufferedImage = null;

	// �ӵ��б�
	public static List<Bullet> bulletList = new ArrayList<Bullet>();
	public static List<Bullet> deadBulletList = new ArrayList<Bullet>();

	//�ҵ�̹��
	private Tank myTank = null;
	
	// ̹���б�
	public static List<Tank> tankList = new ArrayList<Tank>();
	public static List<Tank> deadTankList = new ArrayList<Tank>();

	// ��ը�б�
	public static List<Explode> explodeList = new ArrayList<Explode>();
	public static List<Explode> deadExplodeList = new ArrayList<Explode>();

	public TankPanel() {
		// ��ʼ��Point����
		PointUtil pointUtility = PointUtil.getPointUtil();
		POINTARRAY = pointUtility.getPointArray();

		// ��Ӱ���������
		this.addKeyListener(new KeyMonitor());
		
		// ���10������̹��
		for (int i = 0; i < 5; i++) {
			Tank tank = new Tank(40 * i + 1, 50, true);
			tankList.add(tank);
		}
		// ��ʼ��һ��̹��
		myTank = new Tank(POINTARRAY[12][4].x, POINTARRAY[12][4].y, false);
		tankList.add(myTank);
	}

	/*
	 * ���Ʒ���
	 */
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		// ����̹��
		for (Tank tank : tankList) {
			tank.isHitTanks(tankList);
			tank.draw(g);
		}
		tankList.removeAll(deadTankList);

		// �����ӵ�
		for (Bullet bullet : bulletList) {
			bullet.isHitTanks(tankList);
			bullet.draw(g);
		}
		bulletList.removeAll(deadBulletList);

		// ���Ʊ�ը
		for (Explode explode : explodeList) {
			explode.draw(g);
		}
		explodeList.removeAll(deadExplodeList);

		g.setColor(c);
	}

	/*
	 * ˫����
	 */
	@Override
	public void update(Graphics g) {
		// ��ͼƬ�ϻ��ƣ�������ɺ�������Ƶ�Panel��
		if (bufferedImage == null)
			bufferedImage = this.createImage(TankClient.PANEL_WIDTH, TankClient.PANEL_HEIGHT);
		Graphics imageGraphics = bufferedImage.getGraphics();
		Color c = imageGraphics.getColor();
		// ���Ʊ���
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.fillRect(0, 0, TankClient.PANEL_WIDTH, TankClient.PANEL_HEIGHT);
		imageGraphics.setColor(c);
		// ����ǰ��
		this.paint(imageGraphics);
		g.drawImage(bufferedImage, 0, 0, null);
	}

	/**
	 * ����������
	 * 
	 * @author Developer
	 * 
	 */
	private class KeyMonitor extends KeyAdapter {

		/*
		 * ��������
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		/*
		 * �����ͷż���
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

	}
}
