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
	
	GameObject(Image img, float x, float y)
	{
		postion = new Vector2(x,y);
		this.image = img;
		//ImageIcon icon = new ImageIcon;
		size = new Vector2(image.getWidth(this), image.getHeight(this));
	}
	
	GameObject(Image img, int x, int y)
	{
		postion = new Vector2(x,y);
		this.image = img;
		size = new Vector2(image.getWidth(this), image.getHeight(this));
	}
	
	GameObject(Image img, Vector2 v)
	{
		postion = v;
		this.image = img;
		size = new Vector2(image.getWidth(this), image.getHeight(this));
	}
	
	GameObject(String img, float x, float y)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, int x, int y)
	{
		postion = new Vector2(x,y);
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	GameObject(String img, Vector2 v)
	{
		postion = v;
		image = String2Image.getImage(img);
		ImageIcon icon = new ImageIcon(image);
		size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
	}
	
	
	void DrawObject(Graphics g)
	{
		super.paintComponent(g);
		if(show)
			g.drawImage(image, (int)(postion.x - size.x / 2), (int)(postion.y - size.y / 2), this);
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
