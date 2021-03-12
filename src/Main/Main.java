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
	GameObject player = new GameObject("PlayerFly001.png",0,0);
	ArrayList<GameObject> p_Bullet = new ArrayList<>();
	
	ArrayList<GameObject> e_Bullet = new ArrayList<>();
	ArrayList<Vector2> e_Bullet_Speed = new ArrayList<>();
	final int e_Bullet_Cool = 50;
	int e_Bullet_Timer = 0;
	
	
	GameObject boss = new GameObject("dragon1.png",0,0,1.5f,1.5f);
	
	float playerSpeed = 3;
	final float P_BulletSpeed = 4;
	Button btn = new Button("mapchip.png","スタート",200,200,400,100);
	int mainTimer;
	int bulletTimer;
	int score;
	final int MaxHP = 5;
	int hp;
	final int MaxMP = 30;
	int mp;
	
	final int BossMaxHP = 500;
	int b_hp;
	int modeTimer = 0;
	boolean invincibleMode = true;

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
				System.out.println(Debug.FrameRate());
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
				Text.drawString(g, "HP " + String.format("%2d", hp) + "/" + String.format("%2d", MaxHP), 433,140);
				Text.drawString(g, "MP " + String.format("%2d", mp) + "/" + String.format("%2d", MaxMP), 433,170);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
				g.setFont(font);
				Text.drawString(g, "Dragon", 433,210);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "HP " + String.format("%2d", b_hp) + "/" + String.format("%2d", BossMaxHP), 433,256);
				
				
				/*
				 * プレイヤー
				 */
				//移動
				if(KeyInput.inputKey[KeyEvent.VK_W]) player.postion.y -= playerSpeed;
				if(KeyInput.inputKey[KeyEvent.VK_A]) player.postion.x -= playerSpeed;
				if(KeyInput.inputKey[KeyEvent.VK_S]) player.postion.y += playerSpeed;
				if(KeyInput.inputKey[KeyEvent.VK_D]) player.postion.x += playerSpeed;
				
				//移動制限
				player.postion.x = player.postion.x < 0 ? 0 : player.postion.x;
				player.postion.x = player.postion.x > 400 ? 400 : player.postion.x;
				player.postion.y = player.postion.y < 0 ? 0 : player.postion.y;
				player.postion.y = player.postion.y > 500 ? 500 : player.postion.y;
				player.DrawObject(g);
				
				//弾
				if(bulletTimer < 500)
				{
					bulletTimer += Time.flameTime;
				}
				else if(mp < MaxMP)
				{
					bulletTimer -= 500;
					mp++;
				}
				
				if(KeyInput.pressedKey[KeyEvent.VK_SPACE] && mp > 0) 
					{
						mp--;
						p_Bullet.add(new GameObject("player_bullet.png",player.postion.x,player.postion.y-10));
						p_Bullet.add(new GameObject("player_bullet.png",player.postion.x+20,player.postion.y+5));
						p_Bullet.add(new GameObject("player_bullet.png",player.postion.x-20,player.postion.y+5));
						System.out.println("撃った");
					}
				for(int i = 0; i < p_Bullet.size();)
				{
					p_Bullet.get(i).postion.y-=P_BulletSpeed;
					if(p_Bullet.get(i).postion.y < 0 
					|| p_Bullet.get(i).postion.x < 0 
					|| p_Bullet.get(i).postion.x > 400) 
					{
						p_Bullet.remove(i);
						continue;
					}
					if(p_Bullet.get(i).postion.x >= boss.postion.x - boss.size.x / 2
					&& p_Bullet.get(i).postion.x <= boss.postion.x + boss.size.x / 2
					&& p_Bullet.get(i).postion.y >= boss.postion.y - boss.size.y / 2
					&& p_Bullet.get(i).postion.y <= boss.postion.y + boss.size.y / 2
					&& !invincibleMode)
					{
						b_hp--;
						p_Bullet.remove(i);
						continue;
					}
					p_Bullet.get(i).DrawObject(g);
					i++;//物を消すと移動処理されないので、消したときは足さずに次のループへ
				}
				
				/*
				 * 敵
				 */
				boss.DrawObject(g);
				/*
				 * TODO 敵の挙動
				 * 動く(上半分限る)5±2秒で動く
				 * -----------
				 * [方向さえ決まれば一生まっすぐ]
				 * ランダム拡散撃ち
				 * 縦連撃ち&&横連撃ち
				 * プレイヤーに向かって打つ(何方向か余計なのも)
				 * 渦巻撃ち
				 * 渦巻撃ち&&逆渦巻
				 * 
				 * 数列sin波
				 * 花火(撃って数秒後拡散)
				 * 追尾撃ち(一定距離まで近づいたらまっすぐになる。)
				 * 
				 * TODO 倒し方
				 * 30秒耐久(この間無敵)
				 * 10秒間攻撃できる。(弾幕の数を減らす)
				 * 大体5回繰り返す
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
						e_Bullet.add(new GameObject("enemy_bullet.png",boss.postion.x,boss.postion.y-10));
						e_Bullet_Speed.add(new Vector2(Vector2.DegreeToVector((float) ((Math.random() * (360))))));
						e_Bullet_Speed.get(e_Bullet_Speed.size()-1).times(2);
					}
				}
				else
				{
					if(modeTimer >= 10000) 
					{
						invincibleMode = !invincibleMode;
						modeTimer -= 10000;
					}
				}
				
				for(int i = 0; i < e_Bullet.size();)
				{
					
					e_Bullet.get(i).movePostion(e_Bullet_Speed.get(i));
					if(e_Bullet.get(i).postion.y < 0 
					|| e_Bullet.get(i).postion.y > 500
					|| e_Bullet.get(i).postion.x < 0 
					|| e_Bullet.get(i).postion.x > 400) 
					{
						e_Bullet.remove(i);
						e_Bullet_Speed.remove(i);
						continue;
					}
					
					if(Collider.EnterCollider(new Vector2(player.postion), new Vector2(e_Bullet.get(i).postion), 10))
					{
						e_Bullet.remove(i);
						e_Bullet_Speed.remove(i);
						hp--;
						continue;
					}
					
					
					
					e_Bullet.get(i).DrawObject(g);
					System.out.println(Collider.EnterCollider(new Vector2(player.postion), new Vector2(e_Bullet.get(i).postion), 10));
					i++;
				}
				
				
				if(b_hp <= 0) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(hp <= 0) SceneManager.nextScene = SceneManager.Scene.GameOver;
				
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
				mainTimer = 0;
				bulletTimer = 0;
				score = 0;
				hp = MaxHP;
				mp = MaxMP;
				b_hp = BossMaxHP;
				map = new Mapchip();
				player.postion = new Vector2(200,400);
				boss.postion = new Vector2(200,100);
				p_Bullet.clear();
				e_Bullet.clear();
				e_Bullet_Speed.clear();
				e_Bullet_Timer = 0;
				modeTimer = 0;
				invincibleMode = true;
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

