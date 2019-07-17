package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackGround extends RunningObject{
	Image img_ready;//游戏准备界面
	Image img_running;//游戏运行界面
	Image img_over;
	BackGround(){
		try {
			//对本类中的属性赋值
			img_ready=ImageIO.read(new File("images/background.jpg"));
			img_running=ImageIO.read(new File("images/back.jpg"));
			img_over=ImageIO.read(new File("222.jpg"));
			//对父类中继承过来的属性赋值
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
