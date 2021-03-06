package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener
{
	public static boolean pressed = false;
	public static boolean pressing = false;
	public static boolean released = false;
	public static Vector2 pressedPostion;
	public static Vector2 releasedPostion;
	public void mouseClicked(MouseEvent e){
		
	}

	public void mouseEntered(MouseEvent e){
		

	}

	public void mouseExited(MouseEvent e){

	}

	public void mousePressed(MouseEvent e){
		pressed = true;
		pressing = true;
		pressedPostion = new Vector2(e.getPoint().x - 3, e.getPoint().y - 26);
	}

	public void mouseReleased(MouseEvent e){
		released = true;
		pressing = false;
		releasedPostion = new Vector2(e.getPoint().x - 3, e.getPoint().y - 26);
	}
}

