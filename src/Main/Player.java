package Main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends GameObject{
	ArrayList<GameObject> p_Bullet = new ArrayList<>();
	float playerSpeed = 3;
	final float P_BulletSpeed = 4;
	final int MaxHP = 5;
	int hp;
	final int MaxMP = 30;
	int mp;
	
	int bulletTimer;//MP回復用タイマー
	
	boolean invincible = false;
	final int InvTime = 1000;
	int invTimer;
	
	Player(String img, Vector2 pos) 
	{
		super(img, pos);
	}
	
	//敵が複数いる場合はメインにゲッターを用意して情報を取得
	//今回は1体だけなので引数にしている
	//TODO ボスクラスを作ったら第3,4引数はいらない
	void MoveDraw(Graphics g,GameObject boss)
	{
		if(invincible)
		{
			invTimer += Time.flameTime;
			show = Math.sin(invTimer/20) <= 0 ? true : false;
			if(invTimer>=InvTime)
			{
				show = true;
				invincible = false;
				invTimer = 0;
			}
		}
		//移動
		if(KeyInput.inputKey[KeyEvent.VK_W]) postion.y -= playerSpeed;
		if(KeyInput.inputKey[KeyEvent.VK_A]) postion.x -= playerSpeed;
		if(KeyInput.inputKey[KeyEvent.VK_S]) postion.y += playerSpeed;
		if(KeyInput.inputKey[KeyEvent.VK_D]) postion.x += playerSpeed;
		
		//移動制限
		postion.x = postion.x < 0 ? 0 : postion.x;
		postion.x = postion.x > 400 ? 400 : postion.x;
		postion.y = postion.y < 0 ? 0 : postion.y;
		postion.y = postion.y > 500 ? 500 : postion.y;
		DrawObject(g);
		
		//弾
		if(bulletTimer < 500) bulletTimer += Time.flameTime;
		else if(mp < MaxMP)
		{
			bulletTimer -= 500;
			mp++;
		}
		
		if(KeyInput.pressedKey[KeyEvent.VK_SPACE] && mp > 0) 
		{
			mp--;
			p_Bullet.add(new GameObject("player_bullet.png",new Vector2(postion.x,postion.y-10)));
			p_Bullet.add(new GameObject("player_bullet.png",new Vector2(postion.x+20,postion.y+5)));
			p_Bullet.add(new GameObject("player_bullet.png",new Vector2(postion.x-20,postion.y+5)));
		}
		for(int i = 0; i < p_Bullet.size();)
		{
			p_Bullet.get(i).postion.y-=P_BulletSpeed;
			if(p_Bullet.get(i).postion.y < 0 || p_Bullet.get(i).postion.x < 0 || p_Bullet.get(i).postion.x > 400) 
			{
				p_Bullet.remove(i);
				continue;
			}
			p_Bullet.get(i).DrawObject(g);
			i++;//物を消すと移動処理されないので、消したときは足さずに次のループへ
		}
	}
	
	void Init()
	{
		bulletTimer = 0;
		hp = MaxHP;
		mp = MaxMP;
		p_Bullet.clear();
		postion = new Vector2(200,400);
		invincible = false;
		invTimer = 0;
	}
}
