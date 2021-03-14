package Main;

import java.awt.Graphics;

public class FireFlower extends Bullet{
	final float DiffDistance = 100;
	float currentDist = 0;
	float velocity;
	
	boolean diffusion = false;
	boolean end = false;
	
	FireFlower(String img, Vector2 pos, Vector2 speed, float vel) {
		
		super(img, pos, speed);
		System.out.println("撃った");
		velocity = vel;
		this.speed.times(vel);
	}
	
	void MoveDraw(Graphics g, Boss person)
	{
		
		if(currentDist <= DiffDistance)
		{
			System.out.println(currentDist);
			currentDist+=velocity;
			movePostion(speed);
			DrawObject(g);
		}
		else
		{
			System.out.println("拡散");
			for(int i = 0;i < 360;i+=36)
			{
				Vector2 vec = new Vector2(Vector2.DegreeToVector(i));
				vec.times(3);
				person.Add(new Bullet("enemy_bullet.png", new Vector2(postion),vec));
			}
			end = true;
		}
	}
}
