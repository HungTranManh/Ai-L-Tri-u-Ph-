package ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class CustomHelpAudience extends View {
    private Paint paint;
    private Random rd;
    private Boolean a, b, c, d;
    private int trueCase, level;
    private int lineA, lineB, lineC, lineD;

    public CustomHelpAudience(Context context) {
        super(context);
        setUp();
    }

    public CustomHelpAudience(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public CustomHelpAudience(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        rd = new Random();
        int width = getWidth();
        int height = getHeight();
        int widthColumn = width / 9;
        int lineMax = height-100;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(32);
        canvas.drawLine(0, height
                , width, height, paint);
        canvas.drawLine(0, height
                , 0, 0, paint);
        paint.setStrokeWidth(widthColumn);
        switch (trueCase) {
            case 1:
                lineA = lineTrue();
                if (b) {
                    lineB = rd.nextInt(100 - lineA);
                }
                if (c) {
                    lineC = rd.nextInt(100 - lineA - lineB);
                }
                if (d) {
                    lineD = 100 - lineA - lineB - lineC;
                }
                break;

            case 2:
                lineB = lineTrue();
                if (a) {
                    lineA = rd.nextInt(100 - lineB);
                }
                if (c) {
                    lineC = rd.nextInt(100 - lineA - lineB);
                }
                if (d) {
                    lineD = 100 - lineA - lineB - lineC;
                }
                break;
            case 3:
                lineC = lineTrue();
                if (a) {
                    lineA = rd.nextInt(100 - lineC);
                }
                if (b) {
                    lineB = rd.nextInt(100 - lineA - lineC);
                }
                if (d) {
                    lineD = 100 - lineA - lineB - lineC;
                }
                break;
            case 4:
                lineD = lineTrue();
                if (a) {
                    lineA = rd.nextInt(100 - lineD);
                }
                if (c) {
                    lineC = rd.nextInt(100 - lineA - lineD);
                }
                if (b) {
                    lineB = 100 - lineA - lineD - lineC;
                }
                break;
            default:
                break;
        }
        try {
            canvas.drawLine(widthColumn, height, widthColumn, height-(lineMax / 100)*lineA, paint);
            canvas.drawText("A:"+lineA+"%",widthColumn,100,paint);
            canvas.drawLine(widthColumn * 3, height, widthColumn * 3, height-(lineMax / 100)*lineB, paint);
            canvas.drawText("B:"+lineB+"%",widthColumn*3,100,paint);
            canvas.drawLine(widthColumn * 5, height, widthColumn * 5, height-(lineMax / 100)*lineC, paint);
            canvas.drawText("C:"+lineC+"%",widthColumn*5,100,paint);
            canvas.drawLine(widthColumn * 7, height, widthColumn * 7, height-(lineMax / 100)*lineD, paint);
            canvas.drawText("D:"+lineD+"%",widthColumn*7,100,paint);
        } catch (ArithmeticException ex) {
            System.out.print(ex);
        }
        canvas.restore();

    }

    private int lineTrue() {
        if (level < 5) {
            return 70 + rd.nextInt(3);
        } else if (level >= 5 && level <= 10) {
            return 50 + rd.nextInt(50);
        } else if (level > 10) {
            return 30 + rd.nextInt(70);
        }
        return 0;
    }

    private void setUp() {
        a = true;
        b = true;
        c = true;
        d = true;
        lineA=0;
        lineB=0;
        lineC=0;
        lineD=0;
        trueCase = 0;
        level = 0;
    }

    public void setUpdateChart(boolean a, boolean b, boolean c, boolean d, int trueCase, int level) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.trueCase = trueCase;
        this.level = level;
        invalidate();
    }
}
