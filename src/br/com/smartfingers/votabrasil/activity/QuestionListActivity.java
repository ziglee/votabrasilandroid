package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.adapter.QuestionsAdapter;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.task.FetchQuestionByIdTask;
import br.com.smartfingers.votabrasil.task.FetchQuestionsTask;

public class QuestionListActivity extends RoboListActivity {

	private static final String LOGTAG = QuestionListActivity.class.getName();
	
	private Question[] result;
	
	private ProgressDialog fetchingNextDialog;
	
	@InjectView(R.id.loading_layout)
	private LinearLayout loadingLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getListView().setVisibility(View.GONE);
		loadingLayout.setVisibility(View.VISIBLE);
		new FetchQuestionsTask(this).execute();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		try{
			fetchingNextDialog = ProgressDialog.show(QuestionListActivity.this, "", "Carregando enquete...");
		} catch (Exception e) {
			Log.e(LOGTAG, "Error showing progress dialog", e);
		}
		
		Question question = result[position];
		new FetchQuestionByIdTask(this).execute(question.id);
	}
	
	public void executeAfterFetchQuestions(Question[] result) {
		this.result = result;
		setListAdapter(new QuestionsAdapter(this, this.result));
		loadingLayout.setVisibility(View.GONE);
		getListView().setVisibility(View.VISIBLE);
	}

	public void executeAfterFetchQuestionById(Question question) {
		try{
			if (fetchingNextDialog != null) {
				fetchingNextDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error dismissing progress dialog", e);
		}
		
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTION, question);
		startActivity(intent);
	}
}
