//===================================================================================
//Author: Devin Tan & Samuel Wong
//Time: 29/1/2018
//===============================Description=========================================
//Manager for entities. does updates, renders, and clean ups of done entities
//===================================================================================

package assignment.sidm.com.assignment01_ver2;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.LinkedList;

public class EntityManager
{
    public final static EntityManager Instance = new EntityManager();
    private SurfaceView view = null;
    private LinkedList<EntityBase> enetityList = new LinkedList<EntityBase>();
    private LinkedList<EntityBase> collisionList = new LinkedList<EntityBase>();


    private EntityManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void Update(float _dt)
    {
        LinkedList<EntityBase> removeList = new LinkedList<EntityBase>();

        for(EntityBase currEntity : enetityList)
        {
            if(currEntity.IsDone())
                removeList.add(currEntity);

            currEntity.Update(_dt);
        }

        for(EntityBase currEntity  : removeList)
        {
            enetityList.remove(currEntity);
            collisionList.remove(currEntity);
        }

        removeList.clear();

        //collision here
        for(int i = 0;i < collisionList.size();++i)
        {
            EntityBase currEntity = collisionList.get(i);

            if(currEntity instanceof Collidable)
            {
                Collidable first = (Collidable) currEntity;

                for(int j = i + 1;j < collisionList.size(); ++j)
                {
                    EntityBase otherEntity = collisionList.get(j);

                    if(otherEntity instanceof Collidable)
                    {
                        Collidable second = (Collidable) otherEntity;

                        if(Collision.SphereToSphere(first.GetPosX(),first.GetPosY(),first.GetRadius(),
                                second.GetPosX(),second.GetPosY(),second.GetRadius()))
                        {
                            first.OnHit(second);
                            second.OnHit(first);
                        }
                    }
                }
            }

            if(currEntity.IsDone())
            {
                removeList.add(currEntity);
            }
        }

        /*
        for(int i = 0;i < enetityList.size();++i)
        {
            EntityBase currEntity = enetityList.get(i);

            if(currEntity instanceof Collidable)
            {
                Collidable first = (Collidable) currEntity;

                for(int j = i + 1;j < enetityList.size(); ++j)
                {
                    EntityBase otherEntity = enetityList.get(j);

                    if(otherEntity instanceof  Collidable)
                    {
                        Collidable second = (Collidable) otherEntity;

                        if(Collision.SphereToSphere(first.GetPosX(),first.GetPosY(),first.GetRadius(),
                                second.GetPosX(),second.GetPosY(),second.GetRadius()))
                        {
                            first.OnHit(second);
                            second.OnHit(first);
                        }
                    }
                }
            }

            if(currEntity.IsDone())
            {
                removeList.add(currEntity);
            }
        }*/

    }

    public void Render(Canvas _canvas)
    {
        for(EntityBase currEntity : enetityList)
        {
            currEntity.Render(_canvas);
        }
    }

    public void AddEntity(EntityBase _newEntity)
    {
        _newEntity.Init(view);
        enetityList.add(_newEntity);

        if(_newEntity instanceof  Collidable)
            collisionList.add(_newEntity);
    }

    public void RemoveAllEntity()
    {
        enetityList.clear();
    }

}

