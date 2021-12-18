package Main;

import java.awt.Graphics;

public class FireFlower extends Bullet{
	final float DiffDistance = 150;//拡散する距離
	float currentDist = 0;//発射されて進んだ距離
	float speed;//速度

	boolean end = false;//拡散したか


	FireFlower(String img, Vector2 pos, Vector2 vel, float spd, Tag tag)
	{
		super(img, pos, vel, tag);
		speed = spd;

		//速度を1に修正
		velocity.normalize();
		velocity.times(spd);
	}

	public void Update(Graphics g)
	{
		if(currentDist <= DiffDistance)//拡散前
		{
			currentDist+=speed;
		}
		else//拡散後
		{
			for(int i = 0;i < 360;i+=36)
			{
				Vector2 vel = new Vector2(Vector2.DegreeToVector(i));
				vel.times(speed);
				ObjectManager.Instantiate(new Bullet("image/enemy_bullet.png", new Vector2(postion), vel, Tag.BossBullet));
			}
			ObjectManager.Destroy(this);
		}
		super.Update(g);
	}
}
