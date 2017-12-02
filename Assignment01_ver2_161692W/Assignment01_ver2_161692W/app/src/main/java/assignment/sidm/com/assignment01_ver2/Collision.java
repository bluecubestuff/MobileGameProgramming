package assignment.sidm.com.assignment01_ver2;

public class Collision
{
    public static boolean SphereToSphere(float x,float y,float radius1,
                                         float x2,float y2,float radius2)
    {
        float xVec = x2 - x;
        float yVec = y2 - y;
        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if(distSquared > rSquared)
            return false;



        return true;
    }
}
