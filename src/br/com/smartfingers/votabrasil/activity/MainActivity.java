package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;
import br.com.smartfingers.votabrasil.task.FetchVotesCountTask;

import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends RoboActivity implements NextQuestionFetchable {
	
	private static final String LOGTAG = MainActivity.class.getName();
	
	@InjectView(R.id.vote_btn)
	private LinearLayout voteBtn;
	@InjectView(R.id.vote_label)
	private TextView voteLabel;
	@InjectView(R.id.list_btn)
	private LinearLayout listBtn;
	@InjectView(R.id.list_label)
	private TextView listLabel;
	@InjectView(R.id.intro_txt)
	private TextView introTxt;
	@InjectView(R.id.count_txt)
	private TextView countTxt;
	@InjectView(R.id.advertising_banner_view)
	private LinearLayout adViewContainer;
	
	private ProgressDialog fetchingNextDialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        introTxt.setTypeface(MyApplication.fontBold);
        countTxt.setTypeface(MyApplication.fontDefault);
        voteLabel.setTypeface(MyApplication.fontDefault);
        listLabel.setTypeface(MyApplication.fontDefault);
        
        voteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					fetchingNextDialog = ProgressDialog.show(MainActivity.this, "", "Buscando próxima enquete");
				} catch (Exception e) {
					Log.e(LOGTAG, "Error showing progress dialog", e);
				}
				new FetchNextQuestionTask(MainActivity.this).execute();
			}
		});
        
        listBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, QuestionListActivity.class));
			}
        });
        
		AdView adView = new AdView(this, AdSize.BANNER, MyApplication.ADMOB_ID);
		adViewContainer.addView(adView);
		adView.loadAd(MyApplication.getAdRequest());
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		countTxt.setText("Computando votos...");
		new FetchVotesCountTask(this).execute();
	}

	public void executeAfterFetchNextQuestion(Question result, Exception exception) {
		try{
			if (fetchingNextDialog != null) {
				fetchingNextDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error dismissing progress dialog", e);
		}
		
		if(exception != null) {
			Log.e(LOGTAG, "Error fetching next question", exception);
			Toast.makeText(this, "Erro ao buscar próxima enquete", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (result != null) {
			Intent intent = new Intent(this, QuestionActivity.class);
			intent.putExtra(QuestionActivity.EXTRA_QUESTION, result);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Todas as enquetes foram respondidas", Toast.LENGTH_LONG).show();
		}
	}

	public void executeAfterFetchVotesCount(Long count, Exception exception) {
		if(exception != null) {
			Log.e(LOGTAG, "Error fetching votes count", exception);
			countTxt.setText("--- votos");
			return;
		}
		
		countTxt.setText(Long.toString(count) + " votos");
	}
}