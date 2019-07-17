package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NinjaBullet extends RunningObject{
	Image imgs[] = new Image[2];//存放两种子弹的图片
	static int img_index=0;//辅助改变英雄子弹图片
	static int img_num=2;//图片总数
	NinjaBullet(Ninja n) {
		for(int i=0;i<imgs.length;i++) {
				try {
			imgs[i] = ImageIO.read(new File("images/shenqi_"+(i+1)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		this.img=imgs[img_index];
		this.y=n.y+n.img.getHeight(null)/2-this.img.getHeight(null)/2;
		this.x=n.x;
		this.xSpeed=20;
		this.ySpeed=0;
	}
	public void move() {
		this.x+=this.xSpeed;
		this.y-=this.ySpeed;
	}
	public boolean outofBounds() {
		// TODO Auto-generated method stub
		return this.x>GamePanel.PANEL_WIDTH;
	}
	public static void changeImage(int wheelRotation) {
		if(wheelRotation<=-1){//若鼠标滚轮向上滚动
			if(img_index==0){
				img_index=img_num-1;
			}else{
				img_index--;
			}
		}else{//若鼠标滚轮向下滚动
			if(img_index==img_num-1){
				img_index=0;
				
			}else{
				img_index++;
			}
			
		}
		
	}
}
