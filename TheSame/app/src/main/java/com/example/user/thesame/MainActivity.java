package com.example.user.thesame;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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
        import android.util.Log;

public class MainActivity extends Activity {
    private SameView myview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview=new SameView(this);
        setContentView(R.layout.activity_main);
        LinearLayout ll=(LinearLayout) findViewById(R.id.mylinear);
        ll.addView(myview);
    }

    /*
    * 所有看到的物件都為 View
    * */
    private class SameView extends View{
        private final int N = 10; // 12 or 8
        /* final 是作具名常數用的 ... */
        private int same[][]; /* 陣列的定義 */
        /*
                N * N
                 1 ~ 6
                */
        private Random rnd;
        private int width,height; /* 視窗的寬高 */
        private int rowUp,colUp, rowDown, colDown, n = 0;
        /*
        rowUp,colUp, 滑鼠放開的位置
        rowDown, colDown -> 滑鼠按下的位置
                */
        public SameView(Context context){
            super(context);
            setFocusable(true);    /* 取得焦點   */
            setFocusableInTouchMode(true); /* 取得焦點   */
        }

        /*
         * @Override
          * 意為複寫我副程式的相同方法
          * */
        @Override
        public void onSizeChanged(int scrnW,int scrnH,int oldw,int oldh) {
            /*
            * onSizeChanged 為當大小改變，做 initSame();
            * super 指呼叫父類別 -> 在這為 onSizeChanged
            * */
            super.onSizeChanged(scrnW, scrnH, oldw, oldh);
            width = scrnW;
            height = scrnH;
            initSame(); // 此為自幹的部分
        }

        private void initSame() {
            rnd = new Random();
            same = new int[N][N]; /* 跟陣列的定義 private int same[][];  合起來為宣告 */
            /*
                         *  int[N][N] -> N * N
                        * */
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    same[i][j] = rnd.nextInt(6)+1;
        }

        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            int w = width / N; /* 小矩形 寬 */
            int h = height / N; /* 小矩形 高 */

            /* 顏色資訊 */
            int colors[] = {0, Color.YELLOW, Color.GREEN, Color.BLUE, Color.LTGRAY, Color.CYAN, Color.MAGENTA};

      /*  EX : color = ( r << 16 )| (g << 8) | b
            *  r : 0 - 255
            *  g :  0 - 255
            *  b :  0 - 255
            *  a :  0 - 255
            *  a -> 代表透明度
            * */

            Paint mPaint = new Paint();
            mPaint.setAntiAlias(true);

            for (int i = 1; i < N; i++) {
                canvas.drawLine(i*w, 0, i*w, height, mPaint); /* N - 1 直線 */
                canvas.drawLine(0, i*h, width, i*h, mPaint); /* N - 1 橫線 */
            }

            /*
            * 400 * 600
            *               j = 0    j = 1  j = 2   j = 3
            *           _____________________
            *          |                                          |
            *  i = 0 |      *          *        *       *   |  -> ( 0, 3)
            *          |                                          |
            *  i = 1 |      *          *        *       *   |  -> ( 1, 3)
            *          |                                          |
            *  i = 2 |      *          *        *       *   |  -> ( 2, 3)
            *          |                                          |
            *  i = 3 |      *          *        *       *   |  -> ( 3, 3)
            *          |_____________________|
            *
            * */



            for(int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    int r = i * h; /* y 座標 */
                    int c = j * w; /* x 座標 */
                    /* ( i ,j ) -> x =  j * w ; y = i * h*/

                    int color;

                    /* 色彩 */
                    color = colors[same[i][j]]; /* 1 - 6 隨機*/
          /*
                    * 正方形 初始點 -> c + 0.1 w &  r + 0.1 h
                    * 正方形 結束點 -> c + 0.9 w &  r + 0.9 h
                    *
                    * */

                    mPaint.setColor(color);
                    float lt = (float) (c + w * 0.1);
                    float tp = (float) (r + h * 0.1);
                    float rt = (float) (c + w * 0.9);
                    float bm = (float) (r + h * 0.9);
                    canvas.drawRect(lt, tp, rt, bm, mPaint);
                }
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            int xx = colDown * w;
            int yy = rowDown * h;
            mPaint.setColor(Color.RED);
            canvas.drawRect(xx,yy,xx+w,yy+h,mPaint);
            xx = colUp * w;
            yy = rowUp * h;
            mPaint.setColor(Color.BLACK);
            canvas.drawRect( xx, yy, xx+w, yy+h, mPaint);
        }
        /* 觸控事件 */
        public boolean onTouchEvent(MotionEvent me){

      /*
            * Down -> 按下
            * Up -> 放開
            * Move -> 拖曳
            */
            super.onTouchEvent(me);
            int w = width / N;
            int h = height / N;

      /* EX : xy
            * 400 * 600
            *                       400
            *           _____________________
            *          |                                          |
            *          |      00       01      02     03  |
            *          |                                          |
            *  600  |      10       11      12     13  |
            *          |                                          |
            *          |      20       21      22     23  |
            *          |                                          |
            *          |      30       31      32     33  |
            *          |_____________________|
            *
            * */
            switch(me.getAction()){

                case MotionEvent.ACTION_UP:/* 放開 */

                    /* PHP & ASP 只能用 if() 來判斷 */
                    colUp = (int)me.getX() / w; /* 行 */
                    rowUp = (int)me.getY() / h; /* 列 */

                    System.out.println(colUp + ", " + rowUp);
                    Log.d("BUG", "MouseUp" );
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE: /* 拖曳 */
                    colUp = (int)me.getX() / w;
                    rowUp = (int)me.getY() / h;
                    System.out.println(colUp + ", " + rowUp);
                    Log.d("BUG", "MouseMove" );
                    break;
                case MotionEvent.ACTION_DOWN: /* 按下 */
                    colDown = (int)me.getX() / w;
                    rowDown = (int)me.getY() / h;
                    System.out.println(colDown + ", " + rowDown);
                    Log.d("BUG", "MouseDown" );
                    break;
            }
            return true;
        }
    }
}

