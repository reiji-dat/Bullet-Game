package Main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//キーボード入力用のクラス
class KeyInput implements KeyListener
{
	public static boolean[] inputKey = new boolean[256];
	//押した瞬間のキーを取得
	public void keyPressed(KeyEvent e)
    {
        inputKey[e.getKeyCode()] = true;
    }
	
	//離した瞬間のキーを取得
	public void keyReleased(KeyEvent e)
	{
		inputKey[e.getKeyCode()] = false;
	}
	
	//押し続けている間キーを取得
	public void keyTyped(KeyEvent e)
	{
		inputKey[e.getKeyCode()] = true;
	}
}
