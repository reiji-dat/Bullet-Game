package Main;

import java.awt.Graphics;

public class Tracking extends Bullet{
	private Player player;
	boolean nearPlayer = false;
	float speed;
	Tracking(String img, Vector2 pos, Vector2 vel, float spd, Tag tag) {
		super(img, pos, vel, tag);
		speed = spd;

		velocity.normalize();
		this.velocity.times(speed);
	}

	private boolean once = false;
	public void Update(Graphics g)
	{
		if(player == null) return;
		//プレイヤーが近くなったらまっすぐ飛ぶようにする
		if (Collider.EnterCollider(player.postion, new Vector2(postion),50))nearPlayer=true;
		if(!nearPlayer)//プレーヤーとの向きを調べ追尾する。
		{
			float ang = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
			velocity = new Vector2(Vector2.DegreeToVector(ang));
			velocity.times(speed);
		}
		else if(!once)//普通の弾に切り替える
		{
			velocity.normalize();
			velocity.times(speed);
			once = true;
		}
		super.Update(g);
	}

	public void Start()
	{
		player = (Player)ObjectManager.FindObjectsTag(Tag.Player)[0];
	}
}
