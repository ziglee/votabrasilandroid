package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;

public class VoteActivity extends RoboActivity {

	@InjectView(R.id.title_txt)
	private TextView titleTxt;
	@InjectView(R.id.content_txt)
	private TextView contentTxt;
	@InjectView(R.id.yes_btn)
	private Button yesBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        
        yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(VoteActivity.this, ResultActivity.class));
			}
		});
        
        new FetchNextQuestionTask(this).execute();
    }

	public void executeAfterAsyncTask(Question result) {
		titleTxt.setText(result.title);
		contentTxt.setText(result.content);
	}
}
