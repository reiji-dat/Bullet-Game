package Main;

import java.awt.Graphics;
import java.util.ArrayList;

public class Boss extends GameObject
{
	//弾
	ArrayList<Bullet> e_Bullet = new ArrayList<>();
	ArrayList<FireFlower> fireFlower = new ArrayList<>();
	ArrayList<Tracking> tracking = new ArrayList<>();

	final int e_Bullet_Cool = 64;//ランダム撃ちの頻度
	int e_Bullet_Timer;//敵のランダム撃ちタイマー

	int putternTime;//パターン攻撃の攻撃頻度
	int e_pBullet_Timer;//パターン攻撃タイマー

	final int MaxHP = 500;//最大体力
	int hp;//体力

	int modeTimer;//ボスの状態タイマー
	final int ModeChangeTime = 25000;

	boolean invincible = true;//無敵かどうか
	int atkChangeTimer;//攻撃種類変更タイマー

	float volDeg;//回転用
	float volDeg2;//逆回転用

	int moveTimer;//動き関係タイマー
	int moveTime = 4000;//動く(止まる)時間
	boolean move = false;//動くか止まるか
	Vector2 speed = new Vector2(Vector2.Zero);//スピード

	//アニメーションタイマー
	final int ChangeTime = 100;
	int anmTimer = 0;

	enum AttackPattern	//攻撃パターン
	{
		Closs,		//縦横
		Pick,		//狙い撃ち
		Voluted,	//渦巻
		Voluted2,	//渦巻&逆渦巻
		FireFlower,//花火
		Tracking	//追尾
	}
	AttackPattern pattern;

	Boss(Vector2 pos, Vector2 size,String... img) {super(pos, size,Tag.Boss, img);}

