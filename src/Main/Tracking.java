package Main;

import java.awt.Graphics;

//TODO マジックナンバーを消す
/**
 * 追尾弾の追加
 */
public class Tracking extends Bullet{
	private Player player;					//ターゲット
	private boolean nearPlayer = false;	//近くにプレイヤーがいるか
	private float speed;					//速さ

	/**
	 * 追尾弾の追加
	 * @param img 画像
	 * @param pos 場所
	 * @param vel 方向ベクトル
	 * @param spd 速度
	 * @param tag タグ
	 */
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
		if (Collider.EnterCollider(player.postion, new Vector2(postion),40))nearPlayer=true;
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
		GameObject[] obj = ObjectManager.FindObjectsTag(Tag.Player);
		if(obj.length != 0 && new CastClass<Player>().CheckDownCast(obj[0]))
			player = (Player)obj[0];
	}
}
