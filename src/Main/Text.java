package Main;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Text {
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
	
	public static void drawString(Graphics g,String text,int x,int y,AdjustWidth wid, AdjustHeight hei)
	{
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
				y -= rect.height + fm.getMaxAscent();
				break;
		}
		g.drawString(text, x, y);
	}
	
	public static void drawString(Graphics g,String text,int x,int y,AdjustWidth wid)
	{
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
	}
	
	public static void drawString(Graphics g,String text,int x,int y,AdjustHeight hei)
	{
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
				y -= rect.height + fm.getMaxAscent();
				break;
		}
		
	}
	
	public static void drawString(Graphics g,String text,int x,int y)
	{
		FontMetrics fm = g.getFontMetrics();
		y += fm.getMaxAscent();
		g.drawString(text, x, y);
	}
}
