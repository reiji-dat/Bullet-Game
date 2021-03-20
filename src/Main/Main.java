package Main;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
		//ゲームループ
		//このままだと10ミリ秒+描画時間で微妙に遅くなっている。
		while(th != null){
			try{
				Thread.sleep(10);//10ミリ秒休む
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
	AudioManager[] bgm = new AudioManager[] {new AudioManager("audio/title.wav",0.5f),new AudioManager("audio/buttle.wav",0.5f)};
	Mapchip map;
	GameObject frame = new GameObject("image/frame.png",new Vector2(400,250));
	Player player = new Player(Vector2.Zero,"image/PlayerFly001.png","image/PlayerFly002.png","image/PlayerFly003.png");
	Boss boss = new Boss(Vector2.Zero,new Vector2(1.5f,1.5f),"image/dragon1.png","image/dragon2.png","image/dragon3.png","image/dragon4.png","image/dragon5.png");
	
	Button btn = new Button("image/frame_button.png","スタート",200,200,400,100);
	Button rnk = new Button("image/frame_button.png","ランキング",200,325,400,100);
	Button bck = new Button("image/frame_button.png","↩",10,10,100,100);
	int mainTimer;//UIのタイマー

	int highScore = -1;
	
	Ranking rank = new Ranking(5,false,1500000,1500000,1500000,1500000,1500000);
	
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
				
				rnk.DrawButton(g);
				if(rnk.pressed)
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Ranking;
				}
				
				font = new Font(null,Font.PLAIN,16);
				g.setFont(font);
				Text.drawString(g,"©2021 - Ho'pe",400, 500, Text.AdjustWidth.Center,Text.AdjustHeight.Bottom);
				break;
			case Game:
				map.DrawMapchip(g);
				mainTimer += Time.flameTime;
				
				player.MoveDraw(g, boss);
				boss.MoveDraw(g,player);
				frame.DrawObject(g);
				
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,42);
				g.setFont(font);
				Text.drawString(g, "Time "+Time.MMSSFF(mainTimer), 433,20);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "HP " + String.format("%2d", player.hp) + "/" + String.format("%2d", player.MaxHP), 433,140);
				Text.drawString(g, "MP " + String.format("%2d", player.mp) + "/" + String.format("%2d", player.MaxMP), 433,170);
				Text.drawString(g, "HP " + String.format("%2d", boss.hp) + "/" + String.format("%2d", boss.BossMaxHP), 433,256);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
				g.setFont(font);
				Text.drawString(g, "Player", 433,90);
				Text.drawString(g, "Dragon", 433,210);
				
				if(boss.hp <= 0) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(player.hp <= 0) SceneManager.nextScene = SceneManager.Scene.GameOver;
				if(KeyInput.pressedKey[KeyEvent.VK_O])SceneManager.nextScene = SceneManager.Scene.Clear;
				break;
			case Clear:
				
				//この場合値が小さいほど良い。つまり、ランキングは昇順
				//ランキングに追加
				
				
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,55);
				g.setFont(font);
				Text.drawString(g, "Time "+Time.MMSSFF(mainTimer), 400,50,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
				g.setFont(font);
				Text.drawString(g, "HighScore "+Time.MMSSFF(rank.data[0]), 400,100,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"クリア",400, 125, Text.AdjustWidth.Center);
				
				font = new Font(null,Font.PLAIN,32);
				g.setFont(font);
				
				btn.DrawButton(g);
				if(btn.pressed)
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Title;
				}
				
				rnk.DrawButton(g);
				if(rnk.pressed)
				{
					SEManager.PlaySE(SEManager.SE.Select);
					SceneManager.nextScene = SceneManager.Scene.Ranking;
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
			case Ranking:
				font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
				g.setFont(font);
				Text.drawString(g,"ランキング",400, 10, Text.AdjustWidth.Center);
				font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,48);
				g.setFont(font);
				for(int i = 0; i < rank.data.length; i++)Text.drawString(g,(i+1)+" : "+Time.MMSSFF(rank.data[i]),200, 100 + i*54);
				
				bck.DrawButton(g);
				if(bck.pressed) 
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
				bgm[0].PlayLoop();
				btn.text = "スタート";
				break;
			case Game:
				bgm[0].Stop();
				bgm[1].Reset();
				bgm[1].PlayLoop();
				player.Init();
				boss.Init();
				mainTimer = 0;
				map = new Mapchip();
				break;
			case Clear:
				rank.Add(mainTimer);
				SEManager.PlaySE(SEManager.SE.Clear);
				bgm[1].Stop();
				btn.text = "タイトル";
				break;
			case GameOver:
				SEManager.PlaySE(SEManager.SE.Gameover);
				bgm[1].Stop();
				btn.text = "タイトル";
				break;
			case Ranking:
				bgm[0].Stop();
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