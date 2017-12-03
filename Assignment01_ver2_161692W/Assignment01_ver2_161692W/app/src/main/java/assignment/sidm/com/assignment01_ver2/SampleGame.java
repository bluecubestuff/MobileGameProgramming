package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class SampleGame
{
    public final static SampleGame Instance = new SampleGame();
    //private  EntityBase currEntity = null;
    private boolean isPressed = false;

    private float timer = 0.0f;

    private Ball currBall = null;
    private Vector3 force = new Vector3(1,1,1);
    private float worldX, worldY;

    private SampleGame()
    {

    }

    public void Init(SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackground.Create();

        worldX = 100.f;
        worldY = worldX * (_view.getHeight()/_view.getWidth());

        currBall = Ball.Create();
    }

    public void Update(float _deltaTime)
    {

        timer += _deltaTime;
        //TODO: delete the ball when it shld be gone
        //when user throw
        /*
        if (true){
            Vector3 force = new Vector3(0, -10 , 5);
            currBall.Throw(force);
            currBall.unFreeze();
        }
        if (!currBall.getFreeze()){
            timer += _deltaTime;
        }

        if (timer >= 2.f){
            currBall = Ball.Create();
            timer = 0;
        }
        */

        //here to throw
        if(TouchManager.Instance.isDown() && !isPressed)
        {   //check if mouse down spawn a ball
            currBall =  Ball.Create();
            isPressed = true;
        }

        if(TouchManager.Instance.HasTouch())
        {   //if holding down on screen
            if(currBall != null) {
                //set the force base on dist  from finger to ball
                force =  new Vector3(TouchManager.Instance.GetPosX() - currBall.GetPosX(),
                        TouchManager.Instance.GetPosY() - currBall.GetPosY(), 1);
            }
        }
        else
        {   //not holding down on screen
            if(currBall != null)
            {   //throw ball & unfreeze
                isPressed = false;
                currBall.Throw(force);
                currBall.unFreeze();
            }
        }
        //end of da throw

        EntityManager.Instance.Update(_deltaTime);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }
    public float getWorldX(){return worldX;}
    public float getWorldY(){return worldY;}
}

