package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = findViewById(R.id.cakeview);
        cakeController cController = new cakeController(cakeView);

        Button btn1 = findViewById(R.id.blowOut);
        btn1.setOnClickListener(cController);

        Switch sw1 = findViewById(R.id.candleSwitch);
        sw1.setOnCheckedChangeListener(cController);

        SeekBar sb1 = findViewById(R.id.sb1);
        sb1.setOnSeekBarChangeListener(cController);

    }
    public void goodbye(View button){
        Log.i("button", "Goodbye") ;
        finishAffinity();
    }
}
