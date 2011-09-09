package br.com.smartfingers.votabrasil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;

public class QuestionsAdapter extends ArrayAdapter<Question> {

    private LayoutInflater mInflater;
    
	public QuestionsAdapter(Context context, Question[] objects) {
		super(context, R.layout.template_list_item, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		
		if (null == convertView) {
			row = mInflater.inflate(R.layout.template_list_item, null);
		} else {
			row = convertView;
		}

		Question item = getItem(position);
		
		TextView center = (TextView) row.findViewById(R.id.center);
		center.setText(item.title);
		
		TextView bottom = (TextView) row.findViewById(R.id.bottom);
		
		String bottomStr = "Sua resposta: ";
		String answer = item.answer;
		if (answer != null) {
			if(answer.equalsIgnoreCase("yes")) {
				bottomStr += "Sim";
			} else {
				bottomStr += "Não";
			}
		} else {
			bottomStr += "???";
		}
		bottom.setText(bottomStr);
		
		return row;
	}
}
