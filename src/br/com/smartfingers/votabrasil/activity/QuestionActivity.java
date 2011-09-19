package br.com.smartfingers.votabrasil.activity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.smartfingers.votabrasil.MyApplication;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.service.QuestionService;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;
import br.com.smartfingers.votabrasil.task.PostVoteTask;
import br.com.smartfingers.votabrasil.view.PieChart;
import br.com.smartfingers.votabrasil.view.PieItem;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.inject.Inject;

public class QuestionActivity extends RoboActivity implements NextQuestionFetchable {

	private static final String LOGTAG = QuestionActivity.class.getName();
	public static final String EXTRA_QUESTION = "EXTRA_QUESTION";
	
	@InjectView(R.id.content_txt)
	private TextView contentTxt;
	@InjectView(R.id.yes_btn)
	private ImageButton yesBtn;
	@InjectView(R.id.no_btn)
	private ImageButton noBtn;
	@InjectView(R.id.yes_check)
	private ImageView yesCheck;
	@InjectView(R.id.no_check)
	private ImageView noCheck;
	@InjectView(R.id.result_layout)
	private LinearLayout resultLayout;
	@InjectView(R.id.yes_no_bars)
	private ScrollView yesNoBars;
	@InjectView(R.id.chart_layout)
	private LinearLayout chartLayout;
	@InjectView(R.id.yes_percentage)
	private TextView yesPercentage;
	@InjectView(R.id.no_percentage)
	private TextView noPercentage;
	@InjectView(R.id.you_voted_prefix)
	private TextView youVotedPrefix;
	@InjectView(R.id.you_voted_value)
	private TextView youVotedValue;
	@InjectView(R.id.total_votes_suffix)
	private TextView totalVotesSuffix;
	@InjectView(R.id.total_votes_value)
	private TextView totalVotesValue;
	@InjectView(R.id.yes_btn_label)
	private TextView yesBtnLabel;
	@InjectView(R.id.no_btn_label)
	private TextView noBtnLabel;
	@InjectView(R.id.next_question_over)
	private LinearLayout nextQuestionOver;
	@InjectView(R.id.next_question_label)
	private TextView nextQuestionLabel;
	@InjectView(R.id.advertising_banner_view)
	private LinearLayout adViewContainer;
	
	@InjectExtra(EXTRA_QUESTION)
	private Question questionExtra;
	
	private Question question;
	
	@Inject
	private QuestionService service;
	
	private ProgressDialog fetchingNextDialog;
	private ProgressDialog postingVoteDialog;
	private PieChart pieChartView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        question = questionExtra;
        
        pieChartView = new PieChart(this);
        pieChartView.setLayoutParams(new LayoutParams(150, 150));
		pieChartView.setGeometry(150, 150, 5, 5, 5, 5);
		pieChartView.setSkinParams(1);
		chartLayout.addView(pieChartView);
		
        resultLayout.setVisibility(View.GONE);
		contentTxt.setText(question.content.toUpperCase());
		
		contentTxt.setTypeface(MyApplication.fontDefault);
		yesBtnLabel.setTypeface(MyApplication.fontBold);
		noBtnLabel.setTypeface(MyApplication.fontBold);
		nextQuestionLabel.setTypeface(MyApplication.fontBold);
		yesPercentage.setTypeface(MyApplication.fontBold);
    	noPercentage.setTypeface(MyApplication.fontBold);
    	youVotedPrefix.setTypeface(MyApplication.fontDefault);
    	youVotedValue.setTypeface(MyApplication.fontBold);
    	totalVotesSuffix.setTypeface(MyApplication.fontDefault);
    	totalVotesValue.setTypeface(MyApplication.fontBold);
		
        yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				question.answer = "yes";
				if (question.yes == null) {
					question.yes = 1L;
				} else {
					question.yes++;
				}
				
				yesCheck.setVisibility(View.VISIBLE);
				
