//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
// Sample entity not in use
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class SampleEntity implements EntityBase, Collidable
{

    private Bitmap bmp = null;
    private boolean isDone = false;
    private boolean isMove = false;
    private boolean isInit = false;

    private float xPos, yPos, xDir, yDir, lifeTime;
    private Vector3 pos,dir;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    public boolean IsMove() {return isMove;}

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    public void SetIsMove(boolean _isMove) {isMove = _isMove;}

    @Override
    public void Init(SurfaceView _view) {

        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
         lifeTime = 100.0f;
        //Random randGen = new Random();

        //xPos = randGen.nextFloat() * _view.getWidth();
        //yPos = randGen.nextFloat() * _view.getHeight();

        //xDir = randGen.nextFloat() * 100.0f - 50.f;
        //yDir = randGen.nextFloat() * 100.0f - 50.f;

        pos = new Vector3(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(), 0);
        dir = new Vector3(1.f,1.f,1.f);
        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        lifeTime -= _dt;

       if(lifeTime <= 0.f)
           SetIsDone(true);

        if(isMove)
        {
            pos.x +=  dir.x * _dt;
            pos.y += dir.y * _dt;
        }


       // xPos += xDir * _dt;
       // yPos += yDir * _dt;

    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, pos.x - bmp.getWidth() * 0.5f, pos.y - bmp.getHeight() * 0.5f, null);
    }

    //so anyone can create sampleEntity
    public static SampleEntity Create()
    {
        SampleEntity result = new SampleEntity();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    public Vector3 GetPos() { return pos;}
    public void SetPos(final Vector3 _pos) { pos =_pos;}

    public Vector3 GetDir() { return dir;}
    public void SetDir(final Vector3 _dir) { dir =_dir;}

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
    public float GetPosZ() {
        return 0;
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
