package Main;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * テキストクラス
 */
public class Text {
	/**
	 * 右寄り、左寄り、中央
	 */
	public enum AdjustWidth
	{
		Center,
		Right,
		Left
	}

	/**
	 * 上寄せ、下寄せ、中央
	 */
	public enum AdjustHeight
	{
		Center,
		Top,
		Bottom
	}

	/**
	 * 文字を表示
	 * @param g
	 * @param text 文字
	 * @param x 横
	 * @param y 縦
	 * @param wid 左右寄せ
	 * @param hei 上下寄せ
	 */
	public static void DrawString(Graphics g,String text,int x,int y,AdjustWidth wid, AdjustHeight hei)
	{
		x = (int)(x * Main.MagniWidth);
		y = (int)(y * Main.MagniHeight);
		Font o_font = g.getFont();
		Font font = g.getFont();
		font = new Font(font.getName(),font.getStyle(),(int)(font.getSize() * Main.MagniWidth));

		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		Rectangle rect = fm.getStringBounds(text, g).getBounds();
		switch(wid)
		{
			case Center:
				x -= rect.width/2;
				break;
			case Right:
				x -= rect.width;
				break;
			case Left:
				break;
		}
		switch(hei)
		{
			case Center:
				y += rect.height / 2;
				break;
			case Top:
				y += fm.getMaxAscent();
				break;
			case Bottom:
				y -= fm.getMaxAscent() / 2;//2で割ったほうがちょうどよい理由を後で調べる
				break;
		}
		g.drawString(text, x, y);
		g.setFont(o_font);
	}

	/**
	 * 文字を表示
	 * @param g
	 * @param text 文字
	 * @param x 横
	 * @param y 縦
	 * @param wid 左右寄せ
	 */
	public static void DrawString(Graphics g,String text,int x,int y,AdjustWidth wid)
	{
		DrawString(g, text, x, y, wid, AdjustHeight.Top);
	}

	/**
	 * 文字を表示
	 * @param g
	 * @param text 文字
	 * @param x 横
	 * @param y 縦
	 * @param hei 上下寄せ
	 */
	public static void DrawString(Graphics g,String text,int x,int y,AdjustHeight hei)
	{
		DrawString(g, text, x, y, AdjustWidth.Left, hei);
	}

	/**
	 * 文字を表示
	 * @param g
	 * @param text 文字
	 * @param x 横
	 * @param y 縦
	 */
	public static void DrawString(Graphics g,String text,int x,int y)
	{
		DrawString(g, text, x, y, AdjustWidth.Left, AdjustHeight.Top);
	}
}
