package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.activity.QuestionActivity;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class PostVoteTask extends AsyncTask<String, String, Boolean> {

	private QuestionActivity activity;
	private Long questionId;
	private Exception exception;
	private QuestionService service;
	
	public PostVoteTask(QuestionService service, QuestionActivity activity, Long questionId) {
		this.activity = activity;
		this.questionId = questionId;
		this.service = service;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		try {
			return service.postVote(MyApplication.uuid, questionId, params[0]);
		} catch (Exception e) {
			exception = e;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		activity.executeAfterPostVote(result, exception);
	}
}
