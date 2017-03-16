package com.example.user.canvasdraw;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
public class MainActivity extends Activity {
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView( new View(this) {
	         Paint mPaint = new Paint();
	         Path mPath = new Path();
	         @Override
	         protected void onDraw(Canvas canvas) {
	            super.onDraw(canvas);
	            int width = this.getWidth();
	            int height = this.getHeight();
	            int radius = width > height ? height/2 : width/2;
	            int center_x = width/2;
	            int center_y = height/2;
	            mPaint.setColor(Color.BLACK);
	            mPaint.setStyle(Paint.Style.FILL);
	            mPaint.setStrokeWidth(2);
	            mPaint.setAntiAlias(true);
  	             // draw a circle
	            canvas.drawCircle(center_x, center_y, radius, 
	            		mPaint);
	            // draw a rectangle

	            mPaint.setColor(Color.argb(50, 255, 0, 0));
	            canvas.drawRect(center_x - radius, center_y - radius, 
	            		center_x + radius, center_y + radius, mPaint);	     
	            // draw a triangle
	            mPaint.setColor(Color.MAGENTA);
	            mPath.moveTo(center_x, center_y - radius);
	            mPath.lineTo(center_x - radius, center_y); 
	            mPath.lineTo(center_x + radius, center_y);
	            mPath.lineTo(center_x, center_y - radius);
	            canvas.drawPath(mPath, mPaint);
	            // draw some text and rotation
	            mPaint.setTextSize(50);
	            mPaint.setTextAlign(Paint.Align.CENTER);
	            mPaint.setTypeface(Typeface.create(Typeface.SERIF, 
	            		Typeface.ITALIC));
	            mPaint.setColor(Color.RED);

				 /* 設定還原點 */
	            canvas.save();

				canvas.rotate(45, center_x, center_y);
	            canvas.drawText( "Android Canvas" , center_x , center_y, 
	            		mPaint);

				 /*  跳到上一個 canvas.save(); */
	            canvas.restore();

	            mPaint.setColor(Color.BLUE);
	            canvas.drawText( "Hello World" , center_x+radius/2 , center_y + 70, mPaint);  
	         }
	      });
	    }
	}
