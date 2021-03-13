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
	GameObject(String img, float x, float y)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, float x, float y, float w, float h)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, float x, float y, int w, int h)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, float x, float y, Vector2 size)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		size.times(size);
	}
	
	
	
	GameObject(String img, int x, int y)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, int x, int y, float w, float h)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, int x, int y, int w, int h)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, int x, int y, Vector2 size)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		size.times(size);
	}
	
	
	
	GameObject(String img, Vector2 v)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, Vector2 v, float w, float h)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, Vector2 v, int w, int h)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth() * w, icon.getIconHeight() * h);
	}
	
	GameObject(String img, Vector2 v, Vector2 size)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		size.times(size);
	}
	
	
	
	void DrawObject(Graphics g)
	{
		if(show)
			g.drawImage(image, (int)(postion.x - size.x / 2), (int)(postion.y - size.y / 2), (int)size.x, (int)size.y, this);
	}
	
	//指定した数値分移動します
	
	void movePostion(float x, float y)
	{
		postion.plus(new Vector2(x,y));
	}
	void movePostion(int x, int y)
	{
		postion.plus(new Vector2(x,y));
	}
	void movePostion(Vector2 v)
	{
		postion.plus(v);
	}
	
}
