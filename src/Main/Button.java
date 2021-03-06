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
		URL url=this.getClass().getResource(img);
		System.out.println(url);
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
		super.paintComponent(g);
		if(pressed)
		{
			pressed = false;
			return;
		}
		if(show)
		{
			g.drawImage(image, (int)postion.x, (int)postion.y, (int)size.x, (int)size.y, this);
			Text.drawString(g, text, (int)(postion.x + size.x / 2), (int)(postion.y + size.y / 2), Text.AdjustWidth.Center, Text.AdjustHeight.Center);
			if(MouseInput.pressed)
			{
				Vector2 pos = MouseInput.pressedPostion;
				
				if(postion.x <= pos.x && postion.x + size.x >= pos.x
				&& postion.y <= pos.y && postion.y + size.y >= pos.y)
				pressed = true;
			}
		}
	}
}
