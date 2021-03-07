package Main;


public class Vector2 {
	
	public float x,y;
	
	Vector2(float x,float y)
	{
		this.x = x;
        this.y = y;
	}
	
	Vector2(int x,int y)
	{
		this.x = x;
        this.y = y;
	}
	
	void plus(Vector2 v2)
	{      
		x += v2.x;
		y += v2.y;
	}
	
	void minus(Vector2 v2)
	{      
		x -= v2.x;
		y -= v2.y;
	}
	
	void times(Vector2 v2)
	{      
		x *= v2.x;
		y *= v2.y;
	}
	
	void division(Vector2 v2)
	{
		x /= v2.x;
		y /= v2.y;
	}
	
	void normalize()
	{
		float xx = x * x;
		float yy = y * y;
		float d = xx + yy;
		d = (float)Math.sqrt(d);
		x /= d;
		y /= d;
	}
	
	//距離を求める(パブスタにする)
	public static float distance(Vector2 v1,Vector2 v2)
	{
		v1.minus(v2);
		v1.x *= v1.x;
		v1.y *= v1.y;
		return (float)Math.sqrt(v1.x + v1.y);
	}
	
	String ToString()
	{
		return "(" + x + "," + y + ")";
	}
}
