package de.geithonline.flashgeith;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class StartingPoint extends Activity {
	/** Called when the activity is first created. */
	private Camera camera = null;
	private Camera.Parameters camParams;
	private boolean mbTorchEnabled = false;
	// View-Elemente
	private TextView statusView;
	private ImageView statusImage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		if (camera != null) {
			turnFlashOff();
			camera.release();
			camera = null;
		}
		super.onPause();
		finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Referenzen auf Oberflächenelemente besorgen
		statusView = (TextView) findViewById(R.id.Status);
		statusImage = (ImageView) findViewById(R.id.statusImage);
		// mal schauen, ob wir den Button anschalten dürfen
		statusImage.setEnabled(false);
		final boolean initOk = initCamera();
		if (initOk) {
			statusImage.setEnabled(true);
		} else {
			statusView.setText(R.string.MsgNoTorchMode);
			statusView.setVisibility(TextView.VISIBLE);
		}
		// Clicklistener für den button und image
		statusImage.setOnClickListener(new OnClickListener() {
			public void onClick(final View arg0) {
				toggleFlashLight();
			}
		});
	}

	/**
	 * Initialisiert die Kamera
	 * 
	 * @return true if ok
	 */
	private boolean initCamera() {
		final int camCounter = Camera.getNumberOfCameras();
		if (camCounter == 0)
			return false;
		if (camera == null)
			camera = Camera.open();
		camParams = camera.getParameters();
		final List<String> flashModes = camParams.getSupportedFlashModes();
		if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
			return true;
		}
		return false;
	}

	/**
	 * Toggles the Flashlight
	 */
	private void toggleFlashLight() {
		if (!mbTorchEnabled)
			turnFlashOn();
		else
			turnFlashOff();
	}

	private void turnFlashOff() {
		camParams.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(camParams);
		mbTorchEnabled = false;
		statusImage.setImageResource(R.drawable.button_off);
	}

	private void turnFlashOn() {
		camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(camParams);
		mbTorchEnabled = true;
		statusImage.setImageResource(R.drawable.button_on);
	}
}