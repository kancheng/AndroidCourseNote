package com.example.user.recursive_square_mt2;
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


        void mydraw1(Canvas canvas, float x, float y, float lx, float ly ) {



            float tx = (float) ((lx - x)/4);
            float ty = (float) ((ly - y)/4);

            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x, y, lx, ly, mPaint);

            /*
                        X O
                        O O
                        */
            canvas.drawRect(((float)( x - tx )), ((float)( y - ty )), ((float)( x + tx)), ((float)( y + ty)), mPaint);

            /*
                        O X
                        O O
                        */
            canvas.drawRect(((float)( lx - tx )), ((float)( y - ty )), ((float)( lx + tx)), ((float)( y + ty)), mPaint);

            /*
                        O O
                        X O
                        */
            canvas.drawRect(((float)( x - tx )), ((float)( ly - ty )), ((float)( x + tx)), ((float)( ly + ty)), mPaint);

            /*
                        O O
                        X O
                        */
            canvas.drawRect(((float)( lx - tx )), ((float)( ly - ty )), ((float)( lx + tx)), ((float)( ly + ty)), mPaint);

           /* test canvas.drawRect
                        canvas.drawRect(400, 400, 60, 80, mPaint);
                        */

           if( tx > 10){
               mydraw1(canvas, ((float)( x - tx )), ((float)( y - ty )), ((float)( x + tx)), ((float)( y + ty)));
               mydraw1(canvas, ((float)( lx - tx )), ((float)( y - ty )), ((float)( lx + tx)), ((float)( y + ty)));
               mydraw1(canvas, ((float)( x - tx )), ((float)( ly - ty )), ((float)( x + tx)), ((float)( ly + ty)));
               mydraw1(canvas, ((float)( lx - tx )), ((float)( ly - ty )), ((float)( lx + tx)), ((float)( ly + ty)));
           }

        }

        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            float width = getWidth();
            float height = getHeight();
            canvas.drawColor(Color.WHITE);

            mydraw1(canvas, ((float) ((width / 100) * 30)),
                    ((float) ((height / 100) * 30)),
                    ((float) ((width / 100) * 30) + ((width /100) * 40)),
                    ((float) ((height / 100) * 30) + ((width /100) * 40))
            );


        }
    }

}