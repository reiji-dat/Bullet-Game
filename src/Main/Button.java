package Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.JPanel;

//TODO メソッドを引数にしコールバックする。
/**
 * ボタンクラス
 */
public class Button extends JPanel{
	/**
	 * 表示、非表示
	 */
	public boolean visible = true;
	/**
	 * 押されている状態
	 */
	public boolean pressed = false;

	private Image image = null;//画像
	private String text = "";//画像パス

	/**
	 * ボタンの位置(位置は左上基準)
	 */
	public Vector2 postion = null;
	/**
	 * ボタンサイズ
	 */
	public Vector2 size = null;

	/**
	 * ボタンクラス(位置は左上基準)
	 * @param img 画像の相対パス
	 * @param text ボタンに表示したい文字
	 * @param x 横の位置
	 * @param y 縦の位置
	 * @param width 横のサイズ
	 * @param height 縦のサイズ
	 */
	Button(String img,String text, int x,int y,int width,int height)
	{
		URL url=this.getClass().getResource(img);//一度URLに変換しないとエクスポートできない
		try {
			image = createImage((ImageProducer) url.getContent());
		}catch(Exception ex){
			image = null;
		}
		this.text = text;
		postion = new Vector2(x,y);
		size = new Vector2(width,height);
	}

	/**
	 * ボタンを描画
	 * @param g
	 */
	public void DrawButton(Graphics g)
	{
		//押されているなら離すまで押された判定にする。
		if(pressed && MouseInput.released) pressed = false;

		if(visible)
		{
			g.drawImage(image, (int)(postion.x * Main.MagniWidth), (int)(postion.y * Main.MagniHeight), (int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
			Text.DrawString(g, text, (int)(postion.x + size.x / 2), (int)(postion.y + size.y / 2), Text.AdjustWidth.Center, Text.AdjustHeight.Center);
			if(MouseInput.pressed)
			{
				Vector2 pos = new Vector2(MouseInput.pressedPostion);
				Vector2 bpos = new Vector2(postion);

				bpos.x *= Main.MagniWidth;
				bpos.y *= Main.MagniHeight;

				//枠内にマウスポイントがあれば押された判定
				if(bpos.x <= pos.x && bpos.x + size.x * Main.MagniWidth >= pos.x
				&& bpos.y <= pos.y && bpos.y + size.y * Main.MagniHeight >= pos.y)
					pressed = true;
			}
		}
	}
}
