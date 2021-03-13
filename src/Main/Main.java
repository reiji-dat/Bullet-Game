package Main;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
//メインクラス
public class Main{
	public static void main(String[] args) {
		GameWindow gw = new GameWindow("ボス戦シューティング",800,500);
		gw.add(new DrawCanvas());
		
		gw.setVisible(true);
		gw.StartGameLoop();
	}
}

//JFrameを継承している。
class GameWindow extends JFrame implements Runnable{
	private Thread th = null;
	
	public GameWindow(String title, int width, int height) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width + 6,height + 29);
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(new KeyInput());
		addMouseListener(new MouseInput());
		
	}
	
	//ゲームループの開始
	public synchronized void StartGameLoop(){
		if ( th == null ) {
			th = new Thread(this);
			th.start();
		}
	}
	//ゲームループの終了
	public synchronized void StopGameLoop(){
		if ( th != null ) {
			th = null;
		}
	}
	
	public void run(){
		//ゲームループ（定期的に再描画を実行）
		while(th != null){
			try{
				//100fpsに制限
				Thread.sleep(10);//1000で1秒
				repaint();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

class DrawCanvas extends JPanel{
	Mapchip map;
	Player player = new Player("PlayerFly001.png",0,0);
	
	
	ArrayList<Bullet> e_Bullet = new ArrayList<>();
	final int e_Bullet_Cool = 50;
	int e_Bullet_Timer;//敵のランダム撃ちタイマー
	int putternTime;
	int e_pBullet_Timer;
	
	
	GameObject boss = new GameObject("dragon1.png",0,0,1.5f,1.5f);
	
	Button btn = new Button("mapchip.png","スタート",200,200,400,100);
	int mainTimer;//UIのタイマー
	
	int score;
	
	
	final int BossMaxHP = 500;
	public static int b_hp;
	int modeTimer;//ボスの状態タイマー
	boolean invincibleMode = true;
	int atkChangeTimer;
	float volDeg;
	float volDeg2;
	
	enum AttackPattern
	{
		Closs,//縦横
		Pick,//狙い撃ち
		Voluted,//渦巻
		Voluted2,//渦巻&逆渦巻
		SinWave,//sin派
		FireFlower,//花火
		Tracking//追尾
	}
	
	AttackPattern pattern;

	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Update(g);
		Time.TimeCount();
		ResetFrame();
	}
	
	void Update(Graphics g)
	{
		Font font;
		
		switch(SceneManager.currentScene)
		{
			case Title:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"ボス戦シューティング",400, 10, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				btn.DrawButton(g);
				if(btn.pressed) SceneManager.nextScene = SceneManager.Scene.Game;
				
				font = new Font(null,Font.PLAIN,16);
				g.setFont(font);
				Text.drawString(g,"©2021 - Ho'pe",400, 500, Text.AdjustWidth.Center,Text.AdjustHeight.Bottom);
				break;
			case Game:
				map.DrawMapchip(g);
				//デバッグ
				if(KeyInput.inputKey[KeyEvent.VK_P]) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(KeyInput.inputKey[KeyEvent.VK_O]) SceneManager.nextScene = SceneManager.Scene.GameOver;
				
				mainTimer += Time.flameTime;
				/*
				 * UI
				 */
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "Time "+Time.MMSSFF(mainTimer), 433,20);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,24);
				g.setFont(font);
				Text.drawString(g, "Score " + String.format("%07d", score), 433,60);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
				g.setFont(font);
				Text.drawString(g, "Player", 433,90);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "HP " + String.format("%2d", player.hp) + "/" + String.format("%2d", player.MaxHP), 433,140);
				Text.drawString(g, "MP " + String.format("%2d", player.mp) + "/" + String.format("%2d", player.MaxMP), 433,170);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
				g.setFont(font);
				Text.drawString(g, "Dragon", 433,210);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "HP " + String.format("%2d", b_hp) + "/" + String.format("%2d", BossMaxHP), 433,256);
				
				player.MoveDraw(g, boss, invincibleMode);
				/*
				 * 敵
				 */
				boss.DrawObject(g);
				/*
				 * TODO 敵の挙動
				 * 動く(上半分限る)5±2秒で動く
				 * -----------
				 * 数列sin波
				 * 花火(撃って数秒後拡散)
				 * 追尾撃ち(一定距離まで近づいたらまっすぐになる。)
				 * 
				 * TODO 倒し方
				 * 30秒耐久(この間無敵)
				 * 10秒間攻撃できる。(弾幕の数を減らす)
				 * 大体3回繰り返す
				 * クリア
				 */
				modeTimer += Time.flameTime;
				if(invincibleMode)
				{
					if(modeTimer >= 25000) 
					{
						invincibleMode = !invincibleMode;
						modeTimer -= 25000;
					}
					e_Bullet_Timer += Time.flameTime;
					if(e_Bullet_Timer >= e_Bullet_Cool)
					{
						e_Bullet_Timer -= e_Bullet_Cool;
						//TODO 画像情報を毎回取得しているため1度だけで良い
						e_Bullet.add(new Bullet("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
						e_Bullet.get(e_Bullet.size()-1).speed 
						= new Vector2(Vector2.DegreeToVector((float) ((Math.random() * (360)))));
						e_Bullet.get(e_Bullet.size()-1).speed.times(2);
					}
					//パターン攻撃
					e_pBullet_Timer += Time.flameTime;
					if(e_pBullet_Timer >= putternTime)
					{
						e_pBullet_Timer -= putternTime;
						switch(pattern)
						{
							case Closs:
								for(int i = 100; i < 400;i+=100)
								{
									e_Bullet.add(new Bullet("enemy_bullet.png",i,0));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(0,3);
								}
								for(int i = 100; i < 500;i+=100)
								{
									e_Bullet.add(new Bullet("enemy_bullet.png",0,i));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(3,0);
								}
								break;
							case Pick:
								Vector2 a = new Vector2(player.postion);
								float deg = Vector2.Angle(new Vector2(boss.postion.x,boss.postion.y-10),new Vector2(a));
								for(int i = -40;i <= 40;i+=20)
								{
									e_Bullet.add(new Bullet("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(deg + i));
									e_Bullet.get(e_Bullet.size()-1).speed.times(3);
								}
								break;
							case Voluted:
								for(int i = -144;i <= 144;i+=72)
								{
									e_Bullet.add(new Bullet("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
									e_Bullet.get(e_Bullet.size()-1).speed.times(3);
								}
								volDeg+=10;
								break;
							case Voluted2:
								for(int i = -144;i <= 144;i+=72)
								{
									e_Bullet.add(new Bullet("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
									e_Bullet.get(e_Bullet.size()-1).speed.times(3);
									e_Bullet.add(new Bullet("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
									e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg2 + i));
									e_Bullet.get(e_Bullet.size()-1).speed.times(3);
								}
								volDeg+=10;
								volDeg2-=10;
								break;
							default:
								break;
						}
					}
					atkChangeTimer += Time.flameTime;
					if(atkChangeTimer >= 5000)
					{
						//TODO 攻撃パターン抽選
						int next = (int)(Math.random() * 4);
						switch(next)
						{
							case 0:
								pattern = AttackPattern.Closs;
								putternTime = 500;
								break;
							case 1:
								pattern = AttackPattern.Pick;
								putternTime = 333;
								break;
							case 2:
								pattern = AttackPattern.Voluted;
								putternTime = 150;
								volDeg = 0;
								break;
							case 3:
								pattern = AttackPattern.Voluted2;
								putternTime = 150;
								volDeg = 0;
								volDeg2 = 180;
								break;
							default:
								break;
						}
						atkChangeTimer -= 5000;
						e_pBullet_Timer = 0;
					}
				}
				else
				{
					if(modeTimer >= 10000) 
					{
						invincibleMode = !invincibleMode;
						modeTimer -= 10000;
						atkChangeTimer = 0;
					}
				}
				
				for(int i = 0; i < e_Bullet.size();)
				{
					e_Bullet.get(i).movePostion(e_Bullet.get(i).speed);
					if(e_Bullet.get(i).postion.y < 0 
					|| e_Bullet.get(i).postion.y > 500
					|| e_Bullet.get(i).postion.x < 0 
					|| e_Bullet.get(i).postion.x > 400) 
					{
						e_Bullet.remove(i);
						continue;
					}
					if(Collider.EnterCollider(player.postion, new Vector2(e_Bullet.get(i).postion), 10))
					{
						e_Bullet.remove(i);
						player.hp--;
						continue;
					}
					e_Bullet.get(i).DrawObject(g);
					i++;
				}
				
				
				if(b_hp <= 0) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(player.hp <= 0) SceneManager.nextScene = SceneManager.Scene.GameOver;
				
				break;
			case Clear:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"クリア",400, 100, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				btn.DrawButton(g);
				if(btn.pressed) SceneManager.nextScene = SceneManager.Scene.Title;
				break;
			case GameOver:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"ゲームオーバー",400, 100, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				btn.DrawButton(g);
				if(btn.pressed) SceneManager.nextScene = SceneManager.Scene.Title;
				break;
			default:
				break;
		}
		if(SceneManager.nextScene != SceneManager.Scene.None)
		{
			SceneManager.currentScene = SceneManager.nextScene;
			SceneManager.nextScene = SceneManager.Scene.None;
			SceneInit();
		}
	}
	
	void SceneInit()
	{
		switch(SceneManager.currentScene)
		{
			case Title:
				btn.text = "スタート";
				break;
			case Game:
				player.Init();
				mainTimer = 0;
				score = 0;
				b_hp = BossMaxHP;
				map = new Mapchip();
				boss.postion = new Vector2(200,100);
				e_Bullet.clear();
				e_Bullet_Timer = 0;
				atkChangeTimer = 0;
				modeTimer = 0;
				e_pBullet_Timer = 0;;
				invincibleMode = true;
				pattern = AttackPattern.Closs;
				putternTime = 500;
				break;
			case Clear:
				btn.text = "タイトル";
				break;
			case GameOver:
				btn.text = "タイトル";
				break;
			default:
				break;
		}
	}
	
	void ResetFrame()
	{
		KeyInput.resetBool();
		MouseInput.pressed = false;
		MouseInput.released = false;
	}
}

