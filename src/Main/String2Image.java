package Main;

import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.JPanel;

public class String2Image extends JPanel{

	public static String2Image Instance = new String2Image();
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
