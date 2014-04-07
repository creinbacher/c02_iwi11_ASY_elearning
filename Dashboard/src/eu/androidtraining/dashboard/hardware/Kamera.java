package eu.androidtraining.dashboard.hardware;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class Kamera extends Activity implements Callback {

	SurfaceView mSurfaceView;
	private Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hardware_kamera);
		mSurfaceView = (SurfaceView) findViewById(R.id.sv_hardware_kamera);
		mSurfaceView.getHolder().addCallback(this);
		mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Parameters parameter = mCamera.getParameters();
		List<Camera.Size> previewGroessen = parameter.getSupportedPreviewSizes();
		int previewWidth = width;
		int previewHeight = height;
		Size size;
		// suchen wir nach der ersten Previewgröße, die kleiner 
		// als der angebotene Bereich ist
		for (int i = 0; i < previewGroessen.size(); i++) {
			size = previewGroessen.get(i);
			if (width > size.width && height > size.height) {
				previewHeight = size.height;
				previewWidth = size.width;
				break;
			}
		}
		parameter.setPreviewSize(previewWidth, previewHeight);
		parameter.setPictureFormat(PixelFormat.JPEG);
		mCamera.setParameters(parameter);
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		PictureCallback pictureCallback =
			new PictureCallback() {
				
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					try {
						File bild = new File(
								Kamera.this.getFilesDir() + "/bild.jpg");
						FileOutputStream bildOut =
							new FileOutputStream(bild);
						bildOut.write(data);
						bildOut.flush();
						bildOut.close();
						Toast.makeText(Kamera.this, 
							getResources().getString(
								R.string.txt_hardware_kamera_bild)
								+ "\n" + bild.getAbsolutePath(), 
							Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mCamera.startPreview();
				}
			};
		mCamera.takePicture(null, null, pictureCallback);
		return super.onTouchEvent(event);
	}
}
