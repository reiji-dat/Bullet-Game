package Main;

public class SceneManager {

	public static enum Scene
	{
		None,
		Title,
		Game,
		Clear,
		GameOver
	}
	
	public static Scene currentScene = Scene.Title;
	//シーンを変えるときこれを変える。
	public static Scene nextScene = Scene.None;
}
