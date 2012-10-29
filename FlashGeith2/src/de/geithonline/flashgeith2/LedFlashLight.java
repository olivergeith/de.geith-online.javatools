package de.geithonline.flashgeith2;

import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class LedFlashLight {
	private Camera camera = null;
	private Camera.Parameters camParams;
	private boolean torchEnabled = false;
	private boolean initOK = false;
	private final int camCounter = Camera.getNumberOfCameras();
	private List<String> flashModes = null;

	public LedFlashLight() {
		initOK = initCamera();
	}

	public boolean isTorchEnabled() {
		return torchEnabled;
	}

	public boolean isInitOK() {
		return initOK;
	}

	/**
	 * Initialisiert die Kamera
	 * 
	 * @return true if ok
	 */
	private boolean initCamera() {
		if (camCounter == 0) {
			return false;
		}
		if (camera == null)
			camera = Camera.open();
		camParams = camera.getParameters();
		flashModes = camParams.getSupportedFlashModes();
		if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
			return true;
		}
		camera.release();
		camera = null;
		return false;
	}

	public List<String> getFlashModes() {
		return flashModes;
	}

	public void close() {
		if (camera != null) {
			turnFlashOff();
			camera.release();
			camera = null;
		}
	}

	public void toggleFlashLight() {
		if (!torchEnabled)
			turnFlashOn();
		else
			turnFlashOff();
	}

	public void turnFlashOff() {
		if (camera != null) {
			camParams.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(camParams);
			torchEnabled = false;
		}
	}

	public void turnFlashOn() {
		if (camera != null) {
			camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(camParams);
			torchEnabled = true;
		}
	}
}
