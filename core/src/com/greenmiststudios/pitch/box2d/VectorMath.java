package com.greenmiststudios.pitch.box2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.math.BigDecimal;

/**
 * Created by geoffpowell on 9/30/15.
 */
public class VectorMath {

    public static final float CAMERA_EPSILON = 1.192092896e-10F;
    public static final float FLT_EPSILON = 1.192092896e-07F;
    public static final float LERP = 0.1f;

    public static float[] convertVerts(Vector2[] vertices, float scale) {
        float[] verts = new float[vertices.length*2];
        for (int i = 0; i < verts.length; i+=2) {
            verts[i] = vertices[i/2].x * scale;
            verts[i+1] = vertices[i/2].y * scale;
        }
        return verts;
    }

    public static boolean epsilonEquals (float first, float second, float epsilon) {
        if (Math.abs(first - second) < epsilon) return false;
        return true;
    }

    public static float round(float number, int places) {
        return BigDecimal.valueOf(number).setScale(places, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    public static Vector2 max(Vector2 a, Vector2 b){
        return new Vector2(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }

    public static Vector2 min(Vector2 a, Vector2 b){
        return new Vector2(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public static Vector2 multiply(float s, Vector2 a) {
        return new Vector2(s * a.x, s * a.y);
    }

    public static Vector2 multiply(Vector2 a, float s) {
        return new Vector2(a.x * s, a.y * s);
    }

    public static Vector2 minus(Vector2 a) {
        a.x = (-a.x);
        a.y = (-a.y);
        return a;
    }

    public static float wrap(float value) {
        if (value < FLT_EPSILON - 180) {
            return -180f;
        } else if (value > 180 - FLT_EPSILON) {
            return 180.0f;
        } else {
            return value;
        }
    }

    public static float normalize(Vector2 vector) {
        float length = vector.len();

        if (length < FLT_EPSILON) {
            return 0.0f;
        }
        float invLength = 1.0f / length;
        vector.x = vector.x * invLength;
        vector.y = vector.y * invLength;

        return length;
    }
}
