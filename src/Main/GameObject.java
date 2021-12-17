package Main;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameObject extends JPanel{
	public String tag;
	Vector2 postion;
	Vector2 size;
	Image[] image;
	boolean show = true;

	GameObject(String img, Vector2 v)
	{
		Init(v, Vector2.One,"", img);
	}

	GameObject(String img, Vector2 v, Vector2 _size)
	{
		Init(v, _size,"", img);
	}

	GameObject(Vector2 v, String... img)
	{
		Init(v, Vector2.One,"", img);
	}

	GameObject(Vector2 v, Vector2 _size, String... img)
	{
		Init(v, _size,"", img);
	}

	GameObject(String img, Vector2 v, String _tag)
	{
		Init(v, Vector2.One,"", img);
	}

	GameObject(String img, Vector2 v, Vector2 _size, String _tag)
	{
		Init(v, _size, img);
	}

	GameObject(Vector2 v,String _tag, String... img)
	{
		Init(v, Vector2.One, _tag, img);
	}

	GameObject(Vector2 v, Vector2 _size,String _tag, String... img)
	{
		Init(v, _size, _tag, img);
	}

	public void Init(Vector2 v, Vector2 _size, String _tag, String... img)
	{
		postion = v;
		image = new Image[img.length];
		for(int i = 0; i < img.length; i++)
		{
			image[i] = String2Image.getImage(img[i]);
			ImageIcon icon = new ImageIcon(image[i]);
			size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		}
		ImageIcon icon = new ImageIcon(image[0]);
		this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
		size.x *= _size.x;
		size.y *= _size.y;

		tag = _tag;

		//ObjectManager.Instantiate(this);
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

	public void Update()
	{

	}

	public void Start()
	{

	}

	public void OnDestroy()
	{

	}
}
