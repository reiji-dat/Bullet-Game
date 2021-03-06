package Main;
import java.awt.Font;
import java.awt.Graphics;

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
	Button test = new Button("mapchip.png",100,100,200,200);
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		Font font = new Font("ＭＳ Ｐゴシック",Font.BOLD,64);
		g.setFont(font);
		Text.drawString(g,"ボス戦シューティング",400, 10, Text.AdjustWidth.Center);
		
		font = new Font(null,Font.PLAIN,32);
		g.setFont(font);
		test.DrawButton(g);
		//player.DrawObject(g);
	}
}

