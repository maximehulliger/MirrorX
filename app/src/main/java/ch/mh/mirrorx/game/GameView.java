package ch.mh.mirrorx.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback, GestureDetector.GestureListener {

    public Level level;
    private final GestureDetector rotationDetector;
    public int width;
    public  int height;
    Context context;
    private GameThread gameThread;
    public boolean over = false;
    private Vector2D victoryTextPos = Vector2D.rel(0.5f, 0.35f), victoryBackSize = Vector2D.rel(0.7f, 0.15f);
    private Paint victoryPaint = new Paint(), victoryBackgroundPaint = new Paint();

    public GameView(Context c, AttributeSet attributeSet) {
        super(c, attributeSet);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        context=c;
        rotationDetector = new GestureDetector(this);
        setBackgroundColor(Color.BLACK);

        victoryBackgroundPaint.setAntiAlias(true);
        victoryBackgroundPaint.setColor(Color.rgb(255, 216, 177));
        victoryPaint.setColor(Color.rgb(255, 102, 0));
        victoryPaint.setAntiAlias(true);
        victoryPaint.setTextAlign(Paint.Align.CENTER);
        victoryPaint.setTextSize(60);
    }

    public void setLevel(Level level) {
        this.level = level;
        refreshRays();
    }

    private GameElement.Playable selectedElement = null;

    @Override
    public void onPointerDown(Vector2D pos) {
        if (!over) {
            // on trouve le miroir le plus proche
            GameElement.Playable closestPlayable = null;
            double closestDistSq = Double.MAX_VALUE;
            for (GameElement e : level.elements) {
                GameElement.Playable p = e.getPlayable();
                if (p != null) {
                    double distSq = p.getPos().distSq(pos);
                    if (distSq < closestDistSq) {
                        closestPlayable = p;
                        closestDistSq = distSq;
                    }
                }
            }
            if (closestPlayable != null) {
                selectedElement = closestPlayable;
            }
        } else {
            ((Activity)context).finish();
        }
    }

    @Override
    public void onRotation(double angle) {
        if (selectedElement != null) {
            selectedElement.rotate(angle);
            refreshRays();
        }
    }

    @Override
    public void onDrag(Vector2D drag) {
        if (selectedElement != null) {
            selectedElement.move(drag);
            refreshRays();
        }
    }

    private void refreshRays() {
        for (GameElement e : level.elements)
            e.onReset();
        for (Source s : level.sources)
            s.emit(level.elements);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (level != null) {
	        for (Source s : level.sources)
	            s.draw(canvas);
	        for (GameElement e : level.elements)
	            e.draw(canvas);
	        for (Source s : level.sources)
	            s.drawRay(canvas);
            if (over) {
                canvas.drawRoundRect(victoryTextPos.x-victoryBackSize.x/2,
                        victoryTextPos.y-victoryBackSize.y/2,
                        victoryTextPos.x+victoryBackSize.x/2,
                        victoryTextPos.y+victoryBackSize.y/2,
                        10, 10, victoryBackgroundPaint);
                canvas.drawText("Victory !",
                        victoryTextPos.x,
                        victoryTextPos.y - ((victoryPaint.descent() + victoryPaint.ascent()) / 2),
                        victoryPaint);
            }
        }
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (level != null)
    		rotationDetector.onTouchEvent(event);
        return true;
    }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(this);
        gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
        try {
            gameThread.running = false;
            gameThread.join();
        } catch (Exception e) {

        }
	}
}
