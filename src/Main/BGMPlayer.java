package Main;

public class BGMPlayer {
	public static BGMPlayer Instance = new BGMPlayer();

	private final String f = "audio/";
	private AudioManager sound[] =
			new AudioManager[]{new AudioManager(f + "title.wav",0.5f),new AudioManager(f +"buttle.wav",0.5f)};

	enum BGM
	{
		Title,
		Buttle
	}

	public static void PlayBGM(BGM se)
	{
		int n = 0;
		switch(se)
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

	public static void StopBGM()
	{
		for(int i = 0; i < Instance.sound.length; i++)
		{
			Instance.sound[i].Reset();
		}
	}

}
