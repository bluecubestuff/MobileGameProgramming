package assignment.sidm.com.assignment01_ver2;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import assignment.sidm.com.assignment01_ver2.R;

public class SettingPage extends Activity implements OnClickListener {

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //hide top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settingpage);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //set listener to something btn
    }

    @Override
    //invoke callback event in the view
    public void onClick(View v)
    {
        //intent - action to be perform
        Intent intent = new Intent();

        if(v == btn_back)
        {   //intent is to set to another class which is another page/screen to go to
            intent.setClass(this,Mainmenu.class);
        }

        startActivity(intent);
    }
}