	void MoveDraw(Graphics g,Player player)
	{
		moveTimer+=Time.flameTime;
		if(moveTimer >= moveTime)//動きを変える時間を超えたら
		{
			moveTimer = 0;
			if(move)//動いていたら止まる処理
			{
				moveTime = 4000;
				speed = new Vector2(Vector2.Zero);
			}
			else	//止まっていたら動く処理
			{
				moveTime = (int)(Math.random()*2000)+3000;
				speed = new Vector2(Vector2.DegreeToVector((float)Math.random()*360));
				speed.division(4);
			}
			move = !move;//状態反転
		}
		//動ける範囲の制限
		if(postion.x < 50 || postion.x > 350 || postion.y < 50 || postion.y > 200)
		{
			speed.times(-1);
			//movePostion(speed);//動く処理を重複させてハマるのを防ぐ
		}
		movePostion(speed);
		anmTimer+=Time.flameTime;
		int t = anmTimer / ChangeTime;
		t = t % 8;
		t = -Math.abs(-t+4)+4;//往復アニメーション処理
		DrawObject(g,t);

		/*
		//プレイヤーの弾が当たった処理
		for(int i = 0; i < player.p_Bullet.size();)
		{
			if(player.p_Bullet.get(i).postion.x >= postion.x - size.x / 2
			&& player.p_Bullet.get(i).postion.x <= postion.x + size.x / 2
			&& player.p_Bullet.get(i).postion.y >= postion.y - size.y / 2
			&& player.p_Bullet.get(i).postion.y <= postion.y + size.y / 2
			&& !invincible)//無敵時間以外
			{
				hp--;
				player.p_Bullet.remove(i);
				continue;
			}
			i++;//物を消すと移動処理されないので、消したときは足さずに次のループへ
		}

		modeTimer += Time.flameTime;
		if(invincible)
		{
			//TODO 変更時間を定数にしたほうが良い
			if(modeTimer >=  ModeChangeTime)
			{
				invincible = !invincible;
				modeTimer -= ModeChangeTime;
			}
			//花火時以外はランダム撃ちをする。
			if(pattern != AttackPattern.FireFlower)
			{
				e_Bullet_Timer += Time.flameTime;
				if(e_Bullet_Timer >= e_Bullet_Cool)
				{
					e_Bullet_Timer -= e_Bullet_Cool;
					//TODO 画像情報を毎回取得しているため1度だけで良い
					e_Bullet.add(new Bullet("image/enemy_bullet.png", new Vector2(postion.x, postion.y-10)));
					e_Bullet.get(e_Bullet.size()-1).speed
						= new Vector2(Vector2.DegreeToVector((float) ((Math.random() * (360)))));
					e_Bullet.get(e_Bullet.size()-1).speed.times(2);
				}
			}
			//パターン攻撃
			e_pBullet_Timer += Time.flameTime;
			if(e_pBullet_Timer >= putternTime)
			{
				Vector2 firePos = new Vector2(postion.x, postion.y-10);
				e_pBullet_Timer -= putternTime;
				float deg = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
				switch(pattern)//パターンごとの弾を撃つ処理
				{
					case Closs:
						for(int i = 100; i < 400;i+=100)
						{
							e_Bullet.add(new Bullet("image/enemy_bullet.png",new Vector2(i,0)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(0,3);
						}
						for(int i = 100; i < 500;i+=100)
						{
							e_Bullet.add(new Bullet("image/enemy_bullet.png",new Vector2(0,i)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(3,0);
						}
						break;
					case Pick:
						for(int i = -40;i <= 40;i+=20)
						{
							e_Bullet.add(new Bullet("image/enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(deg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						break;
					case Voluted:
						for(int i = -144;i <= 144;i+=72)
						{
							e_Bullet.add(new Bullet("image/enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						volDeg+=10;
						break;
					case Voluted2:
						for(int i = -144;i <= 144;i+=72)
						{
							e_Bullet.add(new Bullet("image/enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
							e_Bullet.add(new Bullet("image/enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg2 + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						volDeg+=10;
						volDeg2-=10;
						break;
					case FireFlower:
						for(int i = -180;i < 180;i+=60)
							//花火はBossクラスの子として扱い、拡散時は親子関係を外す
							fireFlower.add(new FireFlower("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg + i)),1));
						break;
					case Tracking:
							//花火と同じく
							tracking.add(new Tracking("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg)),1));
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
		else //無敵じゃなければ
		{
			if(modeTimer >= 10000)
			{
				invincible = !invincible;
				modeTimer -= 10000;
				atkChangeTimer = 0;
			}
		}

		//それぞれプレーヤーの当たり判定
		for(int i = 0; i < e_Bullet.size();)
		{
			e_Bullet.get(i).movePostion(e_Bullet.get(i).speed);
			if(e_Bullet.get(i).postion.y < 0
			|| e_Bullet.get(i).postion.y > 500
			|| e_Bullet.get(i).postion.x < 0
			|| e_Bullet.get(i).postion.x > 400)
			{
				e_Bullet.remove(i);
				continue;
			}
			if(!player.invincible && Collider.EnterCollider(player.postion, new Vector2(e_Bullet.get(i).postion), 7))
			{
				SEPlayer.PlaySE(SEPlayer.SE.Damage);
				player.invincible = true;
				e_Bullet.remove(i);
				player.hp--;
				continue;
			}
			e_Bullet.get(i).DrawObject(g);
			i++;
		}
		for(int i = 0; i < fireFlower.size();)
		{
			fireFlower.get(i).movePostion(fireFlower.get(i).speed);
			if(fireFlower.get(i).postion.y < 0
			|| fireFlower.get(i).postion.y > 500
			|| fireFlower.get(i).postion.x < 0
			|| fireFlower.get(i).postion.x > 400)
			{
				fireFlower.remove(i);
				continue;
			}
			if(!player.invincible && Collider.EnterCollider(player.postion, new Vector2(fireFlower.get(i).postion), 7))
			{
				SEPlayer.PlaySE(SEPlayer.SE.Damage);
				player.invincible = true;
				fireFlower.remove(i);
				player.hp--;
				continue;
			}
			if(fireFlower.get(i).end)
			{
				fireFlower.remove(i);
				continue;
			}
			fireFlower.get(i).MoveDraw(g,this);
			i++;
		}
		for(int i = 0; i < tracking.size();)
		{

			tracking.get(i).movePostion(tracking.get(i).speed);
			if(tracking.get(i).postion.y < 0
			|| tracking.get(i).postion.y > 500
			|| tracking.get(i).postion.x < 0
			|| tracking.get(i).postion.x > 400)
			{
				tracking.remove(i);
				continue;
			}
			if(!player.invincible && Collider.EnterCollider(player.postion, new Vector2(tracking.get(i).postion), 7))
			{
				SEPlayer.PlaySE(SEPlayer.SE.Damage);
				player.invincible = true;
				tracking.remove(i);
				player.hp--;
				continue;
			}
			if(tracking.get(i).nearPlayer)
			{
				tracking.remove(i);
				continue;
			}
			tracking.get(i).MoveDraw(g,this,player);
			i++;
		}
		*/
	}

	//初期化
	void Init()
	{
		postion = new Vector2(200,100);
		hp = MaxHP;
		//e_Bullet.clear();
		//fireFlower.clear();
		//tracking.clear();
		e_Bullet_Timer = 0;
		atkChangeTimer = 0;
		modeTimer = 0;
		e_pBullet_Timer = 0;;
		invincible = true;
		pattern = AttackPattern.Closs;
		putternTime = 500;
		moveTimer = 0;
		moveTime = 4000;
		anmTimer = 0;
	}

	public void Start()
	{
	}

	//弾の追加(特殊弾から普通の弾に移行するときに使う)
	void Add(Bullet bullet){ e_Bullet.add(bullet); }
}