package Main;

/**
 * 時間計測のためのクラス
 */
public class Time{
	public static Time Instance = new Time();

	private long pastTime;
	private long currentTime;

	/**
	 * 1フレームの時間(ミリ秒)
	 */
	public static long flameTime;

	Time()
	{
		currentTime = System.currentTimeMillis();
	}

	/**
	 * 時間計測(毎フレーム呼ぶ必要有)
	 */
	public static void TimeCount()
	{
		Instance.pastTime = Instance.currentTime;
		Instance.currentTime = System.currentTimeMillis();
		flameTime = Instance.currentTime - Instance.pastTime;
	}

	//時間をストリング型に直す処理
	/**
	 * 時間をストリング型に直す
	 * @param time 時間
	 * @return 文字列
	 */
	public static String MMSSFF(int time)
	{
		int frames = (time % 1000) /10;
		time /= 1000;
		int seconds = time % 60;
		int minutes = time / 60;
		return String.format("%02d",minutes) + ":" + String.format("%02d",seconds) + ":" + String.format("%02d",frames);
	}

	/**
	 * 1フレームの秒数
	 * @return 秒数
	 */
	public static float GetDeltaTime()
	{
		return flameTime / 1000.0f;
	}
}
