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
        ResourceManager.Instance.Init(view);
        EntityManager.Instance.Init(view);
        StateManager.Instance.Init(view);
        GameSystem.Instance.Init(view);
        //SampleGame.Instance.Init(view);
    }

    public void Terminate()
    {
        isRunning = false;
    }

    @Override
    public void run()
    { // This is for frame rate control
        long framePerSecond = 1000 / targetFPS;
        long startTime = 0;

        // This is to calculate delta time (more precise)
        long prevTime = System.nanoTime();

       //StateManager.Instance.Start("HighScoreState");
       StateManager.Instance.Start(GameSystem.Instance.GetStateName());

        // This is the game loop
        while (isRunning && StateManager.Instance.GetCurrentState() != "INVALID")
        {
            startTime = System.currentTimeMillis();
            long currTime = System.nanoTime();
            float deltaTime = (float) ((currTime - prevTime) / 1000000000.0f);
            prevTime = currTime;

            // Update
            StateManager.Instance.Update(deltaTime);

            // Render
            Canvas canvas = holder.lockCanvas(null);
            if (canvas != null)
            {
                // Able to render
                synchronized (holder)
                {
                    // Fill the background color to reset
                    canvas.drawColor(Color.BLACK);

                    StateManager.Instance.Render(canvas);
                }
                holder.unlockCanvasAndPost(canvas);
            }

            // Frame rate controller
            try
            {
                long sleepTime = framePerSecond - (System.currentTimeMillis() - startTime);

                if (sleepTime > 0)
                    sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                isRunning = false;
            }

            // End of Loop
        }
    }

}

