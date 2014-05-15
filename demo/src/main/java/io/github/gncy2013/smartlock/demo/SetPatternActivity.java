package io.github.gncy2013.smartlock.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import io.github.gncy2013.smartlock.demo.LockPatternView.Cell;
import io.github.gncy2013.smartlock.demo.LockPatternView.OnPatternListener;

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
