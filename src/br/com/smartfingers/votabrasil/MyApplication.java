package br.com.smartfingers.votabrasil;

import java.util.List;
import java.util.UUID;

import roboguice.application.RoboApplication;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;

import com.google.ads.AdRequest;
import com.google.inject.Module;

public class MyApplication extends RoboApplication {

	public static final String ADMOB_ID = "a14e6c37d31557f";
	
	public static final String PREFERENCES = "PREFERENCES";
	public static final String PREF_UUID = "UUID";
	
	public static String uuid;
	public static Typeface fontBold;
	public static Typeface fontDefault;

	@Override
	protected void addApplicationModules(List<Module> modules) {
        modules.add(new MyRoboGuiceModule());
    }
	
	@Override
	public void onCreate() {
		super.onCreate();

		fontBold = Typeface.createFromAsset(this.getAssets(), "fonts/fradmcn.ttf");
		fontDefault = Typeface.createFromAsset(this.getAssets(), "fonts/framdcn.ttf");
		
		SharedPreferences pref = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		
		uuid = pref.getString(PREF_UUID, null);
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
			Editor editor = pref.edit();
			editor.putString(PREF_UUID, uuid);
			editor.commit();
		}
	}
	
	public static AdRequest getAdRequest() {
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		adRequest.addTestDevice("65F4BC41250F1D77F9536673F952FD17"); //Meu Galaxy S II
		return adRequest;
	}
}
