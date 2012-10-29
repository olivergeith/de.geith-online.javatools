package de.geithonline.peugeothaase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PeugeotHaaseMainActivity extends Activity {
	private static final String INTENT_CLASS_ABOUTACTIVITY = "de.geithonline.peugeothaase.ABOUTACTIVITY";
	/** Called when the activity is first created. */

	private Button call0800button;
	private Button callFestnetzbutton;
	private Button callWebSite;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Referenz auf den call0800button besorgen
		call0800button = (Button) findViewById(R.id.call0800button);
		call0800button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				call0800();
			}
		});

		callFestnetzbutton = (Button) findViewById(R.id.callFest);
		callFestnetzbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				callFestNetz();
			}
		});

		callWebSite = (Button) findViewById(R.id.callWebsite);
		callWebSite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				gotoWebsite();
			}
		});

	}

	protected void callFestNetz() {
		final Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:04321-56180"));
		startActivity(intent);
	}

	protected void call0800() {
		final Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0800-5618000"));
		startActivity(intent);
	}

	protected void gotoWebsite() {
		final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.peugeot-haase-neumuenster.de/"));
		startActivity(intent);
	}

	/*
	 * ***********************************************************
	 * everything about Options-Menue
	 * ***********************************************************
	 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aboutitem:
			startAbout();
			break;
		case R.id.exititem:
			finish();
			break;
		default:
			Toaster.showInfoToast(this, "... WTF ??");
			break;
		}
		return false;
	}

	/**
	 * Startet die About-Seite
	 */
	private void startAbout() {
		final Intent intent = new Intent(INTENT_CLASS_ABOUTACTIVITY);
		startActivity(intent);
	}

}