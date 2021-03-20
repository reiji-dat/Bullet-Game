package Main;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameObject extends JPanel{
	Vector2 postion;
	Vector2 size;
	Image[] image;
	boolean show = true;
	
	GameObject(String img, Vector2 v)
	{
		postion = v;
		image = new Image[1];
		image[0] = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image[0]);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, Vector2 v, Vector2 size)
	{
		postion = v;
		image[0] = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image[0]);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		this.size.times(size);
	}
	
	GameObject(Vector2 v, String... img)
	{
		postion = v;
		image = new Image[img.length];
		for(int i = 0; i < img.length; i++)
		{
			image[i] = String2Image.getImage(img[i]);
			ImageIcon icon = new ImageIcon(image[i]);
			size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		}
		
	}
	
	GameObject(Vector2 v, Vector2 size, String... img)
	{
		postion = v;
		image = new Image[img.length];
		for(int i = 0; i < img.length; i++)
		{
			image[i] = String2Image.getImage(img[i]);
			ImageIcon icon = new ImageIcon(image[i]);
			this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		}
		this.size.times(size);
	}
	
	void DrawObject(Graphics g)
	{
		if(show)
			g.drawImage
			(image[0], (int)((postion.x - size.x / 2) * Main.MagniWidth), (int)((postion.y - size.y / 2) * Main.MagniHeight), 
					(int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
	}
	
	void DrawObject(Graphics g, int num)
	{
		num = num > image.length - 1 ? 0 : num;
		if(show)
			g.drawImage
			(image[num], (int)((postion.x - size.x / 2) * Main.MagniWidth), (int)((postion.y - size.y / 2) * Main.MagniHeight), 
					(int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
	}
	
	//指定した数値分移動します
	void movePostion(Vector2 v)
	{
		postion.plus(v);
	}
	
}
