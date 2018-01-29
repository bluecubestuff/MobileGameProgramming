//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
// Shows the main menu and determines which activity to start next
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import assignment.sidm.com.assignment01_ver2.R;

public class Mainmenu extends Activity implements OnClickListener {
    public final static Mainmenu instance = new Mainmenu();
    //define dem btns
    private Button btn_start;
    private Button btn_highscore;
    private Button btn_setting;
    private Button btn_facebook;

    private MediaPlayer mp;

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

        btn_facebook = (Button)findViewById(R.id.btn_facebook);
        btn_facebook.setOnClickListener(this); //set listener to something btn

        //AudioPlayer.Instance.PlayAudio(this, "music");
        mp = MediaPlayer.create(this, R.raw.music);
        mp.start();
    }

    @Override
    //invoke callback event in the view
    public void onClick(View v)
    {
        //intent - action to be perform
        Intent intent = new Intent();

        if(v == btn_start)
        {   //intent is to set to another class which is another page/screen to go to
            GameSystem.Instance.SetStateName("Default");
            intent.setClass(this,GamePage.class);
            //AudioPlayer.Instance.PlayAudio(this, "press");
        }
        else if(v == btn_highscore)
        {
            GameSystem.Instance.SetStateName("HighScoreState");
            intent.setClass(this,GamePage.class);
            //intent.setClass(this,ScorePage.class);
            //AudioPlayer.Instance.PlayAudio(this, "press");
        }
        else if(v == btn_setting)
        {
            intent.setClass(this,SettingPage.class);
            //AudioPlayer.Instance.PlayAudio(this, "press");
        }
        else if(v == btn_facebook)
        {
            intent.setClass(this,ScorePage.class);
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        mp.release();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }
}

