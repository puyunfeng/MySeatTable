package com.example.pyf.myseattable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;


/**
 * Created by pyf on 2017/2/8.
 */

public class SeatTable extends View {
    private int row = 5;
    private int column = 5;
    Bitmap seatBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_gray);//可选时座位的图片
    private Bitmap checkedSeatBitmap;
    private Bitmap seatSoldBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_sold);
    private int verSpacing = 50;//座位垂直间距
    private int horSpacing = 50;//座位水平间距
    private int translateY = 50;//座位偏移的距离
    private int translateX = 50;//座位偏移的距离
    private static final int SEAT_TYPE_NOT_AVAILABLE = 4;//座位不可用
    private static final int SEAT_TYPE_SOLD = 1;//座位已售
    private static final int SEAT_TYPE_AVAILABLE = 3;//座位可选
    private static final int SEAT_TYPE_SELECTED = 2;// 座位已经选中
    private int seatWidth=seatBitmap.getWidth();//座位图片的宽度
    private int seatHeight=seatBitmap.getHeight();//座位图片的高度
    boolean isOnClick;
    private SeatChecker seatChecker;


    public void setSeatChecker(SeatChecker seatChecker) {
        this.seatChecker = seatChecker;
        //清屏刷新屏幕
        invalidate();
    }

    Matrix tempMatrix = new Matrix();
    Paint paint = new Paint();

    public SeatTable(Context context) {
        super(context);
    }

    public SeatTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeatTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSeat(canvas);
    }

    private void drawSeat(Canvas canvas) {
        for (int i = 0; i < row; i++) {
            float top = i * seatBitmap.getHeight() + i * verSpacing + translateY;
            float bottom = top + seatBitmap.getHeight();
            if (bottom < 0 || top > getHeight()) {
                continue;
            }
            for (int j = 0; j < column; j++) {
                float left = j * seatBitmap.getWidth() + j * horSpacing + translateX;
                float right = left + seatBitmap.getWidth();
                //绘制的图像出界
                if (right < 0 || left > getWidth()) {
                    continue;
                }
                tempMatrix.setTranslate(left, top);
                int seatType = getSeatType(i, j);
                switch (seatType) {
                    case SEAT_TYPE_AVAILABLE:
                        canvas.drawBitmap(seatBitmap, tempMatrix, paint);
                        break;
                    case SEAT_TYPE_NOT_AVAILABLE:
                        break;
                    case SEAT_TYPE_SELECTED:
//                        canvas.drawBitmap(checkedSeatBitmap, tempMatrix, paint);
//                        drawText(canvas, i, j, top, left);
                        break;
                    case SEAT_TYPE_SOLD:
                        canvas.drawBitmap(seatSoldBitmap, tempMatrix, paint);
                        break;
                }

            }
        }
    }

    private int getSeatType(int i, int j) {
        if (seatChecker != null) {
            if (!seatChecker.isValidSeat(i, j)) {
                return SEAT_TYPE_NOT_AVAILABLE;
            } else if (seatChecker.isSold(i, j)) {
                return SEAT_TYPE_SOLD;
            }
            ;
        }
        return SEAT_TYPE_AVAILABLE;
    }

    ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }
    });
    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isOnClick = true;
            int x = (int) e.getX();
            int y = (int) e.getY();
            //最大的位置坐标
            int posX=5*seatWidth+5*horSpacing+translateX+seatWidth;
            int posY=5*seatHeight+5*verSpacing+translateY+seatHeight;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    int tempX = (int) (j * seatWidth + j * horSpacing + translateX);
                    int maxTempX = (int) (tempX + seatWidth);
                    int tempY = (int) (i * seatHeight + i * verSpacing+translateY);
                    int maxTempY = (int) (tempY + seatHeight);
                    if (seatChecker != null && seatChecker.isValidSeat(i, j) && !seatChecker.isSold(i, j)) {
                        if(x>=tempX&&x<=maxTempX&&y>=tempY&&y<=maxTempY){
                            Toast.makeText(getContext(),"当前点击的位置是"+(i+1)+"行"+(j+1)+"列",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            return super.onSingleTapConfirmed(e);
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int x = (int) event.getX();
        super.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }
}
