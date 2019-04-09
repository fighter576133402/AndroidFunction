package cn.wangjianlog.ipcstudy.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/9
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class ScrollerLayout extends ViewGroup {

    private Scroller mScroller;
    //判定为拖动的最小移动像素数
    private int mTouchSlop;
    private int mLastX;
    private int mLastY;
    private int leftBorder;
    private int rightBorder;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 在水平方向上进行布局子控件
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        for (int i = 0; i < getChildCount(); i++) {
//            View childView = getChildAt(i);
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int translationX = (int)getTranslationX() + deltaX;
                int translationY = (int)getTranslationY() + deltaY;
                setTranslationX(translationX);
                setTranslationY(translationY);
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void startScroll(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(),
                dx, dy, 1000);
        postInvalidate();
    }
}
