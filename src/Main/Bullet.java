package Main;

import java.awt.Graphics;

/**
 * 弾クラス
 */
public class Bullet extends GameObject{

	public Vector2 velocity;	//速度ベクトル

	//判別したいため必ずタグを入れる
	/**
	 * 弾クラス
	 * @param img 画像相対パス
	 * @param pos 場所
	 * @param tag タグ
	 */
	Bullet(String img, Vector2 pos, Tag tag) {
		super(img, pos, tag);
		velocity = Vector2.Zero;
	}

	/**
	 * 弾クラス
	 * @param img 画像相対パス
	 * @param pos 場所
	 * @param vel 方向ベクトル
	 * @param tag タグ
	 */
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
