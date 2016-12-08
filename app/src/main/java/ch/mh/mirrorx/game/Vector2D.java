package ch.mh.mirrorx.game;


public class Vector2D {

    public final static Vector2D zero = new Vector2D();

    public double x, y;

    public Vector2D() {
        x = y = 0;
    };

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    };

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    public String toString() {
        return "["+x+", "+y+"]";
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    };

    public double distSq(Vector2D v) {
        final double dx = v.x - x, dy = v.y - y;
        return dx*dx + dy*dy;
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
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        double dx = x-center.x;
        double dy = y-center.y;
        x = center.x+dx*cos-dy*sin;
        y = center.y+dy*cos+dx*sin;
        return this;
    };

    public Vector2D rotate(double theta) {
        rotate(theta, zero);
        return this;
    };

    public double dot(Vector2D v) {
        return x*v.x+y*v.y;
    };

    /** Cross with extended z coordinate = 0. Return the z coordinate */
    public double cross(Vector2D v) {
        return (x*v.y) - (y*v.x);
    }

    /** Cross a z unit vector. */
    public Vector2D crossZn() {
        return new Vector2D(y, -x);
    }
};