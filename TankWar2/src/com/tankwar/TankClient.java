package com.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏运行的框架
 * 
 * @author Developer
 * 
 */
public class TankClient extends Frame {
	private static final long serialVersionUID = 1L;
	/*
	 * 游戏框架的大小
	 */
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	/*
	 * 游戏显示的大小
	 */
	public static final int PANEL_WIDTH = 416;
	public static final int PANEL_HEIGHT = 416;
	// 游戏界面
	private TankPanel panel = null;

	// 刷新线程
	private RefreshThread refreshThread = null;

	public static void main(String[] args) {
		TankClient client = new TankClient();
		client.launchFrame();
	}

	/**
	 * 创建Frame
	 */
	private void launchFrame() {
		this.setBounds(300, 60, GAME_WIDTH, GAME_HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				refreshThread.setFlag(false);
				System.exit(0);
			}

		});
		panel = new TankPanel();
		panel.setBounds(0, 25, PANEL_WIDTH, PANEL_HEIGHT);
		panel.setBackground(Color.BLACK);
		panel.setVisible(true);
		this.add(panel);
		panel.requestFocus();
		refreshThread = new RefreshThread(this, panel);
		// 启动刷新线程
		new Thread(refreshThread).start();
	}

	/* 
	 * 绘制方法
	 */
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.PLAIN, 13));
		g.drawString("Bullet Count:" + TankPanel.bulletList.size(), 420, 40);
		g.drawString("Tank Count:" + TankPanel.tankList.size(), 420, 60);
		g.setColor(c);
	}

}
