package Main;

public class FireFlower extends Bullet{
	final float DiffDistance = 100;//拡散する距離
	float currentDist = 0;//発射されて進んだ距離
	float velocity2;//速度

	boolean end = false;//拡散したか


	FireFlower(String img, Vector2 pos, Vector2 speed, float vel)
	{
		super(img, pos, speed);
		velocity2 = vel;
		this.velocity.times(vel);
	}

	/*
	void MoveDraw(Graphics g, Boss person)
	{
		if(currentDist <= DiffDistance)//拡散前
		{
			currentDist+=velocity;
			movePostion(speed);
			DrawObject(g);
		}
		else//拡散後
		{
			for(int i = 0;i < 360;i+=36)
			{
				Vector2 vec = new Vector2(Vector2.DegreeToVector(i));
				vec.times(3);
				person.Add(new Bullet("image/enemy_bullet.png", new Vector2(postion),vec));
			}
			end = true;
		}
	}
	*/
}
