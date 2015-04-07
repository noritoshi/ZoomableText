package com.example.android.interactivechart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ZoomableTextView extends TextView implements Zoomable {

	private float _scale = 1.0f;
	private int _originWidth;
	private int _originHeight;
	private LayoutParams _params;
	private float _originTextSize;

	public ZoomableTextView(Context context) {
		super(context);
		_originTextSize = getTextSize();
	}

	public ZoomableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_originTextSize = getTextSize();
	}

	public ZoomableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_originTextSize = getTextSize();
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
		setTextSize(_originTextSize * _scale);
		LayoutParams params = getLayoutParams();
		params.width = (int) (_originWidth * scale);
		params.height = (int) (_originHeight * scale);
		setLayoutParams(params);
	}
}
