package br.com.smartfingers.votabrasil.adapter;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionsAdapter extends ArrayAdapter<Question> {

    private LayoutInflater mInflater;
    
	public QuestionsAdapter(Context context, Question[] objects) {
		super(context, R.layout.list_item, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		
		if (null == convertView) {
			row = mInflater.inflate(R.layout.list_item, null);
		} else {
			row = convertView;
		}

		Question item = getItem(position);
		
		TextView center = (TextView) row.findViewById(R.id.center);
		TextView percentYesTxt = (TextView) row.findViewById(R.id.percent_yes_txt);
		TextView percentNoTxt = (TextView) row.findViewById(R.id.percent_no_txt);
		TextView topRight = (TextView) row.findViewById(R.id.top_right);
		TextView bottomRight = (TextView) row.findViewById(R.id.bottom_right);
		
		center.setTypeface(MyApplication.fontDefault);
		percentYesTxt.setTypeface(MyApplication.fontBold);
		percentNoTxt.setTypeface(MyApplication.fontBold);
		topRight.setTypeface(MyApplication.fontDefault);
		bottomRight.setTypeface(MyApplication.fontBold);
		
		center.setText(item.title);
		
		long total = item.yes + item.no;
    	float yes = 0;
    	float no = 0;
    	if (total != 0) {
	    	yes = (float) item.yes / total;
	    	no = (float) item.no / total;
    	}

    	NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		nf.setMaximumFractionDigits(0);
		nf.setGroupingUsed(true);
		
		topRight.setText(nf.format(total) + " votos");
		percentYesTxt.setText(nf.format(yes * 100) + "%");
		percentNoTxt.setText(nf.format(no * 100) + "%");
		
		String bottomStr = "Você votou ";
		String answer = item.answer;
		if (answer != null) {
			if(answer.equalsIgnoreCase("yes")) {
				bottomStr += "SIM";
			} else {
				bottomStr += "NÃO";
			}
		} else {
			bottomStr = "Você não votou";
		}
		bottomRight.setText(bottomStr);
		
		return row;
	}
}
