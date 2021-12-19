package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.BGMPlayer.BGM;
import Main.SEPlayer.SE;

/**
 * ゲームシーンを管理するファイル
 */

/**
 * タイトルシーン
 */
class TitleScene extends BaseScene
{
	//ボタン
	private Button btn = new Button("image/frame_button.png","スタート",200,200,400,100);
	private Button rnk = new Button("image/frame_button.png","ランキング",200,325,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.DrawString(g,"ボス戦シューティング",400, 10, Text.AdjustWidth.Center);

		font = new Font(null,Font.PLAIN,32);
		g.setFont(font);
		btn.DrawButton(g);
		if(btn.pressed)
		{
			SEPlayer.PlaySE(SE.Select);
			SceneManager.scene.ChangeScene(new GameScene());
		}

		rnk.DrawButton(g);
		if(rnk.pressed)
		{
			SEPlayer.PlaySE(SE.Select);
			SceneManager.scene.ChangeScene(new RankingScene());
		}

		font = new Font(null,Font.PLAIN,16);
		g.setFont(font);
		Text.DrawString(g,"©2021 - 高橋怜児 var1.1.0-debug",400, 500, Text.AdjustWidth.Center,Text.AdjustHeight.Bottom);
	}

	public void Start()
	{
		BGMPlayer.PlayBGM(BGM.Title);
	}
}

/**
 * ゲームシーン
 */
class GameScene extends BaseScene
{
	//プレイヤー
	private Player player;
	//ボス
	private Boss boss;

	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);
		Font font;

		GameManager.mainTimer += Time.flameTime;

		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,42);
		g.setFont(font);
		Text.DrawString(g, "Time "+Time.MMSSFF(GameManager.mainTimer), 433,20);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
		g.setFont(font);

		if(boss.invincible)
			Text.DrawString(g, "無敵", 433,256);
		else
			Text.DrawString(g, "攻撃チャンス！", 433,256);

		Text.DrawString(g, "HP " + String.format("%2d", player.hp) + "/" + String.format("%2d", player.MaxHP), 433,140);
		Text.DrawString(g, "MP " + String.format("%2d", player.mp) + "/" + String.format("%2d", player.MaxMP), 433,170);
		Text.DrawString(g, "HP " + String.format("%2d", boss.hp) + "/" + String.format("%2d", boss.MaxHP), 433,310);


		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
		g.setFont(font);
		Text.DrawString(g, "Player", 433,90);
		Text.DrawString(g, "Dragon", 433,210);

		//ボスが1体だからこの処理ができる。複数いる場合は別の方法をしなければいけない。
		if(boss.hp <= 0) SceneManager.scene.ChangeScene(new ClearScene());
		if(player.hp <= 0) SceneManager.scene.ChangeScene(new GameOverScene());

		//デバッグ用
		if(Main.DebugMode)
		{
			Font d;
			g.setColor(Color.BLUE);
			d = new Font("ＭＳ Ｐゴシック",Font.BOLD,16);
			g.setFont(d);
			Text.DrawString(g, "デバッグモード有効\nPキーでクリア", 433,350);
			if(KeyInput.pressedKey[KeyEvent.VK_P]) SceneManager.scene.ChangeScene(new ClearScene());

			d = new Font("ＭＳ Ｐゴシック",Font.BOLD,10);
			g.setFont(d);

			Text.DrawString(g,Debug.FrameRate() + "FPS(精度低) 1フレーム" + Time.GetDeltaTime() + "秒",800, 0, Text.AdjustWidth.Right, Text.AdjustHeight.Top);
			g.setColor(Color.BLACK);
		}
	}

	public void Start()
	{
		BGMPlayer.PlayBGM(BGM.Buttle);

		//マップ生成
		ObjectManager.Instantiate(ObjectManager.Instantiate(new Mapchip()));

		boss = (Boss)ObjectManager.Instantiate(
				new Boss(Vector2.Zero,new Vector2(1.5f,1.5f),"image/dragon1.png","image/dragon2.png","image/dragon3.png","image/dragon4.png","image/dragon5.png"));

		player = (Player)ObjectManager.Instantiate(
				new Player(Vector2.Zero,"image/PlayerFly001.png","image/PlayerFly002.png","image/PlayerFly003.png"));

		ObjectManager.Instantiate(new GameObject("image/frame.png",new Vector2(400,250)));

		GameManager.mainTimer = 0;
	}
}

/**
 * クリアシーン
 */
class ClearScene extends BaseScene
{
	private Button ttl = new Button("image/frame_button.png","タイトル",200,200,400,100);
	private Button rnk = new Button("image/frame_button.png","ランキング",200,325,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);
		Font font;

		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,55);
		g.setFont(font);
		Text.DrawString(g, "Time "+Time.MMSSFF(GameManager.mainTimer), 400,50,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
		g.setFont(font);
		Text.DrawString(g, "HighScore "+Time.MMSSFF(GameManager.rank.data[0]), 400,100,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.DrawString(g,"クリア",400, 125, Text.AdjustWidth.Center);

		font = new Font(null,Font.PLAIN,32);
		g.setFont(font);
		ttl.DrawButton(g);
		if(ttl.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new TitleScene());
		}

		rnk.DrawButton(g);
		if(rnk.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new RankingScene());
		}
	}
	public void Start()
	{
		GameManager.rank.Add(GameManager.mainTimer);
		SEPlayer.PlaySE(SEPlayer.SE.Clear);
		BGMPlayer.StopBGM();
	}
}

/**
 * ゲームオーバーシーン
 */
class GameOverScene extends BaseScene
{
	private Button btn = new Button("image/frame_button.png","タイトル",200,200,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.DrawString(g,"ゲームオーバー",400, 100, Text.AdjustWidth.Center);

		font = new Font(null,Font.PLAIN,32);
		g.setFont(font);

		btn.DrawButton(g);
		if(btn.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new TitleScene());
		}
	}
	public void Start()
	{
		SEPlayer.PlaySE(SEPlayer.SE.Gameover);
		BGMPlayer.StopBGM();
	}
}

/**
 * ランキングシーン
 */
class RankingScene extends BaseScene
{
	private Button bck = new Button("image/frame_button.png","↩",10,10,100,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.DrawString(g,"ランキング",400, 10, Text.AdjustWidth.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,48);
		g.setFont(font);
		for(int i = 0; i < GameManager.rank.data.length; i++)Text.DrawString(g,(i+1)+" : "+Time.MMSSFF(GameManager.rank.data[i]),200, 100 + i*54);

		bck.DrawButton(g);
		if(bck.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new TitleScene());
		}
	}
	public void Start()
	{
		BGMPlayer.StopBGM();
	}
}
