package assignment.sidm.com.assignment01_ver2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import assignment.sidm.com.assignment01_ver2.R;

public class Mainmenu extends Activity implements OnClickListener {

    //define dem btns
    private Button btn_start;
    private Button btn_highscore;
    private Button btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //hide top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this); //set listener to start btn

        btn_highscore = (Button)findViewById(R.id.btn_highscore);
        btn_highscore.setOnClickListener(this); //set listener to something btn

        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this); //set listener to something btn
    }

    @Override
    //invoke callback event in the view
    public void onClick(View v)
    {
        //intent - action to be perform
        Intent intent = new Intent();

        if(v == btn_start)
        {   //intent is to set to another class which is another page/screen to go to
            intent.setClass(this,GamePage.class);
        }
        else if(v == btn_highscore)
        {
            intent.setClass(this,HighscorePage.class);
        }
        else if(v == btn_setting)
        {
            intent.setClass(this,SettingPage.class);
        }

        startActivity(intent);
    }

}

