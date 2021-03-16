package Main;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Main{
	public static final int DefaultWidth = 800;
	public static final int DefaultHeight = 500;
	
	public static final int Width = 1292;
	public static final int Height = 808;
	
	public static final float MagniWidth = (float)Width / (float)DefaultWidth;
	public static final float MagniHeight = (float)Height / (float)DefaultHeight;
	
	public static void main(String[] args) throws Exception{
		System.out.println(Width);
		System.out.println(DefaultWidth);
		System.out.println((float)Width / (float)DefaultWidth);
		System.out.println(Main.MagniWidth);
		GameWindow gw = new GameWindow("ボス戦シューティング",Width,Height);
		gw.add(new DrawCanvas());
		gw.setVisible(true);
		gw.StartGameLoop();
		
	}
}

//JFrameを継承している。
class GameWindow extends JFrame implements Runnable{
	private Thread th = null;
	
	public GameWindow(String title, int width, int height) 
	{
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width + 6,height + 29);
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(new KeyInput());
		addMouseListener(new MouseInput());
		
		
	}
	
	//ゲームループの開始
	public synchronized void StartGameLoop()
	{
		if ( th == null ) {
			th = new Thread(this);
			th.start();
		}
	}
	//ゲームループの終了
	public synchronized void StopGameLoop()
	{
		if ( th != null ) th = null;
	}
	
	public void run(){
		//ゲームループ（定期的に再描画を実行）
		while(th != null){
			try{
				Thread.sleep(10);//1000で1秒
				repaint();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

class DrawCanvas extends JPanel{
	AudioManager[] bgm = new AudioManager[] {new AudioManager("title.wav",0.5f),new AudioManager("buttle.wav",0.5f)};
	Mapchip map;
	Player player = new Player("PlayerFly001.png",Vector2.Zero);
	Boss boss = new Boss("dragon1.png",Vector2.Zero,new Vector2(1.5f,1.5f));
	
	Button btn = new Button("mapchip.png","スタート",200,200,400,100);
	int mainTimer;//UIのタイマー
	int score;

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
				if(btn.pressed)
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Game;
				}
				
				font = new Font(null,Font.PLAIN,16);
				g.setFont(font);
				Text.drawString(g,"©2021 - Ho'pe",400, 500, Text.AdjustWidth.Center,Text.AdjustHeight.Bottom);
				break;
			case Game:
				map.DrawMapchip(g);
				mainTimer += Time.flameTime;
				
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "Time "+Time.MMSSFF(mainTimer), 433,20);
				Text.drawString(g, "HP " + String.format("%2d", player.hp) + "/" + String.format("%2d", player.MaxHP), 433,140);
				Text.drawString(g, "MP " + String.format("%2d", player.mp) + "/" + String.format("%2d", player.MaxMP), 433,170);
				Text.drawString(g, "HP " + String.format("%2d", boss.hp) + "/" + String.format("%2d", boss.BossMaxHP), 433,256);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,24);
				g.setFont(font);
				Text.drawString(g, "Score " + String.format("%07d", score), 433,60);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
				g.setFont(font);
				Text.drawString(g, "Player", 433,90);
				Text.drawString(g, "Dragon", 433,210);
				
				player.MoveDraw(g, boss);
				boss.MoveDraw(g,player);
				
				
				if(boss.hp <= 0) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(player.hp <= 0) SceneManager.nextScene = SceneManager.Scene.GameOver;
				
				break;
			case Clear:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"クリア",400, 100, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				btn.DrawButton(g);
				if(btn.pressed)
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Title;
				}
				break;
			case GameOver:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"ゲームオーバー",400, 100, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				btn.DrawButton(g);
				if(btn.pressed) 
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Title;
				}
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
				bgm[0].Reset();
				bgm[0].Play();
				btn.text = "スタート";
				break;
			case Game:
				bgm[0].Stop();
				bgm[1].Reset();
				bgm[1].Play();
				player.Init();
				boss.Init();
				mainTimer = 0;
				score = 0;
				map = new Mapchip();
				break;
			case Clear:
				bgm[1].Stop();
				btn.text = "タイトル";
				break;
			case GameOver:
				bgm[1].Stop();
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