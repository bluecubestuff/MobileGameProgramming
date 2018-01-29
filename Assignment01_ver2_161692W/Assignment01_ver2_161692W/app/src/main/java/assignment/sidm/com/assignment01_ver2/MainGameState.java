//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//
//===================================================================================

package assignment.sidm.com.assignment01_ver2;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class MainGameState implements StateBase
{
    private float timer = 0.0f;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        //AudioManager.Instance.PlayAudio(R.raw.bgm);
        SampleGame.Instance.Init(_view);
        //SampleBackground.Create();
        //SamplePauseButton.Create(100);
    }

    @Override
    public void OnExit() {
        SampleGame.Instance.Exit();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        //EntityManager.Instance.Render(_canvas);
        SampleGame.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        SampleGame.Instance.Update(_dt);
    }
}


