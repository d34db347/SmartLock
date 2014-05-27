package io.github.gncy2013.smartlock.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import io.github.gncy2013.smartlock.common.EraseView;
import io.github.gncy2013.smartlock.common.LockPatternUtils;
import io.github.gncy2013.smartlock.common.TimeView;
import io.github.gncy2013.smartlock.demo.R;

public class EraseActivity extends Activity {
    private EraseView mEraseView;
    private LockPatternUtils mLockPatternUtils;
    private static final String TAG = "EraseActivity";
    public static TimeView mTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences loc_size;
        loc_size = getSharedPreferences("LOC_SIZE", MODE_PRIVATE);
        int pattern_location = loc_size.getInt("LOC", 1);
        int pattern_size = loc_size.getInt("SIZE", 1);
        int location_and_size = pattern_location * 10 + pattern_size;
        switch (location_and_size) {
            case 0 : setContentView(R.layout.erase_left_18); break;
            case 1 : setContentView(R.layout.erase_left_30); break;
            case 2 : setContentView(R.layout.erase_left_41); break;
            case 20 : setContentView(R.layout.erase_right_18); break;
            case 21 : setContentView(R.layout.erase_right_30); break;
            case 22 : setContentView(R.layout.erase_right_41); break;
            case 10 : setContentView(R.layout.erase_middle_18); break;
            case 11 : setContentView(R.layout.erase_middle_30); break;
            case 12 : setContentView(R.layout.erase_middle_41); break;
        }
        mEraseView = (EraseView) findViewById(R.id.EraseView);
        mLockPatternUtils = new LockPatternUtils(this);
        mLockPatternUtils.clearErase();
        mEraseView.setOnPatternListener(new EraseView.OnPatternListener() {
            public void onPatternStart() {}
            public void onPatternCleared() {}
            public void onPatternCellAdded(List<EraseView.Cell> pattern) {}
            public void onPatternDetected(List<EraseView.Cell> pattern) {
                mLockPatternUtils.saveErasePattern(pattern);
                if (mLockPatternUtils.checkErase() == 1) {
                    EraseActivity.this.finish();
                    Toast.makeText(EraseActivity.this, "擦除完毕", Toast.LENGTH_SHORT).show();}
                else if (mLockPatternUtils.checkErase() == 2)
                    Toast.makeText(EraseActivity.this, "再擦除一次", Toast.LENGTH_SHORT).show();
            }
        });

        mTimeView=new TimeView(this,this.getApplicationContext());

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            //透明状态栏 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeView.unregisterComponent();
    }
}
