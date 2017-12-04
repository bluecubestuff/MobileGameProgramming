package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    }

    @Override
    public void OnHit(Collidable _other) {
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
        scale = new Vector3(1,1,1);
    }

    @Override
    public void Update(float _dt) {



    }

    @Override
    public void Render(Canvas _canvas) {

    }
}
