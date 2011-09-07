package br.com.smartfingers.votabrasil;

import java.util.List;
import java.util.UUID;

import roboguice.application.RoboApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.inject.Module;

public class MyApplication extends RoboApplication {

	public static final String PREFERENCES = "PREFERENCES";
	public static final String PREF_UUID = "UUID";
	
	public static String uuid;

	@Override
	protected void addApplicationModules(List<Module> modules) {
        modules.add(new MyRoboGuiceModule());
    }
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		SharedPreferences pref = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		
		uuid = pref.getString(PREF_UUID, null);
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
			Editor editor = pref.edit();
			editor.putString(PREF_UUID, uuid);
			editor.commit();
		}
	}
}
