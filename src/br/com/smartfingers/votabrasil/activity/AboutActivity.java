package br.com.smartfingers.votabrasil.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.smartfingers.votabrasil.R;

public class AboutActivity extends RoboActivity {
	
	public static Intent emailIntent;
	public static Intent makertIntent;
	
	@InjectView(R.id.about)
	private TextView aboutTxt;
	@InjectView(R.id.credits)
	private TextView creditsTxt;
	@InjectView(R.id.email_us_btn)
	private ImageView emailUsBtn;
	@InjectView(R.id.rate_us_btn)
	private ImageView rateUsBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    	emailIntent.setType("message/rfc822");
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"dev@smartfingers.com.br"});
    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Vota Brasil App");
    	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
    	
    	makertIntent = new Intent(Intent.ACTION_VIEW);
    	makertIntent.setData(Uri.parse("market://details?id=br.com.smartfingers.votabrasil"));
    	
		rateUsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(makertIntent);
			}
		});
		
		emailUsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(emailIntent);
			}
		});

    	aboutTxt.setAutoLinkMask(Linkify.ALL);
    	aboutTxt.setLinksClickable(true);
    	
    	creditsTxt.setAutoLinkMask(Linkify.ALL);
    	creditsTxt.setLinksClickable(true);
	}
}
