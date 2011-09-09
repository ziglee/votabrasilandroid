package br.com.smartfingers.votabrasil.activity;

import java.text.NumberFormat;
import java.util.Locale;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.smartfingers.votabrasil.R;
import br.com.smartfingers.votabrasil.entity.Question;
import br.com.smartfingers.votabrasil.task.FetchNextQuestionTask;
import br.com.smartfingers.votabrasil.task.PostVoteTask;

public class QuestionActivity extends RoboActivity implements NextQuestionFetchable {

	public static final String EXTRA_QUESTION = "EXTRA_QUESTION";
	
	@InjectView(R.id.title_txt)
	private TextView titleTxt;
	@InjectView(R.id.content_txt)
	private TextView contentTxt;
	@InjectView(R.id.yes_btn)
	private Button yesBtn;
	@InjectView(R.id.no_btn)
	private Button noBtn;
	@InjectView(R.id.next_btn)
	private Button nextBtn;
	@InjectView(R.id.result_layout)
	private LinearLayout resultFrame;
	@InjectView(R.id.yes_bar)
	private LinearLayout yesBar;
	@InjectView(R.id.no_bar)
	private LinearLayout noBar;
	@InjectView(R.id.yes_percentage)
	private TextView yesPercentage;
	@InjectView(R.id.no_percentage)
	private TextView noPercentage;
	@InjectView(R.id.you_voted_txt)
	private TextView youVotedPercentage;	
	@InjectView(R.id.total_votes_txt)
	private TextView totalVotesTxt;
	
	@InjectExtra(EXTRA_QUESTION)
	private Question question;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        titleTxt.setText(question.title);
		contentTxt.setText(question.content);
        
        yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				question.answer = "yes";
				if (question.yes == null) {
					question.yes = 1L;
				} else {
					question.yes++;
				}
				new PostVoteTask(QuestionActivity.this, question.id).execute("yes");
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
				new PostVoteTask(QuestionActivity.this, question.id).execute("no");
			}
		});

        nextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new FetchNextQuestionTask(QuestionActivity.this).execute();
			}
		});
        
        if(question.answer != null) {
        	executeAfterPostVote();
        }
    }
	
	public void executeAfterFetchNextQuestion(Question result) {
		Intent intent = new Intent(this, QuestionActivity.class);
		intent.putExtra(QuestionActivity.EXTRA_QUESTION, result);
		startActivity(intent);
		finish();
	}

	public void executeAfterPostVote() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		nf.setMaximumFractionDigits(1);
		
    	LinearLayout.LayoutParams yesLp = (LinearLayout.LayoutParams) yesBar.getLayoutParams();
    	LinearLayout.LayoutParams noLp = (LinearLayout.LayoutParams) noBar.getLayoutParams();

    	long total = question.yes + question.no;
    	yesLp.weight = (float) question.yes / total;
    	noLp.weight = (float) question.no / total;
    	
    	if (question.answer.equalsIgnoreCase("yes")) {
    		youVotedPercentage.setText("Você votou 'Sim'");
    	} else {
    		youVotedPercentage.setText("Você votou 'Não'");
    	}
    	
    	totalVotesTxt.setText(total + " votos");
    	
    	yesPercentage.setText(nf.format(yesLp.weight * 100) + "%");
    	noPercentage.setText(nf.format(noLp.weight * 100) + "%");
    	
		resultFrame.setVisibility(View.VISIBLE);
		yesBtn.setVisibility(View.GONE);
    	noBtn.setVisibility(View.GONE);
	}
}