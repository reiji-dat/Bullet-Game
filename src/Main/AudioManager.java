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

public class AudioManager {
	Clip clip;
	float volume = 1.0f;

	AudioManager(String name)
	{
		URL url = getClass().getResource(name);
		clip = createClip(url);
		setVolume(1);
	}
	AudioManager(String name, float vol)
	{
		URL url = getClass().getResource(name);
		clip = createClip(url);
		System.out.println(vol);
		setVolume(vol);
	}

	Clip createClip(URL name) {
		//引数を使用しオーディオインプットストリーム(AIS)を取得。
		//try - catch 文は例外時の処理を行う処理である。
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(name)){
			System.out.println("AIS\t\t\t"+ais);
			//ファイルの形式取得。
			AudioFormat format = ais.getFormat();
			System.out.println("Format\t\t"+format);
			//フォーマット情報をデータラインに設定
			DataLine.Info data = new DataLine.Info(Clip.class,format);
			System.out.println("DataLine\t"+data);
			//データラインをクリップに代入。
			Clip c = (Clip)AudioSystem.getLine(data);
			System.out.println("Clip\t\t"+c);
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

	//これらはいらないかもしれないが分かりやすいため設定した
	void Play()
	{
		clip.start();
	}

	void PlayLoop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	void PlayLoop(int loop)
	{
		clip.loop(loop);
	}

	void Stop()
	{
		clip.stop();
	}

	void Reset()
	{
		clip.stop();
		clip.flush();
		clip.setFramePosition(0);
	}

	void setVolume(float vol)
	{
		volume = vol;//この変数は基本いらないが現在の音量を画面上に表示するときに役に立つ？
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		//普通に数字を設定するだけだと上手く動作しなかったため
		//https://nompor.com/2017/12/14/post-128/ こちらを参考にした
		ctrl.setValue((float)Math.log10((float)volume)*20);
	}
}
