package assignment.sidm.com.assignment01_ver2;

public interface Collidable
{
    String GetType();
    float GetPosX();
    float GetPosY();
    float GetRadius();

    void OnHit(Collidable _other);

}
