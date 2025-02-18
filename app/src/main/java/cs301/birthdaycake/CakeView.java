package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint checker1 = new Paint();
    Paint checker2 = new Paint();

    Paint textPaint = new Paint();

    private cakeModel mod;

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 80.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    public static final float checkerSize = 50.0f;




    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFF5dbfff);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        checker1.setColor(0xffff50df);
        checker1.setStyle(Paint.Style.FILL);
        checker2.setColor(Color.BLACK);
        checker2.setStyle(Paint.Style.FILL);

        textPaint.setColor(0xFFFF0000);
        textPaint.setTextSize(90);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        setBackgroundColor(Color.WHITE);  //better than black default

        mod = new cakeModel();
    }

    public cakeModel getCakeModel(){
        return mod;
    }
    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if (this.mod.hasCandles) {
            int offset = 200;

            for (int i = 0; i < this.mod.numCandles; i++){
                int candleX = (int) (left + (i-(this.mod.numCandles-1)/2)*offset);
                canvas.drawRect(candleX, bottom - candleHeight, candleX + candleWidth, bottom, candlePaint);
                if (this.mod.isLit) {
                    //draw the outer flame
                    float flameCenterX = candleX + candleWidth / 2;
                    float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
                    canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

                    flameCenterY += outerFlameRadius / 3;
                    canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
                }
                //draw the wick
                float wickLeft = candleX + candleWidth / 2 - wickWidth / 2;
                float wickTop = bottom - wickHeight - candleHeight;
                canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
            }
//            if (this.mod.isLit) {
//                //draw the outer flame
//                float flameCenterX = left + candleWidth / 2;
//                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
//                canvas.drawCircle(flameCenterX + offset, flameCenterY, outerFlameRadius, outerFlamePaint);
//                canvas.drawCircle(flameCenterX - offset, flameCenterY, outerFlameRadius, outerFlamePaint);
//
//                //draw the inner flame
//                flameCenterY += outerFlameRadius / 3;
//                canvas.drawCircle(flameCenterX - offset, flameCenterY, innerFlameRadius, innerFlamePaint);
//                canvas.drawCircle(flameCenterX + offset, flameCenterY, innerFlameRadius, innerFlamePaint);
//            }
//            //draw the wick
//            float wickLeft = left + candleWidth / 2 - wickWidth / 2;
//            float wickTop = bottom - wickHeight - candleHeight;
//            canvas.drawRect(wickLeft - offset, wickTop, wickLeft + wickWidth - offset, wickTop + wickHeight, wickPaint);
//            canvas.drawRect(wickLeft + offset, wickTop, wickLeft + wickWidth + offset, wickTop + wickHeight, wickPaint);
        }
    }


    public void drawBalloon(Canvas canvas)
    {
        float width = 50;
        float height = 80;
        canvas.drawOval(this.mod.xTouch, this.mod.yTouch, this.mod.xTouch+ width, this.mod.yTouch+height, cakePaint);
    }//drawBalloon

    public void drawCheckerboard (Canvas canvas){
        canvas.drawRect(this.mod.touchX-checkerSize, this.mod.touchY-checkerSize, this.mod.touchX, this.mod.touchY, checker1);
        canvas.drawRect(this.mod.touchX, this.mod.touchY-checkerSize, this.mod.touchX+checkerSize, this.mod.touchY, checker2);
        canvas.drawRect(this.mod.touchX-checkerSize, this.mod.touchY, this.mod.touchX, this.mod.touchY+checkerSize, checker2);
        canvas.drawRect(this.mod.touchX, this.mod.touchY, this.mod.touchX+checkerSize, this.mod.touchY+checkerSize, checker1);
    }//drawCheckerboard

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Now a candle in the center
        drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);

        drawBalloon(canvas);

        canvas.drawText("("+this.mod.Xval +","+this.mod.Yval+")", 1900,600,textPaint);

        drawCheckerboard(canvas);

    }//onDraw

}//class CakeView

