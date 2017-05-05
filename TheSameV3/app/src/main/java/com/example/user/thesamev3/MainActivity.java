package com.example.user.thesamev3;
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
        LinearLayout ll=(LinearLayout)findViewById(R.id.mylinear);
        ll.addView(myview);
    }
    private class SameView extends View{
        private final int N = 10;
        private int same[][];
        private Random rnd;
        private int width,height;
        private int rowUp,colUp, rowDown, colDown, n = 0;
        public SameView(Context context){
            super(context);
            setFocusable(true);    /* 取得焦點   */
            setFocusableInTouchMode(true); /* 取得焦點   */
        }
        @Override
        public void onSizeChanged(int scrnW,int scrnH,int oldw,int oldh) {
            super.onSizeChanged(scrnW, scrnH, oldw, oldh);
            width = scrnW;
            height = scrnH;
            initSame();
        }
        private void initSame() {
            rnd = new Random();
            same = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    same[i][j] = rnd.nextInt(6)+1;
        }
        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            int w = width / N;
            int h = height / N;
            int colors[] = {0, Color.YELLOW, Color.GREEN, Color.BLUE, Color.LTGRAY, Color.CYAN, Color.MAGENTA};
            Paint mPaint = new Paint();
            mPaint.setAntiAlias(true);
            for (int i = 1; i < N; i++) {
                canvas.drawLine(i*w, 0, i*w, height, mPaint);
                canvas.drawLine(0, i*h, width, i*h, mPaint);
            }
            mPaint.setStyle(Paint.Style.FILL);
            for(int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    //   lee               if (same[i][j] != -10) {
                    int r = i * h;
                    int c = j * w;
                    int color;
                    //                       if (same[i][j] >= 0)
                    color = colors[same[i][j]];
                    //  lee                     else
                    // lee                          color = Color.WHITE;
                    mPaint.setColor(color);
                    float lt = (float) (c + w * 0.1);
                    float tp = (float) (r + h * 0.1);
                    float rt = (float) (c + w * 0.9);
                    float bm = (float) (r + h * 0.9);
                    canvas.drawRect(lt, tp, rt, bm, mPaint);
                    //  lee                 }
                }
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            int xx = colDown * w;
            int yy = rowDown * h;
            mPaint.setColor(Color.RED);
            canvas.drawRect(xx, yy, xx+w, yy+h, mPaint);
            xx = colUp * w;
            yy = rowUp * h;
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(xx, yy, xx+w, yy+h, mPaint);
        }
        private boolean check(int row, int col) {
            boolean ok = false;
            int v = same[row][col];
            int cnt = 1;
            int from = row;
            int to = row;
            System.out.println(row + ", " + col);
            for (int r = row - 1; r >= 0; r--)
                if (same[r][col] == v) {
                    cnt++;
                    from = r;
                }
                else
                    break;
            for (int r = row + 1; r < N; r++)
                if (same[r][col] == v) {
                    cnt++;
                    to = r;
                }
                else
                    break;
            if (cnt >= 3) {
                ok = true;
                for (int i= from; i <= to; i++)
                    same[i][col] = -v;
            }
            //           System.out.println(row + ", " + col + " row : " + cnt);
            cnt = 1;
            from = col;
            to = col;
            for (int c = col - 1; c >= 0; c--)
                if (same[row][c] == v) {
                    cnt++;
                    from = c;
                }
                else
                    break;
            for (int c = col + 1; c < N; c++)
                if (same[row][c] == v) {
                    cnt++;
                    to = c;
                }
                else
                    break;
            if (cnt >= 3) {
                ok = true;
                for (int i= from; i <= to; i++)
                    same[row][i] = -v;
            }
            System.out.println(row + ", " + col + " cnt : " + cnt);
            return ok;
        }
        private void delete() {
            for (int j = 0; j < N; j++)  {
                int k = 0;
                while (k < N && same[k][j] < 0) {
                    same[k][j] = -10;
                    k++;
                }
                for (int i = N-1; i >= k; i--)
                    if (same[i][j] != -10)
                        while (i < N-1 && same[i+1][j] < 0) {
                            same[i+1][j] = same[i][j];
                            same[i][j] = -10;
                            i++;
                        }
            }
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (same[i][j] == -10)
                        same[i][j] = rnd.nextInt(6) + 1;
        }
        private void detect(int rd, int cd, int ru, int cu) {
            if (rd == ru && Math.abs(cd-cu) == 1 || cd == cu && Math.abs(rd-ru) == 1) {
                int v = same[rd][cd];
                same[rd][cd] = same[ru][cu];
                same[ru][cu] = v;
                boolean ok1 = check(rd, cd);
                boolean ok2 = check(ru, cu);
                if (ok1 || ok2)
                    delete();
                else {
                    v = same[rd][cd];
                    same[rd][cd] = same[ru][cu];
                    same[ru][cu] = v;
                }
            }

        }
        public boolean onTouchEvent(MotionEvent me){
            super.onTouchEvent(me);
            int w = width / N;
            int h = height / N;
            switch(me.getAction()){
                case MotionEvent.ACTION_UP:
                    colUp = (int)me.getX() / w;
                    rowUp = (int)me.getY() / h;
                    System.out.println(colUp + ", " + rowUp);
                    Log.d("BUG", "MouseUp" );
                    detect(rowDown, colDown, rowUp, colUp);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //                   colUp = (int)me.getX() / w;
//                  rowUp = (int)me.getY() / h;
//                   System.out.println(colUp + ", " + rowUp);
//                   Log.d("BUG", "MouseMove" );
                    break;
                case MotionEvent.ACTION_DOWN:
                    colDown = (int)me.getX() / w;
                    rowDown = (int)me.getY() / h;
                    System.out.println(colDown + ", " + rowDown);
                    Log.d("BUG", "MouseDown" );
                    invalidate();
                    break;
            }
            return true;
        }
    }
}
