package Main;

public class SEManager
{
	public static SEManager Instance = new SEManager();
	private AudioManager sound[] = new AudioManager[]{new AudioManager("select.wav"),new AudioManager("attack.wav"),new AudioManager("damage.wav")};
	enum SE
	{
		Select,
		Attack,
		Damage
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
		}
		Instance.sound[n].Reset();
		Instance.sound[n].Play();
	}
}
