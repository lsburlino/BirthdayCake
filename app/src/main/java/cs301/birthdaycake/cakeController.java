package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class cakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener
            , SeekBar.OnSeekBarChangeListener{
    private CakeView cakeView;
    private cakeModel cakeModel;
    public cakeController (CakeView cakeView){
        this.cakeView = cakeView;
        this.cakeModel = this.cakeView.getCakeModel();
    }

    @Override
    public void onClick(View view){
        this.cakeModel.isLit = false;
        this.cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.cakeModel.hasCandles = isChecked;
        this.cakeView.invalidate();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.cakeModel.numCandles = progress;
        this.cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        this.cakeModel.xTouch = motionEvent.getX();
        this.cakeModel.yTouch = motionEvent.getY();

        this.cakeView.invalidate();

        return false;

    }
}
