package assignment.sidm.com.assignment01_ver2;

import android.graphics.Canvas;
import android.view.SurfaceView;

public interface EntityBase
{
    boolean IsDone();
    void SetIsDone(boolean _isDone);

    boolean IsInit();

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);
}
