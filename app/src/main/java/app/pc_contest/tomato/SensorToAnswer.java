package app.pc_contest.tomato;



import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class SensorToAnswer extends Activity implements SensorEventListener {

    private TextView myText;
    // SensorManagerインスタンス
    private SensorManager sma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);
        myText = findViewById(R.id.text_temp);
        // SensorManagerのインスタンスを取得する
        sma = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sma.registerListener(this, sma.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }


    // センサーの値が変化すると呼ばれる
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        String str =   " X= " + x + "\n"
                + " Y= " + y + "\n"
                + " Z= " + z;
        myText.setText(str);
    }

    // センサーの精度が変更されると呼ばれる
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}