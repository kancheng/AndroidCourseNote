package com.example.user.easydrawdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements OnClickListener{
    private Button button_line,button_circle,button_rect,button_free;
    private DrawDemo drawDemo;


    private int choice, red, green, blue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initial();  為初始化，其目的是為了要把按鈕抓近來 */
        initial();
        setListeners();
    }
    private void initial(){
        /* 將 xml 中的 button 抓近來(實體) */
        button_line = (Button)findViewById(R.id.line_id);
        button_circle = (Button)findViewById(R.id.circle_id);
        button_rect = (Button)findViewById(R.id.rect_id);
        button_free = (Button)findViewById(R.id.free_id);

        /* drawDemo 為自幹的畫布，繼承自 view 的視窗，在 Android 的世界裡你所有的東西都是 view */
        drawDemo = new DrawDemo(this);

        /* 將自己做 view 畫圖物件丟到 xml 的 Layout 物件 */
        LinearLayout linear=(LinearLayout)findViewById(R.id.lin_id);

        /* 將 新增的物間丟到 xml 中的 Layout 物件裡面 */
        linear.addView(drawDemo);

        /* 結束後回歸預設的按鈕 */
        choice=0;
    }

    /*  setListeners  為按鈕的事件，設定四個按鈕*/
    private void setListeners(){
        button_line.setOnClickListener(this);
        button_circle.setOnClickListener(this);
        button_rect.setOnClickListener(this);
        button_free.setOnClickListener(this);
    }

    @Override
    /*  onClick 為按鈕的事件，按下去後發生的事情 */
    public void onClick(View view){
        /*  按下去後會隨意產生其顏色 */
        red = (int)(Math.random() * 256);
        green = (int)(Math.random() * 256);
        blue = (int)(Math.random() * 256);

        /* 利用 view 來確認哪個按鈕被按下 */
        switch(view.getId()){

            /*  Java會用 R.java 找到 xml 中 定義的 id， 在此找到的 id 為 activity_main.xml 內容的 button */
            case R.id.line_id:
                Toast.makeText(this,
                        "直線",
                        Toast.LENGTH_SHORT).show();
                choice=0;
                break;
            case R.id.circle_id:
                Toast.makeText(this,
                        "圓形",
                        Toast.LENGTH_SHORT).show();
                choice=1;
                break;
            case R.id.rect_id:
                Toast.makeText(this,
                        "矩形",
                        Toast.LENGTH_SHORT).show();
                choice=2;
                break;
            case R.id.free_id:
                Toast.makeText(this,
                        "隨意線條",
                        Toast.LENGTH_SHORT).show();
                choice=3;
                break;
        }
    }


    /*  自幹的繪圖物件 */
    private class DrawDemo extends View{
        private Paint mPaint;
        private Path mPath;
        private Canvas mCanvas;

        /* Bitmap 為 點陣圖 */
        private Bitmap mBitmap;

        private float startx,starty,endx,endy;
        private boolean isUp=false;

        /* 初始化 */
        public DrawDemo(Context context){
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);
            initial();
        }

        private void initial(){
            mPaint=new Paint();
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
        }


        @Override
        public void onSizeChanged(int oldw,int oldh,int width,int height){
            super.onSizeChanged(oldw,oldh,width,height);

            /*  利用 Bitmap.createBitmap 來建立一個新的畫布丟進去 */
            mBitmap=Bitmap.createBitmap(oldw,oldh,Config.ARGB_8888);
            mCanvas=new Canvas(mBitmap);
        }

        /* 當畫布開始畫的時候 */
        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);

            /* 把畫布傳進去 */
            drawShape(canvas);

            canvas.drawBitmap(mBitmap,0,0,mPaint);
        }


        /*  按下去的事件，抓座標 */
        @Override
        public boolean onTouchEvent(MotionEvent me){

            float x=me.getX();
            float y=me.getY();
            switch(me.getAction()){
                case MotionEvent.ACTION_DOWN:
                    isUp=false;
                    touch_down(x,y);
                    break;

                case MotionEvent.ACTION_MOVE:
                    touch_move(x,y);
                    break;

                case MotionEvent.ACTION_UP:
                    touch_up();
                    break;

            }
            invalidate();
            return true;
        }

        private void touch_down(float x,float y){
            endx=startx=x;
            endy=starty=y;
        }

        private void touch_move(float x,float y){
            endx=x;
            endy=y;
            if (choice == 3)
                drawShape(mCanvas);
        }

        private void touch_up(){
            drawShape(mCanvas);
        }

        private void drawShape(Canvas canvas){
            mPaint.setColor(Color.rgb(red, green, blue));
            switch(choice){
                case 0:
                    canvas.drawLine(startx,starty,endx,endy,mPaint);
                    break;
                case 1:
                    canvas.drawCircle(startx,starty,getRadius(
                            startx,starty,endx,endy),mPaint);
                    break;
                case 2:
//    			canvas.drawRect(startx,starty,endx,endy,mPaint);
                    canvas.drawLine(startx,(starty+endy)/2,(startx+endx)/2,starty,mPaint);
                    canvas.drawLine(startx,(starty+endy)/2,(startx+endx)/2,endy,mPaint);
                    canvas.drawLine(endx,(starty+endy)/2,(startx+endx)/2,starty,mPaint);
                    canvas.drawLine(endx,(starty+endy)/2,(startx+endx)/2,endy,mPaint);

                    break;
                case 3:
                    canvas.drawLine(startx,starty,endx,endy,mPaint);
                    startx = endx;
                    starty = endy;
                    break;
            }

            if(canvas.equals(mCanvas)){
                endx=startx;
                endy=starty;
            }
            invalidate();
        }

        private float getRadius(float startx,float starty,float endx,float endy){
            return (float)(Math.sqrt(
                    (startx-endx)*(startx-endx)+(starty-endy)*(starty-endy)));
        }
    }
}
