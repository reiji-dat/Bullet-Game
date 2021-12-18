package Main;

import java.awt.Graphics;

public class Bullet extends GameObject{
	public Vector2 velocity;
	Bullet(String img, Vector2 pos, Tag tag) {
		super(img, pos, tag);
		velocity = Vector2.Zero;
	}

	Bullet(String img, Vector2 pos, Vector2 vel, Tag tag) {
		super(img, pos, tag);
		velocity = vel;
	}

	public void Update(Graphics g)
	{
		MovePostion(velocity);

		if(postion.y < 0 || postion.x < 0 || postion.x > 400 || postion.y > 500)
		{
			ObjectManager.Destroy(this);
		}
		super.Update(g);
	}
}
