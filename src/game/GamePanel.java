package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener,MouseWheelListener,KeyListener{
	public static final int GAME_READY=0;
	public static final int GAME_RUNNING=1;//游戏运行状态
	public static final int GAME_PAUSE=2;//游戏暂停状态
	public static final int GAME_OVER=3;//游戏结束状态
	int max_jiangshi_num=4;// max_ePlane_num 存放最大敌机数量
	int js2num=8;
	int js3num=2;
	public static final int PANEL_WIDTH=GameFrame.FRAME_WIDTH-7;
	public static final int PANEL_HEIGHT=GameFrame.FRAME_HEIGHT-30;
	public static int Skill_code=0;
	int status=GAME_READY;
	BackGround back=new BackGround();
	StartButton startButton=new StartButton();
	ArrayList<JiangShi> jiangshis=new ArrayList<JiangShi>();
	ArrayList<js2> jiangshis2=new ArrayList<js2>();
	ArrayList<js3> jiangshis3=new ArrayList<js3>();
	ArrayList<NinjaBullet> nBullets=new ArrayList<NinjaBullet>();//忍者子弹列表
	ArrayList<Boom> booms=new ArrayList<Boom>();//爆炸效果 列表对象
	ArrayList<NinjaSkill> nSkills=new ArrayList<NinjaSkill>();//技能列表
	Ninja ninja;
	JButton btnstart=new JButton("");
	JButton btnstop=new JButton("");
    AudioClip audio,renzhebullet;
    JLabel lblLife=new JLabel("命数： ");//显示英雄机命数的标签
    JLabel lblpro=new JLabel("保护值： ");//显示英雄机命数的标签
    int protect=10;
    int totalScore;
    int i=1;

	//构造方法
	public GamePanel() {
		//添加生命值标签
		this.setLayout(null);
		lblpro.setForeground(Color.WHITE);
		lblpro.setFont(new Font("微软雅黑",Font.BOLD,14));
		lblpro.setBounds(10, 10, 100, 20);
		lblLife.setForeground(Color.WHITE);
		lblLife.setFont(new Font("微软雅黑",Font.BOLD,14));
		lblLife.setBounds(10, 30, 100, 20);
		this.add(lblpro);
		this.add(lblLife);
		//初始设置为不在屏幕上显示
		lblpro.setVisible(false);
		lblLife.setVisible(false);
		
		 btnstart.addMouseListener(this);
         btnstart.setBounds(50, 50, 30,30);
 	    btnstop.addMouseListener(this);
         btnstop.setBounds(50, 50,30, 30);
         btnstart.setIcon(new ImageIcon("images/SY-1.png"));
         btnstop.setIcon(new ImageIcon("images/SY-2.png"));
         this.add(btnstart);
  	   
 	    this.add(btnstop);
 	    btnstop.setVisible(false);
 	    btnstart.setVisible(false);
 	   try {
			audio=Applet.newAudioClip(new File("bg.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
	}
	 
	//画图方法
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		back.draw(g);//画僵尸
		startButton.draw(g);//画开始按钮
		//画僵尸
		if(status==GAME_RUNNING || status==GAME_PAUSE) {
			for(int i=0;i<jiangshis.size();i++) {
				JiangShi jiangshi=jiangshis.get(i);
				jiangshi.draw(g);
		}
			for(int i=0;i<jiangshis2.size();i++) {
				js2 jiangshi2=jiangshis2.get(i);
				jiangshi2.draw(g);
		}
			for(int i=0;i<jiangshis3.size();i++) {
				js3 jiangshi3=jiangshis3.get(i);
				jiangshi3.draw(g);
		}
			//画忍者
			if(ninja!=null) {
				ninja.draw(g);
			}
			//画忍者子弹
			for(int i=0;i<nBullets.size();i++) {
				NinjaBullet nButtle=nBullets.get(i);
				nButtle.draw(g);
			}
			//画忍者技能
			for(int i=0;i<nSkills.size();i++) {
				NinjaSkill nSkill=nSkills.get(i);
				nSkill.draw(g);
			}
			//画爆炸对象
			for(int i=0;i<booms.size();i++) {
				Boom boom=booms.get(i);
				boom.draw(g, boom.width, boom.height);
			}
		}
	}
	
	void action() {
		while(true) {
			if(status==GAME_RUNNING) {
				//System.out.println(jiangshis.size());
				for(int i=0;i<jiangshis.size();i++) {
					JiangShi jiangshi=jiangshis.get(i);
					//2.1 僵尸移动
					jiangshi.move();
					if(jiangshi.outofBounds()) {
						jiangshis.remove(i);
						ninja.protection--;
						i--;
						if(ninja.protection==0 ) {
							 audio.stop();//背景音乐停止
							 //isstartplaybgmuxic=false;//是否播放背景音乐改为false
							 status=GAME_OVER;
							 back.change(status);//改变背景图为游戏结束图
						 }
					}
			}
				for(int i=0;i<jiangshis2.size();i++) {
					js2 jiangshi2=jiangshis2.get(i);
					//2.1 僵尸移动
					jiangshi2.move();
					if(jiangshi2.outofBounds()) {
						jiangshis2.remove(i);
						ninja.protection--;
						i--;
						if(ninja.protection==0 ) {
							 audio.stop();//背景音乐停止
							 //isstartplaybgmuxic=false;//是否播放背景音乐改为false
							 status=GAME_OVER;
							 back.change(status);//改变背景图为游戏结束图
						 }
					}
			}
				for(int i=0;i<jiangshis3.size();i++) {
					js3 jiangshi3=jiangshis3.get(i);
					//2.1 僵尸移动
					jiangshi3.move();
					if(jiangshi3.outofBounds()) {
						jiangshis3.remove(i);
						ninja.protection--;
						i--;
						 if(ninja.protection==0 ) {
							 audio.stop();//背景音乐停止
							 //isstartplaybgmuxic=false;//是否播放背景音乐改为false
							 status=GAME_OVER;
							 back.change(status);//改变背景图为游戏结束图
						 }
					}
			}
				//对protection 判断
				
				//4.对忍者子弹的处理
				//System.out.println(hBullets.size());
				for(int i=0;i<nBullets.size();i++) {
					NinjaBullet nBullet=nBullets.get(i);
					//4.1子弹移动
					nBullet.move();
					//4.2若越界，则删除
					if(nBullet.outofBounds()) {
						nBullets.remove(i);
						i--;
					}
				}
				//对忍者技能处理
				for(int i=0;i<nSkills.size();i++) {
					NinjaSkill nSkill=nSkills.get(i);
					//4.1技能移动
					nSkill.move();
					//4.2若越界，则删除
					if(nSkill.outofBounds()) {
						i--;
					}
				}
				//5.碰撞检测
				bangCheck();
				
				//6.对每个爆破对象进行处理
				for(int i=0;i<booms.size();i++) {
					Boom boom=booms.get(i);
					//6.1对象移动
					boom.move();
					//6.2判断爆破是否结束，若结束则清除该对象
					if(boom.isEnd()) {
						booms.remove(i);
						i--;
					}
				}
				//7.对英雄机的中断延迟状态进行处理
				if(ninja.isSuspend) {
					ninja.delay_Time--;//延迟的时间减一
					if(ninja.delay_Time==0) {//若延迟时间为0
						ninja.isSuspend=false;//取消中断延迟
//						all_bomb.play();
						//要发个大招，清空屏幕
						//清除前，把所有敌机分数累加到总游戏分数上
						for(int i=0; i<jiangshis.size(); i++) {
							JiangShi jiangshi=jiangshis.get(i);
							totalScore+=jiangshi.score;
						}
//						jiangshis.clear();
//						nBullets.clear();
//						nSkills.clear();
//						Boom boom=new Boom(0,0,PANEL_WIDTH,PANEL_HEIGHT,0,0);
//						booms.add(boom);
						//使英雄机重新出现在面板正下方
						ninja.y=GamePanel.PANEL_HEIGHT/2-ninja.img.getHeight(null)/2;
						ninja.x=ninja.img.getWidth(null)+100;
					}
				}
				
			//往僵尸中补充对象
				if(jiangshis.size()<max_jiangshi_num) {
				jiangshis.add(new JiangShi());
			}
				if(jiangshis2.size()<js2num) {
					jiangshis2.add(new js2());
				}
				if(jiangshis3.size()<js3num) {
					jiangshis3.add(new js3());
				}
				//9、刷新分数和命数信息
				lblLife.setText("命数： "+ninja.life);
				lblpro.setText("保护值： "+ninja.protection);
				
		}
			
			
			repaint();
			
			//两次重画期间间隔50ms
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	/**
	 * bangCheck方法，主要用来检查忍者和僵尸相遇的情况，以及发生相撞时 爆炸的对象
	 * 相撞主要发生如下两种情况：
	 * 1.忍者与僵尸相撞
	 * 2.僵尸与忍者子弹相撞
	 */
	void bangCheck() {
		//站在僵尸角度判断被撞的情况
		boolean ninja_ishitted=false;//记录忍者是否被撞
		for(int i=0;i<jiangshis.size();i++) {
			JiangShi jiangshi=jiangshis.get(i);
			boolean jiangshi_ishitted=false;//检测僵尸和忍者的相撞情况
			if(jiangshi.ishittedBy(ninja)) {
				ninja_ishitted=true;
				jiangshi_ishitted=true;
			}
			for(int j=0;j<nSkills.size();j++) {
				NinjaSkill nSkill=nSkills.get(j);
				if(jiangshi.ishittedBy(nSkill)) {
					jiangshi_ishitted=true;	 
					nSkills.remove(j);
					j--;
				}
			}
			 //1.2 检测僵尸与忍者子弹相撞情况
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //移出英雄机子弹
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //1.3通过上面检测后，如果该僵尸被撞上，则加上爆炸效果，并移除该僵尸
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1增加一个爆炸对象
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2移除敌机
				 jiangshis.remove(i);
				 i--;
			 }
		}
		//js2
		for(int i=0;i<jiangshis2.size();i++) {
			js2 jiangshi=jiangshis2.get(i);
			boolean jiangshi_ishitted=false;//检测僵尸和忍者的相撞情况
			if(jiangshi.ishittedBy(ninja)) {
				ninja_ishitted=true;
				jiangshi_ishitted=true;
			}
			 //1.2 检测僵尸与忍者子弹相撞情况
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //移出英雄机子弹
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //与技能相撞
			 for(int j=0;j<nSkills.size();j++) {
					NinjaSkill nSkill=nSkills.get(j);
					if(jiangshi.ishittedBy(nSkill)) {
						jiangshi_ishitted=true;	
						nSkills.remove(j);
						j--;
					}
				}
			 //1.3通过上面检测后，如果该僵尸被撞上，则加上爆炸效果，并移除该僵尸
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1增加一个爆炸对象
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2移除敌机
				 jiangshis2.remove(i);
				 i--;
			 }
		}
		//js3
		for(int i=0;i<jiangshis3.size();i++) {
			js3 jiangshi=jiangshis3.get(i);
			boolean jiangshi_ishitted=false;//检测僵尸和忍者的相撞情况
			if(jiangshi.ishittedBy(ninja)) {
				ninja_ishitted=true;
				jiangshi_ishitted=true;
			}
			 //1.2 检测僵尸与忍者子弹相撞情况
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //移出英雄机子弹
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //与技能相撞
			 for(int j=0;j<nSkills.size();j++) {
					NinjaSkill nSkill=nSkills.get(j);
					if(jiangshi.ishittedBy(nSkill)) {
						jiangshi_ishitted=true;	 
						nSkills.remove(j);
						j--;
					}
				}
			 //1.3通过上面检测后，如果该僵尸被撞上，则加上爆炸效果，并移除该僵尸
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1增加一个爆炸对象
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2移除敌机
				 jiangshis3.remove(i);
				 i--;
			 }
		}
		
		
		if(ninja_ishitted) {//如果英雄级被撞了
//			 hero_bomb.play();
//			 Boom boom=new Boom(ninja.x, ninja.y, ninja.img.getWidth(null), ninja.img.getHeight(null), ninja.xSpeed, ninja.ySpeed);
//			 booms.add(boom);
			 //改变英雄机的一些参数
			 ninja.life--;//英雄机减少一条命
			 ninja.isSuspend=true;//英雄机进入中断延迟
			 ninja.delay_Time=40;//英雄机40个时间间隔后重新出现
//			 ninja.y=PANEL_HEIGHT+ninja.img.getHeight(null);
			 //当英雄级命数为0时，游戏结束
			 if(ninja.life==0 ) {
				 audio.stop();//背景音乐停止
				 //isstartplaybgmuxic=false;//是否播放背景音乐改为false
				 status=GAME_OVER;
				 back.change(status);//改变背景图为游戏结束图
				 
			 }
		 }
	}
	

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1 && status==GAME_READY && e.getX()>=startButton.x && e.getX()<=startButton.x+startButton.img.getWidth(null) && 
				e.getY()>=startButton.y && e.getY()<=startButton.y+startButton.img.getHeight(null))
		{
			audio.play();
			btnstart.setVisible(true);
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jiangshis.add(new JiangShi());
			ninja=new Ninja();
			status=GAME_RUNNING;
			lblLife.setVisible(true);
			lblpro.setVisible(true);//显示标签信息
			startButton.remove(status);//点完开始按钮后，通过新建的remove方法把按钮图片消除
			back.change(status);//点完开始后更换
	
		}else if(e.getButton()==MouseEvent.BUTTON3) {//点右键实现游戏的暂停和继续
			if(status==GAME_RUNNING) {//若是运行状态
				status=GAME_PAUSE;//切换成暂停状态
			}else if(status==GAME_PAUSE) {
				status=GAME_RUNNING;
			}
		}else if(status==GAME_RUNNING && e.getButton()==MouseEvent.BUTTON1) {//如果游戏在运行状态时，单击鼠标左键，则发射子弹
			//System.out.println("............");
			NinjaBullet nBullet=ninja.shoot();
			nBullets.add(nBullet);
		}
		else if(status==GAME_OVER&&e.getButton()==MouseEvent.BUTTON1&&
				e.getX()>=535&&e.getX()<=785&&e.getY()>=480&&e.getY()<=595){
			System.out.println(e.getX()+","+e.getY());
			ninja.life=3;
			ninja.protection=10;
			status=GAME_RUNNING;
			back.change(status);//换背景图游戏正在运行时的背景图
		}
		
		//播放音乐
		if(e.getSource()==btnstart){//如果点击了播放音乐按钮
			//System.out.println("你单击了开始");
			audio.stop();
				btnstart.setVisible(false);
				btnstop.setVisible(true);
			}


     else if(e.getSource()==btnstop){//如果点击了停止播放按钮
                              btnstop.setVisible(false);
	              btnstart.setVisible(true);
                               audio.play();}
	             
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method 
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(status==GAME_READY && (e.getX()>=startButton.x && e.getX()<=startButton.x+startButton.img.getWidth(null)) && (e.getY()>=startButton.y && e.getY()<=startButton.y+startButton.img.getHeight(null))) 
		{
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}else {//否则鼠标变成默认形式
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		}
		if(status==GAME_OVER&&e.getX()>=535&&e.getX()<=785
				&&e.getY()>=480&&e.getY()<=595){
			//如果在游戏结束界面上单击指定区域“结束游戏”
			//则鼠标变为手势
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
		}
		//鼠标移动，忍者跟着移动
		if(status==GAME_RUNNING) {
			int x=e.getX()-ninja.img.getWidth(null)/2;
			int y=e.getY()-ninja.img.getHeight(null)/2;
			ninja.moveTo(x, y);//忍者移动
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println("wori");
		if(status==GAME_RUNNING) {
			NinjaBullet.changeImage(e.getWheelRotation());
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(status==GAME_RUNNING) {
			if(i==1){
				if(e.getKeyCode()==KeyEvent.VK_P){
					audio.play();
					btnstart.setVisible(false);
					btnstop.setVisible(true);
					i=0;
					}
				
			}
			else if(i==0){
				
			if(e.getKeyCode()==KeyEvent.VK_P){
				audio.stop();
				 btnstop.setVisible(false);
	             btnstart.setVisible(true);
	             i=1;         
			}
			}
			
		}
		if(status==GAME_RUNNING && e.getKeyCode()==KeyEvent.VK_SPACE) {//如果游戏在运行状态时，单击空格键，则发射大招
			//System.out.println("............");
			Skill_code=0;
			NinjaSkill nSkill=ninja.skill();
			nSkills.add(nSkill);
			jiangshis.clear();
			jiangshis2.clear();
			jiangshis3.clear();
			}
		else if(status==GAME_RUNNING&&e.getKeyChar()==KeyEvent.VK_Q) {
			Skill_code=1;
			NinjaSkill nSkill=ninja.skill();
			nSkills.add(nSkill);
			jiangshis.clear();
			jiangshis2.clear();
			jiangshis3.clear();
			}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
