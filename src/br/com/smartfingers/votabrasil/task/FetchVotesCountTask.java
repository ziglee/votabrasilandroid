package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.activity.MainActivity;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchVotesCountTask extends AsyncTask<String, String, Long> {

	private MainActivity activity;
	private Exception exception;
	private QuestionService service;
	
	public FetchVotesCountTask(QuestionService service, MainActivity activity) {
		this.activity = activity;
		this.service = service;
	}
	
	@Override
	protected Long doInBackground(String... params) {
		try {
			return service.getVotesCount();
		} catch (Exception e) {
			exception = e;
		}
		return 0L;
	}
	
	@Override
	protected void onPostExecute(Long result) {
		activity.executeAfterFetchVotesCount(result, exception);
	}
}
