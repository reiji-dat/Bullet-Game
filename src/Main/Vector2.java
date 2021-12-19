package Main;

//ベクトル処理Pointだとintしか対応していないため独自に作った
/**
 * ベクトルクラス
 */
public class Vector2 {
	public float x,y;
	public static final Vector2 Top = new Vector2(0,-1);
	public static final Vector2 Bottom = new Vector2(0,1);
	public static final Vector2 Right = new Vector2(1,0);
	public static final Vector2 Left = new Vector2(-1,0);
	public static final Vector2 Zero = new Vector2(0,0);
	public static final Vector2 One = new Vector2(1,1);

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

	/**
	 * 足す
	 * @param v2
	 */
	void plus(Vector2 v2)
	{
		x += v2.x;
		y += v2.y;
	}

	/**
	 * 引く
	 * @param v2
	 */
	void minus(Vector2 v2)
	{
		x -= v2.x;
		y -= v2.y;
	}

	/**
	 * 掛ける
	 * @param a
	 */
	void times(float a)
	{
		x *= a;
		y *= a;
	}

	/**
	 * 割る
	 * @param a
	 */
	void division(float a)
	{
		x /= a;
		y /= a;
	}

	/**
	 * 正規化
	 */
	void normalize()
	{
		float xx = x * x;
		float yy = y * y;
		float d = xx + yy;
		d = (float)Math.sqrt(d);
		x /= d;
		y /= d;
	}

	/**
	 * 距離を求める(負荷高)
	 * @param v1
	 * @param v2
	 * @return 距離
	 */
	public static float distance(Vector2 v1,Vector2 v2)
	{
		v2.minus(v1);
		v2.x *= v2.x;
		v2.y *= v2.y;
		return (float)Math.sqrt(v2.x + v2.y);
	}

	/**
	 * 0を始点に角度を求める(上が0度)
	 * @param v
	 * @return 角度(degree)
	 */
	public static float Angle(Vector2 v)
	{
		float r = (float) Math.atan2( v.y - Zero.y ,v.x - Zero.x);
		if (r < 0) {
	        r = (float) (r + 2 * Math.PI);
	    }
	    return (float) (r * 360 / (2 * Math.PI));
	}

	/**
	 * 2点から角度を求める(上が0度)
	 * @param v1
	 * @param v2
	 * @return 角度(degree)
	 */
	public static float Angle(Vector2 v1, Vector2 v2)
	{
		float r = (float) Math.atan2( v2.y - v1.y ,v2.x - v1.x);
		if (r < 0) {
	        r = (float) (r + 2 * Math.PI);
	    }
	    return (float) (r * 360 / (2 * Math.PI));
	}

	//角度からベクトルを求める(上が0度、距離は1)
	/**
	 * 角度からベクトルを求める(上が0度, 距離は1)
	 * @param degree 角度(degree)
	 * @return 距離が1のベクトル
	 */
	public static Vector2 DegreeToVector(float degree)
	{
		float rad = (float)Math.toRadians(degree);
		return new Vector2((float)Math.cos(rad), (float)Math.sin(rad));
	}

	//主にデバッグ用
	public String ToString()
	{
		return "(" + x + "," + y + ")";
	}
}
