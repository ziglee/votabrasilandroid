package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;

public class MainActivity extends RoboActivity implements NextQuestionFetchable {
	
	@InjectView(R.id.vote_btn)
	private Button voteBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        voteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new FetchNextQuestionTask(MainActivity.this).execute();
			}
		});
    }

	public void executeAfterFetchNextQuestion(Question result) {
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTION, result);
		startActivity(intent);
	}
}