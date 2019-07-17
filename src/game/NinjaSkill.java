package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NinjaSkill extends RunningObject{
	Image imgs[] = new Image[2];//������ּ��ܵ�ͼƬ
	Image Ls[]=new Image[10];
	static int img_index=0;//�����ı�Ӣ�ۼ���ͼƬ
	static int L_index=0;//�����ı�������ͼƬ
	static int img_num=2;//ͼƬ����
	NinjaSkill(Ninja n) {
		for(int i=0;i<imgs.length;i++) {
				try {
			imgs[i] = ImageIO.read(new File("images/Skill"+(i+1)+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		for (int i=0;i<Ls.length;i++){
			try {
				Ls[i]=ImageIO.read(new File("images/timg000"+i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GamePanel Skill_code=new GamePanel();
		if(GamePanel.Skill_code==0){//������Ϊ�ո�
			img_index=0;
			this.img=imgs[img_index];
			this.y=n.y+n.img.getHeight(null)/2-this.img.getHeight(null)/2;
			this.x=n.x;
			this.xSpeed=40;
			this.ySpeed=0;
		}
		else if(GamePanel.Skill_code==1){//������ΪQ
			img_index=1;
			L_index=1;
		this.img=Ls[L_index];
		this.y=n.y+n.img.getHeight(null)/2-this.img.getHeight(null)/2;
		this.x=n.x;
		this.xSpeed=40;
		this.ySpeed=0;
		}
		
	}
	public void move() {
		if(GamePanel.Skill_code==1){
			L_index++;
			img=Ls[L_index/5%10];
		}
		this.x+=this.xSpeed;
		this.y-=this.ySpeed;
	}
	public boolean outofBounds() {
		// TODO Auto-generated method stub
		return this.x>GamePanel.PANEL_WIDTH;
	}
		
	
	
}