package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.activity.MainActivity;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchVotesCountTask extends AsyncTask<String, String, Long> {

	private MainActivity activity;
	private Exception exception;
	
	public FetchVotesCountTask(MainActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Long doInBackground(String... params) {
		try {
			return QuestionService.getVotesCount();
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
