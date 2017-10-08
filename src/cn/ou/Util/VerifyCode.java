package cn.ou.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 画验证码
 * @author Administrator
 *
 */
public class VerifyCode {
	private int base = 30;
	private int width = 4*base;
	private int height = base;
	private int len = 4;
	private String codes = "23456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	private String[] fontNames = {"微软雅黑", "宋体", "楷体", "隶书", "行楷", "黑体"};
	public void drawImage(OutputStream output){
		Graphics2D g2 = null;
		try {
			//1.创建一个图片缓冲区对象
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			//2.得到绘制环境(拿到画笔)
			g2 = (Graphics2D) bi.getGraphics();
			
			//3.开始画图
			//>>设置背景颜色
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, width, height);
			
			//>>设置画笔颜色
			g2.setColor(Color.GREEN);
			//g2.setColor(new Color(0, 0, 255));

			//>>设置字体
			g2.setFont(new Font("微软雅黑", Font.BOLD, 22));

			for (int i = 0; i < len; i++) {
				//设置随机字体
				g2.setFont(new Font(fontNames[this.getRandom(0, fontNames.length)], Font.BOLD, 22));
				//设置随机颜色
				g2.setColor(new Color(this.getRandom(0, 150), this.getRandom(0, 150), this.getRandom(0, 150)));
				
				double theta = this.getRandom(-30, 30) * Math.PI/180;//旋转的弧度
				g2.rotate(theta, 30*i+7, height-6);
				
				String code = codes.charAt(this.getRandom(0, codes.length()))+"";
				g2.drawString(code, 30*i+7, height-6);
				
				g2.rotate(-theta, 30*i+7, height-6);
			}
			
			//>>画干扰线
			for (int i = 0; i < 5; i++) {
				//设置随机颜色
				g2.setColor(new Color(this.getRandom(0, 150), this.getRandom(0, 150), this.getRandom(0, 150)));
				g2.drawLine(this.getRandom(0, width), this.getRandom(0, height), this.getRandom(0, width), this.getRandom(0, height));
				g2.drawOval(this.getRandom(0, width), this.getRandom(0, height), this.getRandom(0, 7), this.getRandom(0, 7));
			}
			
			//>>添加边框
			g2.setColor(Color.GRAY);
			g2.drawRect(0, 0, width-1, height-1);
			
			//4.保存图片到指定的位置
			ImageIO.write(bi, "JPEG", output);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			//5.释放资源
			g2.dispose();
		}
	}
	
	/**
	 * 返回指定范围内的随机数
	 * @param start
	 * @param end
	 * @return 
	 */
	public int getRandom(int start, int end){
		Random random = new Random();
		return random.nextInt(end-start)+start;
	}
	public static void main(String[] args) throws Exception {
		VerifyCode vc = new VerifyCode();
		vc.drawImage(new FileOutputStream(new File("./WebRoot/img/regist/vc.jpg")));
	}
}
