package Main;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
//メインクラス
public class Main{
	public static void main(String[] args) {
		GameWindow gw = new GameWindow("テストウィンドウ",800,500);
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
		setSize(width,height);
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
	GameObject player = new GameObject("PlayerFly001.png",1,1);
	Button btn = new Button("mapchip.png","スタート",200,200,400,100);
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Update(g);
		//player.DrawObject(g);
		ResetFrame();
	}
	
	void Update(Graphics g)
	{
		switch(SceneManager.currentScene)
		{
			case Title:
				Font font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
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
				Text.drawString(g,"ゲーム中",400, 0, Text.AdjustWidth.Center);
				if(KeyInput.inputKey[KeyEvent.VK_P]) SceneManager.nextScene = SceneManager.Scene.Clear;
				if(KeyInput.inputKey[KeyEvent.VK_O]) SceneManager.nextScene = SceneManager.Scene.GameOver;
				break;
			case Clear:
				Text.drawString(g,"クリア",400, 100, Text.AdjustWidth.Center);
				btn.DrawButton(g);
				if(btn.pressed) SceneManager.nextScene = SceneManager.Scene.Title;
				break;
			case GameOver:
				Text.drawString(g,"ゲームオーバー",400, 100, Text.AdjustWidth.Center);
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
		MouseInput.pressed = false;
		MouseInput.released = false;
	}
}

