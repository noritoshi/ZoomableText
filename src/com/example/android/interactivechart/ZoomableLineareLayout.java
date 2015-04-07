package com.example.android.interactivechart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ZoomableLineareLayout extends LinearLayout implements Zoomable {

	private float _scale = 1.0f;
	private android.view.ViewGroup.LayoutParams _params;
	private int _originWidth;
	private int _originHeight;

	public ZoomableLineareLayout(Context context) {
		super(context);
	}

	public ZoomableLineareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		initLayoutParamas();
	}

	private void initLayoutParamas() {
		if (_params != null) {
			return;
		}
		_params = getLayoutParams();
		_originWidth = _params.width;
		_originHeight = _params.height;
	}

	@Override
	public void scale(float scale) {
		if (scale == _scale) {
			return;
		}

		_scale = scale;
		android.view.ViewGroup.LayoutParams params = getLayoutParams();
		params.width = (int) (_originWidth * scale);
		params.height = (int) (_originHeight * scale);
		setLayoutParams(params);

		scaleChildren();
	}

	private void scaleChildren() {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			((Zoomable) child).scale(_scale);
		}

		requestLayout();
	}

}
