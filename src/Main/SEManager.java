package Main;

//SEを再生するクラス
public class SEManager
{
	public static SEManager Instance = new SEManager();
	final String pt = "audio/";
	private AudioManager sound[] = 
			new AudioManager[]{new AudioManager(pt+"select.wav"),new AudioManager(pt+"attack.wav"),new AudioManager(pt+"damage.wav"),
								new AudioManager(pt+"clear.wav"),new AudioManager(pt+"gameover.wav")};
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
