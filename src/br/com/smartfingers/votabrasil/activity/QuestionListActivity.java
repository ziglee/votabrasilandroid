package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.adapter.QuestionsAdapter;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;
import br.com.smartfingers.votabrasil.task.FetchQuestionByIdTask;
import br.com.smartfingers.votabrasil.task.FetchQuestionsTask;

import com.google.inject.Inject;

public class QuestionListActivity extends RoboListActivity {

	private static final String LOGTAG = QuestionListActivity.class.getName();
	
	private Question[] result;
	
	private ProgressDialog fetchingNextDialog;
	
	@InjectView(R.id.loading_layout)
	private LinearLayout loadingLayout;
	
	@Inject
	private QuestionService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);
		setupMenu();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getListView().setVisibility(View.GONE);
		loadingLayout.setVisibility(View.VISIBLE);
		new FetchQuestionsTask(service, this).execute();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		try{
			fetchingNextDialog = ProgressDialog.show(QuestionListActivity.this, "", "Carregando enquete...");
		} catch (Exception e) {
			Log.e(LOGTAG, "Error showing progress dialog", e);
		}
		
		Question question = result[position];
		new FetchQuestionByIdTask(service, this).execute(question.id);
	}
	
	public void executeAfterFetchQuestions(Question[] result, Exception exception) {
		if(exception != null) {
			Log.e(LOGTAG, "Error fetching questions", exception);
			Toast.makeText(this, "Erro ao listar questionários", Toast.LENGTH_LONG).show();
			this.result = new Question[0];
		} else {
			this.result = result;
		}
		
		setListAdapter(new QuestionsAdapter(this, this.result));
		loadingLayout.setVisibility(View.GONE);
		getListView().setVisibility(View.VISIBLE);
	}

	public void executeAfterFetchQuestionById(Question question, Exception exception) {
		try{
			if (fetchingNextDialog != null) {
				fetchingNextDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error dismissing progress dialog", e);
		}
		
		if(exception != null) {
			Log.e(LOGTAG, "Error fetching question by id", exception);
			Toast.makeText(this, "Erro ao buscar questionário", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTION, question);
		startActivity(intent);
	}
	
	@InjectView(R.id.home_menu)
	private ImageView homeMenu;
	@InjectView(R.id.vote_menu)
	private ImageView voteMenu;
	@InjectView(R.id.questions_menu)
	private ImageView questionsMenu;
	
	private void setupMenu() {
		voteMenu.setVisibility(View.GONE);
		questionsMenu.setVisibility(View.GONE);
		homeMenu.setOnClickListener(MainActivity.getHomeOnClickListener(this));
	}
}
