package Main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 音関係の処理クラス
 */
public class AudioManager {

	public Clip clip;				//音声が入る場所
	public float volume = 1.0f;	//音量

	/**
	 * 音関係の処理クラス
	 * @param name 音声ファイルの相対パス
	 */
	AudioManager(String name)
	{
		URL url = getClass().getResource(name);
		clip = createClip(url);
		setVolume(1);
	}
	/**
	 * 音関係の処理クラス
	 * @param name 音声ファイルの相対パス
	 * @param vol 音量
	 */
	AudioManager(String name, float vol)
	{
		URL url = getClass().getResource(name);
		clip = createClip(url);
		setVolume(vol);
	}

	/**
	 * 相対パスからクリップを生成
	 * @param name 相対パス
	 * @return クリップ
	 */
	Clip createClip(URL name) {
		//引数を使用しオーディオインプットストリーム(AIS)を取得。
		//try - catch 文は例外時の処理を行う処理である。
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(name)){
			//ファイルの形式取得。
			AudioFormat format = ais.getFormat();
			//フォーマット情報をデータラインに設定
			DataLine.Info data = new DataLine.Info(Clip.class,format);
			//データラインをクリップに代入。
			Clip c = (Clip)AudioSystem.getLine(data);
			//AISをクリップに開く
			c.open(ais);
			return c;
		} catch (UnsupportedAudioFileException e) {//ファイルタイプが異なる、対応していない場合
			System.out.println("未対応の形式の可能性があります。");
			e.printStackTrace();
		} catch (IOException e) {//入出力の失敗
			System.out.println("入力または出力が失敗した可能性があります。");
			e.printStackTrace();
		} catch (LineUnavailableException e) {//ラインが使用不可
			System.out.println("ラインが使用不可の可能性があります。");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 再生
	 */
	public void Play()
	{
		clip.start();
	}

	/**
	 * ループ再生
	 */
	public void PlayLoop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * 回数指定ループ再生
	 * @param loop
	 */
	public void PlayLoop(int loop)
	{
		clip.loop(loop);
	}

	/**
	 * 一時停止
	 */
	public void Stop()
	{
		clip.stop();
	}

	/**
	 * リセット
	 */
	public void Reset()
	{
		clip.stop();
		clip.flush();
		clip.setFramePosition(0);
	}

	/**
	 * ボリューム設定
	 * @param vol 音量
	 */
	public void setVolume(float vol)
	{
		volume = vol;//この変数は基本いらないが現在の音量を画面上に表示するときに役に立つ？
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		//普通に数字を設定するだけだと上手く動作しなかったため
		//https://nompor.com/2017/12/14/post-128/ こちらを参考にした
		ctrl.setValue((float)Math.log10((float)volume)*20);
	}
}
