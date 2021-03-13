package Main;
class Collider {
	public static boolean EnterCollider(Vector2 v1,Vector2 v2,float v1size)
	{
		v1size*=v1size;
		v2.minus(v1);
		v2.times(v2);
		return v1size >= v2.x+v2.y ? true : false;
	}
}
