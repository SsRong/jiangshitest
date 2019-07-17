package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JiangShi extends RunningObject{
	Image imgs1[]=new Image[12];
	//Image imgs2[]=new Image[12];
	//Image imgs3[]=new Image[12];
	int score;//敌机的分数
	int img_index=0;
	public JiangShi() {
		for(int i=0;i<imgs1.length;i++) {
			try {
				imgs1[i]=ImageIO.read(new File("images/jiangshi/js"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//对父类初始化
		img=imgs1[0];
		x=img.getWidth(null)+GamePanel.PANEL_WIDTH;
		y=(int)(Math.random()*((GamePanel.PANEL_HEIGHT-img.getHeight(null))/2)+250);
		xSpeed=10;
		ySpeed=0;
	}
	
	void move() {
		this.x-=xSpeed;
			if(x>0) {
				img=imgs1[(img_index++)/10%10];
		}
	}

	public boolean outofBounds() {
		
		return x+img.getWidth(null)<0;
	}
}
