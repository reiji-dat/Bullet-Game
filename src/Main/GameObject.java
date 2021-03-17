package Main;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameObject extends JPanel{
	Vector2 postion;
	Vector2 size;
	Image image;
	boolean show = true;
	
	GameObject(String img, Vector2 v)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, Vector2 v, Vector2 size)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		this.size.times(size);
	}
	
	void DrawObject(Graphics g)
	{
		if(show)
			g.drawImage
			(image, (int)((postion.x - size.x / 2) * Main.MagniWidth), (int)((postion.y - size.y / 2) * Main.MagniHeight), 
					(int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
	}
	
	//指定した数値分移動します
	void movePostion(Vector2 v)
	{
		postion.plus(v);
	}
	
}
