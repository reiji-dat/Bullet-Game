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
			dt[i] = 0;
		}
		data = dt;
	}
	
	Ranking(int lng, int... score)
	{
		descending = true;
		Sort(lng, score);
	}
	
	void Sort(int lng, int score[])
	{
		int dt[] = new int[lng];
		//TODO 上からlng番目しかいらないので選択ソート
		data = dt;
	}

}
