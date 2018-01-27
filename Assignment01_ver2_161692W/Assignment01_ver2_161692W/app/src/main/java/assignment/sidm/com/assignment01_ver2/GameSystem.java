package assignment.sidm.com.assignment01_ver2;
import android.view.SurfaceView;

public class GameSystem
{
    public final static GameSystem Instance = new GameSystem();

    // Game stuff

    private boolean isPaused = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new MainGameState());
        StateManager.Instance.AddState(new IntroState());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }
}