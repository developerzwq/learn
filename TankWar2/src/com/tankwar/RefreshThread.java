package com.tankwar;

/**
 * 刷新线程
 * 
 * @author Developer
 * 
 */
public class RefreshThread implements Runnable {
	private boolean flag = true;
	private TankClient tankClient = null;
	private TankPanel tankPanel = null;

	public RefreshThread(TankClient tankClient, TankPanel tankPanel) {
		this.tankClient = tankClient;
		this.tankPanel = tankPanel;
	}

	/**
	 * 设置flag变量，以便停止线程
	 * 
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		while (flag) {
			tankClient.repaint();
			tankPanel.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
