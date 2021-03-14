package Main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mapchip extends JPanel{
	Image mapImages = String2Image.getImage("mapchip.png");
	Point size;
	
	Point chipSize = new Point(32,32);
	Point split;
	
	int chips;
	Point[] chipPoint;
	//初期化
	Mapchip()
	{
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
	int[][] map = new int[][] {
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
		{
			for(int x = 0; x < map[y].length; x++)
			{
				g.drawImage(mapImages, (int)(x * chipSize.x * Main.MagniWidth), (int)(y * chipSize.y * Main.MagniHeight),
						(int)((x * chipSize.x + chipSize.x) * Main.MagniWidth), (int)((y * chipSize.y + chipSize.x) * Main.MagniHeight),
						chipPoint[map[y][x]].x,chipPoint[map[y][x]].y,
						chipPoint[map[y][x]].x+chipSize.x,chipPoint[map[y][x]].y+chipSize.y,this);
			}
		}
	}
}
