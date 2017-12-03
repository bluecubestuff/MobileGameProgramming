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

    @Override
    public String GetType() {
        return null;
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
        scale = new Vector3(1, 1,1);

        //randomly decides what kind of ball should it be
        Random randGen = new Random();
        float chance = randGen.nextFloat();
        if (chance <= .25){
            type = TYPE.PAPER;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
        }
        else if (chance <= .5){
            type = TYPE.PLASTIC;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
        }
        else if (chance <= .75){
            type = TYPE.GLASS;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
        }
        else{
            type = TYPE.METAL;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.paper_trash);
        }
    }

    @Override
    public void Update(float _dt) {
        if (freeze){
            Log.d("freeze", "true");
        }
        else{
            Log.d("freeze", "false");
        }
        //update the ball
        if (!freeze) {
            Vector3 gravity = new Vector3(0, -50, 0);
            pos.x += vel.x * _dt;
            pos.y += vel.y * _dt;
            pos.z += vel.z * _dt;
            vel.y -= gravity.y * _dt;
            scale.x = scale.y = 1.f / (float)sqrt(pos.z);
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
        mtx.setScale(scale.x, scale.y);
        mtx.postTranslate((float)(xPos - bmp.getWidth() * (scale.x/2)), (float)(yPos - bmp.getHeight() * (scale.y/2)));

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

    public Vector3 CameraSpace(Canvas _canvas){
        float yRatio = _canvas.getHeight() / SampleGame.Instance.getWorldY();
        float xRatio = _canvas.getWidth() / SampleGame.Instance.getWorldX();
        Vector3 camPos = new Vector3(pos.x * xRatio, pos.y * yRatio, 0);
        return camPos;
    }
}
