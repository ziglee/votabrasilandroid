package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.smartfingers.votabrasil.R;

public class MainActivity extends RoboActivity {
	
	@InjectView(R.id.vote_btn)
	private Button voteBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        voteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, VoteActivity.class));
			}
		});
    }
}