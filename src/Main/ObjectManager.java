package Main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager
{
	public static List<GameObject> objects = new ArrayList<GameObject>();

	public static void UpdateObjects(Graphics g)
	{
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
		return obj;
	}

	public static GameObject[] FindObjectsTag(String tag)
	{
		List<GameObject> hits = new ArrayList<GameObject>();
		for(int i = 0; i < objects.size(); i++)
		{
			hits.add(objects.get(i));
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
