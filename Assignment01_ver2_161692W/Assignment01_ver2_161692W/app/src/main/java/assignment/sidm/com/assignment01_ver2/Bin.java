package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.SurfaceView;

public class Bin implements EntityBase, Collidable
{
    private String binType = "bin";
    private Vector3 pos = new Vector3(1,1,1);
    private Vector3 scale;
    private boolean isDone;
    private Bitmap bmp = null;

    private TYPE type;
    enum TYPE {
        NONE,
        PAPER,
        PLASTIC,
        GLASS,
        METAL
    }

    Bin(Vector3 _pos, TYPE _type)
    {
        pos = _pos;
        type = _type;
        binType = "paper_bin";
        EntityManager.Instance.AddEntity(this);
    }

    @Override
    public String GetType() {
        return binType;
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
    public float GetRadius() {
        return bmp.getHeight() * 0.5f;
        //return scale.x * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
        Log.d("BIN_HIT","HIT");
        switch(_other.GetType())
        {
            case "paper_ball":
               // if(type == type.PAPER)
                    //add score
              //  else
                    //minus score
                break;
            case "plastic_ball":
                break;
            case "glass_ball":
                break;
            case "metal_ball":
                break;
        }
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
    public void Init(SurfaceView _view) {
        isDone = false;
        scale = new Vector3(100,100,1);

        switch(type)
        {
            case PAPER:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin_placeholder);
                break;
            case PLASTIC:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin_placeholder);
                break;
            case GLASS:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin_placeholder);
                break;
            case METAL:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin_placeholder);
                break;
        }


    }

    @Override
    public void Update(float _dt) {



    }

    @Override
    public void Render(Canvas _canvas) {
        //convert from 3D space into 2D
        float xPos, yPos;
        Vector3 screenPos = CameraSpace(_canvas);
        xPos = screenPos.x;
        yPos = screenPos.y;
        Matrix mtx = new Matrix();
        mtx.setScale(scale.x, scale.y);
        mtx.postTranslate((float)(xPos - bmp.getWidth() * (scale.x/2)), (float)(yPos - bmp.getHeight() * (scale.y/2)));

        //Log.d("PosX:",Float.toString(pos.x));

        //mtx.postTranslate((float)(scale.x * bmp.getWidth() * 0.5), (float)(scale.y * bmp.getHeight() * 0.5));
        _canvas.drawBitmap(bmp, mtx, null);
    }

    public Vector3 CameraSpace(Canvas _canvas){
        float yRatio = _canvas.getHeight() / SampleGame.Instance.getWorldY();
        float xRatio = _canvas.getWidth() / SampleGame.Instance.getWorldX();
        Vector3 camPos = new Vector3(pos.x * xRatio, pos.y * yRatio, 0);
        return camPos;
    }
}
