package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class SampleGame
{
    public final static SampleGame Instance = new SampleGame();

    private float timer = 0.0f;
    private Ball currBall;

    private SampleGame()
    {

    }

    public void Init(SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackground.Create();

        currBall = Ball.Create();
    }

    public void Update(float _deltaTime)
    {
        //TODO: delete the ball when it shld be gone
        //when user throw
        if (true){
            Vector3 force = new Vector3(0, -100, 0);
            currBall.Throw(force);
            currBall.unFreeze();
        }
        if (!currBall.getFreeze()){
            timer += _deltaTime;
        }

        if (timer >= 2.f){
            currBall = Ball.Create();
            timer = 0;
        }

        EntityManager.Instance.Update(_deltaTime);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }
}

