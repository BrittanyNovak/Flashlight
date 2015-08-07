package com.example.student.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private Camera cam;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters camParm;
    ToggleButton tbFlashlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(hasFlash){
            getCamera();
        }
    }

    private void getCamera() {
        if (cam == null) {
            try {
                cam = Camera.open();
                camParm = cam.getParameters();

            } catch (RuntimeException t) {
                t.printStackTrace();
            }
        }
    }

    private void turnOnFlashlight(){
        if(!isFlashOn){
            if(cam == null || camParm == null){ getCamera(); }
        }

        camParm = cam.getParameters();
        camParm.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(camParm);
        cam.startPreview();
        isFlashOn = true;
    }

    private void turnOffFlashlight(){
        if(isFlashOn){
            if(cam == null || camParm == null){ return; }
        }

        camParm = cam.getParameters();
        camParm.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(camParm);
        cam.stopPreview();
        cam.release();
        cam = null;
        isFlashOn = false;
    }

    public void onFlashlightClick(View v){
        tbFlashlight = (ToggleButton)v;
        if(isFlashOn){
            turnOffFlashlight();
            tbFlashlight.setTextOff("Off");

        } else{
            turnOnFlashlight();
            tbFlashlight.setTextOn("On");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
