package ro.epb.itec.tripmemories.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ToggleViewPager extends ViewPager {

	private boolean isPagingEnabled = true;
	
	public ToggleViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ToggleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	
	public void setPagingEnabled(boolean isPagingEnabled) {
		this.isPagingEnabled = isPagingEnabled;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	    if (this.isPagingEnabled) {
	        return super.onInterceptTouchEvent(ev);
	    }
	    return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    if (this.isPagingEnabled) {
	        return super.onTouchEvent(ev);
	    }
	    return false;
	}

}
