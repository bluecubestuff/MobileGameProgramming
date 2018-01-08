package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.SurfaceView;

public class UI_Element implements EntityBase, Collidable
{
    private String uiType = "pause";
    private Vector3 pos = new Vector3(1,1,1);
    private Vector3 scale;
    private boolean isDone;
    private boolean isInit = false;
    private boolean isPause = false;
    private Bitmap bmp = null;

    private Vector3 sPos = new Vector3(1,1,1);

    float size = 16;

    private TYPE type;
    enum TYPE {
        NONE,
        PAUSE
    }

    UI_Element(Vector3 _pos, TYPE _type)
    {
        pos = _pos;
        type = _type;

        switch(_type)
        {
            case PAUSE:
                uiType = "pasue";
                break;
        }

        EntityManager.Instance.AddEntity(this);
    }

    @Override
    public String GetType() {
        return uiType;
    }

    @Override
    public float GetPosX() {
        return pos.x;
    }

    @Override
    public float GetPosY() {
        return pos.y;
    }

    @Override
    public float GetPosZ()  {
        return pos.z;
    }

    @Override
    public float GetRadius() {
        return (size * 0.5f);
        //return scale.x * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {

    }


    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public void Init(SurfaceView _view) {
        isDone = false;
        scale = new Vector3(1,1,1);
        isInit = true;
        switch(type)
        {
            case PAUSE:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.setting);
                break;
        }


    }

    @Override
    public void Update(float _dt) {
        scale.x = scale.y = 1.f / pos.z;

        if(TouchManager.Instance.isDown())
        {
            //if(Collision.SphereToSphere(pos.x,pos.y,GetRadius(),
             //       TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),1))
            if(TouchManager.Instance.GetPosX() > sPos.x - 100 && TouchManager.Instance.GetPosX() < sPos.x + 100
                    && TouchManager.Instance.GetPosY() > sPos.y - 100 && TouchManager.Instance.GetPosY() < sPos.y + 100)
            {   //hardcode for now
                Log.d("CLICK ON PAUSE","PAUSE");
                isPause = true;
            }
        }
    }

    public float getSize() {return (size / 2);}

    @Override
    public void Render(Canvas _canvas) {
        //convert from 3D space into 2D
        float xPos, yPos;
        Vector3 screenPos = CameraSpace(_canvas);
        xPos = screenPos.x;
        yPos = screenPos.y;
        Matrix mtx = new Matrix();
        mtx.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //scale the bmp to 1 unit in world space
        float oneUnit = _canvas.getWidth() / SampleGame.Instance.getWorldX();
        mtx.postScale(oneUnit / bmp.getWidth(), oneUnit/ bmp.getHeight());
        mtx.postScale(scale.x * size, scale.y * size);
        mtx.postTranslate(xPos, yPos);

        sPos.Set(xPos,yPos,1);
        //Log.d("PosX:",Float.toString(xPos));
        //Log.d("PosY:",Float.toString(yPos));
        //Log.d("PosY:",Float.toString(pos.y));

        //mtx.postTranslate((float)(scale.x * bmp.getWidth() * 0.5), (float)(scale.y * bmp.getHeight() * 0.5));
        _canvas.drawBitmap(bmp, mtx, null);
    }

    public Vector3 CameraSpace(Canvas _canvas){
        float yRatio = _canvas.getHeight() / SampleGame.Instance.getWorldY();
        float xRatio = _canvas.getWidth() / SampleGame.Instance.getWorldX();
        Vector3 camPos = new Vector3(pos.x * xRatio, pos.y * yRatio, 0);
        return camPos;
    }

    public void setIsPause(boolean _isPause)
    {
        isPause = _isPause;
    }

    public boolean getIsPause()
    {
        return isPause;
    }
}
