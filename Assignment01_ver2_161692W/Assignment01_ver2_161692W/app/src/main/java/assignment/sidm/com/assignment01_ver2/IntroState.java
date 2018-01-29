//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.SurfaceView;

public class IntroState implements StateBase
{
    private float timer = 5.0f;
    private Bitmap logo = null;
    private Bitmap intro = null;
    private static float rotate = 0;
    private int screenX, screenY;

    @Override
    public String GetName() {
        return "Default";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        logo = ResourceManager.Instance.GetBitmap(R.mipmap.ic_launcher);
        intro = ResourceManager.Instance.GetBitmap(R.drawable.intruct);
        screenX = _view.getWidth();
        screenY = _view.getHeight();
    }

    @Override
    public void OnExit()
    {
        Log.d("IsExit","exit");
    }

    @Override
    public void Render(Canvas _canvas)
    {
        //EntityManager.Instance.Render(_canvas);
        // Render the log
        Matrix transform = new Matrix();
        transform.postScale(1.f/intro.getWidth(), 1.f/intro.getHeight());
        transform.postScale(screenX, screenY);
        _canvas.drawBitmap(intro, transform, null);

        transform = new Matrix();
        transform.postTranslate(-logo.getWidth() / 2, -logo.getHeight() / 2);
        transform.postRotate(rotate);
        transform.postTranslate(screenX/2, screenY/2);
        _canvas.drawBitmap(logo, transform, null);
    }

    @Override
    public void Update(float _dt)
    {
        rotate += 180 * _dt;
        timer -= _dt;
        if (timer <= 0.0f)
        {
            // We are done showing our splash screen
            // Move on time!
            StateManager.Instance.ChangeState("MainGame");
        }
    }
}

