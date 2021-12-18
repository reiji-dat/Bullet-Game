package Main;

import java.awt.Graphics;

import Main.AttackPatternData.AttackPattern;

public class Boss extends GameObject
{
	final int e_Bullet_Cool = 64;//ランダム撃ちの頻度
	int e_Bullet_Timer;//敵のランダム撃ちタイマー

	int e_pBullet_Timer;//パターン攻撃タイマー

	final int MaxHP = 500;//最大体力
	int hp;//体力

	int modeTimer;//ボスの状態タイマー
	final int ModeChangeTime = 25000;

	boolean invincible = true;//無敵かどうか
	int atkChangeTimer;//攻撃種類変更タイマー


	int moveTimer;//動き関係タイマー
	int moveTime = 4000;//動く(止まる)時間
	boolean move = false;//動くか止まるか
	Vector2 velocity = new Vector2(Vector2.Zero);//スピード

	//アニメーションタイマー
	final int ChangeTime = 100;
	int anmTimer = 0;


	//クラスで状態遷移
	//data[index]でパターンごと読み込む
	AttackPatternData[] data = {new AttackPatternData((AttackPattern.Closs), 500, 0, 0), new AttackPatternData((AttackPattern.Closs), 500, 0, 0)};
	int dataIndex = 0;
	/*
	 * 			switch(next)//intからenumにすることが出来なかっためswitch文で分岐させた
			{
				case 0:
					pattern = AttackPattern.Closs;
					putternTime = 500;//攻撃頻度をそれぞれ変更
					break;
				case 1:
					pattern = AttackPattern.Pick;
					putternTime = 333;
					break;
				case 2:
					pattern = AttackPattern.Voluted;
					putternTime = 150;
					volDeg = 0;
					break;
				case 3:
					pattern = AttackPattern.Voluted2;
					putternTime = 150;
					volDeg = 0;
					volDeg2 = 180;
					break;
				case 4:
					pattern = AttackPattern.FireFlower;
					putternTime = 910;
					break;
				case 5:
					pattern = AttackPattern.Tracking;
					putternTime = 500;
					break;
			}
	 */

	Boss(Vector2 pos, Vector2 size,String... img) {super(pos, size,Tag.Boss, img);}

	public void Update(Graphics g)
	{
		MoveMent();

		Animation();

		modeTimer += Time.flameTime;
		if(invincible)
		{
			if(modeTimer >=  ModeChangeTime)
			{
				invincible = !invincible;
				modeTimer -= ModeChangeTime;
			}
			Attack();
		}
		else //無敵じゃなければ
		{
			HitBullet();
			if(modeTimer >= 10000)
			{
				invincible = !invincible;
				modeTimer -= 10000;
				atkChangeTimer = 0;
			}
		}
		super.Update(g);
	}

	private void MoveMent()
	{
		moveTimer+=Time.flameTime;
		if(moveTimer >= moveTime)//動きを変える時間を超えたら
		{
			moveTimer = 0;
			if(move)//動いていたら止まる処理
			{
				moveTime = 4000;
				velocity  = new Vector2(Vector2.Zero);
			}
			else	//止まっていたら動く処理
			{
				moveTime = (int)(Math.random()*2000)+3000;
				velocity  = new Vector2(Vector2.DegreeToVector((float)Math.random()*360));
				velocity.division(4);
			}
			move = !move;//状態反転
		}
		//動ける範囲の制限
		if(postion.x < 50 || postion.x > 350 || postion.y < 50 || postion.y > 200)
		{
			velocity .times(-1);
		}
		MovePostion(velocity );
	}

	private void Animation()
	{
		anmTimer+=Time.flameTime;
		int t = anmTimer / ChangeTime;
		t = t % 8;
		t = -Math.abs(-t+4)+4;//往復アニメーション処理
		imageIndex = t;
	}

