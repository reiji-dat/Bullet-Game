package Main;

//ベクトル処理Pointだとintしか対応していないため独自に作った
public class Vector2 {
	public float x,y;
	public static final Vector2 Top = new Vector2(0,-1);
	public static final Vector2 Bottom = new Vector2(0,1);
	public static final Vector2 Right = new Vector2(1,0);
	public static final Vector2 Left = new Vector2(-1,0);
	public static final Vector2 Zero = new Vector2(0,0);
	
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
	
	Vector2(Vector2 v)
	{
		this.x = v.x;
        this.y = v.y;
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
	
	void times(float a)
	{      
		x *= a;
		y *= a;
	}
	
	void division(Vector2 v2)
	{
		x /= v2.x;
		y /= v2.y;
	}
	
	void division(float a)
	{
		x /= a;
		y /= a;
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
	
	//距離を求める
	public static float distance(Vector2 v1,Vector2 v2)
	{
		v2.minus(v1);
		v2.x *= v2.x;
		v2.y *= v2.y;
		return (float)Math.sqrt(v2.x + v2.y);
	}
	
	//0を始点に向きを求める
	public static float Angle(Vector2 v)
	{
		float r = (float) Math.atan2( v.y - Zero.y ,v.x - Zero.x);
		if (r < 0) {
	        r = (float) (r + 2 * Math.PI);
	    }
	    return (float) (r * 360 / (2 * Math.PI));
	}
	
	//向きを求める
	public static float Angle(Vector2 v1, Vector2 v2)
	{
		float r = (float) Math.atan2( v2.y - v1.y ,v2.x - v1.x);
		if (r < 0) {
	        r = (float) (r + 2 * Math.PI);
	    }
	    return (float) (r * 360 / (2 * Math.PI));
	}
	
	//角度からベクトルを求める(上が0度、距離は1)
	public static Vector2 DegreeToVector(float degree)
	{
		float rad = (float)Math.toRadians(degree);
		return new Vector2((float)Math.cos(rad), (float)Math.sin(rad));
	}
	
	//主にデバッグ用
	String ToString()
	{
		return "(" + x + "," + y + ")";
	}
}
