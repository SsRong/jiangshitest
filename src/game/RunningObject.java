package game;

import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * 奔跑物类
 *
 */
public class RunningObject {
	Image img;
	int x;
	int y;
	int xSpeed;
	int ySpeed;
	void draw(Graphics g) {
		g.drawImage(this.img, this.x, this.y, null);
	}
	void draw(Graphics g,int width,int height) {//重载绘画方法
		g.drawImage(this.img, this.x, this.y, width, height, null);
		
	}
	//判断调用该方法的飞行物(this)是否与另一飞行物（obj）相撞
		public boolean ishittedBy(RunningObject obj) {
			return this.x+this.img.getWidth(null)>=obj.x && 
					this.x<=obj.x+obj.img.getWidth(null) && 
					this.y+this.img.getHeight(null)>=obj.y &&
					this.y<=obj.y+obj.img.getHeight(null);
		}


}
