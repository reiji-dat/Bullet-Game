package Main;

/**
 * ランキングクラス
 */
public class Ranking {

	/**
	 * true:降順, false:昇順
	 */
	public boolean descending;//降順かどうか(大きい順)
	/**
	 * ランキングデータ
	 */
	public int data[];

	/**
	 * ランキングクラス
	 * @param lng 要素数
	 */
	Ranking(int lng)
	{
		descending = true;
		int dt[] = new int[lng];
		for(int i = 0; i < dt.length; i++)
		{
			dt[i] = -2147483648;
		}
		data = dt;
	}

	/**
	 * ランキングクラス
	 * @param lng 要素数
	 * @param score 初期スコア(配列)
	 */
	Ranking(int lng, int... score)
	{
		descending = true;
		Sort(lng, score);
	}

	/**
	 * ランキングクラス
	 * @param lng 要素数
	 * @param des true:降順, false:昇順
	 */
	Ranking(int lng,boolean des)
	{
		descending = des;
		int dt[] = new int[lng];
		for(int i = 0; i < dt.length; i++)
		{
			dt[i] = descending ? -2147483648 : 2147483647;//降順だったらintの最小、逆は最大
		}
		data = dt;
	}

	/**
	 * ランキングクラス
	 * @param lng 要素数
	 * @param des true:降順, false:昇順
	 * @param score 初期スコア(配列)
	 */
	Ranking(int lng, boolean des, int... score)
	{
		descending = des;
		Sort(lng, score);
	}

	/**
	 * ソート
	 * @param lng 要素数
	 * @param score スコア(配列)
	 */
	private void Sort(int lng, int score[])
	{
		//バブルソート
		for(int r = score.length - 1; r >= 1;r--)
		{
			for(int i = 1; i <= r; i++)
			{
				if(descending && score[i-1] < score[i])
				{
					//入れ替える
					score[i-1] = score[i-1] + score[i];
					score[i] = score[i-1] - score[i];
					score[i-1] = score[i-1] - score[i];
				}
				else if(score[i-1] > score[i])
				{
					//入れ替える
					score[i-1] = score[i-1] + score[i];
					score[i] = score[i-1] - score[i];
					score[i-1] = score[i-1] - score[i];
				}
			}
		}
		//score[]ソート済み、取りたいデータ数だけ取る。
		//もし足りなければ初期値を設定する
		int dt[] = new int[lng];//ランキング表示上限設定
		int k = descending ? -2147483648 : 2147483647;
		for(int i = 0; i < dt.length; i++) dt[i] = i < score.length ? score[i] : k;
		data = dt;
	}

	/**
	 * ランキングに値を追加(要素数不変)
	 * @param score 追加したい値
	 */
	public void Add(int score)
	{
		//番人法
		if(descending)
		{
			data[data.length - 1] = data[data.length - 1] < score ? score : data[data.length - 1];
			for(int i = data.length - 2; i >= 0; i--)
			{
				if(data[i] < data[i+1])
				{
					data[i+1] = data[i+1] + data[i];
					data[i] = data[i+1] - data[i];
					data[i+1] = data[i+1] - data[i];
				}
			}
		}
		else
		{
			data[data.length - 1] = data[data.length - 1] > score ? score : data[data.length - 1];
			for(int i = data.length - 2; i >= 0; i--)
			{
				if(data[i] > data[i+1])
				{
					data[i+1] = data[i+1] + data[i];
					data[i] = data[i+1] - data[i];
					data[i+1] = data[i+1] - data[i];
				}
			}
		}
	}
}