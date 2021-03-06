package Main;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.JPanel;

public class GameObject extends JPanel{
	Vector2 postion;
	Image image;
	boolean show = true;
	
	GameObject(Image img, float x, float y)
	{
		postion = new Vector2(x,y);
		this.image = img;
	}
	
	GameObject(Image img, int x, int y)
	{
		postion = new Vector2(x,y);
		this.image = img;
	}
	
	GameObject(Image img, Vector2 v)
	{
		postion = v;
		this.image = img;
	}
	
	GameObject(String img, float x, float y)
	{
		postion = new Vector2(x,y);
		URL url=this.getClass().getResource(img);
		System.out.println(url);
		try {
			image = createImage((ImageProducer) url.getContent());
		}catch(Exception ex){
			image = null;
		}
	}
	
	GameObject(String img, int x, int y)
	{
		postion = new Vector2(x,y);
		URL url=this.getClass().getResource(img);
		System.out.println(url);
		try {
			image = createImage((ImageProducer) url.getContent());
		}catch(Exception ex){
			image = null;
		}
	}
	
	GameObject(String img, Vector2 v)
	{
		postion = v;
		URL url=this.getClass().getResource(img);
		System.out.println(url);
		try {
			image = createImage((ImageProducer) url.getContent());
		}catch(Exception ex){
			image = null;
		}
	}
	
	
	void DrawObject(Graphics g)
	{
		super.paintComponent(g);
		if(show)
			g.drawImage(image, (int)postion.x, (int)postion.y, this);
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
