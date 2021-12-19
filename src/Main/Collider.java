package Main;

/**
 * コライダークラス
 */
class Collider {
	//円形同士の当たり判定
	/**
	 * 円形同士の当たり判定(1つ目大きさあり,2つ目大きさなし)
	 * @param v1 調べたいもの
	 * @param v2 調べたい場所
	 * @param v1size 調べたいものの大きさ
	 * @return 当たっているか
	 */
	public static boolean EnterCollider(Vector2 v1,Vector2 v2,float v1size)
	{
		v1size*=v1size;
		v2.minus(v1);
		v2.x *= v2.x;
		v2.y *= v2.y;
		return v1size >= v2.x+v2.y;
	}
}
