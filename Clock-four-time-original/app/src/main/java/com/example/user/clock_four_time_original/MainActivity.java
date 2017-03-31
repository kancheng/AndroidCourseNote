package com.example.user.clock_four_time_original;
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
    private TextView clock;
    private int mHour,mMinutes,mSeconds, mmSeconds;
    private Thread clockThread;
    private boolean endClock=true;
    private LinearLayout add;
    private MyView myview;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clock = (TextView)findViewById(R.id.clock);/*取得Text物件*/
        //add = (AbsoluteLayout)findViewById(R.id.add);
        add=(LinearLayout)findViewById(R.id.add);
        myview = new MyView(this);/*取得繪製的圖片*/
        add.addView(myview);	 /*將圖片加入XML*/
        clockThread = new ClockThread();
        clockThread.start();
    }
    private Handler ClockHand = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
//            clock.setText(mHour+" : "+mMinutes+" : "+mSeconds);
            myview.redraw();
        }
    };
    //   執行緒
    class ClockThread extends Thread{
        public void run(){
            while(endClock){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){}
                long time = System.currentTimeMillis();/*取得系統時間*/
    			/*透過Calendar物件來取得小時與分鐘*/
                final Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);
                mHour = mCalendar.get(Calendar.HOUR);/*時*/
                mMinutes = mCalendar.get(Calendar.MINUTE);/*分*/
                mSeconds = mCalendar.get(Calendar.SECOND);/*秒*/
                mmSeconds = mCalendar.get(Calendar.MILLISECOND);
                ClockHand.sendMessage(new Message());/*畫出時鐘*/
            }
        }
    }
    //  繪圖
    class MyView extends View{
        public MyView(Context context) {
            super(context);
        }
        public void redraw(){
            invalidate();
        }
        void draw(Canvas canvas, int x, int y, int width, int height, int h, int m, int s, int ms) {
//            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setAntiAlias(true);/*消去鋸齒*/
            paint.setColor(Color.WHITE);/*設定顏色*/
            paint.setStyle(Paint.Style.FILL);/*設定paint的style為空心*/
            canvas.drawRect(x, y, width, height, paint);

            String timeStr = h + " : " + m + " : " + s;
            paint.setTextSize(50);
            paint.setColor(Color.BLACK);/*設定顏色*/
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(timeStr, x + width/2, y+100, paint);

            paint.setStyle(Paint.Style.STROKE);/*設定paint的style為空心*/
            paint.setStrokeWidth(5);/*設定paint的外框寬度*/
			/*取得圓的半徑*/
            int centerX = x + width/2;/*圓心座標*/
            int centerY = y + height/2;
            int r = (width<height) ? width/2:height/2;
            canvas.drawCircle(centerX,centerY,r,paint);/*畫一個空心圓*/
            double timeline = r*0.6;/*時針、分針、秒針的長度*/
            double meline = r*0.7;
            double seline = r*0.8;
			/*時*/
           double tangle = (double)(Math.PI/2-(h+m/60f)*30*Math.PI/180);
            int tx = (int)(centerX+timeline*Math.cos(tangle));
            int ty = (int)(centerY-timeline*Math.sin(tangle));
			/*分*/
            double mangle = (double)(Math.PI/2-(m+s/60f)*6*Math.PI/180);
            int mx = (int)(centerX+meline*Math.cos(mangle));
            int my = (int)(centerY-meline*Math.sin(mangle));
		    /*秒*/
            double sangle = (double)(Math.PI/2-(s + ms / 1000f)*6f*Math.PI/180);
            int sx = (int)(centerX+seline*Math.cos(sangle));
            int sy = (int)(centerY-seline*Math.sin(sangle));
		    /*畫出時針、分針、秒針*/
            canvas.drawLine(centerX,centerY,tx,ty, paint);
            canvas.drawLine(centerX,centerY,mx,my, paint);
            paint.setColor(Color.RED);
            canvas.drawLine(centerX,centerY,sx,sy, paint);
			/*畫出刻度*/
            for(int i =0;i<60;i+=1){
	    		/*設定長度*/
                double leng = r * 0.9;
                double angle = (double)(Math.PI/2-i*6*Math.PI/180);
                int x1 = (int)(centerX+leng*Math.cos(angle));
                int y1 = (int)(centerY-leng*Math.sin(angle));
                int l=r/20;
                if(i%5==0){
                    paint.setColor(Color.BLUE);
                    l=r/15;
                }else{
                    paint.setColor(Color.RED);
                }
                leng-=l;
                int  x2 =(int)(centerX+leng*Math.cos(angle));
                int y2 = (int)(centerY-leng*Math.sin(angle));
                canvas.drawLine(x1,y1,x2,y2, paint);
            }
        }
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();
            draw(canvas, 0, 0, width/2, height/2, mHour, mMinutes, mSeconds, mmSeconds);
            draw(canvas, width/2, 0, width/2, height/2, mHour+8, mMinutes, mSeconds, mmSeconds);
            draw(canvas, 0, height/2, width/2, height/2, mHour+3, mMinutes, mSeconds, mmSeconds);
            draw(canvas, width/2, height/2, width/2, height/2, mHour+5, mMinutes, mSeconds, mmSeconds);
//            draw(canvas, 0, height*2/3, width/2, height/3, mHour+3, mMinutes, mSeconds, mmSeconds);
//            draw(canvas, width/2, height*2/3, width/2, height/3, mHour+5, mMinutes, mSeconds, mmSeconds);
        }
    }
    /*若關閉時鐘Activity，則將執行緒停止
    protected void onDestroy(){
    	super.onDestroy();
	}
    */
    protected void onStop() {
        super.onDestroy();
        endClock=false;
        clockThread.stop();
        clockThread=null;
    }
}