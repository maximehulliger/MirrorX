package ch.mh.mirrorx.game;

import android.view.MotionEvent;

public class GestureDetector {

    private static final int INVALID_POINTER_ID = -1;
    private float fX, fY, sX, sY;
    private int ptrID1, ptrID2;

    private GestureListener mListener;

    public GestureDetector(GestureListener listener){
        mListener = listener;
        ptrID1 = INVALID_POINTER_ID;
        ptrID2 = INVALID_POINTER_ID;
    }

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ptrID1 = event.getPointerId(event.getActionIndex());
                sX = event.getX(event.findPointerIndex(ptrID1));
                sY = event.getY(event.findPointerIndex(ptrID1));
                mListener.onPointerDown(new Vector2D(sX, sY));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                ptrID2 = event.getPointerId(event.getActionIndex());
                fX = event.getX(event.findPointerIndex(ptrID2));
                fY = event.getY(event.findPointerIndex(ptrID2));
                break;
            case MotionEvent.ACTION_MOVE:
                if(ptrID1 != INVALID_POINTER_ID)
                    if (ptrID2 == INVALID_POINTER_ID) {
                        Vector2D drag = new Vector2D(event.getX() - sX, event.getY() - sY);
                        sX = event.getX();
                        sY = event.getY();
                        mListener.onDrag(drag);
                    } else {
                        float nsX = event.getX(event.findPointerIndex(ptrID1));
                        float nsY = event.getY(event.findPointerIndex(ptrID1));
                        float nfX = event.getX(event.findPointerIndex(ptrID2));
                        float nfY = event.getY(event.findPointerIndex(ptrID2));

                        final double angle = angleBetweenLines(fX, fY, sX, sY, nfX, nfY, nsX, nsY);

                        sX = nsX;
                        sY = nsY;
                        fX = nfX;
                        fY = nfY;

                        mListener.onRotation(angle);
                    }
                break;
            case MotionEvent.ACTION_UP:
                ptrID1 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                ptrID2 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_CANCEL:
                ptrID1 = INVALID_POINTER_ID;
                ptrID2 = INVALID_POINTER_ID;
                break;
        }
        return true;
    }

    private double angleBetweenLines (float fX, float fY, float sX, float sY,
                                      float nfX, float nfY, float nsX, float nsY) {
        double angle1 = Math.atan2( (fY - sY), (fX - sX) );
        double angle2 = Math.atan2( (nfY - nsY), (nfX - nsX) );

        return angle2 - angle1;
    }

    public static interface GestureListener {
        public void onPointerDown(Vector2D pos);
        public void onRotation(double angle);
        public void onDrag(Vector2D drag);
    }
}