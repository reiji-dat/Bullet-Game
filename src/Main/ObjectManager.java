package Main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * キャスト用クラス
 * @param <T> キャストしたいクラス
 */
class CastClass<T>
{
	/**
	 * ダウンキャストが出来るかチェック。
	 * ダウンキャストとは継承先のクラスに変更すること。
	 * @param obj 変更したいオブジェクト
	 * @return キャストが成功したか
	 */
	public boolean CheckDownCast(GameObject obj)
	{
		try {
			T a = (T)obj;
			return true;
		}catch(ClassCastException e)
		{
			System.out.println("キャストが失敗しました！");
			return false;
		}

	}
}

/**
 * GameObjectを一括管理するクラス
 */
public class ObjectManager
{
	/**
	 * 生成したGameObject
	 */
	public static List<GameObject> objects = new ArrayList<GameObject>();

	/**
	 * 生成されたGameObjectの状態を全て更新
	 * @param g
	 */
	public static void UpdateObjects(Graphics g)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).Update(g);
		}
	}

	/**
	 * 初回のみの処理を実行
	 * @param obj 実行したいGameObject
	 */
	public static void StartObject(GameObject obj)
	{
		obj.Start();
	}

	/**
	 * 消したときに呼ばれる処理
	 * @param obj 実行したいGameObject
	 */
	public static void OnDestroyObject(GameObject obj)
	{
		obj.OnDestroy();
	}

	/**
	 * GameObjectを生成
	 * @param obj 生成したいオブジェクト
	 * @return 生成したオブジェクト
	 */
	public static GameObject Instantiate(GameObject obj)
	{
		objects.add(obj);
		StartObject(obj);
		return obj;
	}

	/**
	 * タグ指定されたオブジェクトだけを取得
	 * @param tag タグ
	 * @return 取得したオブジェクト
	 */
	public static GameObject[] FindObjectsTag(GameObject.Tag tag)
	{
		List<GameObject> hits = new ArrayList<GameObject>();
		for(int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).tag == tag) hits.add(objects.get(i));
		}
		return hits.toArray(new GameObject[0]);
	}

	/**
	 * 指定したオブジェクトを消す
	 * @param obj 消したいオブジェクト
	 */
	public static void Destroy(GameObject obj)
	{
		OnDestroyObject(obj);
		objects.remove(obj);
	}

	//シーンをまたぐものがないから全て消す。
	/**
	 * 全てのオブジェクトを消す。
	 */
	public static void AllDestroyGameObjects()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			OnDestroyObject(objects.get(i));
		}
		objects.clear();
	}
}
