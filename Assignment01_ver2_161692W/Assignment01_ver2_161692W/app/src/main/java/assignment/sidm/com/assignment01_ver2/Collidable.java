//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//Base class for entities that has collision
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

public interface Collidable
{
    String GetType();
    float GetPosX();
    float GetPosY();
    float GetPosZ();
    float GetRadius();

    void OnHit(Collidable _other);

}
