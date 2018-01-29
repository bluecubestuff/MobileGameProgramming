//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
// Manages the touch input for game & what kind of touch is being used
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.util.Log;
import android.view.MotionEvent;

public class TouchManager {
    public final static TouchManager Instance = new TouchManager();


    public enum TouchState
    {
        NONE,
        DOWN,
        MOVE,
        UP
    }

    private TouchState status = TouchState.NONE;
    private int posX,posY;

    private TouchManager() {
        posX = 0;
        posY = 0;
    }

    public boolean HasTouch() {
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    //check for clicks
    public boolean isDown() {
        return status == TouchState.DOWN;
    }


    public int GetPosX()
    {
        return posX;
    }

    public int GetPosY()
    {
        return posY;
    }

    public void Update(int _posX,int _posY, int motionEventState)
    {
        posX = _posX;
        posY = _posY;
       switch(motionEventState)
       {
           case MotionEvent.ACTION_DOWN:
               status = TouchState.DOWN;
               Log.d("DOWN","MotionEvent.ACTION_DOWN");
               break;
           case MotionEvent.ACTION_MOVE:
               status = TouchState.MOVE;
               Log.d("MOVE","MotionEvent.ACTION_MOVE");
               break;
           case MotionEvent.ACTION_UP:
               status = TouchState.UP;
               Log.d("UP","MotionEvent.ACTION_UP");
               break;
       }
    }
}