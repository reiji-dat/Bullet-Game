package Main;

//時間を計測するための処理
public class Time{
	public static Time Instance = new Time(); 
	long pastTime;
	long currentTime;
	public static long flameTime;
	
	Time()
	{
		currentTime = System.currentTimeMillis();
	}
	public static void TimeCount() 
	{
		Instance.pastTime = Instance.currentTime;
		Instance.currentTime = System.currentTimeMillis();
		flameTime = Instance.currentTime - Instance.pastTime;
	}
	
	//時間をストリング型に直す処理
	public static String MMSSFF(int time)
	{
		int frames = (time % 1000) /10;
		time /= 1000;
		int seconds = time % 60;
		int minutes = time / 60;
		return String.format("%02d",minutes) + ":" + String.format("%02d",seconds) + ":" + String.format("%02d",frames);
	}
}
