package com.example.android.interactivechart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class ScaleableView extends FrameLayout {

	private float _scale = 1;
	private float mScrollX = 0;
	private float mScrollY = 0;

	private float _initialTextX = 200;
	private float _initialTextY = 200;

	public ScaleableView(Context context) {
		this(context, null, 0);
	}

	public ScaleableView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScaleableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onScale(float scale) {
		_scale = scale;

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			((Zoomable) child).scale(_scale);
		}

		requestLayout();
	}

	void layoutChildren(int left, int top, int right, int bottom,
			boolean forceLeftGravity) {
		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				final LayoutParams params = (LayoutParams) child.getLayoutParams();

//				Log.d("nori", "scrlX:" + mScrollX + ",scrlY:" + mScrollY);
				child.layout(
						(int) (getDrawXForView(params.leftMargin)),
						(int) (getDrawYForView(params.topMargin)),
						(int) ((getDrawXForView(params.leftMargin + child.getMeasuredWidth()))),
						(int) ((getDrawYForView(params.topMargin + child.getMeasuredHeight())))
						);

				Log.d("nori", "scaled x:" + child.getLeft() + ",y:" + child.getTop() + ",w:" + child.getWidth() + ",h:"
						+ child.getHeight());
			}
		}
//		Log.d("nori", "layoutChildren@ScaleableView");

	}

	private int getDrawYForView(int y) {
		return (int) ((y - mScrollY) * _scale);
	}

	private int getDrawXForView(int x) {
		return (int) ((x - mScrollX) * _scale);
	}

	public void scroll(float x, float y) {
		mScrollX = x;
		mScrollY = y;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams params = (LayoutParams) child.getLayoutParams();
				params.setMargins(
						(int) (getDrawXForView((int) _initialTextX)),
						(int) (getDrawYForView((int) _initialTextY)),
						params.rightMargin,
						params.bottomMargin);
				child.setLayoutParams(params);
			}
		}

		requestLayout();
	}
}
