package Main;

/**
 * BGMを再生するクラス
 */
public class BGMPlayer {
	public static BGMPlayer Instance = new BGMPlayer();

	//パス
	private final String path = "audio/";
	//BGM
	private AudioManager sound[] =
			new AudioManager[]{new AudioManager(path + "title.wav",0.5f),new AudioManager(path +"buttle.wav",0.5f)};

	/**
	 * BGMの種類
	 */
	public enum BGM
	{
		Title,
		Buttle
	}

	/**
	 * BGMを再生。既に流れている場合は一度止めて最初から流す。
	 * @param bgm BGMの種類
	 */
	public static void PlayBGM(BGM bgm)
	{
		int n = 0;
		switch(bgm)
		{
			case Title:
				n = 0;
				break;
			case Buttle:
				n = 1;
		}
		StopBGM();
		Instance.sound[n].PlayLoop();
	}

	/**
	 * BGMを止める。
	 */
	public static void StopBGM()
	{
		for(int i = 0; i < Instance.sound.length; i++)
		{
			Instance.sound[i].Reset();
		}
	}

}
