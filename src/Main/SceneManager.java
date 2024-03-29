package Main;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SceneManager {

	public enum Scene
	{
		None,
		Title,
		Game,
		Clear,
		GameOver,
		Ranking
	}

	//判別するためには必要だが今回は使わない
	//public static Scene currentScene = Scene.None;

	public static GameWindow scene;
}

//JFrameを継承している。
class GameWindow extends JFrame implements Runnable{

	BaseScene currentPanel;

	private Thread th = null;

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
		//ゲームループ
		//このままだと10ミリ秒+描画時間で微妙に遅くなっている。
		//TODO : 60fpsに制限する処理
		while(th != null){
			try{
				Thread.sleep(10);	//10ミリ秒休む
				repaint();			//再描画
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void ChangeScene(BaseScene panel) {
		//ContentPaneにはめ込まれたパネルを削除
		if(panel != null) panel.OnDestroy();
		getContentPane().removeAll();

		super.add(panel);//パネルの追加
		currentPanel = panel;
		validate();//更新
		repaint();//再描画
	}
}

class BaseScene extends JPanel
{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Update(g);
		Time.TimeCount();//1フレームの経過時間を計測
		//TODO もうちょっといい方法があるかも？
		ResetFrame();//キーやマウスの初期化
	}

	void ResetFrame()
	{
		KeyInput.resetBool();
		MouseInput.pressed = false;
		MouseInput.released = false;
	}

	boolean start;

	public void Update(Graphics g)
	{
		//1フレームのみ読み込む
		if(!start)
		{
			Start();//シーンごとの初期化を呼ぶ
			start = true;
		}

		//ObjectManager.UpdateObjects(g);
	}

	public void Start()
	{

	}

	public void OnDestroy()
	{
		//ObjectManager.AllDestroyGameObjects();
	}
}
