package br.com.smartfingers.votabrasil.activity;

import java.text.NumberFormat;
import java.util.Locale;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;
import br.com.smartfingers.votabrasil.task.FetchVotesCountTask;

import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.inject.Inject;

public class MainActivity extends RoboActivity implements NextQuestionFetchable {
	
	private static final String LOGTAG = MainActivity.class.getName();
	
	@InjectView(R.id.vote_btn)
	private LinearLayout voteBtn;
	@InjectView(R.id.vote_label)
	private TextView voteLabel;
	@InjectView(R.id.questions_btn)
	private LinearLayout listBtn;
	@InjectView(R.id.questions_label)
	private TextView listLabel;
	@InjectView(R.id.intro_txt)
	private TextView introTxt;
	@InjectView(R.id.count_txt)
	private TextView countTxt;
	@InjectView(R.id.advertising_banner_view)
	private LinearLayout adViewContainer;
	
	@Inject
	private QuestionService service;
	
	private ProgressDialog fetchingNextDialog;
	private OnClickListener voteOnClickListener;
	private OnClickListener questionsOnClickListener;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        introTxt.setTypeface(MyApplication.fontBold);
        countTxt.setTypeface(MyApplication.fontDefault);
        voteLabel.setTypeface(MyApplication.fontDefault);
        listLabel.setTypeface(MyApplication.fontDefault);
        
        voteOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					fetchingNextDialog = ProgressDialog.show(MainActivity.this, "", "Buscando próxima enquete");
				} catch (Exception e) {
					Log.e(LOGTAG, "Error showing progress dialog", e);
				}
				new FetchNextQuestionTask(service, MainActivity.this).execute();
			}
		};
		
        questionsOnClickListener = getQuestionsOnClickListener(this);

        voteBtn.setOnClickListener(voteOnClickListener);
		listBtn.setOnClickListener(questionsOnClickListener);
        
        setupMenu();
        
		AdView adView = new AdView(this, AdSize.BANNER, MyApplication.ADMOB_ID);
		adViewContainer.addView(adView);
		adView.loadAd(MyApplication.getAdRequest());
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		countTxt.setText("Computando votos...");
		new FetchVotesCountTask(service, this).execute();
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
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		nf.setMaximumFractionDigits(0);
		nf.setGroupingUsed(true);
		
		countTxt.setText(nf.format(count) + " votos");
	}
	
	@InjectView(R.id.home_menu)
	private ImageView homeMenu;
	@InjectView(R.id.vote_menu)
	private ImageView voteMenu;
	@InjectView(R.id.questions_menu)
	private ImageView questionsMenu;
	
	private void setupMenu() {
		homeMenu.setVisibility(View.GONE);
		voteMenu.setOnClickListener(voteOnClickListener);
		questionsMenu.setOnClickListener(questionsOnClickListener);
	}
	
	public static OnClickListener getHomeOnClickListener(final Activity activity) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.startActivity(new Intent(activity, MainActivity.class));
			}
        };
	}
	
	public static OnClickListener getQuestionsOnClickListener(final Activity activity) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.startActivity(new Intent(activity, QuestionListActivity.class));
			}
        };
	}
}