package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import br.com.smartfingers.votabrasil.R;

public class ResultActivity extends RoboActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

    }

/*	@InjectView(R.id.result_chart)
	private XYPlot resultChart;
	private SimpleXYSeries series;
	private void setupChart() {
		XYGraphWidget graphWidget = resultChart.getGraphWidget();
		graphWidget.getGridBackgroundPaint().setColor(Color.WHITE);
        graphWidget.getGridLinePaint().setColor(Color.BLACK);
        graphWidget.getDomainOriginLinePaint().setColor(Color.BLACK);
        graphWidget.getRangeOriginLinePaint().setColor(Color.BLACK);
        
        series = new SimpleXYSeries("Resposta");
		resultChart.addSeries(series, BarRenderer.class, new BarFormatter(Color.BLUE, Color.RED));
        resultChart.setDomainStepValue(2);
        resultChart.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 20);
        resultChart.setRangeBoundaries(0, 100, BoundaryMode.FIXED);
        
        resultChart.setDomainValueFormat(new Format() {
			private static final long serialVersionUID = 1L;
			
	        @Override
	        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
	        	Number num = (Number) obj;
	            int roundNum = (int) (num.floatValue() + 0.5f);
	            switch(roundNum) {
	                case 0:
	                    toAppendTo.append("Sim");
	                    break;
	                default:
	                    toAppendTo.append("Nao");
	            }
	            return toAppendTo;
	        }
	 
	        @Override
	        public Object parseObject(String source, ParsePosition pos) {
	            return null;  // We don't use this so just return null for now.
	        }
	    });

        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        nf.setMaximumFractionDigits(0);
        nf.setMinimumFractionDigits(0);
        
        resultChart.setRangeValueFormat(new Format() {
			private static final long serialVersionUID = 1L;

			@Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                long timestamp = ((Number) obj).longValue();
                return new StringBuffer(nf.format(timestamp) + "%");
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
        
        resultChart.setDomainLabel("");
        resultChart.getDomainLabelWidget().pack();
        resultChart.setRangeLabel("Votos");
        resultChart.getRangeLabelWidget().pack();
        resultChart.setGridPadding(40, 0, 40, 0);
        resultChart.disableAllMarkup();
        
        BarRenderer barRenderer = (BarRenderer) resultChart.getRenderer(BarRenderer.class);
        if(barRenderer != null) {
            barRenderer.setBarWidth(30);
        }
	}

	private void plotChart() {
		Number[] seriesNumbers = {35.5, 64.5};
		series.setModel(Arrays.asList(seriesNumbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
		
        resultChart.redraw();
	}
	*/
}
