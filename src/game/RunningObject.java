package game;

import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * ��������
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
	void draw(Graphics g,int width,int height) {//���ػ滭����
		g.drawImage(this.img, this.x, this.y, width, height, null);
		
	}
	//�жϵ��ø÷����ķ�����(this)�Ƿ�����һ�����obj����ײ
		public boolean ishittedBy(RunningObject obj) {
			return this.x+this.img.getWidth(null)>=obj.x && 
					this.x<=obj.x+obj.img.getWidth(null) && 
					this.y+this.img.getHeight(null)>=obj.y &&
					this.y<=obj.y+obj.img.getHeight(null);
		}


}
