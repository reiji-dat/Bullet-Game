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
	
	public static Scene currentScene = Scene.None;
	//シーンを変えるときこれを変える。
	public static Scene nextScene = Scene.Title;
}
