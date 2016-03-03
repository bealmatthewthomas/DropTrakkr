package bag.droptrakkr;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //SensorActivity accelerometerActivity;
    private long lastUpdate=0;
    private Boolean toggleEv=false;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private TextView dataView;
    private Button button;
    private Double[][] aList= new Double [20][3];
    private double x;
    private double y;
    private double z;
    private int loop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer= senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        onResume();
        //onPause();
        dataView=(TextView) findViewById(R.id.dataReceive);
        button = (Button) findViewById(R.id.buttonStart);
        loop=0;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType()== Sensor.TYPE_ACCELEROMETER){
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
    @Override
    protected void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    public void toggleEvent(View view) {
         button.setClickable(false);
        //onResume();
        long currTime;
        String xyzData = "";
        int samples=0;
        while (samples<20) {
            currTime = System.currentTimeMillis();
            if ((currTime - lastUpdate) > 100) {
                aList[samples][0]=x;
                aList[samples][1]=y;
                aList[samples][2]=z;
                lastUpdate = currTime;
                samples++;
            }
        }
         //onPause();
        // button.setEnabled(true);
    }
}
