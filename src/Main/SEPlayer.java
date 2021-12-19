package Main;

/**
 * SEを再生するクラス
 */
public class SEPlayer
{
	public static SEPlayer Instance = new SEPlayer();
	//サウンドデータ
	private AudioManager[] sound = {new AudioManager("audio/select.wav"),new AudioManager("audio/attack.wav"),new AudioManager("audio/damage.wav"),
								new AudioManager("audio/clear.wav"),new AudioManager("audio/gameover.wav"),new AudioManager("audio/boss-damage.wav")
								,new AudioManager("audio/Weakness.wav")};

	/**
	 * SEの種類
	 */
	enum SE
	{
		Select,
		Attack,
		Damage,
		Clear,
		Gameover,
		BossDamage,
		Weakness
	}

	/**
	 * SEを再生
	 * @param se 再生したいSE
	 */
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
			case BossDamage:
				n = 5;
				break;
			case Weakness:
				n = 6;
				break;
		}
		Instance.sound[n].Reset();
		Instance.sound[n].Play();
	}
}
