package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackGround extends RunningObject{
	Image img_ready;//��Ϸ׼������
	Image img_running;//��Ϸ���н���
	Image img_over;
	BackGround(){
		try {
			//�Ա����е����Ը�ֵ
			img_ready=ImageIO.read(new File("images/background.jpg"));
			img_running=ImageIO.read(new File("images/back.jpg"));
			img_over=ImageIO.read(new File("222.jpg"));
			//�Ը����м̳й��������Ը�ֵ
			img=img_ready;
			x=0;
			y=0;
			xSpeed=0;
			ySpeed=0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void change(int status) {
		if(status==GamePanel.GAME_RUNNING) {
			img=img_running;
		}
		else if(status==GamePanel.GAME_OVER) {
			img=img_over;
		}
		
	}

}