	private void Attack()
	{
		RandomAttack();
		//パターン攻撃
		e_pBullet_Timer += Time.flameTime;
		if(e_pBullet_Timer >= putternTime)
		{
			Vector2 firePos = new Vector2(postion.x, postion.y-10);
			e_pBullet_Timer -= putternTime;

			GameObject player = ObjectManager.FindObjectsTag(Tag.Player)[0];
			float deg = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
			switch(pattern)//パターンごとの弾を撃つ処理
			{
				case Closs:
					for(int i = 100; i < 400;i+=100)
					{
						ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(i,0),
										new Vector2(0,3),
										Tag.BossBullet));
					}
					for(int i = 100; i < 500;i+=100)
					{
						ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(0,i),
										new Vector2(3,0),
										Tag.BossBullet));
					}
					break;
				case Pick:
					for(int i = -40;i <= 40;i+=20)
					{
						Bullet b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(deg + i)),
										Tag.BossBullet));
						b.velocity.times(3);
					}
					break;
				case Voluted:
					for(int i = -144;i <= 144;i+=72)
					{
						Bullet b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(volDeg + i)),
										Tag.BossBullet));
						b.velocity.times(3);
					}
					volDeg+=10;
					break;
				case Voluted2:
					for(int i = -144;i <= 144;i+=72)
					{
						Bullet b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(volDeg + i)),
										Tag.BossBullet));
						b.velocity.times(3);

						b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(volDeg2 + i)),
										Tag.BossBullet));
						b.velocity.times(3);
					}
					volDeg+=10;
					volDeg2-=10;
					break;
				case FireFlower:
					for(int i = -180;i < 180;i+=60)
						//花火はBossクラスの子として扱い、拡散時は親子関係を外す
					ObjectManager.Instantiate(new FireFlower("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg + i)),3, Tag.BossBullet));
					break;
				case Tracking:
						//花火と同じく
					ObjectManager.Instantiate(new Tracking("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg)), 2, Tag.BossBullet));
					break;
			}
		}
		atkChangeTimer += Time.flameTime;
		if(atkChangeTimer >= 5000)//パターン変更処理
		{
			//攻撃パターン抽選
			int next = (int)(Math.random() * 6);
			switch(next)//intからenumにすることが出来なかっためswitch文で分岐させた
			{
				case 0:
					pattern = AttackPattern.Closs;
					putternTime = 500;//攻撃頻度をそれぞれ変更
					break;
				case 1:
					pattern = AttackPattern.Pick;
					putternTime = 333;
					break;
				case 2:
					pattern = AttackPattern.Voluted;
					putternTime = 150;
					volDeg = 0;
					break;
				case 3:
					pattern = AttackPattern.Voluted2;
					putternTime = 150;
					volDeg = 0;
					volDeg2 = 180;
					break;
				case 4:
					pattern = AttackPattern.FireFlower;
					putternTime = 910;
					break;
				case 5:
					pattern = AttackPattern.Tracking;
					putternTime = 500;
					break;
			}
			atkChangeTimer -= 5000;
			e_pBullet_Timer = 0;
		}
	}

	private void RandomAttack()
	{
		//花火時以外はランダム撃ちをする。
		if(pattern != AttackPattern.FireFlower)
		{
			e_Bullet_Timer += Time.flameTime;
			if(e_Bullet_Timer >= e_Bullet_Cool)
			{
				e_Bullet_Timer -= e_Bullet_Cool;
				Vector2 vel = new Vector2(Vector2.DegreeToVector((float) (Math.random() * 360)));
				vel.times(1);
				ObjectManager.Instantiate(
						new Bullet(
								"image/enemy_bullet.png",
								new Vector2(postion.x, postion.y-10),
								vel,
								Tag.BossBullet));
			}
		}
	}

	private void HitBullet()
	{
		GameObject[] objs = ObjectManager.FindObjectsTag(Tag.PlayerBullet);
		for(int i = 0; i < objs.length; i++)
		{
			if(objs[i].postion.x >= postion.x - size.x / 2
					&& objs[i].postion.x <= postion.x + size.x / 2
					&& objs[i].postion.y >= postion.y - size.y / 2
					&& objs[i].postion.y <= postion.y + size.y / 2
					&& !invincible)
			{
				ObjectManager.Destroy(objs[i]);
				hp--;
				break;
			}
		}
	}

	public void Start()
	{
		postion = new Vector2(200,100);
		hp = MaxHP;
		e_Bullet_Timer = 0;
		atkChangeTimer = 0;
		modeTimer = 0;
		e_pBullet_Timer = 0;;
		invincible = true;
		dataIndex = 0;
		moveTimer = 0;
		moveTime = 4000;
		anmTimer = 0;
	}
}

class AttackPatternData
{
	public enum AttackPattern	//攻撃パターン
	{
		Closs,		//縦横
		Pick,		//狙い撃ち
		Voluted,	//渦巻
		Voluted2,	//渦巻&逆渦巻
		FireFlower,//花火
		Tracking	//追尾
	}
	public AttackPattern pattern;
	public float patternTime;
	public float volDeg1;
	public float volDeg2;

	AttackPatternData(AttackPattern p, int time, float deg1, float deg2)
	{
		pattern = p;
		patternTime = time;
		volDeg1 = deg1;
		volDeg2 = deg2;
	}
}