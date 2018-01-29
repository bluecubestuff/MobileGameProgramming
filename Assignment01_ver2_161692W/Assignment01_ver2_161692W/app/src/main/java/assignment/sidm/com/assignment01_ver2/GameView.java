package assignment.sidm.com.assignment01_ver2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import javax.microedition.khronos.opengles.GL;

public class GameView extends SurfaceView
{
    private SurfaceHolder holder = null;
    private UpdateThread updateThread = new UpdateThread(this);

    public GameView(Context _context)
    {
        super(_context);
        holder = getHolder();

        if(holder != null)
        {
            holder.addCallback(new SurfaceHolder.Callback()
            {
                @Override
                public void surfaceCreated(SurfaceHolder holder)
                {
                    //setup
                    if(!updateThread.IsRunning())
                        updateThread.Initialise();

                    if(!updateThread.isAlive())
                        updateThread.start();
                        //updateThread.run();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder)
                {
                    updateThread.Terminate();
                }

            });
        }
    }
}

