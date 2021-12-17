package Main;

//SEを再生するクラス
public class SEPlayer
{
	public static SEPlayer Instance = new SEPlayer();
	private final String f = "audio/";
	private AudioManager sound[] =
			new AudioManager[]{new AudioManager(f+"select.wav"),new AudioManager(f+"attack.wav"),new AudioManager(f+"damage.wav"),
								new AudioManager(f+"clear.wav"),new AudioManager(f+"gameover.wav")};

	enum SE
	{
		Select,
		Attack,
		Damage,
		Clear,
		Gameover
	}

	public static void PlaySE(SE se)
	{
		int n = 0;
		switch(se)
		{
			case Select:
				n = 0;
				break;
			case Attack:
				n = 1;
				break;
			case Damage:
				n = 2;
				break;
			case Clear:
				n = 3;
				break;
			case Gameover:
				n = 4;
				break;
		}
		Instance.sound[n].Reset();
		Instance.sound[n].Play();
	}
}
