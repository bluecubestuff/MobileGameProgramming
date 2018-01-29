//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//background for game. seperate class for background to settle render order
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

public class SampleBackground implements EntityBase
{
    private Bitmap bmp = null;
    private boolean isDone = false;

    private float xPos,yPos,offset;
    private SurfaceView view;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public boolean IsInit() {return bmp != null;}

    @Override
    public void Init(SurfaceView _view) {
        view = _view;
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.game_bg);
        offset = 0.0f;
    }

    @Override
    public void Update(float _dt) {
        offset += _dt * 0.1f;
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix ms = new Matrix();
        ms.postScale(1.f/bmp.getWidth(), 1.f/bmp.getHeight());
        ms.postScale(SampleGame.screenX, SampleGame.screenY);
        _canvas.drawBitmap(bmp, ms, null);

        //_canvas.drawBitmap(bmp, 0,0,null);
    }

    public static SampleBackground Create()
    {
        SampleBackground result = new SampleBackground();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}
