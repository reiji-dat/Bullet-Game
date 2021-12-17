package Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.JPanel;;
public class Button extends JPanel{
	boolean show = true;
	boolean pressed = false;
	Image image = null;
	String text = "";
	Vector2 postion = null;
	Vector2 size = null;
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
	
	void DrawButton(Graphics g)
	{
		if(pressed)
		{
			pressed = false;
			return;
		}
		if(show)
		{
			g.drawImage(image, (int)(postion.x * Main.MagniWidth), (int)(postion.y * Main.MagniHeight), (int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
			Text.drawString(g, text, (int)(postion.x + size.x / 2), (int)(postion.y + size.y / 2), Text.AdjustWidth.Center, Text.AdjustHeight.Center);
			if(MouseInput.pressed)
			{
				Vector2 pos = new Vector2(MouseInput.pressedPostion);
				Vector2 bpos = new Vector2(postion);
				bpos.x *= Main.MagniWidth;
				bpos.y *= Main.MagniHeight;
				if(bpos.x<= pos.x && bpos.x + size.x*Main.MagniWidth >= pos.x
				&& bpos.y<= pos.y && bpos.y + size.y*Main.MagniHeight>= pos.y)
					pressed = true;
			}
		}
	}
}
