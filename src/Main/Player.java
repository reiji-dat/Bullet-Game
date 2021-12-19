package Main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends GameObject{
	private final float Speed = 3;//プレイヤーの移動速度
	private final float BulletSpeed = 5;//弾の速度
	public final int MaxHP = 5;//最大体力
	public int hp = MaxHP;//体力

	public final int MaxMP = 50;//最大弾数
	public int mp = MaxMP;//弾数
	private int bulletTimer = 0;//MP回復用タイマー

	public boolean invincible = false;//ダメージ時の無敵
	private final int InvTime = 1000;	//ダメージ時の無敵時間
	private int invTimer = 0;				//ダメージ時の無敵時間タイマー

	//アニメーション
	private final int ChangeTime = 100;
	private int anmTimer = 0;

	Player( Vector2 pos, String... img) {super(pos,Tag.Player, img);}

	//敵が複数いる場合はメインにゲッターを用意して情報を取得？
	//今回は1体だけなので引数にしている
	@Override
	public void Update(Graphics g)
	{
		Invincible();
		Movement();
		Animation();
		MP();
		ShotBullet();
		HitBullet();

		super.Update(g);
	}

	private void Invincible()
	{
		if(invincible)//無敵時間は点滅する
		{
			invTimer += Time.flameTime;
			show = Math.sin(invTimer/20) <= 0 ? true : false;//sin波を利用(数字はちょうどいい感じに調整)
			if(invTimer>=InvTime)
			{
				show = true;
				invincible = false;
				invTimer = 0;
			}
		}
	}

	private void Movement()
	{
		//移動
		if(KeyInput.inputKey[KeyEvent.VK_W]) postion.y -= Speed;
		if(KeyInput.inputKey[KeyEvent.VK_A]) postion.x -= Speed;
		if(KeyInput.inputKey[KeyEvent.VK_S]) postion.y += Speed;
		if(KeyInput.inputKey[KeyEvent.VK_D]) postion.x += Speed;

		//移動制限
		postion.x = postion.x < 0 ? 0 : postion.x;
		postion.x = postion.x > 400 ? 400 : postion.x;
		postion.y = postion.y < 0 ? 0 : postion.y;
		postion.y = postion.y > 500 ? 500 : postion.y;
	}

	private void Animation()
	{
		anmTimer += Time.flameTime;
		int t = anmTimer / ChangeTime;
		t = t % 4;//定数の方が尚良い
		t = -Math.abs(-t+2)+2;//往復アニメーション処理
		imageIndex = t;
	}

	private void MP()
	{
		//弾
		if(bulletTimer < 500) bulletTimer += Time.flameTime;
		else if(mp < MaxMP)
		{
			bulletTimer -= 500;
			mp++;
		}
	}

	int shotTime = 0;
	final int shotCoolTime = 100;
	private void ShotBullet()
	{
		shotTime -= Time.flameTime;
		if(KeyInput.inputKey[KeyEvent.VK_SPACE] && mp > 0 && shotTime <= 0)
		{
			shotTime = shotCoolTime;
			SEPlayer.PlaySE(SEPlayer.SE.Attack);
			mp--;
			ObjectManager.Instantiate(new Bullet("image/player_bullet.png",new Vector2(postion.x,postion.y-10), new Vector2(0,-BulletSpeed), Tag.PlayerBullet));
			ObjectManager.Instantiate(new Bullet("image/player_bullet.png",new Vector2(postion.x+20,postion.y+5), new Vector2(0,-BulletSpeed), Tag.PlayerBullet));
			ObjectManager.Instantiate(new Bullet("image/player_bullet.png",new Vector2(postion.x-20,postion.y+5), new Vector2(0,-BulletSpeed), Tag.PlayerBullet));
		}
	}

	private void HitBullet()
	{

		GameObject[] objs = ObjectManager.FindObjectsTag(Tag.BossBullet);
		for(int i = 0; i < objs.length; i++)
		{
			if(!invincible && Collider.EnterCollider(postion, new Vector2(objs[i].postion), 5))
			{
				SEPlayer.PlaySE(SEPlayer.SE.Damage);
				invincible = true;
				ObjectManager.Destroy(objs[i]);
				hp--;
				break;
			}
		}
	}

	//初期化
	@Override
	public void Start()
	{
		postion = new Vector2(200,400);
	}
}
