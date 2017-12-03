package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Buttception on 3/12/2017.
 */

public class Ball implements EntityBase, Collidable{
    private Vector3 pos;
    private Vector3 vel;
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
        vel.Set(force.x, force.y,0);
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
        pos = new Vector3(_view.getWidth() / 2,_view.getHeight() /2,1);
        vel = new Vector3(0,0,0);

        //randomly decides what kind of ball should it be
        Random randGen = new Random();
        float chance = randGen.nextFloat();
        if (chance <= .25){
            type = TYPE.PAPER;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.start);
        }
        else if (chance <= .5){
            type = TYPE.PLASTIC;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.start);
        }
        else if (chance <= .75){
            type = TYPE.GLASS;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.start);
        }
        else{
            type = TYPE.METAL;
            bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.start);
        }
    }

    @Override
    public void Update(float _dt) {
        //update the ball
        Vector3 gravity = new Vector3(0, -100, 100);
        pos = pos.Add(vel.multiply_scalar(_dt));
        vel = vel.Subtract(gravity.multiply_scalar(_dt));
    }

    @Override
    public void Render(Canvas _canvas) {
        //convert from 3D space into 2D
        float xPos, yPos;
        xPos = pos.x / pos.z;
        yPos = pos.y / pos.z;
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
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
}
