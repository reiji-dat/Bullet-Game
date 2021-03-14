package Main;

import java.awt.Graphics;

public class Tracking extends Bullet{
	//TODO もしtrueになったら普通の弾に切り替える
	boolean nearPlayer = false;
	float velocity;
	Tracking(String img, Vector2 pos, Vector2 speed, float vel) {
		super(img, pos, speed);
		velocity = vel;
		this.speed.times(vel);
	}

	void MoveDraw(Graphics g, Boss person, Player player)
	{
		if (Collider.EnterCollider(player.postion, new Vector2(postion),50))nearPlayer=true;
		if(!nearPlayer)
		{
			float ang = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
			speed = new Vector2(Vector2.DegreeToVector(ang));
			speed.times(velocity);
			movePostion(speed);
			DrawObject(g);
		}
		else
		{
			speed.times(velocity);
			person.Add(new Bullet("enemy_bullet.png", new Vector2(postion),speed));
		}
	}
}
