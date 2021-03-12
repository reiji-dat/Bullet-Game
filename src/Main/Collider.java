package Main;
class Collider {
	public static boolean EnterCollider(Vector2 v1,Vector2 v2,float v1size)
	{
		if(v1size>=Vector2.distance(v1, v2))
			return true;
		else return false;
	}
}
