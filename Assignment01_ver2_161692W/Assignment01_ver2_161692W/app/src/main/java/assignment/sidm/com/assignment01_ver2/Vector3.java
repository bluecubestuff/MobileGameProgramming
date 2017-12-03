package assignment.sidm.com.assignment01_ver2;

import static java.lang.Math.sqrt;

public class Vector3
{
    public float x,y,z;

    public Vector3(float _x, float _y, float _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }

    public Vector3(final Vector3 rhs)
    {
        x = rhs.x;
        y = rhs.y;
        z = rhs.z;
    }

    void Set(float _x, float _y, float _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }

    void SetZero()
    {
      x = y = z = 0.0f;
    }

    Vector3 Add(final Vector3 rhs)
    {
        return new Vector3(x + rhs.x,y + rhs.y,z + rhs.z);
    }

    Vector3 Subtract(final Vector3 rhs)
    {
        return new Vector3(x - rhs.x,y - rhs.y,z - rhs.z);
    }

    Vector3 unary_negation()
    {
        return new Vector3(-x,-y ,-z );
    }

    Vector3 multiply_scalar(final float scalar)
    {
        return new Vector3(x * scalar,y * scalar ,z * scalar );
    }

    float Length()
    {
        return (float) sqrt(x * x + y * y + z * z);
    }

    float LengthSquared ()
    {
        return x * x + y * y + z * z;
    }

    float Dot(final Vector3 rhs)
    {
        return x * rhs.x + y * rhs.y + z * rhs.z;
    }

    Vector3 Cross( final Vector3 rhs )
    {
        return new Vector3(y * rhs.z - z * rhs.y, z * rhs.x - x * rhs.z, x * rhs.y - y * rhs.x);
    }

    //er yea
    Vector3 Normalized()
    {
        float d = Length();
        //if(d <= Math::EPSILON && -d <= Math::EPSILON)
           // throw DivideByZero();
        return new Vector3(x / d, y / d, z / d);
    }

    Vector3 Normalize()
    {
        float d = Length();
       // if(d <= Math::EPSILON && -d <= Math::EPSILON)
            //throw DivideByZero();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }
}
