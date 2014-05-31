package io.github.gncy2013.smartlock.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import io.github.gncy2013.smartlock.common.LockPatternUtils;
import io.github.gncy2013.smartlock.common.LockPatternView;
import io.github.gncy2013.smartlock.common.LockPatternView.Cell;
import io.github.gncy2013.smartlock.common.LockPatternView.DisplayMode;
import io.github.gncy2013.smartlock.common.LockPatternView.OnPatternListener;
import io.github.gncy2013.smartlock.common.TimeView;
import io.github.gncy2013.smartlock.demo.R;

public class LockPatternActivity extends Activity  {
    private LockPatternView mLockPatternView;
    private LockPatternUtils mLockPatternUtils;
    private static final String TAG = "LockPatternActivity";
    public static TimeView mTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences loc_size;
        loc_size = getSharedPreferences("LOC_SIZE", MODE_PRIVATE);
        int pattern_location = loc_size.getInt("LOC", 1);
        int pattern_size = loc_size.getInt("SIZE", 1);
        int isStealth = loc_size.getInt("STEALTH", 0);
        int location_and_size = pattern_location * 10 + pattern_size;
        switch (location_and_size) {
            case 0 : setContentView(R.layout.activity_pattern_lock_left_18); break;
            case 1 : setContentView(R.layout.activity_pattern_lock_left_30); break;
            case 2 : setContentView(R.layout.activity_pattern_lock_left_41); break;
            case 20 : setContentView(R.layout.activity_pattern_lock_right_18); break;
            case 21 : setContentView(R.layout.activity_pattern_lock_right_30); break;
            case 22 : setContentView(R.layout.activity_pattern_lock_right_41); break;
            case 10 : setContentView(R.layout.activity_pattern_lock_middle_18); break;
            case 11 : setContentView(R.layout.activity_pattern_lock_middle_30); break;
            case 12 : setContentView(R.layout.activity_pattern_lock_middle_41); break;
        }
        mLockPatternView = (LockPatternView) findViewById(R.id.LockView);
        mLockPatternUtils = new LockPatternUtils(this);
        //是否显示轨迹
        if (isStealth == 1)
            mLockPatternView.setInStealthMode(true);

        mLockPatternView.setOnPatternListener(new OnPatternListener() {
            public void onPatternStart() {}
            public void onPatternDetected(List<Cell> pattern) {
                    int result = mLockPatternUtils.checkPattern(pattern);
                    if (result != 1) {
                        if(result == 0) {
                            mLockPatternView.setDisplayMode(DisplayMode.Wrong);
                            Toast.makeText(LockPatternActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }else {
                            mLockPatternView.clearPattern();
                            Toast.makeText(LockPatternActivity.this, "请先设置密码", Toast.LENGTH_SHORT).show();
                            LockPatternActivity.this.finish();
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), SetPatternActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                         }
                    } else {
                            Toast.makeText(LockPatternActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                            /** Jump to Erase
                            *
                            */
                            LockPatternActivity.this.finish();
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), EraseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
            }
            public void onPatternCleared() {}
            public void onPatternCellAdded(List<Cell> pattern) {}
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

    /**屏蔽BACK, VOL UP/DOWN
     */
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return disableKeycode(keyCode, event);
    }

    private boolean disableKeycode(int keyCode, KeyEvent event)
    {
        int key = event.getKeyCode();
        switch (key)
        {
            //case KeyEvent.KEYCODE_HOME:
            //case KeyEvent.KEYCODE_BACK:
            //case KeyEvent.KEYCODE_VOLUME_UP:
            //case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
