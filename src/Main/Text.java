package Main;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Text {
	//右寄りか左よりか中央か又は上に寄せるか下に寄せるか
	public static enum AdjustWidth
	{
		Center,
		Right,
		Left
	}
	public static enum AdjustHeight
	{
		Center,
		Top,
		Bottom
	}
	
	//文字を表示
	public static void drawString(Graphics g,String text,int x,int y,AdjustWidth wid, AdjustHeight hei)
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
	
	public static void drawString(Graphics g,String text,int x,int y,AdjustWidth wid)
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
		y += fm.getMaxAscent();
		g.drawString(text, x, y);
		g.setFont(o_font);
	}
	
	public static void drawString(Graphics g,String text,int x,int y,AdjustHeight hei)
	{
		x = (int)(x * Main.MagniWidth);
		y = (int)(y * Main.MagniHeight);
		Font o_font = g.getFont();
		Font font = g.getFont();
		font = new Font(font.getName(),font.getStyle(),(int)(font.getSize() * Main.MagniWidth));
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		Rectangle rect = fm.getStringBounds(text, g).getBounds();
		switch(hei)
		{
			case Center:
				y -= rect.height / 2 + fm.getMaxAscent();
				break;
			case Top:
				y += fm.getMaxAscent();
				break;
			case Bottom:
				y -= fm.getMaxAscent()/ 2;
				break;
		}
		g.drawString(text, x, y);
		g.setFont(o_font);
	}
	
	public static void drawString(Graphics g,String text,int x,int y)
	{
		x = (int)(x * Main.MagniWidth);
		y = (int)(y * Main.MagniHeight);
		Font o_font = g.getFont();
		Font font = g.getFont();
		font = new Font(font.getName(),font.getStyle(),(int)(font.getSize() * Main.MagniWidth));
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		y += fm.getMaxAscent();
		g.drawString(text, x, y);
		g.setFont(o_font);
	}
}
