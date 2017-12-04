package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

import static java.lang.Math.sqrt;

/**
 * Created by Buttception on 3/12/2017.
 */

public class Ball implements EntityBase, Collidable{
    private Vector3 pos;
    private Vector3 vel;
    private Vector3 scale;
    private boolean isDone;
    private boolean freeze;
    private TYPE type;
    private Bitmap bmp = null;
    private float size;

    private String ballType;

    @Override
    public String GetType() {
        return ballType;
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
        return (size * 0.5f);
    }

    @Override
    public void OnHit(Collidable _other)
    {
        if(pos.z < 10.f ||
                pos.z > 15.f)
            return;

        if(_other.GetType() == "paper_bin")
            SetIsDone(true);

       // Log.d("BALL_HIT","HIT");
    }

    public void Throw(Vector3 force){
        //force is a 2D vector of the line the user drawn on the screen
        //Vector3 x = new Vector3(force.x, 0, 0);
        //Vector3 y = new Vector3(0, force.y, 0);
        //vel.Set(force.x, force.y, x.Cross(y).Length());
        vel.Set(force.x, force.y,force.z);
    }

    enum TYPE {
        NONE,
        PAPER,
        PLASTIC,
        GLASS,
        METAL
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
        freeze = true;
        pos = new Vector3(SampleGame.Instance.getWorldX() / 2,SampleGame.Instance.getWorldY() /4 * 3,1);
        vel = new Vector3(0,0,0);
        size = SampleGame.Instance.getWorldX() /2;
        scale = new Vector3(1, 1,1);
        size = 20;

        //randomly decides what kind of ball should it be
        Random randGen = new Random();
        float chance = randGen.nextFloat();
        if (chance <= .25){
            type = TYPE.PAPER;
            ballType = "paper_ball";
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
        }
        else if (chance <= .5){
            type = TYPE.PLASTIC;
            ballType = "plastic_ball";
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.plastic_trash_placeholder);
        }
        else if (chance <= .75){
            type = TYPE.GLASS;
            ballType = "glass_ball";
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.glass_trash_placeholder);
        }
        else{
            type = TYPE.METAL;
            ballType = "metal_ball";
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.metal_trash_placeholder);
        }
    }

    @Override
    public void Update(float _dt) {
        //update the ball
        if (!freeze) {
            Vector3 gravity = new Vector3(0, -30, 0);
            pos = pos.Add(vel.multiply_scalar(_dt));
            vel = vel.Subtract(gravity.multiply_scalar(_dt));
            scale.x = scale.y = 1.f / pos.z;

            Log.d("PosZ:",Float.toString(pos.z));

            if (shouldDespawn()){
                SetIsDone(true);
                Log.d("Ball", "Despawned");
            }
        }
    }

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

        //mtx.postTranslate((float)(xPos - bmp.getWidth() * (scale.x/2)), (float)(yPos - bmp.getHeight() * (scale.y/2)));
        //mtx.postTranslate((float)(scale.x * bmp.getWidth() * 0.5), (float)(scale.y * bmp.getHeight() * 0.5));
        _canvas.drawBitmap(bmp, mtx, null);
    }

    public static Ball Create(){
        Ball result = new Ball();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    public void unFreeze(){
        freeze = false;
    }

    public boolean getFreeze(){
        return freeze;
    }

    public float getSize() {return (size/2);}

    public boolean shouldDespawn(){
        if (pos.x > SampleGame.Instance.getWorldX() || pos.x < 0 || pos.y > SampleGame.Instance.getWorldY()){
            return true;
        }
        return false;
    }

    public Vector3 CameraSpace(Canvas _canvas){
        float yRatio = _canvas.getHeight() / SampleGame.Instance.getWorldY();
        float xRatio = _canvas.getWidth() / SampleGame.Instance.getWorldX();
        Vector3 camPos = new Vector3(pos.x * xRatio, pos.y * yRatio, 0);
        return camPos;
    }
}
