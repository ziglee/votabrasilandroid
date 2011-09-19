package br.com.smartfingers.votabrasil.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChart extends View {
	
	private static final int WAIT = 0;
	private static final int IS_READY_TO_DRAW = 1;
	private static final int IS_DRAW = 2;
	private static final float START_INC = 30;
	
	private Paint bgPaints = new Paint();
	private Paint linePaints = new Paint();
	private int width;
	private int height;
	private int gapLeft;
	private int gapRight;
	private int gapTop;
	private int gapBottom;
	private int bgColor;
	private int state = WAIT;
	private float start;
	private float sweep;
	private int maxConnection;
	private List<PieItem> dataArray;
	
	public PieChart (Context context){
		super(context);
	}
	
	public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (state != IS_READY_TO_DRAW && state != IS_DRAW) return;
		canvas.drawColor(bgColor);

		bgPaints.setAntiAlias(true);
		bgPaints.setStyle(Paint.Style.FILL);
		bgPaints.setColor(0x88FF0000);
		bgPaints.setStrokeWidth(0.5f);

		linePaints.setAntiAlias(true);
		linePaints.setStyle(Paint.Style.STROKE);
		linePaints.setColor(0xff000000);
		linePaints.setStrokeWidth(0.5f);

		RectF ovals = new RectF( gapLeft, gapTop, width - gapRight, height - gapBottom);

		start = START_INC;
		
		for (PieItem item : dataArray) {
			bgPaints.setColor(item.color);
			sweep = (float) 360 * ((float)item.count / (float)maxConnection);
			canvas.drawArc(ovals, start, sweep, true, bgPaints);
			canvas.drawArc(ovals, start, sweep, true, linePaints);
			start += sweep;
		}
		state = IS_DRAW;
	}
	
	public void setGeometry(int width, int height, int gapLeft, int gapRight, int gapTop, int gapBottom) {
		this.width = width;
		this.height = height;
		this.gapLeft = gapLeft;
		this.gapRight = gapRight;
		this.gapTop = gapTop;
		this.gapBottom = gapBottom;
	}
	
	public void setSkinParams(int bgColor) {
		this.bgColor = bgColor;
	}
	
	public void setData(List<PieItem> data, int maxConnection) {
		this.dataArray = data;
		this.maxConnection = maxConnection;
		this.state = IS_READY_TO_DRAW;
	}

	public int getColorValue(int index) {
		if (dataArray == null) return 0;
		if (index < 0){
			return ((PieItem)dataArray.get(0)).color;
		} else if (index >= dataArray.size()){
			return ((PieItem)dataArray.get(dataArray.size()-1)).color;
		} else {
			return ((PieItem)dataArray.get(dataArray.size()-1)).color;
		}
	}
}