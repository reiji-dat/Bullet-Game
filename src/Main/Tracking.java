package Main;

import java.awt.Graphics;

public class Tracking extends Bullet{
	boolean nearPlayer = false;
	float velocity;
	Tracking(String img, Vector2 pos, Vector2 speed, float vel) {
		super(img, pos, speed);
		velocity = vel;
		this.speed.times(vel);
	}

	void MoveDraw(Graphics g, Boss person, Player player)
	{
		//プレイヤーが近くなったらまっすぐ飛ぶようにする
		if (Collider.EnterCollider(player.postion, new Vector2(postion),50))nearPlayer=true;
		if(!nearPlayer)//プレーヤーとの向きを調べ追尾する。
		{
			float ang = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
			speed = new Vector2(Vector2.DegreeToVector(ang));
			speed.times(velocity);
			movePostion(speed);
			DrawObject(g);
		}
		else//普通の弾に切り替える
		{
			speed.times(velocity);
			person.Add(new Bullet("image/enemy_bullet.png", new Vector2(postion),speed));
		}
	}
}
