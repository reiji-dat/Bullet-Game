package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * シーン管理クラス
 */
public class SceneManager {

	//判別するためには必要だが今回は使わない
	/*
	public enum Scene
	{
		None,
		Title,
		Game,
		Clear,
		GameOver,
		Ranking
	}
	public static Scene currentScene = Scene.None;
	*/
	public static GameWindow scene;
}

//JFrameを継承している。
/**
 * ウィンドウクラス
 */
class GameWindow extends JFrame implements Runnable{
	public BaseScene currentPanel;

	private Thread th = null;

	private final int fps = 60;
	//正確にしたいためdouble型
	private final double sleepAddTime = 1000.0 / fps;

	public GameWindow(String title, int width, int height)
	{
		super(title);//superとは継承先の初期設定を行う処理
		setDefaultCloseOperation(EXIT_ON_CLOSE);//ウィンドウを閉じたら実行を停止する。
		setSize(width + 6,height + 29);//フレーム分のピクセルを考慮(もっといい方法が合えるかも)
		setLocationRelativeTo(null);//ウィンドウ表示場所(nullは中心に表示)
		setResizable(false);//画面サイズの変更設定(無効)
		addKeyListener(new KeyInput());//キー情報を読み込む
		addMouseListener(new MouseInput());//マウス情報を読み込む
	}

	//ゲームループの開始
	public synchronized void StartGameLoop()
	{
		if ( th == null ) { //Threadが開始されていなければ
			th = new Thread(this);
			th.start();		//ゲームループを開始
		}
	}
	//ゲームループの終了
	public synchronized void StopGameLoop()
	{
		if ( th != null ) th = null;//nullじゃなければnullにする。
	}


	public void run(){
		double nextTime = System.currentTimeMillis() + sleepAddTime;
		//ゲームループ
		//このままだと10ミリ秒+描画時間で微妙に遅くなっている。
		//TODO : 60fpsに制限する処理
		while(th != null){
			try{
				long res = (long)nextTime - System.currentTimeMillis();
				if ( res < 0 ) res = 0;
				Thread.sleep(res);
				repaint();
				nextTime += sleepAddTime;
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void ChangeScene(BaseScene panel) {
		//ContentPaneにはめ込まれたパネルを削除
		if(currentPanel != null) currentPanel.OnDestroy();
		getContentPane().removeAll();

		super.add(panel);//パネルの追加
		currentPanel = panel;
		validate();//更新
		repaint();//再描画
	}
}

/**
 * シーンの基本部分
 */
class BaseScene extends JPanel
{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Update(g);
		Time.TimeCount();//1フレームの経過時間を計測
		ResetFrame();//キーやマウスの初期化
	}

	private void ResetFrame()
	{
		KeyInput.resetBool();
		MouseInput.pressed = false;
		MouseInput.released = false;
	}

	boolean start;

	public void Update(Graphics g)
	{
		if(Main.DebugMode)
		{
			Font d;
			d = new Font("ＭＳ Ｐゴシック",Font.BOLD,10);
			g.setFont(d);
			g.setColor(Color.BLUE);
			Text.DrawString(g,Debug.FrameRate() + "FPS(精度低) 1フレーム" + Time.GetDeltaTime() + "秒",800, 0, Text.AdjustWidth.Right, Text.AdjustHeight.Top);
			g.setColor(Color.BLACK);
		}
		//1フレームのみ読み込む
		if(!start)
		{
			Start();//シーンごとの初期化を呼ぶ
			start = true;
		}

		ObjectManager.UpdateObjects(g);
	}

	public void Start()
	{

	}

	public void OnDestroy()
	{
		ObjectManager.AllDestroyGameObjects();
	}
}
