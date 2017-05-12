# TheSame Note

---

title: "TheSame"
author: "Haoye"
date: "2017-5-12"

---

## 1. Code

TheSame  Code 片段如下 :

```
public class MainActivity extends Activity { /* MainActivity 主程式 */

    private SameView myview; /* SameView */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview=new SameView(this);
        setContentView(R.layout.activity_main);
        LinearLayout ll=(LinearLayout)findViewById(R.id.mylinear);
        ll.addView(myview);
    }

    private class SameView extends View{

		/* 全域變數 */
        private final int N = 10;	/* 陣列範圍 */
			/* N * M */
			/* 10 * 12 */

        private int same[][];	/* 圖片矩陣 */
        private Random rnd;	/* 隨機數物件 */
        private int width, height;	/* 螢幕寬、高 */

		/* 與陣列相對應之 x, y 座標 */
		private int row, col, selRow, selCol, n = 0;

		/* 
		* 滑鼠所產生 row, col
		* 滑鼠按下去的位置 selRow, selCol
		* 按下去跟放開都是同一個位置 !!!
		*
		* n = 0 ，在此為按下去相同顏色的個數。
		* 
		*/

		/* SameView 的 建構式 */
        public SameView(Context context){
            super(context);
            setFocusable(true);	/* 取得焦點 */
            setFocusableInTouchMode(true);	/* 取得焦點 */
        }

	public void onSizeChanged( int w, int h, int oldw, int oldh) {
		super.onSizeChanged (w, h, oldw, oldh);
		width = w;	/* 取得螢幕之寬 */
		height = h;	/* 取得螢幕之高 */
		initSame();
		/* 產生遊戲的畫面 */
	}

        private void initSame() {
		rnd = new Random();
		same = new int[N][N];
		for (int i = 0; i < N; i++)
		for (int j = 0; j < N; j++)

		/* 遊戲中的色塊 */
		same[i][j] = rnd.nextInt(4) + 1;
        }

	public void onDraw(Canvas canvas) {
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
			canvas.drawLine( i * w, 0, i * w, height, mPaint);
			canvas.drawLine(0, i * h, width, i * h, mPaint);
		}

		mPaint.setStyle(Paint.Style.FILL);

		for(int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    if (same[i][j] != -10) {
                        int r = i * h;
                        int c = j * w;
                        int color;

                        if (same[i][j] < 0){
							color = colors[-same[i][j]];
						} else {
							color = darker(colors[same[i][j]]);
						}

		/*
		* if (same[i][j] >= 0)
		*	color = colors[same[i][j]];
		* else
		*	color = Color.WHITE;
		*
		*  color  =  Color.WHITE;
		*  可在此嘗試調整成偏暗的顏色 by darker
		*/

                        mPaint.setColor(color);
                        float lt = (float) (c + w * 0.1);
                        float tp = (float) (r + h * 0.1);
                        float rt = (float) (c + w * 0.9);
                        float bm = (float) (r + h * 0.9);
                        canvas.drawRect(lt, tp, rt, bm, mPaint);
                    }
                }
        }

		int darker(int color) {
			int r = (int)(Color.red(color) * 0.8);
			int g = (int)(Color.green(color) * 0.8);
			int b = (int)(Color.blue(color) * 0.8);
			return Color.rgb( r, g, b);
		}

        private int chkSame(int row, int col, int n) {

		/*
		*  int row, int col  為位置
		*  int n 為該格的顏色值
		*/

		int u, d, l, r;

		/*
		* int m = same[row][col];
		*/
		
		u = d = l = r = 0;

		/* n > 0  為檢查條件 */
            if (n > 0) { .....
```

## 2. 變數名

`宣告`、`存取`

## 3. 方法

`宣告(定義)`、`呼叫`，通常一個方法都會被呼叫兩次以上，因為沒有重複使用就直接寫在裡面就好了 ...

