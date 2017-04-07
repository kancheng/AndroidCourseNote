package com.example.user.recursive_flower;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends Activity {
    private Paint mPaint;
    private LinearLayout add;
    private MyView myview;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=(LinearLayout)findViewById(R.id.add);
        myview = new MyView(this);/*取得繪製的圖片*/
        add.addView(myview);	 /*將圖片加入XML*/
    }

    //  繪圖
    class MyView extends View{
        public MyView(Context context) {
            super(context);
        }

        void mydraw1(Canvas canvas, float x, float y, float l, float angle ) {

            float tx = (float) (x + l * Math.cos(angle));
            float ty = (float) (y + l * Math.sin(angle));
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawLine( x, y, tx, ty, mPaint);

            if(l > 1){
                mydraw1(canvas,tx,ty,(l * 2 / 3), (angle + (float)(Math.PI/6)));
                mydraw1(canvas,tx,ty,(l * 2 / 3), (angle - (float)(Math.PI/6)));
            }

        }

        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            float width = getWidth();
            float height = getHeight();
            canvas.drawColor(Color.WHITE);
            mydraw1(canvas, width/2,height/2,height/12, ((float) (Math.PI*2*0.75)));
            mydraw1(canvas, width/2,height/2,height/12, ((float) (Math.PI*2/2)));
            mydraw1(canvas, width/2,height/2,height/12, ((float) (Math.PI*2/4)));
            mydraw1(canvas, width/2,height/2,height/12, ((float) (Math.PI*2/1)));
        }
    }

}