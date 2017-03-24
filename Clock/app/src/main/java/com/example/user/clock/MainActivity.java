package com.example.user.clock;
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
    //private Handler ClockHand;
    private Thread clockThread;
    private boolean endClock=true;
    //private AbsoluteLayout add;
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
            clock.setText(mHour+" : "+mMinutes+" : "+mSeconds);
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
            invalidate(); /*  呼叫 onDraw ， onDraw 畫時鐘 */
        }
        public void haoyeClock4(Canvas canvas4p, int haoyew, int haoyeh){
            int centerX = haoyew;/*圓心座標*/
            int centerY = haoyeh;
            Paint paint = new Paint();
            paint.setAntiAlias(true);/*消去鋸齒*/
            paint.setColor(Color.BLACK);/*設定顏色*/
            paint.setStyle(Paint.Style.STROKE);/*設定paint的style為空心*/
            paint.setStrokeWidth(3);/*設定paint的外框寬度*/
			/*取得圓的半徑*/
            int r = (getWidth()<getHeight()) ? getWidth()/4:getHeight()/4;
            canvas4p.drawCircle(haoyew,haoyeh,r,paint);/*畫一個空心圓*/
            double timeline = r*0.6;/*時針、分針、秒針的長度*/
            double meline = r*0.7;
            double seline = r*0.8;
			/*時*/
            double tangle = (double)(Math.PI/2-(mHour+mMinutes/60f)*30*Math.PI/180);
            int tx = (int)(centerX+timeline*Math.cos(tangle));
            int ty = (int)(centerY-timeline*Math.sin(tangle));
			/*分*/
            double mangle = (double)(Math.PI/2-(mMinutes+mSeconds/60f)*6*Math.PI/180);
            int mx = (int)(centerX+meline*Math.cos(mangle));
            int my = (int)(centerY-meline*Math.sin(mangle));
		    /*秒*/
            double sangle = (double)(Math.PI/2-(mSeconds + mmSeconds / 1000f)*6f*Math.PI/180);
            int sx = (int)(centerX+seline*Math.cos(sangle));
            int sy = (int)(centerY-seline*Math.sin(sangle));
		    /*畫出時針、分針、秒針*/
            canvas4p.drawLine(centerX,centerY,tx,ty, paint);
            canvas4p.drawLine(centerX,centerY,mx,my, paint);
            paint.setColor(Color.RED);
            canvas4p.drawLine(centerX,centerY,sx,sy, paint);
			/*畫出刻度*/
            for(int i =0;i<60;i+=1) {
	    		/*設定長度*/
                double leng = r * 0.9;
                double angle = (double) (Math.PI / 2 - i * 6 * Math.PI / 180);
                int x1 = (int) (centerX + leng * Math.cos(angle));
                int y1 = (int) (centerY - leng * Math.sin(angle));
                int l = 30;
                if (i % 5 == 0) {
                    paint.setColor(Color.BLUE);
                    l = 45;
                } else {
                    paint.setColor(Color.RED);
                }
                leng -= l;
                int x2 = (int) (centerX + leng * Math.cos(angle));
                int y2 = (int) (centerY - leng * Math.sin(angle));
                canvas4p.drawLine(x1, y1, x2, y2, paint);
            }
        }

        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);

            /*   1  2  */
            /*   3  4  */

            int cp1hw = getWidth()/4;
            int cp1hh = getHeight()/4;
            int cp2hw = (getWidth()*3)/4;
            int cp2hh = getHeight()/4;
            int cp3hw = getWidth()/4;
            int cp3hh = (getHeight() * 3)/4;
            int cp4hw = (getWidth()*3)/4;
            int cp4hh = (getHeight()*3)/4;

            /* 呼叫 副類別 的 onDraw */
            haoyeClock4(canvas, cp1hw, cp1hh);
            haoyeClock4(canvas, cp2hw, cp2hh);
            haoyeClock4(canvas, cp3hw, cp3hh);
            haoyeClock4(canvas, cp4hw, cp4hh);



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