package ch.mh.mirrorx.game;


public class Vector2D {

    public final static Vector2D zero = new Vector2D();
    public static int deviceHeight, deviceWidth;

    public float x, y;

    public Vector2D() {
        x = y = 0;
    };

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D rel(float x, float y) {
        return new Vector2D(x*deviceWidth, y*deviceHeight);
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    public String toString() {
        return "["+x+", "+y+"]";
    }

    public float length() {
        return (float)Math.sqrt(x*x+y*y);
    };

    public float distSq(Vector2D v) {
        final float dx = v.x - x, dy = v.y - y;
        return dx*dx + dy*dy;
    }

    public float magSq() {
        return x*x + y*y;
    }

    public Vector2D add(Vector2D v) {
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2D plus(Vector2D v) {
        return new Vector2D(x+v.x,y+v.y);
    };

    public Vector2D minus(Vector2D v) {
        return new Vector2D(x-v.x,y-v.y);
    };

    public Vector2D normalize() {
        final double len = length();
        if( len != 0 ) {
            final double invLen = 1/len;
            x *= invLen;
            y *= invLen;
        }
        return this;
    };

    public Vector2D setLength(double value) {
        normalize();
        x *= value;
        y *= value;
        return this;
    };

    public Vector2D scale(double f) {
        x *= f;
        y *= f;
        return this;
    };

    public Vector2D rotate(double theta, Vector2D center) {
        float sin = (float)Math.sin(theta);
        float cos = (float)Math.cos(theta);
        float dx = x-center.x;
        float dy = y-center.y;
        x = center.x+dx*cos-dy*sin;
        y = center.y+dy*cos+dx*sin;
        return this;
    };

    public Vector2D rotate(double theta) {
        rotate(theta, zero);
        return this;
    };

    public float dot(Vector2D v) {
        return x*v.x+y*v.y;
    };

    /** Cross with extended z coordinate = 0. Return the z coordinate */
    public float cross(Vector2D v) {
        return (x*v.y) - (y*v.x);
    }

    /** Cross a z unit vector. */
    public Vector2D crossZn() {
        return new Vector2D(y, -x);
    }
};