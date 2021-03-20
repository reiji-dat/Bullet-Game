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
	
	int moveTimer;
	int moveTime = 4000;
	boolean move = false;
	Vector2 speed = new Vector2(Vector2.Zero);
	
	final int ChangeTime = 100;
	int anmTimer = 0;
	
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
	
	Boss(Vector2 pos, Vector2 size,String... img) {super(pos, size, img);}
	
	void MoveDraw(Graphics g,Player player)
	{
		
		
		moveTimer+=Time.flameTime;
		if(moveTimer >= moveTime)
		{
			System.out.println(moveTimer+ "," +  moveTime);
			moveTimer = 0;
			if(move)
			{
				moveTime = 4000;
				speed = new Vector2(Vector2.Zero);
			}
			else
			{
				moveTime = (int)(Math.random()*2000)+3000;
				speed = new Vector2(Vector2.DegreeToVector((float)Math.random()*360));
				speed.division(4);
			}
			move = !move;
		}
		if(postion.x < 50 || postion.x > 350 || postion.y < 50 || postion.y > 200)
		{
			moveTimer = 6000;
			postion.times(-1);
			movePostion(speed);
		}
		movePostion(speed);
		
		anmTimer+=Time.flameTime;
		int t = anmTimer / ChangeTime;
		t = t % 8;
		t = -Math.abs(-t+4)+4;//往復アニメーション処理
		DrawObject(g,t);
		
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
		modeTimer += Time.flameTime;
		if(invincible)
		{
			if(modeTimer >= 25000) 
			{
				invincible = !invincible;
				modeTimer -= 25000;
			}
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
				switch(pattern)
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
							fireFlower.add(new FireFlower("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg + i)),1));
						break;
					case Tracking:
							tracking.add(new Tracking("image/enemy_bullet.png", new Vector2(firePos), new Vector2(Vector2.DegreeToVector(deg)),1));
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
				SEManager.PlaySE(SEManager.SE.Damage);
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
				SEManager.PlaySE(SEManager.SE.Damage);
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
				SEManager.PlaySE(SEManager.SE.Damage);
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
		moveTimer = 0;
		moveTime = 4000;
		anmTimer = 0;
	}
	
	void Add(Bullet bullet){ e_Bullet.add(bullet); }
}