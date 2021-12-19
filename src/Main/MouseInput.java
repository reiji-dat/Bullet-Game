package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//どのボタンか判定も含めればどこでも使えるようになる。
/**
 * マウス処理クラス
 */
public class MouseInput implements MouseListener
{
	/**
	 * マウスのどれかが押された瞬間
	 */
	public static boolean pressed = false;
	/**
	 * マウスのどれかが押してある状態
	 */
	public static boolean pressing = false;
	/**
	 * マウスのどれかが離された瞬間
	 */
	public static boolean released = false;

	/**
	 * 押された時の場所
	 */
	public static Vector2 pressedPostion;
	/**
	 * 離された場所の場所
	 */
	public static Vector2 releasedPostion;

	//これがないとエラーになる
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void mousePressed(MouseEvent e){
		pressed = true;
		pressing = true;
		pressedPostion = new Vector2(e.getPoint().x - 3, e.getPoint().y - 26);
	}

	public void mouseReleased(MouseEvent e){
		released = true;
		pressing = false;
		releasedPostion = new Vector2(e.getPoint().x - 3, e.getPoint().y - 26);
	}
}

