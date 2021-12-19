package Main;

import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.JPanel;

/**
 * 文字列から画像に変更する処理
 */
public class StringToImage extends JPanel{
	public static StringToImage Instance = new StringToImage();
	public static Image getImage(String img)
	{
		URL url = Instance.getClass().getResource(img);
		try {
			return Instance.createImage((ImageProducer) url.getContent());
		}catch(Exception ex){
			return  null;
		}
	}
}
