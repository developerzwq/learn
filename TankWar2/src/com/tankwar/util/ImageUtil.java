package com.tankwar.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 从文件中获取Image对象
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Image getImageFromFile(String filePath) throws IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ImageUtil.class.getClassLoader().getResource(filePath));
		return image;
	}

	/**
	 * 图像左转90度
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static BufferedImage rotate90L(String filePath) throws IOException, URISyntaxException {
		File file = new File(ImageUtil.class.getClassLoader().getResource(filePath).toURI());
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage temp = new BufferedImage(width, height, bufferedImage.getType());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				temp.setRGB(j, width - i - 1, bufferedImage.getRGB(i, j));
			}
		}
		return temp;
	}

	/**
	 * 图像右转90度
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static BufferedImage rotate90R(String filePath) throws IOException, URISyntaxException {
		File file = new File(ImageUtil.class.getClassLoader().getResource(filePath).toURI());
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage temp = new BufferedImage(width, height, bufferedImage.getType());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				temp.setRGB(height - j - 1, i, bufferedImage.getRGB(i, j));
			}
		}
		return temp;
	}

	/**
	 * 图像旋转180度
	 * 
	 * @param bufferedImage
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static BufferedImage rotate180(String filePath) throws IOException, URISyntaxException {
		File file = new File(ImageUtil.class.getClassLoader().getResource(filePath).toURI());
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage temp = new BufferedImage(width, height, bufferedImage.getType());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				temp.setRGB(width - i - 1, height - j - 1, bufferedImage.getRGB(i, j));
			}
		}
		return temp;
	}

}
