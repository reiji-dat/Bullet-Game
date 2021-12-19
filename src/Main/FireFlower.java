package Main;

import java.awt.Graphics;

/**
 * 花火クラス
 */
public class FireFlower extends Bullet{
	private final float DiffDistance = 150;	//拡散する距離
	private float currentDist = 0;				//発射されて進んだ距離
	private float speed;						//速度

	/**
	 * 花火クラス
	 * @param img 画像相対パス
	 * @param pos 場所
	 * @param vel 方向ベクトル
	 * @param spd 速度
	 * @param tag タグ
	 */
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
			currentDist+=speed*Time.GetDeltaTime();
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
