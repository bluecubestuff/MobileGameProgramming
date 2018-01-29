//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//A manager to store all the audio and manipulate them
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager
{
    private SurfaceView view = null;
    private boolean isPlaying = false;
    private HashMap<Integer,MediaPlayer> audioMap = new HashMap<Integer,MediaPlayer>();

    public final static AudioManager Instance = new AudioManager();

    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {

    }

    public void PlayAudio(int _id)
    {
        if(audioMap.containsKey(_id))
        {
            MediaPlayer curr = audioMap.get(_id);
            curr.reset();
            curr.start();
        }

        //----------------------if id is not found---------------------\\
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(),_id);
        audioMap.put(_id,newAudio);
        newAudio.start();;
    }

    public boolean IsPlaying()
    {
        return isPlaying;
    }
}
