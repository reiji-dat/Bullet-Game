package Main;

import java.awt.Graphics;

import Main.AttackPatternData.AttackPattern;
import Main.SEPlayer.SE;

public class Boss extends GameObject
{
	private final int randomCoolTime = 64;		//ランダム撃ちの間隔
	private int randomBulletTimer = 0;			//ランダム撃ちのタイマー

	private int patternBulletTimer = 0;		//パターン攻撃タイマー

	public final int MaxHP = 500;				//最大体力
	public int hp = MaxHP;						//体力

	private int stateTimer = 0;				//無敵か無敵じゃない状態タイマー
	private final int StateChangeTime = 25000;//無敵か無敵じゃない状態遷移の時間
	public boolean invincible = true;			//無敵かどうか

	private int atkChangeTimer = 0;			//攻撃種類変更タイマー

	private int moveTimer = 0;					//動き関係タイマー
	private int moveTime = 4000;				//動く(止まる)時間
	private boolean move = false;				//動くか止まるか
	private Vector2 velocity = new Vector2(Vector2.Zero);//進む方向ベクトル

	private final int ChangeTime = 100;		//アニメーション変更時間
	private int anmTimer = 0;					//アニメーションタイマー

	//クラスで状態遷移
	//data[index]でパターンごと読み込む
	private AttackPatternData[] data = {
			new AttackPatternData(AttackPattern.Closs, 500),
			new AttackPatternData(AttackPattern.Pick, 333),
			new AttackPatternData(AttackPattern.Voluted, 150),
			new AttackPatternData(AttackPattern.Voluted2, 150),
			new AttackPatternData(AttackPattern.FireFlower, 900),
			new AttackPatternData(AttackPattern.Tracking, 500)
			};
	private int dataIndex = 0;

	/**
	 * データを変更する
	 * @param value 変更したい値
	 */
	private void SetDataIndex(int value)
	{
		dataIndex = value;
		dataIndex = Clamp(dataIndex ,0 ,data.length - 1);
		data[dataIndex].Init();
	}

	//最大値と最小値の間に変更する。
	//TODO : 自前のMathクラスを作成する。
	private int Clamp(int value, int min, int max) {
	    if (value < min) return min;
	    else if (value > max) return max;
	    else return value;
	}

	Boss(Vector2 pos, Vector2 size,String... img) {super(pos, size,Tag.Boss, img);}

	public void Update(Graphics g)
	{
		Movement();

		Animation();

		stateTimer += Time.flameTime;
		if(invincible)
		{
			if(stateTimer >=  StateChangeTime)
			{
				SEPlayer.PlaySE(SE.Weakness);
				invincible = false;
				stateTimer -= StateChangeTime;
			}
			Attack();
		}
		else //無敵じゃなければ
		{
			HitBullet();
			if(stateTimer >= 10000)
			{

				invincible = true;
				stateTimer -= 10000;
				atkChangeTimer = 0;
			}
			visible = Math.sin(stateTimer / 200) <= 0.95f;//sin波を利用(数字はちょうどいい感じに調整)
		}
		super.Update(g);
	}

