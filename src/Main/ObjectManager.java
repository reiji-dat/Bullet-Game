package Main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class CastClass<T>
{
	public T DownCast(GameObject obj)
	{
		try {
			return (T)obj;
		}catch(ClassCastException e)
		{
			System.out.println("キャストが失敗しました！");
			return null;
		}

	}
}

public class ObjectManager
{
	public static List<GameObject> objects = new ArrayList<GameObject>();

	public static void UpdateObjects(Graphics g)
	{
		//System.out.println("オブジェクトの数" + objects.size());
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).Update(g);
		}
	}

	public static void StartObject(GameObject obj)
	{
		obj.Start();
	}

	public static void OnDestroyObject(GameObject obj)
	{
		obj.OnDestroy();
	}

	public static GameObject Instantiate(GameObject obj)
	{
		System.out.println("オブジェクト生成");
		objects.add(obj);
		StartObject(obj);
		System.out.println("オブジェクトの数" + objects.size());
		return obj;
	}

	public static GameObject[] FindObjectsTag(GameObject.Tag tag)
	{
		List<GameObject> hits = new ArrayList<GameObject>();
		for(int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).tag == tag) hits.add(objects.get(i));
		}
		return hits.toArray(new GameObject[0]);
	}

	public static void Destroy(GameObject obj)
	{
		OnDestroyObject(obj);
		objects.remove(obj);
	}

	//シーンをまたぐものがないから全て消す。
	public static void AllDestroyGameObjects()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			OnDestroyObject(objects.get(i));
		}
		objects.clear();
	}
}
