package Main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mapchip extends JPanel{
	//初期化で設定すればMain側で自由度の高いマップが作れる
	Image mapImages = String2Image.getImage("image/mapchip.png");
	//intなのでポイントにした
	Point size;//全体のサイズ
	Point chipSize = new Point(32,32);//チップのサイズ
	Point split;//縦横それぞれの枚数
	int chips;//チップ全体の数
	Point[] chipPoint;//チップごとのの始点

	//初期化
	Mapchip()
	{
		//super(Vector2.Zero);
		ImageIcon icon = new ImageIcon(mapImages);
		size = new Point(icon.getIconWidth(), icon.getIconHeight());
		split = new Point(size.x / chipSize.x, size.y / chipSize.y);
		chips = split.x * split.y;

		chipPoint = new Point[chips];
		int xy = 0;
		for(int y = 0; y < size.y; y+=chipSize.y)
		{
			for(int x = 0; x < size.x; x+=chipSize.x)
			{
				System.out.println(xy);
				chipPoint[xy] = new Point(x,y);
				xy++;
			}
		}

	}

	//マップ
	//128以上は使わないためbyte型でメモリ削減
	final byte[][] map = new byte[][] {
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

	void DrawMapchip(Graphics g)
	{
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
