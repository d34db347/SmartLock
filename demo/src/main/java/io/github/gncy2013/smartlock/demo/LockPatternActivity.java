package io.github.gncy2013.smartlock.demo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import io.github.gncy2013.smartlock.demo.LockPatternView.Cell;
import io.github.gncy2013.smartlock.demo.LockPatternView.DisplayMode;
import io.github.gncy2013.smartlock.demo.LockPatternView.OnPatternListener;

public class LockPatternActivity extends Activity  {
    private LockPatternView lockPatternView;
    private LockPatternUtils lockPatternUtils;
    //private boolean opFLag = true;
    private static final String TAG = "LockPatternActivity";
    public static TimeView mTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pattern_location= getIntent().getIntExtra("pattern_location",1);
        int pattern_size = getIntent().getIntExtra("pattern_size",0);
        if (pattern_location == 0&& pattern_size == 0)
            setContentView(R.layout.activity_pattern_lock_left_18);
        else if (pattern_location == 0&& pattern_size == 1)
            setContentView(R.layout.activity_pattern_lock_left_30);
        else if (pattern_location == 0&& pattern_size == 2)
            setContentView(R.layout.activity_pattern_lock_left_41);
        else if (pattern_location == 2 && pattern_size == 0)
            setContentView(R.layout.activity_pattern_lock_right_18);
        else if (pattern_location == 2 && pattern_size == 1)
            setContentView(R.layout.activity_pattern_lock_right_30);
        else if (pattern_location == 2 && pattern_size == 2)
            setContentView(R.layout.activity_pattern_lock_right_41);
        else if (pattern_location == 1 && pattern_size == 2)
            setContentView(R.layout.activity_pattern_lock_middle_41);
        else if (pattern_location == 1 && pattern_size == 1)
            setContentView(R.layout.activity_pattern_lock_middle_30);
        else if (pattern_location == 1 && pattern_size == 0)
            setContentView(R.layout.activity_pattern_lock_middle_18);
        lockPatternView =(LockPatternView)findViewById(R.id.LockView);
        lockPatternUtils = new LockPatternUtils(this);
        lockPatternView.setOnPatternListener(new OnPatternListener() {

            public void onPatternStart() {
            }

            public void onPatternDetected(List<Cell> pattern) {
                    int result = lockPatternUtils.checkPattern(pattern);
                    if (result!= 1) {
                        if(result==0) {
                            lockPatternView.setDisplayMode(DisplayMode.Wrong);
                            Toast.makeText(LockPatternActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }else {
                            lockPatternView.clearPattern();
                            Toast.makeText(LockPatternActivity.this, "请设置密码", Toast.LENGTH_SHORT).show();
                         }
                    } else { Toast.makeText(LockPatternActivity.this, "密码正确", Toast.LENGTH_SHORT).show(); }
            }

            public void onPatternCleared() {
            }

            public void onPatternCellAdded(List<Cell> pattern) {
            }
        });

        mTimeView=new TimeView(this,this.getApplicationContext());

        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
            {
                //透明状态栏 透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
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
            case KeyEvent.KEYCODE_HOME:
            //case KeyEvent.KEYCODE_BACK:
            //case KeyEvent.KEYCODE_VOLUME_UP:
            //case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
