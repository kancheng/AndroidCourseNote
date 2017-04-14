package com.example.user.recursive_rectangle_mt3;
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

        int indexnum = 6;


        void mydraw1(Canvas canvas, float x, float y, float lx, float ly ) {

            indexnum = indexnum - 1;
            float tx = (float) (x);
            float ty = (float) (y);
            float tlx = (float) (lx);
            float tly = (float) (ly);

            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x, y, lx, ly, mPaint);

            float taddlx = (float) (getWidth() * 0.1);
            float taddly = (float) (getHeight() * 0.1);

           /* test canvas.drawRect
                        canvas.drawRect(400, 400, 60, 80, mPaint);
                        */
            if( indexnum > 1){

                mydraw1(canvas, ((float)(tx * 0.9)) ,
                        ((float)(ty * 0.9)),
                        ((float)(tlx + (taddlx * 0.9))),
                        ((float)(tly + (taddly * 0.9)))
                );
            }

        }

        protected void onDraw(Canvas canvas){

            super.onDraw(canvas);
            float width = getWidth();
            float height = getHeight();
            canvas.drawColor(Color.WHITE);
            mydraw1(canvas, ((float) ((width / 100) * 90)), ((float) ((height / 100) * 90)) , ((float) ( (width /100) * 10)), ((float) ((height/100) * 10)));
        }
    }

}