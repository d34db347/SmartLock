package io.github.gncy2013.smartlock.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import io.github.gncy2013.smartlock.common.LockPatternUtils;
import io.github.gncy2013.smartlock.common.LockPatternView;
import io.github.gncy2013.smartlock.common.LockPatternView.Cell;
import io.github.gncy2013.smartlock.common.LockPatternView.OnPatternListener;
import io.github.gncy2013.smartlock.demo.R;

public class SetPatternActivity extends Activity {

    private LockPatternView mLockPatternView;
    private LockPatternUtils mLockPatternUtils;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_set);
        mLockPatternView = (LockPatternView) findViewById(R.id.LockView);

        mLockPatternUtils = new LockPatternUtils(this);
        //启动设置时清空密码
        mLockPatternView.clearPattern();
        mLockPatternUtils.clearLock();
        Toast.makeText(SetPatternActivity.this, "请设置密码", Toast.LENGTH_SHORT).show();

        mLockPatternView.setOnPatternListener(new OnPatternListener() {
            public void onPatternStart() {}
            public void onPatternDetected(List<Cell> pattern) {
                mLockPatternUtils.saveLockPattern(pattern);
                Toast.makeText(SetPatternActivity.this, "密码已设置", Toast.LENGTH_SHORT).show();
            }
            public void onPatternCleared() {}
            public void onPatternCellAdded(List<Cell> pattern) {}
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            //透明状态栏 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);}
    }

    public void onResetClick(View view) {
        mLockPatternView.clearPattern();
        mLockPatternUtils.clearLock();
        Toast.makeText(SetPatternActivity.this, "请设置密码", Toast.LENGTH_SHORT).show();
    }

    public void onConfirmClick(View view) {
        this.finish();
    }
}
