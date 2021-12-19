package Main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * ゲームオブジェクトクラス
 * このクラスもしくはこれを継承しているクラスは直接作らないでください
 * 必ずObjectManagerのInstantiateを使って生成してください。
 */
public class GameObject extends JPanel{

	//GameObjectは編集をしてほしくないためString型のタグにしたほうが良いかも？
	public enum Tag
	{
		None,
		Player,
		Boss,
		PlayerBullet,
		BossBullet,
		Map
	}

	public Tag tag;
	public int imageIndex = 0;
	public Vector2 postion;
	public Vector2 size;
	public Image[] image;
	public boolean visible = true;

	GameObject(String img, Vector2 v)
	{
		Init(v, Vector2.One, Tag.None, img);
	}

	GameObject(String img, Vector2 v, Vector2 _size)
	{
		Init(v, _size,Tag.None, img);
	}

	GameObject(Vector2 v, String... img)
	{
		Init(v, Vector2.One,Tag.None, img);
	}

	GameObject(Vector2 v, Vector2 _size, String... img)
	{
		Init(v, _size,Tag.None, img);
	}

	GameObject(String img, Vector2 v, Tag _tag)
	{
		Init(v, Vector2.One,_tag, img);
	}

	GameObject(String img, Vector2 v, Vector2 _size, Tag _tag)
	{
		Init(v, _size, _tag, img);
	}

	GameObject(Vector2 v, Tag _tag, String... img)
	{
		Init(v, Vector2.One, _tag, img);
	}

	GameObject(Vector2 v, Vector2 _size,Tag _tag, String... img)
	{
		Init(v, _size, _tag, img);
	}

	//初期化
	private void Init(Vector2 v, Vector2 _size, Tag _tag, String... img)
	{
		postion = v;

		if(img[0] != "")
		{
			image = new Image[img.length];
			for(int i = 0; i < img.length; i++)
			{
				image[i] = StringToImage.getImage(img[i]);
				ImageIcon icon = new ImageIcon(image[i]);
				size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
			}
			ImageIcon icon = new ImageIcon(image[0]);
			this.size = new Vector2(icon.getIconWidth(), icon.getIconHeight());
			size.x *= _size.x;
			size.y *= _size.y;
		}

		tag = _tag;
	}

	public void DrawObject(Graphics g)
	{
		int num = clamp(imageIndex, 0, image.length - 1);
		if(visible)
			g.drawImage
			(image[num], (int)((postion.x - size.x / 2) * Main.MagniWidth), (int)((postion.y - size.y / 2) * Main.MagniHeight),
					(int)(size.x * Main.MagniWidth), (int)(size.y * Main.MagniHeight), this);
	}

	//TODO 自前のMathクラスを作りそちらに移す
	private int clamp(int value, int min, int max) {
	    if (value < min) return min;
	    else if (value > max) return max;
	    else return value;
	}

	/**
	 * 指定したベクトル分動かす
	 * @param v 方向ベクトル
	 */
	public void MovePostion(Vector2 v)
	{
		postion.plus(v);
	}

	//継承先で以下の3つを使い処理を変える。
	public void Update(Graphics g)
	{
		DrawObject(g);
	}

	public void Start()
	{

	}

	public void OnDestroy()
	{
	}
}
