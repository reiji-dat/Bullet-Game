package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * キーボード入力用のクラス
 */
class KeyInput implements KeyListener
{
	public static boolean[] inputKey = new boolean[256];
	public static boolean[] pressedKey = new boolean[256];
	private boolean[] onceKey = new boolean[256];

	/**
	 * 押した瞬間のキーを取得
	 */
	public void keyPressed(KeyEvent e)
    {
		if(e.getKeyCode() >= 256) return;
		inputKey[e.getKeyCode()] = true;
		if(!onceKey[e.getKeyCode()])
		{
			pressedKey[e.getKeyCode()] = true;
			onceKey[e.getKeyCode()] = true;
		}
    }

	/**
	 * 離した瞬間のキーを取得
	 */
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() >= 256) return;
		inputKey[e.getKeyCode()] = false;
		pressedKey[e.getKeyCode()] = false;
		onceKey[e.getKeyCode()] = false;
	}

	/**
	 * 押し続けている間キーを取得
	 */
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyCode() >= 256) return;
		inputKey[e.getKeyCode()] = true;
	}

	/**
	 * 毎フレーム状態をリセットする
	 */
	public static void resetBool()
	{
		for(int i = 0; i < pressedKey.length; i++) pressedKey[i] = false;
	}
}
