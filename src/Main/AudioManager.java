package Main;

import java.io.IOException;
import java.net.MalformedURLException;
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
	
	//TODO 後で解析
	Clip createClip(URL name) {
		//指定されたURLのオーディオ入力ストリームを取得
		
		
		
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(name)){
			
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			c.open(ais);
			
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
		ctrl.setValue((float)Math.log10((float)volume)*20);
	}
	
}
