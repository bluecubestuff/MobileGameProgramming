package assignment.sidm.com.assignment01_ver2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class SampleGame
{
    public final static SampleGame Instance = new SampleGame();
    //private  EntityBase currEntity = null;
    private boolean isPressed = false;

    private float timer = 0.0f;
    private Bitmap bmp = null;

    private Ball currBall = null;
    private Vector3 force = new Vector3(1,1,1);
    public static float worldX, worldY, screenX, screenY;
    private Vector3 press = new Vector3(0,0,0);
    private int score = 0;
    private Paint paint;

    private int binCount = 0;
    private float binSpawnTimer = 0;
    private float binSpawnRate = 2;

    private UI_Element pause;
   // private boolean isPause = false;

    private SampleGame()
    {

    }

    public void SetScore(int _score)
    {
        score = _score;
    }

    public int GetScore()
    {
        return score;
    }

    public void Init(SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackground.Create();

        worldX = 100.f;
        worldY = worldX * (_view.getHeight()/_view.getWidth());
        screenX = _view.getWidth();
        screenY = _view.getHeight();

        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(55);

        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.game_bg);

        pause = new UI_Element(new Vector3(4 * getWorldX() / 4.5f,getWorldY() / 11, 1), UI_Element.TYPE.PAUSE);
        //UI_Element.Create();
    }

    public void Update(float _deltaTime)
    {
        if(pause.getIsPause() == true)
        {
            if(TouchManager.Instance.isDown())
                pause.setIsPause(false);
        }

        if(pause.getIsPause() == false) {
            timer += _deltaTime;
            //TODO: delete the ball when it shld be gone

            //here to throw
            if (TouchManager.Instance.isDown() && !isPressed) {   //check if mouse down spawn a ball
                currBall = Ball.Create();
                press.x = TouchManager.Instance.GetPosX();
                press.y = TouchManager.Instance.GetPosY();
                isPressed = true;
            }

            if (TouchManager.Instance.HasTouch()) {   //if holding down on screen
                if (currBall != null) {
                    //set the force base on dist  from finger to ball
                    force = new Vector3(TouchManager.Instance.GetPosX() - press.x,
                            TouchManager.Instance.GetPosY() - press.y, 1);
                    force.x /= (screenX / worldX);
                    force.y /= (screenY / worldY);
                    force.z = 5;
                }
            } else {   //not holding down on screen
                if (currBall != null) {   //throw ball & unfreeze
                    isPressed = false;
                    currBall.Throw(force);
                    currBall.unFreeze();
                    currBall = null;
                }
            }
            //end of da throw

            EntityManager.Instance.Update(_deltaTime);
            BinController(_deltaTime);
        }
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

        String str_score = String.valueOf(score);
        _canvas.drawText(str_score,_canvas.getWidth() * 0.5f,_canvas.getHeight(),paint);

        if(pause.getIsPause() == true)
        {
            _canvas.drawText("PAUSE",_canvas.getWidth() * 0.5f,_canvas.getHeight() * 0.5f,paint);
        }

        //render the background

    }
    public float getWorldX(){return worldX;}
    public float getWorldY(){return worldY;}

    public void BinController(double dt)
    {
        binSpawnTimer += dt;
        if (binSpawnTimer > binSpawnRate)
        {
            binSpawnTimer = 0;
            Random r = new Random();
            int type = r.nextInt(4 + 1);
            Bin b;
            switch (type)
            {
                case 1:
                    b = new Bin(new Vector3(0, r.nextInt((int)(worldY/5*3 - 5) + 1) + 5, 1), Bin.TYPE.PAPER);
                    break;
                case 2:
                    b = new Bin(new Vector3(0, r.nextInt((int)(worldY/5*3 - 5) + 1) + 5, 1), Bin.TYPE.GLASS);
                    break;
                case 3:
                    b = new Bin(new Vector3(0, r.nextInt((int)(worldY/5*3 - 5) + 1) + 5, 1), Bin.TYPE.METAL);
                    break;
                case 4:
                    b = new Bin(new Vector3(0, r.nextInt((int)(worldY/5*3) - 5 + 1) + 5, 1), Bin.TYPE.PLASTIC);
                    break;
            }
            Log.d("spawnned", "spawno");
        }
    }

   // public void SetIsPause(boolean _isPause)
   // {
     //   isPause = _isPause;
    //}

   // public boolean IsPause()
   // {
   //     return isPause;
   // }
}

