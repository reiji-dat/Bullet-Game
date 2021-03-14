package Main;

import java.awt.Graphics;
import java.util.ArrayList;

public class Boss extends GameObject
{

	ArrayList<Bullet> e_Bullet = new ArrayList<>();
	ArrayList<FireFlower> fireFlower = new ArrayList<>();
	ArrayList<Tracking> tracking = new ArrayList<>();
	final int e_Bullet_Cool = 64;
	int e_Bullet_Timer;//敵のランダム撃ちタイマー
	int putternTime;
	int e_pBullet_Timer;
	
	final int BossMaxHP = 500;
	int hp;
	int modeTimer;//ボスの状態タイマー
	boolean invincible = true;
	int atkChangeTimer;
	float volDeg;
	float volDeg2;
	
	enum AttackPattern
	{
		Closs,//縦横
		Pick,//狙い撃ち
		Voluted,//渦巻
		Voluted2,//渦巻&逆渦巻
		FireFlower,//花火
		Tracking//追尾
	}
	
	AttackPattern pattern;
	
	Boss(String img, Vector2 pos, Vector2 size) {
		super(img, pos, size);
	}
	void MoveDraw(Graphics g,Player player)
	{
		DrawObject(g);
		for(int i = 0; i < player.p_Bullet.size();)
		{
			if(player.p_Bullet.get(i).postion.x >= postion.x - size.x / 2
			&& player.p_Bullet.get(i).postion.x <= postion.x + size.x / 2
			&& player.p_Bullet.get(i).postion.y >= postion.y - size.y / 2
			&& player.p_Bullet.get(i).postion.y <= postion.y + size.y / 2
			&& !invincible)
			{
				hp--;//TODO セッターをメインに作る
				player.p_Bullet.remove(i);
				continue;
			}
			i++;//物を消すと移動処理されないので、消したときは足さずに次のループへ
		}
		/*
		 * TODO 敵の挙動
		 * 動く(上半分限る)5±2秒で動く
		 * -----------
		 * 倒し方
		 * 30秒耐久(この間無敵)
		 * 10秒間攻撃できる。(弾幕の数を減らす)
		 * 大体3回繰り返す
		 * クリア
		 */
		modeTimer += Time.flameTime;
		if(invincible)
		{
			if(modeTimer >= 25000) 
			{
				invincible = !invincible;
				modeTimer -= 25000;
			}
			e_Bullet_Timer += Time.flameTime;
			if(e_Bullet_Timer >= e_Bullet_Cool && pattern != AttackPattern.FireFlower)
			{
				e_Bullet_Timer -= e_Bullet_Cool;
				//TODO 画像情報を毎回取得しているため1度だけで良い
				e_Bullet.add(new Bullet("enemy_bullet.png", new Vector2(postion.x, postion.y-10)));
				e_Bullet.get(e_Bullet.size()-1).speed 
				= new Vector2(Vector2.DegreeToVector((float) ((Math.random() * (360)))));
				e_Bullet.get(e_Bullet.size()-1).speed.times(2);
				
			}
			//パターン攻撃
			e_pBullet_Timer += Time.flameTime;
			if(e_pBullet_Timer >= putternTime)
			{
				Vector2 firePos = new Vector2(postion.x, postion.y-10);
				e_pBullet_Timer -= putternTime;
				float deg = Vector2.Angle(new Vector2(postion.x, postion.y-10),new Vector2(player.postion));
				switch(pattern)
				{
					case Closs:
						for(int i = 100; i < 400;i+=100)
						{
							e_Bullet.add(new Bullet("enemy_bullet.png",new Vector2(i,0)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(0,3);
						}
						for(int i = 100; i < 500;i+=100)
						{
							e_Bullet.add(new Bullet("enemy_bullet.png",new Vector2(0,i)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(3,0);
						}
						break;
					case Pick:
						for(int i = -40;i <= 40;i+=20)
						{
							e_Bullet.add(new Bullet("enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(deg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						break;
					case Voluted:
						for(int i = -144;i <= 144;i+=72)
						{
							e_Bullet.add(new Bullet("enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						volDeg+=10;
						break;
					case Voluted2:
						for(int i = -144;i <= 144;i+=72)
						{
							e_Bullet.add(new Bullet("enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
							e_Bullet.add(new Bullet("enemy_bullet.png", new Vector2(firePos)));
							e_Bullet.get(e_Bullet.size()-1).speed = new Vector2(Vector2.DegreeToVector(volDeg2 + i));
							e_Bullet.get(e_Bullet.size()-1).speed.times(3);
						}
						volDeg+=10;
						volDeg2-=10;
						break;
					case FireFlower:
						for(int i = -180;i < 180;i+=60)
							fireFlower.add(new FireFlower("enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg + i)),1));
						break;
					case Tracking:
							tracking.add(new Tracking("enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg)),1));
						break;
				}
			}
			atkChangeTimer += Time.flameTime;
			if(atkChangeTimer >= 5000)
			{
				//攻撃パターン抽選
				int next = (int)(Math.random() * 6);
				switch(next)
				{
					case 0:
						pattern = AttackPattern.Closs;
						putternTime = 500;
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
		else
		{
			if(modeTimer >= 10000) 
			{
				invincible = !invincible;
				modeTimer -= 10000;
				atkChangeTimer = 0;
			}
		}
		
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
	}
	
	void Init()
	{
		postion = new Vector2(200,100);
		hp = BossMaxHP;
		e_Bullet.clear();
		fireFlower.clear();
		tracking.clear();
		e_Bullet_Timer = 0;
		atkChangeTimer = 0;
		modeTimer = 0;
		e_pBullet_Timer = 0;;
		invincible = true;
		pattern = AttackPattern.Closs;
		putternTime = 500;
	}
	
	void Add(Bullet bullet)
	{
		e_Bullet.add(bullet);
	}

}
