package roma.romaway.commons.android.tougu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

@SuppressLint("NewApi")
public class RomaHorizontalScrollView extends HorizontalScrollView{
	public RomaHorizontalScrollView(Context context,
									AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public RomaHorizontalScrollView(Context context,
									AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RomaHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public interface OnScrollChangedListener{
	      public void onScrollChanged(int x, int y, int oldxX, int oldY);
	  }
	  
	  private OnScrollChangedListener onScrollChangedListener;
	  
	  /**
	  * 
	  * @param onScrollChangedListener
	  */
	  public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener){
	      this.onScrollChangedListener=onScrollChangedListener;
	  } 
	  
	  @Override
	  protected void onScrollChanged(int x, int y, int oldX, int oldY){
	      super.onScrollChanged(x, y, oldX, oldY);
	      if(onScrollChangedListener!=null){
	          onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
	      }
	  }
}