	/**
	 * 動きの処理
	 */
	private void Movement()
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
				velocity = new Vector2(Vector2.DegreeToVector((float)Math.random()*360));
				velocity.times(30);
			}
			move = !move;//状態反転
		}
		//動ける範囲の制限
		if(postion.x < 50 || postion.x > 350 || postion.y < 50 || postion.y > 200)
		{
			velocity.times(-1);
		}
		MovePostion(velocity);
	}

	/**
	 * アニメーション
	 */
	private void Animation()
	{
		anmTimer+=Time.flameTime;
		int t = anmTimer / ChangeTime;
		t = t % 8;
		t = -Math.abs(-t+4)+4;//往復アニメーション処理
		imageIndex = t;
	}

	/**
	 * 攻撃処理
	 */
	private void Attack()
	{
		RandomAttack();

		//パターン攻撃
		patternBulletTimer += Time.flameTime;
		if(patternBulletTimer >= data[dataIndex].patternTime)
		{
			Vector2 firePos = new Vector2(postion.x, postion.y-10);
			patternBulletTimer -= data[dataIndex].patternTime;

			GameObject player = ObjectManager.FindObjectsTag(Tag.Player)[0];
			float deg = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
			switch(data[dataIndex].pattern)//パターンごとの弾を撃つ処理
			{
				case Closs:
					for(int i = 100; i < 400;i+=100)
					{
						ObjectManager.Instantiate(
								new Bullet("image/enemy_bullet.png", new Vector2(i, 0), new Vector2(0, 200), Tag.BossBullet));
					}
					for(int i = 100; i < 500;i+=100)
					{
						ObjectManager.Instantiate(
								new Bullet("image/enemy_bullet.png", new Vector2(0, i), new Vector2(200, 0), Tag.BossBullet));
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
						b.velocity.times(200);
					}
					break;
				case Voluted:
					for(int i = -144;i <= 144;i+=72)
					{
						Bullet b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(data[dataIndex].angle1 + i)),
										Tag.BossBullet));
						b.velocity.times(200);
					}
					data[dataIndex].angle1+=10;
					break;
				case Voluted2:
					for(int i = -144;i <= 144;i+=72)
					{
						Bullet b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(data[dataIndex].angle1 + i)),
										Tag.BossBullet));
						b.velocity.times(200);

						b = (Bullet)ObjectManager.Instantiate(
								new Bullet(
										"image/enemy_bullet.png",
										new Vector2(firePos),
										new Vector2(Vector2.DegreeToVector(data[dataIndex].angle2 + i)),
										Tag.BossBullet));
						b.velocity.times(100);
					}
					data[dataIndex].angle1+=10;
					data[dataIndex].angle2-=10;
					break;
				case FireFlower:
					for(int i = -180;i < 180;i+=60)
						//花火はBossクラスの子として扱い、拡散時は親子関係を外す
					ObjectManager.Instantiate(new FireFlower("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg + i)),200, Tag.BossBullet));
					break;
				case Tracking:
						//花火と同じく
					ObjectManager.Instantiate(new Tracking("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg)), 150, Tag.BossBullet));
					break;
			}
		}

		atkChangeTimer += Time.flameTime;
		if(atkChangeTimer >= 5000)//パターン変更処理
		{
			//攻撃パターン抽選
			int next = (int)(Math.random() * data.length);
			SetDataIndex(next);

			atkChangeTimer -= 5000;
			patternBulletTimer = 0;
		}
	}

	/**
	 * ランダム撃ち
	 */
	private void RandomAttack()
	{
		//花火時以外はランダム撃ちをする。
		if(data[dataIndex].pattern != AttackPattern.FireFlower)
		{
			randomBulletTimer += Time.flameTime;
			if(randomBulletTimer >= randomCoolTime)
			{
				randomBulletTimer -= randomCoolTime;
				Vector2 vel = new Vector2(Vector2.DegreeToVector((float) (Math.random() * 360)));
				vel.times(100);
				ObjectManager.Instantiate(
						new Bullet(
								"image/enemy_bullet.png",
								new Vector2(postion.x, postion.y-10),
								vel,
								Tag.BossBullet));
			}
		}
	}

	/**
	 * 当たり判定
	 */
	private void HitBullet()
	{
		//プレイヤーの弾を取得
		GameObject[] objs = ObjectManager.FindObjectsTag(Tag.PlayerBullet);
		for(int i = 0; i < objs.length; i++)
		{
			//矩形の当たり判定
			if(objs[i].postion.x >= postion.x - size.x / 2
					&& objs[i].postion.x <= postion.x + size.x / 2
					&& objs[i].postion.y >= postion.y - size.y / 2
					&& objs[i].postion.y <= postion.y + size.y / 2
					&& !invincible)
			{
				SEPlayer.PlaySE(SE.BossDamage);
				ObjectManager.Destroy(objs[i]);
				hp--;
			}
		}
	}

	public void Start()
	{
		postion = new Vector2(200,100);
	}
}

//Javaに構造体がないため代用
/**
 * 攻撃パターンのデータ
 */
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

	/**
	 * 攻撃パターン
	 */
	public final AttackPattern pattern;
	/**
	 * パターン変更時間
	 */
	public final float patternTime;

	//増えるなら配列にする。
	/**
	 * 基準角その1
	 */
	public final float InitialAngle1;
	/**
	 * 基準角その2
	 */
	public final float InitialAngle2;

	/**
	 * 現在の角度その1
	 */
	public float angle1;
	/**
	 * 現在の角度その2
	 */
	public float angle2;

	AttackPatternData(AttackPattern p, int time, float deg1, float deg2)
	{
		pattern = p;
		patternTime = time;
		InitialAngle1 = deg1;
		InitialAngle2 = deg2;
		Init();
	}

	AttackPatternData(AttackPattern p, int time)
	{
		pattern = p;
		patternTime = time;
		InitialAngle1 = 0;
		InitialAngle2 = 180;
		Init();
	}

	/**
	 * 初期化
	 */
	public void Init()
	{
		angle1 = InitialAngle1;
		angle2 = InitialAngle2;
	}
}