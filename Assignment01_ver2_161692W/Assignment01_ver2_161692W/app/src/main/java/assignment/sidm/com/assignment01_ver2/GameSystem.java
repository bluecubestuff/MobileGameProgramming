//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
// Used to store anything related to system of the game etc(SharePref,adding of states)
//===================================================================================

package assignment.sidm.com.assignment01_ver2;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceView;

public class GameSystem
{
    public final static GameSystem Instance = new GameSystem();
    public final static String SHARED_PREF_ID = "GameSaveFile";
    public String userKey;
    public String StateName;
    public int scoreToShare;
    // Game stuff

    private boolean isPaused = false;

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
        scoreToShare = 0;
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        sharedPref = GamePage.instance.getSharedPreferences(SHARED_PREF_ID,0);

        if(GetIntFromSave("ID") > 10)
        {
            //reset it back to 0 highscore can only display 10
            SaveEditBegin();
            SetIntFromSave("ID", 0);
            SaveEditEnd();
        }
        SaveEditBegin();
        SetIntFromSave("ID",GetIntFromSave("ID") + 1);
        SaveEditEnd();
        userKey = "Score" + GetIntFromSave("ID");
        Log.d("userKey", userKey);

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new MainGameState());
        StateManager.Instance.AddState(new IntroState());
        StateManager.Instance.AddState(new HighScoreState());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public void SetIntFromSave(String _key,int _value)
    {
        if(editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        if(sharedPref != null)
         return sharedPref.getInt(_key,0);
        else
            return 0;
    }

    public void SaveEditBegin()
    {
        if(editor != null)
            return;

        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        if(editor == null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetStateName(String _stateName)
    {
        StateName = _stateName;
    }

    public String GetStateName()
    {
        return StateName;
    }
}