package Main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

//1つのマップしかなく自由度がかなり低い
//自由度を上げることによって他のプロジェクトにも使えそう
/**
 * マップチップクラス
 */
public class Mapchip extends GameObject{
	//初期化で設定すればMain側で自由度の高いマップが作れる
	private Image mapImages = StringToImage.getImage("image/mapchip.png");

	//intなのでポイントにした。Vector2にするとキャストしなければいけないため
	private Point size;							//全体のサイズ
	private Point chipSize = new Point(32,32);	//1チップのサイズ
	private Point split;						//縦横それぞれの枚数
	private int chips;							//チップ全体の数
	private Point[] chipPoint;					//チップごとのの始点

	//マップ
	//128以上は使わないためbyte型
	private final byte[][] map = new byte[][] {
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,12,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,12,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,12,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,12,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,12,5,5,5,5,5},
								{5,12,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,5,5,5,5,5,5,5,5,5,5},
								{5,5,5,12,5,5,5,5,5,5,5,5,5}};

	/**
	 * マップチップクラス
	 */
	Mapchip()
	{
		super(Vector2.Zero, Tag.Map, "");

		ImageIcon icon = new ImageIcon(mapImages);
		//画像サイズ
		size = new Point(icon.getIconWidth(), icon.getIconHeight());
		//縦横それぞれの枚数
		split = new Point(size.x / chipSize.x, size.y / chipSize.y);
		//チップ全体の数
		chips = split.x * split.y;

		//始点をそれぞれ設定
		chipPoint = new Point[chips];
		int i = 0;
		for(int y = 0; y < size.y; y+=chipSize.y)
		{
			for(int x = 0; x < size.x; x+=chipSize.x)
			{
				chipPoint[i] = new Point(x,y);
				i++;
			}
		}

	}

	public void Update(Graphics g)
	{
		//表示方法が全く異なるため全てオーバーライドする。
		if(!visible) return;
		for(int y = 0; y < map.length ; y++)
		for(int x = 0; x < map[y].length; x++)
		{
			//画像,描画場所始点終点,イメージの描画部分始点終点,this
			g.drawImage(mapImages, (int)(x * chipSize.x * Main.MagniWidth), (int)(y * chipSize.y * Main.MagniHeight),
					(int)((x * chipSize.x + chipSize.x) * Main.MagniWidth), (int)((y * chipSize.y + chipSize.x) * Main.MagniHeight),
					chipPoint[map[y][x]].x,chipPoint[map[y][x]].y,
					chipPoint[map[y][x]].x+chipSize.x,chipPoint[map[y][x]].y+chipSize.y,this);
		}
	}
}
