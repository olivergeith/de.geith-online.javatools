package de.geithonline.flashgeith2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	// Intents
	private static final String INTENT_CLASS_ABOUTACTIVITY = "de.geithonline.flashgeith2.ABOUTACTIVITY";
	private static final String INTENT_CLASS_PREFERENCEACTIVITY = "de.geithonline.flashgeith2.MAINPREFERENCEACTIVITY";
	// Konstanten
	private static final int REQUEST_CODE_PREFERENCES = 1;
	private static final String PREF_CBOX_TURNONTORCH_KEY = "turnon_torch_on_startup";

	// Die Klasse, die das Camera-Handling macht
	private LedFlashLight ledFlashLight;

	// View-Elemente
	private TextView statusView;
	private ImageView statusImage;

	private boolean turnOnTorch = false;

	/*
	 * ***********************************************************
	 * All about Lifecycle
	 * ***********************************************************
	 */

	@Override
	protected void onDestroy() {
		ledFlashLight.close();
		super.onDestroy();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Referenzen auf Oberflächenelemente besorgen
		statusView = (TextView) findViewById(R.id.Status);
		statusImage = (ImageView) findViewById(R.id.statusImage);

		// Camara initialisieren
		ledFlashLight = new LedFlashLight();

		// mal schauen, ob wir den Button anschalten dürfen
		statusImage.setEnabled(false);
		final boolean initOk = ledFlashLight.isInitOK();
		if (initOk) {
			statusImage.setEnabled(true);
			// Shall we tun the LED on immediatly ?
			turnOnTorch = getTurnOnTorchSetting();
			if (turnOnTorch == true) {
				ledFlashLight.turnFlashOn();
				setButtonState();
			}
		} else {
			// Statuszeile ausgeben, dass es kein Torchmode gibt
			statusView.setText(R.string.MsgNoTorchMode);
			statusView.setVisibility(TextView.VISIBLE);
		}
		// Clicklistener für den button und image
		statusImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				ledFlashLight.toggleFlashLight();
				setButtonState();
			}

		});
	}

	/**
	 * Setzt da Bild des Buttons in Abhängigkeit vom LED-Status (an oder aus)
	 */
	private void setButtonState() {
		if (ledFlashLight.isTorchEnabled())
			statusImage.setImageResource(R.drawable.button_on);
		else
			statusImage.setImageResource(R.drawable.button_off);
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
		case R.id.about:
			startAbout();
			break;
		case R.id.settings:
			startSettings();
			break;
		case R.id.exit:
			finish();
			break;
		default:
			Toaster.showInfoToast(this, "... WTF ??");
			break;
		}
		return false;
	}

	/*
	 * ***********************************************************
	 * everything about Settings
	 * ***********************************************************
	 */
	/**
	 * Startet den Settings Dialog
	 */
	private void startSettings() {
		final Intent intent = new Intent(INTENT_CLASS_PREFERENCEACTIVITY);
		startActivityForResult(intent, REQUEST_CODE_PREFERENCES);
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// The preferences returned if the request code is what we had given
		// earlier in startSubActivity
		if (requestCode == REQUEST_CODE_PREFERENCES) {

			turnOnTorch = getTurnOnTorchSetting();
			Toaster.showInfoToast(this, getResources().getString(R.string.pref_cbox_turnontorch_title) + ": " + turnOnTorch);
		}
	}

	/**
	 * Liest das Boolean für turnOnTorch
	 * 
	 * @return
	 */
	private boolean getTurnOnTorchSetting() {
		final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final boolean turnOnTorch = sharedPref.getBoolean(PREF_CBOX_TURNONTORCH_KEY, false);
		return turnOnTorch;
	}

	/*
	 * ***********************************************************
	 * everything about
	 * ***********************************************************
	 */
	/**
	 * Startet die About-Seite
	 */
	private void startAbout() {
		final Intent intent = new Intent(INTENT_CLASS_ABOUTACTIVITY);
		startActivity(intent);
	}

}