package assignment.sidm.com.assignment01_ver2;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import assignment.sidm.com.assignment01_ver2.R;

public class SplashPage extends Activity {

    boolean _active = true;
    int _splashTime = 5000; //time for splash screen to be displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //hide top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashpage);

        //thread for displaying the Splash Screen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(200);
                        if(_active) {
                            waited += 200;
                        }
                    }
                } catch(InterruptedException e) {
                    //do nothing
                } finally {
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(SplashPage.this, Mainmenu.class);
                    startActivity(intent);
                }
            }
        };
        splashTread.start();

        //init all the audio stuff
        AudioPlayer.Instance.AddAudio("music", R.raw.music);
        AudioPlayer.Instance.AddAudio("press", R.raw.press);
        AudioPlayer.Instance.AddAudio("throw", R.raw.throw);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            _active = false;
        }

        return true;
    }
}
