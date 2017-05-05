package com.example.user.thesamev4;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
    private SameView myview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview=new SameView(this);
        setContentView(R.layout.activity_main);
        LinearLayout ll=(LinearLayout)findViewById(R.id.mylinear);
        ll.addView(myview);
    }
    private class SameView extends View{
        private final int N = 10; /*	陣列範圍	*/
        private int same[][];/*	圖片矩陣	*/
        private Random rnd;/*	隨機數物件	*/
        private int width,height;	/*	螢幕寬、高	*/
        private int row,col, selRow, selCol, n = 0;/*	與陣列相對應之 x,y 座標	*/
        public SameView(Context context){
            super(context);
            setFocusable(true);	/*	取得焦點	*/
            setFocusableInTouchMode(true);	/*	取得焦點	*/
        }
        @Override
        public void onSizeChanged(int w,int h,int oldw,int oldh){
            super.onSizeChanged(w,h,oldw,oldh);
            width=w;	/*	取得螢幕之寬	*/
            height=h;	/*	取得螢幕之高	*/
            initSame();
        }
        private void initSame()
        {
            rnd = new Random();
            same = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    same[i][j] = rnd.nextInt(4) + 1;
        }

        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            int w = width / N;
            int h = height / N;
            int colors[] = {0, Color.YELLOW,
                    Color.GREEN, Color.BLUE, Color.LTGRAY,
                    Color.CYAN, Color.MAGENTA
            };
            Paint mPaint=new Paint();

            mPaint.setAntiAlias(true);

            for (int i = 1; i < N; i++) {
                canvas.drawLine(i*w, 0, i*w, height, mPaint);
                canvas.drawLine(0, i*h, width, i*h, mPaint);
            }
            mPaint.setStyle(Paint.Style.FILL);
            for(int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    if (same[i][j] != -10) {
                        int r = i * h;
                        int c = j * w;
                        int color;
                        if (same[i][j] >= 0)
                            color = colors[same[i][j]];
                        else
                            color = Color.WHITE;

            /*
                        *  color  =  Color.WHITE;
                        *  可在此嘗試調整成偏暗的顏色
                        * */

                        mPaint.setColor(color);
                        float lt = (float) (c + w * 0.1);
                        float tp = (float) (r + h * 0.1);
                        float rt = (float) (c + w * 0.9);
                        float bm = (float) (r + h * 0.9);
                        canvas.drawRect(lt, tp, rt, bm, mPaint);
                    }
                }
        }
        private int chkSame(int row, int col, int n) {
      /*
            *  int row, int col  為位置
            *  int n 為該格的顏色值
            *
            */
            int u, d, l, r;
//    		int m = same[row][col];
            u = d = l = r = 0;

            /* n > 0  為檢查條件 */
            if (n > 0) {
                same[row][col] = -same[row][col];
                if (row > 0 && same[row-1][col] == n)
                    u = chkSame(row-1, col, n);
                /*  往上檢查 */

                if (row < N-1 && same[row+1][col] == n)
                    d = chkSame(row+1, col, n);
                /*  往下檢查 */

                if (col > 0 && same[row][col-1] == n)
                    l = chkSame(row, col-1, n);
                if (col < N-1 && same[row][col+1] == n)
                    r = chkSame(row, col+1, n);
                /*  往左右檢查 */
                /* 檢查過則給 -1 ，讓他代表不同顏色，不然會無窮迴圈 */

                return u + d + l + r + 1;
                /* 四個方向全部相同時還需要加上我自己 -> +1 */
            }
            else
                return 0;
        }
        private void delete() {
            for (int j = 0; j < N; j++)  {

                /* 逐行檢查每一列 */
                int k = 0;
                while (k < N && same[k][j] < 0) {
                    same[k][j] = -10;
                    k++;
                }
                for (int i = N-1; i >= k; i--)

                    /* 往下搬移 */
                    if (same[i][j] != -10)
                        while (i < N-1 && same[i+1][j] < 0) {

                            /* 往下 */
                            same[i+1][j] = same[i][j];

                            same[i][j] = -10;
                            i++;
                        }
            }
        }
        private void restore() {
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (same[i][j] != -10 && same[i][j] < 0)
                        same[i][j] = -same[i][j];
        }
        private void detect(int x, int y, boolean chk) {
            int w = width / N;
            int h = height / N;
            col = x / w;
            row = y / h;
            if (chk) {

                /* 備分 */
                selRow = row;
                selCol = col;

                /* 按下 Down */
                n = chkSame(row, col, same[row][col]);
            }
            else {
                /* 放開 Up */
                if (row == selRow && col == selCol && n > 1)
                    delete();
                else
                    restore();

            }
//    		invalidate();
        }
        /*	監聽觸控事件	*/
        public boolean onTouchEvent(MotionEvent me){
            int x, y;
            super.onTouchEvent(me);
            switch(me.getAction()){
                case MotionEvent.ACTION_UP:
                    x=(int)me.getX();
                    y=(int)me.getY();
                    detect(x,y, false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_DOWN:
                    x=(int)me.getX();
                    y=(int)me.getY();
                    detect(x,y, true);
                    break;
            }
    		/*	重繪	*/
            invalidate();
            return true;
        }
    }
}