package assignment.sidm.com.assignment01_ver2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceView;

import java.util.LinkedList;

public class HighScoreState implements StateBase
{
    private int numUser = 0;
    private Paint paint;
    private String userID;
    private LinkedList<Integer> ScoreList;
    @Override
    public String GetName() {
        return "HighScoreState";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        //numUser = GameSystem.Instance.GetIntFromSave("ID");

        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(55);

        userID = "Score";
        /*
        ScoreList = new LinkedList<Integer>();
        for (int score : ScoreList)
        {

        }
        */
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {


        for(int i = 1; i <= 10; ++i)
        {
            userID += i;
            String str_score = "Player " + i + ": " + String.valueOf(GameSystem.Instance.GetIntFromSave(userID));
            _canvas.drawText(str_score,_canvas.getWidth() * 0.2f,_canvas.getHeight() * i / 10,paint);
            //Log.d("score----->", userID);
            userID = "Score";
        }

        _canvas.drawText("HIGHSCORE",_canvas.getWidth() * 0.5f,_canvas.getHeight()* 1 / 18,paint);

    }

    @Override
    public void Update(float _dt) {

    }
}
