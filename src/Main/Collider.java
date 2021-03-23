package Main;
class Collider {
	//円形同士の当たり判定
	public static boolean EnterCollider(Vector2 v1,Vector2 v2,float v1size)
	{
		v1size*=v1size;
		v2.minus(v1);
		v2.times(v2);
		return v1size >= v2.x+v2.y ? true : false;
	}
}
