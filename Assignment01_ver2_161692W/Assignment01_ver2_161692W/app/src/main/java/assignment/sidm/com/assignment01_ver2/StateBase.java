//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.graphics.Canvas;
import android.view.SurfaceView;

public interface StateBase
{
    String GetName();

    void OnEnter(SurfaceView _view);
    void OnExit();
    void Render(Canvas _canvas);
    void Update(float _dt);
}
