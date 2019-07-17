package game;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ninja extends RunningObject{
	Image imgs1[]=new Image[4];
	int life=3;//忍者的命数
	int protection=10;
	boolean isSuspend=false;//忍者是否中断延迟(若被打中或撞上，就会中断，延迟)
	int delay_Time=0;//中断延迟的时间
	int img_index=1;

	Ninja() {
		for(int i=0;i<imgs1.length;i++) {
			try {
				imgs1[i]=ImageIO.read(new File("images/mingren_"+(i+1)+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		img=imgs1[1];
		x=GamePanel.PANEL_WIDTH/2-img.getWidth(null)/2;
		y=GamePanel.PANEL_HEIGHT-img.getHeight(null)-50;
		xSpeed=5;
		ySpeed=5;
	}
	public void moveTo(int x,int y)
	{
		this.x=x;
		this.y=y;
		if(x>0) {
			img=imgs1[(img_index++)/4%4];
		}
	}
	public NinjaBullet shoot() {
		// TODO Auto-generated method stub
		return new NinjaBullet(this);
	}
	public NinjaSkill skill() {
		// TODO Auto-generated method stub
		return new NinjaSkill(this);
		}
}
