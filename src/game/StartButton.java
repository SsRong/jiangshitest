package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StartButton extends RunningObject{
	Image img_start;//游戏开始按钮图片
	StartButton() {
		try {
			img_start=ImageIO.read(new File("images/start.png"));
			img=img_start;
			x=GamePanel.PANEL_WIDTH/2-img.getWidth(null)-300;
			y=GamePanel.PANEL_HEIGHT/2;
			xSpeed=0;
			ySpeed=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void remove(int status) {
		if(status==GamePanel.GAME_RUNNING) {
			img=null;
		}
		
	}
}
