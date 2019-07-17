package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Boom extends RunningObject{
	Image imgs[]=new Image[6];
	int img_index=0;//用来辅助产生动画效果
	int width;//画 爆炸效果 时的宽度
	int height;//画 爆炸效果 时的高度
	
	Boom(int x,int y,int width,int height,int xSpeed,int ySpeed) {
		for(int i=0;i<imgs.length;i++) {
			try {
				imgs[i]=ImageIO.read(new File("images/blast_"+(i+1)+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.img=imgs[0];
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.xSpeed=xSpeed;
		this.ySpeed=ySpeed;
		
		
	}

	public void move() {
		this.x+=this.xSpeed;
		this.y+=this.ySpeed;
		//更换图片
		img_index++;
		this.img=imgs[img_index/3%imgs.length];
		
	}

	public boolean isEnd() {
		return img_index==imgs.length*3;
	}

}