package Main;

//デバッグ用(このプロジェクトでは現在使われていない)
public class Debug {
	public static float FrameRate()
	{
		if(Time.flameTime != 0)
			return 1000 / Time.flameTime;
		else
			return 0;
	}
}
