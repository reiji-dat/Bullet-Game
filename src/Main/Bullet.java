package Main;

import java.awt.Graphics;

public class Bullet extends GameObject{
	public Vector2 velocity;
	Bullet(String img, Vector2 pos, Tag tag) {
		super(img, pos, tag);
	}

	Bullet(String img, Vector2 pos, Vector2 vel, Tag tag ) {
		super(img, pos, tag);
		velocity = vel;
	}

	Bullet(String img, Vector2 pos ) {
		super(img, pos);
	}

	Bullet(String img, Vector2 pos,Vector2 vel) {
		super(img, pos);
		velocity = vel;
	}

	public void Update(Graphics g)
	{
		MovePostion(velocity);
		DrawObject(g);

		if(postion.y < 0 || postion.x < 0 || postion.x > 400 || postion.y > 500)
		{
			ObjectManager.Destroy(this);
		}
	}
}
