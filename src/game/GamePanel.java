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
	public static final int GAME_RUNNING=1;//��Ϸ����״̬
	public static final int GAME_PAUSE=2;//��Ϸ��ͣ״̬
	public static final int GAME_OVER=3;//��Ϸ����״̬
	int max_jiangshi_num=4;// max_ePlane_num ������л�����
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
	ArrayList<NinjaBullet> nBullets=new ArrayList<NinjaBullet>();//�����ӵ��б�
	ArrayList<Boom> booms=new ArrayList<Boom>();//��ըЧ�� �б����
	ArrayList<NinjaSkill> nSkills=new ArrayList<NinjaSkill>();//�����б�
	Ninja ninja;
	JButton btnstart=new JButton("");
	JButton btnstop=new JButton("");
    AudioClip audio,renzhebullet;
    JLabel lblLife=new JLabel("������ ");//��ʾӢ�ۻ������ı�ǩ
    JLabel lblpro=new JLabel("����ֵ�� ");//��ʾӢ�ۻ������ı�ǩ
    int protect=10;
    int totalScore;
    int i=1;

	//���췽��
	public GamePanel() {
		//�������ֵ��ǩ
		this.setLayout(null);
		lblpro.setForeground(Color.WHITE);
		lblpro.setFont(new Font("΢���ź�",Font.BOLD,14));
		lblpro.setBounds(10, 10, 100, 20);
		lblLife.setForeground(Color.WHITE);
		lblLife.setFont(new Font("΢���ź�",Font.BOLD,14));
		lblLife.setBounds(10, 30, 100, 20);
		this.add(lblpro);
		this.add(lblLife);
		//��ʼ����Ϊ������Ļ����ʾ
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
	 
	//��ͼ����
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		back.draw(g);//����ʬ
		startButton.draw(g);//����ʼ��ť
		//����ʬ
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
			//������
			if(ninja!=null) {
				ninja.draw(g);
			}
			//�������ӵ�
			for(int i=0;i<nBullets.size();i++) {
				NinjaBullet nButtle=nBullets.get(i);
				nButtle.draw(g);
			}
			//�����߼���
			for(int i=0;i<nSkills.size();i++) {
				NinjaSkill nSkill=nSkills.get(i);
				nSkill.draw(g);
			}
			//����ը����
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
					//2.1 ��ʬ�ƶ�
					jiangshi.move();
					if(jiangshi.outofBounds()) {
						jiangshis.remove(i);
						ninja.protection--;
						i--;
						if(ninja.protection==0 ) {
							 audio.stop();//��������ֹͣ
							 //isstartplaybgmuxic=false;//�Ƿ񲥷ű������ָ�Ϊfalse
							 status=GAME_OVER;
							 back.change(status);//�ı䱳��ͼΪ��Ϸ����ͼ
						 }
					}
			}
				for(int i=0;i<jiangshis2.size();i++) {
					js2 jiangshi2=jiangshis2.get(i);
					//2.1 ��ʬ�ƶ�
					jiangshi2.move();
					if(jiangshi2.outofBounds()) {
						jiangshis2.remove(i);
						ninja.protection--;
						i--;
						if(ninja.protection==0 ) {
							 audio.stop();//��������ֹͣ
							 //isstartplaybgmuxic=false;//�Ƿ񲥷ű������ָ�Ϊfalse
							 status=GAME_OVER;
							 back.change(status);//�ı䱳��ͼΪ��Ϸ����ͼ
						 }
					}
			}
				for(int i=0;i<jiangshis3.size();i++) {
					js3 jiangshi3=jiangshis3.get(i);
					//2.1 ��ʬ�ƶ�
					jiangshi3.move();
					if(jiangshi3.outofBounds()) {
						jiangshis3.remove(i);
						ninja.protection--;
						i--;
						 if(ninja.protection==0 ) {
							 audio.stop();//��������ֹͣ
							 //isstartplaybgmuxic=false;//�Ƿ񲥷ű������ָ�Ϊfalse
							 status=GAME_OVER;
							 back.change(status);//�ı䱳��ͼΪ��Ϸ����ͼ
						 }
					}
			}
				//��protection �ж�
				
				//4.�������ӵ��Ĵ���
				//System.out.println(hBullets.size());
				for(int i=0;i<nBullets.size();i++) {
					NinjaBullet nBullet=nBullets.get(i);
					//4.1�ӵ��ƶ�
					nBullet.move();
					//4.2��Խ�磬��ɾ��
					if(nBullet.outofBounds()) {
						nBullets.remove(i);
						i--;
					}
				}
				//�����߼��ܴ���
				for(int i=0;i<nSkills.size();i++) {
					NinjaSkill nSkill=nSkills.get(i);
					//4.1�����ƶ�
					nSkill.move();
					//4.2��Խ�磬��ɾ��
					if(nSkill.outofBounds()) {
						i--;
					}
				}
				//5.��ײ���
				bangCheck();
				
				//6.��ÿ�����ƶ�����д���
				for(int i=0;i<booms.size();i++) {
					Boom boom=booms.get(i);
					//6.1�����ƶ�
					boom.move();
					//6.2�жϱ����Ƿ������������������ö���
					if(boom.isEnd()) {
						booms.remove(i);
						i--;
					}
				}
				//7.��Ӣ�ۻ����ж��ӳ�״̬���д���
				if(ninja.isSuspend) {
					ninja.delay_Time--;//�ӳٵ�ʱ���һ
					if(ninja.delay_Time==0) {//���ӳ�ʱ��Ϊ0
						ninja.isSuspend=false;//ȡ���ж��ӳ�
//						all_bomb.play();
						//Ҫ�������У������Ļ
						//���ǰ�������ел������ۼӵ�����Ϸ������
						for(int i=0; i<jiangshis.size(); i++) {
							JiangShi jiangshi=jiangshis.get(i);
							totalScore+=jiangshi.score;
						}
//						jiangshis.clear();
//						nBullets.clear();
//						nSkills.clear();
//						Boom boom=new Boom(0,0,PANEL_WIDTH,PANEL_HEIGHT,0,0);
//						booms.add(boom);
						//ʹӢ�ۻ����³�����������·�
						ninja.y=GamePanel.PANEL_HEIGHT/2-ninja.img.getHeight(null)/2;
						ninja.x=ninja.img.getWidth(null)+100;
					}
				}
				
			//����ʬ�в������
				if(jiangshis.size()<max_jiangshi_num) {
				jiangshis.add(new JiangShi());
			}
				if(jiangshis2.size()<js2num) {
					jiangshis2.add(new js2());
				}
				if(jiangshis3.size()<js3num) {
					jiangshis3.add(new js3());
				}
				//9��ˢ�·�����������Ϣ
				lblLife.setText("������ "+ninja.life);
				lblpro.setText("����ֵ�� "+ninja.protection);
				
		}
			
			
			repaint();
			
			//�����ػ��ڼ���50ms
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	/**
	 * bangCheck��������Ҫ����������ߺͽ�ʬ������������Լ�������ײʱ ��ը�Ķ���
	 * ��ײ��Ҫ�����������������
	 * 1.�����뽩ʬ��ײ
	 * 2.��ʬ�������ӵ���ײ
	 */
	void bangCheck() {
		//վ�ڽ�ʬ�Ƕ��жϱ�ײ�����
		boolean ninja_ishitted=false;//��¼�����Ƿ�ײ
		for(int i=0;i<jiangshis.size();i++) {
			JiangShi jiangshi=jiangshis.get(i);
			boolean jiangshi_ishitted=false;//��⽩ʬ�����ߵ���ײ���
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
			 //1.2 ��⽩ʬ�������ӵ���ײ���
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //�Ƴ�Ӣ�ۻ��ӵ�
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //1.3ͨ�������������ý�ʬ��ײ�ϣ�����ϱ�ըЧ�������Ƴ��ý�ʬ
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1����һ����ը����
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2�Ƴ��л�
				 jiangshis.remove(i);
				 i--;
			 }
		}
		//js2
		for(int i=0;i<jiangshis2.size();i++) {
			js2 jiangshi=jiangshis2.get(i);
			boolean jiangshi_ishitted=false;//��⽩ʬ�����ߵ���ײ���
			if(jiangshi.ishittedBy(ninja)) {
				ninja_ishitted=true;
				jiangshi_ishitted=true;
			}
			 //1.2 ��⽩ʬ�������ӵ���ײ���
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //�Ƴ�Ӣ�ۻ��ӵ�
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //�뼼����ײ
			 for(int j=0;j<nSkills.size();j++) {
					NinjaSkill nSkill=nSkills.get(j);
					if(jiangshi.ishittedBy(nSkill)) {
						jiangshi_ishitted=true;	
						nSkills.remove(j);
						j--;
					}
				}
			 //1.3ͨ�������������ý�ʬ��ײ�ϣ�����ϱ�ըЧ�������Ƴ��ý�ʬ
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1����һ����ը����
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2�Ƴ��л�
				 jiangshis2.remove(i);
				 i--;
			 }
		}
		//js3
		for(int i=0;i<jiangshis3.size();i++) {
			js3 jiangshi=jiangshis3.get(i);
			boolean jiangshi_ishitted=false;//��⽩ʬ�����ߵ���ײ���
			if(jiangshi.ishittedBy(ninja)) {
				ninja_ishitted=true;
				jiangshi_ishitted=true;
			}
			 //1.2 ��⽩ʬ�������ӵ���ײ���
			 for(int j=0;j<nBullets.size();j++) {
				 NinjaBullet nBullet=nBullets.get(j);
				 if(jiangshi.ishittedBy(nBullet)) {
					 jiangshi_ishitted=true;	 
					 //�Ƴ�Ӣ�ۻ��ӵ�
					 nBullets.remove(j);
					 j--;
				 }
			 }
			 //�뼼����ײ
			 for(int j=0;j<nSkills.size();j++) {
					NinjaSkill nSkill=nSkills.get(j);
					if(jiangshi.ishittedBy(nSkill)) {
						jiangshi_ishitted=true;	 
						nSkills.remove(j);
						j--;
					}
				}
			 //1.3ͨ�������������ý�ʬ��ײ�ϣ�����ϱ�ըЧ�������Ƴ��ý�ʬ
			 if(jiangshi_ishitted) {
//				 totalScore+=ePlane.score;
//				 enemy_bomb.play();
				 //1.3.1����һ����ը����
				 Boom boom=new Boom(jiangshi.x, jiangshi.y, jiangshi.img.getWidth(null), jiangshi.img.getHeight(null), jiangshi.xSpeed, jiangshi.ySpeed);
				 booms.add(boom);
				 //1.3.2�Ƴ��л�
				 jiangshis3.remove(i);
				 i--;
			 }
		}
		
		
		if(ninja_ishitted) {//���Ӣ�ۼ���ײ��
//			 hero_bomb.play();
//			 Boom boom=new Boom(ninja.x, ninja.y, ninja.img.getWidth(null), ninja.img.getHeight(null), ninja.xSpeed, ninja.ySpeed);
//			 booms.add(boom);
			 //�ı�Ӣ�ۻ���һЩ����
			 ninja.life--;//Ӣ�ۻ�����һ����
			 ninja.isSuspend=true;//Ӣ�ۻ������ж��ӳ�
			 ninja.delay_Time=40;//Ӣ�ۻ�40��ʱ���������³���
//			 ninja.y=PANEL_HEIGHT+ninja.img.getHeight(null);
			 //��Ӣ�ۼ�����Ϊ0ʱ����Ϸ����
			 if(ninja.life==0 ) {
				 audio.stop();//��������ֹͣ
				 //isstartplaybgmuxic=false;//�Ƿ񲥷ű������ָ�Ϊfalse
				 status=GAME_OVER;
				 back.change(status);//�ı䱳��ͼΪ��Ϸ����ͼ
				 
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
			lblpro.setVisible(true);//��ʾ��ǩ��Ϣ
			startButton.remove(status);//���꿪ʼ��ť��ͨ���½���remove�����Ѱ�ťͼƬ����
			back.change(status);//���꿪ʼ�����
	
		}else if(e.getButton()==MouseEvent.BUTTON3) {//���Ҽ�ʵ����Ϸ����ͣ�ͼ���
			if(status==GAME_RUNNING) {//��������״̬
				status=GAME_PAUSE;//�л�����ͣ״̬
			}else if(status==GAME_PAUSE) {
				status=GAME_RUNNING;
			}
		}else if(status==GAME_RUNNING && e.getButton()==MouseEvent.BUTTON1) {//�����Ϸ������״̬ʱ�������������������ӵ�
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
			back.change(status);//������ͼ��Ϸ��������ʱ�ı���ͼ
		}
		
		//��������
		if(e.getSource()==btnstart){//�������˲������ְ�ť
			//System.out.println("�㵥���˿�ʼ");
			audio.stop();
				btnstart.setVisible(false);
				btnstop.setVisible(true);
			}


     else if(e.getSource()==btnstop){//��������ֹͣ���Ű�ť
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
		}else {//���������Ĭ����ʽ
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		}
		if(status==GAME_OVER&&e.getX()>=535&&e.getX()<=785
				&&e.getY()>=480&&e.getY()<=595){
			//�������Ϸ���������ϵ���ָ�����򡰽�����Ϸ��
			//������Ϊ����
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
		}
		//����ƶ������߸����ƶ�
		if(status==GAME_RUNNING) {
			int x=e.getX()-ninja.img.getWidth(null)/2;
			int y=e.getY()-ninja.img.getHeight(null)/2;
			ninja.moveTo(x, y);//�����ƶ�
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
		if(status==GAME_RUNNING && e.getKeyCode()==KeyEvent.VK_SPACE) {//�����Ϸ������״̬ʱ�������ո�����������
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
