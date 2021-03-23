package Main;

public class Bullet extends GameObject{

	Vector2 speed;
	Bullet(String img, Vector2 pos) {
		super(img, pos);
	}
	
	Bullet(String img, Vector2 pos,Vector2 speed) {
		super(img, pos);
		this.speed = speed;
	}
}
