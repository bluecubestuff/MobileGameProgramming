package assignment.sidm.com.assignment01_ver2;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by 161996Y on 1/8/2018.
 */

public class AudioPlayer {
    public final static AudioPlayer Instance = new AudioPlayer();
    private MediaPlayer mp = new MediaPlayer();

    private HashMap<String, Integer> audioMap;

    boolean AddAudio(String name, int uri){
        if (!audioMap.containsKey(name)){
            audioMap.put(name, uri);
            return true;
        }
        else
            return false;
    }

    boolean PlayAudio(Context context, int uri){
        mp = MediaPlayer.create(context, uri);
        mp.start();
        return true;
    }

    boolean PlayAudio(Context context, String name){
        if (audioMap.containsKey(name)){
            mp = MediaPlayer.create(context, audioMap.get(name));
            mp.start();
            return true;
        }
        else
            return false;
    }
}
