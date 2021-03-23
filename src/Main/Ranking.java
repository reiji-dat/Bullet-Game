package Main;

public class Ranking {
	
	boolean descending;//降順かどうか(大きい順)
	int data[];
	
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
	Ranking(int lng, int... score)
	{
		descending = true;
		Sort(lng, score);
	}
	
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
	Ranking(int lng, boolean des, int... score)
	{
		descending = des;
		Sort(lng, score);
	}
	
	void Sort(int lng, int score[])
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
	
	//ランキングに値を追加する。(番人法を使う)
	void Add(int score)
	{
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