package game;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	public static final int FRAME_WIDTH=1300;
	public static final int FRAME_HEIGHT=700;
	
	 GameFrame() {
		this.setTitle("火影战僵尸");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//添加面板
		GamePanel panel=new GamePanel();
		this.add(panel);
		this.setVisible(true);
		panel.action();
	}

	public static void main(String[] args) {
		new GameFrame();

	}

}
