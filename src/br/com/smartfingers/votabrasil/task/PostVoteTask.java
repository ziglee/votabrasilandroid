package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.QuestionActivity;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class PostVoteTask extends AsyncTask<String, String, Question> {

	private QuestionActivity activity;
	private Long questionId;
	
	public PostVoteTask(QuestionActivity activity, Long questionId) {
		this.activity = activity;
		this.questionId = questionId;
	}
	
	@Override
	protected Question doInBackground(String... params) {
		return QuestionService.postVote(MyApplication.uuid, questionId, params[0]);
	}
	
	@Override
	protected void onPostExecute(Question result) {
		activity.executeAfterPostVote();
	}
}
