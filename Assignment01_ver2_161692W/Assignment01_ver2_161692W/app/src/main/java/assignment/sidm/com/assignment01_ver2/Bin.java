//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//
//===================================================================================

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
    private boolean isInit = false;
    private Bitmap bmp = null;
    private float binSpeed = 20;

    float size = 24;

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

        switch(_type)
        {
            case PAPER:
                binType = "paper_bin";
                break;
            case PLASTIC:
                binType = "plastic_bin";
                break;
            case GLASS:
                binType = "glass_bin";
                break;
            case METAL:
                binType = "metal_bin";
                break;

        }

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

       if(_other.GetPosZ() < 7.f ||
               _other.GetPosZ() > 8.5f)
           return;

        Log.d("BIN_HIT","HIT");
       // int currScore = GameSystem.Instance.GetIntFromSave("Score");
        switch(_other.GetType())
        {
            case "paper_ball":
                if(type == TYPE.PAPER)
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() + 1);
                   // ++currScore;
                }
                else
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() - 1);
                   // --currScore;
                }
                break;
            case "plastic_ball":
                if(type == TYPE.PLASTIC)
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() + 1);
                   // ++currScore;
                }
                else
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() - 1);
                   // --currScore;
                }
                break;
            case "glass_ball":
                if(type == TYPE.GLASS)
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() + 1);
                    //++currScore;
                }
                else
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() - 1);
                   // --currScore;
                }
                break;
            case "metal_ball":
                if(type == TYPE.METAL)
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() + 1);
                    //++currScore;
                }
                else
                {
                    SampleGame.Instance.SetScore(SampleGame.Instance.GetScore() - 1);
                   // --currScore;
                }
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
            case PAPER:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_bin_final);
                break;
            case PLASTIC:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.plastic_bin_final);
                break;
            case GLASS:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.glass_bin_final);
                break;
            case METAL:
                bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.metal_bin_final);
                break;
        }


    }

    @Override
    public void Update(float _dt) {
        scale.x = scale.y = 1.f / pos.z;
        if (pos.x > SampleGame.worldX + 10)
            isDone = true;
        if (pos.x < -10)
            isDone = true;
        if (pos.y > SampleGame.worldY + 10)
            isDone = true;
        if (pos.y < -10)
            isDone = true;
        pos.x += binSpeed * _dt;
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