				try{
					postingVoteDialog = ProgressDialog.show(QuestionActivity.this, "", "Enviando seu voto...");
				} catch (Exception e) {
					Log.e(LOGTAG, "Error showing progress dialog", e);
				}
				new PostVoteTask(service, QuestionActivity.this, question.id).execute("yes");
			}
		});
        
        noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				question.answer = "no";
				if (question.no == null) {
					question.no = 1L;
				} else {
					question.no++;
				}
				
				noCheck.setVisibility(View.VISIBLE);

				try{
					postingVoteDialog = ProgressDialog.show(QuestionActivity.this, "", "Enviando seu voto...");
				} catch (Exception e) {
					Log.e(LOGTAG, "Error showing progress dialog", e);
				}
				new PostVoteTask(service, QuestionActivity.this, question.id).execute("no");
			}
		});

        nextQuestionOver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					fetchingNextDialog = ProgressDialog.show(QuestionActivity.this, "", "Buscando próxima enquete");
				} catch (Exception e) {
					Log.e(LOGTAG, "Error showing progress dialog", e);
				}
				new FetchNextQuestionTask(service, QuestionActivity.this).execute();
			}
		});
        
        setupMenu();
        
        AdView adView = new AdView(this, AdSize.BANNER, MyApplication.ADMOB_ID);
		adViewContainer.addView(adView);
		adView.loadAd(MyApplication.getAdRequest());
		
        FlurryAgent.onPageView();
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		if (question.answer != null) {
        	executeAfterPostVote(true, null);
        }
		FlurryAgent.setUserId(MyApplication.uuid);
		FlurryAgent.onStartSession(this, MyApplication.FLURRY_ID);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
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
			finish();
		} else {
			Toast.makeText(this, "Todas as enquetes foram respondidas", Toast.LENGTH_LONG).show();
		}
	}

	public void executeAfterPostVote(Boolean result, Exception exception) {
		
		try {
			if (postingVoteDialog != null) {
				postingVoteDialog.dismiss();
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error dismissing progress dialog", e);
		}
		
		yesCheck.setVisibility(View.GONE);
		noCheck.setVisibility(View.GONE);
		
		if (!result.booleanValue()) {
			Toast.makeText(this, "Erro ao computar seu voto", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (exception != null) {
			Log.e(LOGTAG, "Error posting vote", exception);
			Toast.makeText(this, "Erro ao computar seu voto", Toast.LENGTH_LONG).show();
			return;
		}
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		nf.setMaximumFractionDigits(1);
		
    	long total = question.yes + question.no;
    	float yesPercent = (float) question.yes / total;
    	float noPercent = (float) question.no / total;
    	
    	if (question.answer.equalsIgnoreCase("yes")) {
    		youVotedValue.setText("SIM");
    	} else {
    		youVotedValue.setText("NÃO");
    	}
    	
    	totalVotesValue.setText(Long.toString(total));
    	yesPercentage.setText("Sim " + nf.format(yesPercent * 100) + "%");
    	noPercentage.setText("Não " + nf.format(noPercent * 100) + "%");
    	
		List<PieItem> pieData = new ArrayList<PieItem>();
		
		PieItem yesPieSlice = new PieItem();
		yesPieSlice.count = Math.round(yesPercent * 100);
		yesPieSlice.label = "Sim";
		yesPieSlice.color = 0xff008500;
		
		PieItem noPieSlice = new PieItem();
		noPieSlice.count = Math.round(noPercent * 100);
		noPieSlice.label = "Nao";
		noPieSlice.color = 0xffA60400;
		
		pieData.add(yesPieSlice);
		pieData.add(noPieSlice);
		
		pieChartView.setData(pieData, 100);
		pieChartView.invalidate();
		
		Animation bottomEnterAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_enter);
		nextQuestionOver.startAnimation(bottomEnterAnim);
		
		nextQuestionOver.setVisibility(View.VISIBLE);
		resultLayout.setVisibility(View.VISIBLE);
		yesNoBars.setVisibility(View.GONE);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", question.id.toString());
		params.put("index", question.index.toString());
		params.put("title", question.title);
		params.put("answer", question.answer);
		FlurryAgent.onEvent("Post vote", params);
	}
	
	@InjectView(R.id.home_menu)
	private ImageView homeMenu;
	@InjectView(R.id.vote_menu)
	private ImageView voteMenu;
	@InjectView(R.id.questions_menu)
	private ImageView questionsMenu;
	@InjectView(R.id.info_menu)
	private ImageView infoMenu;
	
	private void setupMenu() {
		voteMenu.setVisibility(View.GONE);
		homeMenu.setOnClickListener(MainActivity.getHomeOnClickListener(this));
		questionsMenu.setOnClickListener(MainActivity.getQuestionsOnClickListener(this));
		infoMenu.setOnClickListener(MainActivity.getAboutOnClickListener(this));
	}
}
