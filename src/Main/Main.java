package Main;

//更新
/**
 * var1.0.0
 * 完成
 */
/**
 * var1.1.0
 * コーディングのスマート化
 * オブジェクト指向が出来るように変更
 * SEの追加
 * 自動連射の追加
 * ゲームの難易度調整
 */

//ここに起動系の処理がある。
/**
 * 起動系の処理
 */
public class Main{

	/**
	 * デバッグモード
	 */
	public static final boolean DebugMode = true;

	//デフォルトの画面サイズ
	/**
	 * デフォルトの横の長さ
	 */
	public static final int DefaultWidth = 800;
	/**
	 * デフォルトの縦の長さ
	 */
	public static final int DefaultHeight = 500;

	//画面サイズ
	/**
	 * 現在の横の長さ
	 */
	public static final int Width = 1280;
	/**
	 * 現在の縦の長さ
	 */
	public static final int Height = 720;

	//デフォルトと現在の倍率(倍率をかけると絵のサイズも変わる)
	/**
	 * 横のデフォルトと現在の倍率
	 */
	public static final float MagniWidth = (float)Width / (float)DefaultWidth;
	/**
	 * 縦のデフォルトと現在の倍率
	 */
	public static final float MagniHeight = (float)Height / (float)DefaultHeight;

	//メインここから処理が始まる。
	public static void main(String[] args) throws Exception{
		//ウィンドウ設定
		SceneManager.scene = new GameWindow("ボス戦シューティング",Width,Height);

		SceneManager.scene.ChangeScene(new TitleScene());	//シーンをタイトルシーンにする
		SceneManager.scene.setVisible(true);				//表示する
		SceneManager.scene.StartGameLoop();					//ゲームループを設定
	}
}