package br.com.smartfingers.votabrasil.task;

import android.os.AsyncTask;
import br.com.smartfingers.votabrasil.activity.MainActivity;
import br.com.smartfingers.votabrasil.service.QuestionService;

public class FetchVotesCountTask extends AsyncTask<String, String, Long> {

	private MainActivity activity;
	
	public FetchVotesCountTask(MainActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Long doInBackground(String... params) {
		return QuestionService.getVotesCount();
	}
	
	@Override
	protected void onPostExecute(Long result) {
		activity.executeAfterPostVote(result);
	}
}
