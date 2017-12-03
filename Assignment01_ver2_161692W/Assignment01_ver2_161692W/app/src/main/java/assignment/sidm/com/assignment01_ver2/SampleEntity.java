package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class SampleEntity implements EntityBase, Collidable
{

    private Bitmap bmp = null;
    private boolean isDone = false;

    private float xPos, yPos, xDir, yDir, lifeTime;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin);
         lifeTime = 5.0f;
        Random randGen = new Random();

        xPos = randGen.nextFloat() * _view.getWidth();
        yPos = randGen.nextFloat() * _view.getHeight();

        xDir = randGen.nextFloat() * 100.0f - 50.f;
        yDir = randGen.nextFloat() * 100.0f - 50.f;
    }

    @Override
    public void Update(float _dt) {
        lifeTime -= _dt;

        if(lifeTime <= 0.f)
           SetIsDone(true);

        xPos += xDir * _dt;
        yPos += yDir * _dt;

        if(TouchManager.Instance.isDown())
        {
            //collision stuff here
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
    }

    //so anyone can create sampleEntity
    public static SampleEntity Create()
    {
        SampleEntity result = new SampleEntity();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    @Override
    public String GetType() {
        return "SampleEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {

        if(_other.GetType() == "SampleEntity")
            SetIsDone(true);

    }
}
