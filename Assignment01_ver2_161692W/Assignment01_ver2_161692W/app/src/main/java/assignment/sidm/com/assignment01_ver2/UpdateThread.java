package assignment.sidm.com.assignment01_ver2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.Settings;
import android.view.SurfaceHolder;

//game loop here
public class UpdateThread extends Thread
{
    static final long targetFPS = 60;
    private GameView view = null;
    private SurfaceHolder holder = null;
    private boolean isRunning = false;

    public UpdateThread(GameView _view)
    {
        view = _view;
        holder = view.getHolder();
    }

    public boolean IsRunning()
    {
       return isRunning;
    }

    public void Initialise()
    {
        isRunning = true;
        AudioManager.Instance.Init(view);
        SampleGame.Instance.Init(view);
    }

    public void Terminate()
    {
        isRunning = false;
    }

    @Override
    public void run()
    {
        long framePerSecond = 1000 / targetFPS; // 1000 in milliseconds
        long startTime = 0;

        //need another variable to calculate deltaTime
        long prevTime = System.nanoTime();

        while(isRunning)
        {
            //Update
            startTime = System.currentTimeMillis();
            long currTime = System.nanoTime();
            float deltaTime = (float)((currTime - prevTime) / 1000000000.0f);
            prevTime = currTime;


            SampleGame.Instance.Update(deltaTime);

            //Render
            Canvas canvas = holder.lockCanvas(null);
            if(canvas != null)
            {
                //synchronized is something like critical section
                synchronized (holder)
                {   //only one thread will draw
                    canvas.drawColor(Color.RED);

                    //render other garbage here
                    SampleGame.Instance.Render(canvas);
                }

                holder.unlockCanvasAndPost(canvas);
            }

            //Post Update/Render
            try
            {
                long sleepTime = framePerSecond - (System.currentTimeMillis() - startTime);
                if(sleepTime > 0)
                    sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                Terminate();
            }

            //End of looop
        }
    }
}

