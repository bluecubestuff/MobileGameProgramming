package assignment.sidm.com.assignment01_ver2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

public class ScorePage extends Activity implements OnClickListener
{
    int m_iHighscore = 0;

    // Facebook UI
    private Button btn_fbLogin;
    private Button btn_sharescore;
    private Button btn_back;

    boolean isLoggedIn = false;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    ProfilePictureView profile_pic;

    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide top bar

        // Initalize for FB
        FacebookSdk.setApplicationId(getString(R.string.app_id));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //AppEventsLogger.activateApp(this);

        setContentView(R.layout.highscorepage);

        m_iHighscore =  GameSystem.Instance.GetIntFromSave("Score");

        // Define for back button
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        // Define fb button
        btn_fbLogin = (LoginButton) findViewById(R.id.fb_login_button);
        btn_fbLogin.setOnClickListener(this);

        // Define scoreshare button
        btn_sharescore = (Button) findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        profile_pic = (ProfilePictureView)findViewById(R.id.picture);
        callbackManager = CallbackManager.Factory.create();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    profile_pic.setProfileId("");
                }
                else{

                    profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                }
            }
        };

        accessTokenTracker.startTracking();

        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this, PERMISSIONS);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                shareScore();
            }
            @Override
            public void onCancel() {
                System.out.println("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Login attempt failed.");
            }
        });

    }



    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        if (v == btn_back) {
            intent.setClass(this, Mainmenu.class);
            startActivity(intent);
        }
        else if(v == btn_sharescore ) {

            AlertDialog.Builder alert_builder = new AlertDialog.Builder(ScorePage.this);

            alert_builder.setTitle("Share score on facebook");
            alert_builder.setMessage("Do you want to share your score of " + String.valueOf(m_iHighscore))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shareScore();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            alert_builder.show();

        }

    }

    // To share info on FB
    public void shareScore(){

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Thank you for playing MGP2017. Your final score is " + m_iHighscore)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

    // FB to use the callback Manager to manage login
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //finish();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //finish();
        super.onStop();
    }
}
