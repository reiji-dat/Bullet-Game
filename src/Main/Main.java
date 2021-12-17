package Main;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


//var1.1 コーディングのスマート化

//ここに起動系の処理がある。
public class Main{
	//デフォルトの画面サイズ
	public static final int DefaultWidth = 800;
	public static final int DefaultHeight = 500;
	//画面サイズ
	public static final int Width = 1292;
	public static final int Height = 808;
	//デフォルトと現在の倍率(倍率をかけると絵のサイズも変わる)
	public static final float MagniWidth = (float)Width / (float)DefaultWidth;
	public static final float MagniHeight = (float)Height / (float)DefaultHeight;

	//ランキング
	//TODO GameManagerを作る。
	public static Ranking rank = new Ranking(5,false,1500000,1500000,1500000,1500000,1500000);



	public static void main(String[] args) throws Exception{
		//ウィンドウ設定
		SceneManager.scene = new GameWindow("ボス戦シューティング",Width,Height);

		SceneManager.scene.ChangeScene(new TitleScene());
		//SceneManager.scene.add();//キャンバスを追加
		SceneManager.scene.setVisible(true);//表示する
		SceneManager.scene.StartGameLoop();//ゲームループを設定
	}
}

class TitleScene extends BaseScene
{
	//ボタン
	Button btn = new Button("image/frame_button.png","スタート",200,200,400,100);
	Button rnk = new Button("image/frame_button.png","ランキング",200,325,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.drawString(g,"ボス戦シューティング",400, 10, Text.AdjustWidth.Center);

		font = new Font(null,Font.PLAIN,32);
		g.setFont(font);
		btn.DrawButton(g);
		if(btn.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new GameScene());
		}

		rnk.DrawButton(g);
		if(rnk.pressed)
		{
			SEPlayer.PlaySE(SEPlayer.SE.Select);
			SceneManager.scene.ChangeScene(new RankingScene());
		}

		font = new Font(null,Font.PLAIN,16);
		g.setFont(font);
		Text.drawString(g,"©2021 - 高橋怜児",400, 500, Text.AdjustWidth.Center,Text.AdjustHeight.Bottom);
	}

	public void Start()
	{
		BGMPlayer.PlayBGM(BGMPlayer.BGM.Title);
	}

	public void OnDestory()
	{

	}
}

class GameScene extends BaseScene
{
	Mapchip map = new Mapchip();//マップ生成
	//フレーム
	GameObject frame = new GameObject("image/frame.png",new Vector2(400,250));
	//プレイヤー
	Player player = new Player(Vector2.Zero,"image/PlayerFly001.png","image/PlayerFly002.png","image/PlayerFly003.png");
	//ボス
	Boss boss = new Boss(Vector2.Zero,new Vector2(1.5f,1.5f),"image/dragon1.png","image/dragon2.png","image/dragon3.png","image/dragon4.png","image/dragon5.png");

	public static int mainTimer;//UIのタイマー

	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);
		Font font;

		//map.DrawMapchip(g);//マップチップ表示
		mainTimer += Time.flameTime;

		//player.MoveDraw(g);	//プレイヤー表示
		boss.MoveDraw(g,player);	//ボス表示
		frame.DrawObject(g);		//フレーム表示



		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,42);
		g.setFont(font);
		Text.drawString(g, "Time "+Time.MMSSFF(mainTimer), 433,20);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
		g.setFont(font);

		if(boss.invincible)
			Text.drawString(g, "無敵", 433,256);
		else
			Text.drawString(g, "攻撃チャンス！", 433,256);

		Text.drawString(g, "HP " + String.format("%2d", player.hp) + "/" + String.format("%2d", player.MaxHP), 433,140);
		Text.drawString(g, "MP " + String.format("%2d", player.mp) + "/" + String.format("%2d", player.MaxMP), 433,170);
		Text.drawString(g, "HP " + String.format("%2d", boss.hp) + "/" + String.format("%2d", boss.MaxHP), 433,310);
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,48);
		g.setFont(font);
		Text.drawString(g, "Player", 433,90);
		Text.drawString(g, "Dragon", 433,210);

		//ボスが1体だからこの処理ができる。複数いる場合は別の方法をしなければいけない。
		//TODO 適切なシーンに変更
		if(boss.hp <= 0) SceneManager.scene.ChangeScene(new ClearScene());
		if(player.hp <= 0) SceneManager.scene.ChangeScene(new GameOverScene());

		//ObjectManager.UpdateObjects(g);
		//デバッグ用
		if(KeyInput.pressedKey[KeyEvent.VK_P]) SceneManager.scene.ChangeScene(new ClearScene());
	}
	public void Start()
	{
		BGMPlayer.PlayBGM(BGMPlayer.BGM.Buttle);
		boss.Init();
		mainTimer = 0;
	}

	public void OnDestory()
	{

	}
}

class ClearScene extends BaseScene
{
	Button ttl = new Button("image/frame_button.png","タイトル",200,200,400,100);
	Button rnk = new Button("image/frame_button.png","ランキング",200,325,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);
		Font font;

		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,55);
		g.setFont(font);
		Text.drawString(g, "Time "+Time.MMSSFF(GameScene.mainTimer), 400,50,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,32);
		g.setFont(font);
		Text.drawString(g, "HighScore "+Time.MMSSFF(Main.rank.data[0]), 400,100,Text.AdjustWidth.Center,Text.AdjustHeight.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.drawString(g,"クリア",400, 125, Text.AdjustWidth.Center);

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
		Main.rank.Add(GameScene.mainTimer);
		SEPlayer.PlaySE(SEPlayer.SE.Clear);
		BGMPlayer.StopBGM();
	}

	public void OnDestory()
	{

	}
}

class GameOverScene extends BaseScene
{
	Button btn = new Button("image/frame_button.png","タイトル",200,200,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.drawString(g,"ゲームオーバー",400, 100, Text.AdjustWidth.Center);

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

	public void OnDestory()
	{

	}
}

class RankingScene extends BaseScene
{
	Button bck = new Button("image/frame_button.png","↩",10,10,100,100);
	Button btn = new Button("image/frame_button.png","タイトル",200,200,400,100);
	public void Update(Graphics g)
	{
		//必ず最初
		super.Update(g);

		Font font;
		font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.drawString(g,"ランキング",400, 10, Text.AdjustWidth.Center);
		font = new Font("ＭＳ Ｐゴシック",Font.PLAIN,48);
		g.setFont(font);
		for(int i = 0; i < Main.rank.data.length; i++)Text.drawString(g,(i+1)+" : "+Time.MMSSFF(Main.rank.data[i]),200, 100 + i*54);

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

	public void OnDestory()
	{

	}
}