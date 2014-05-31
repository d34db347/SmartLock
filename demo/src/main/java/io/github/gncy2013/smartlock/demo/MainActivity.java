package io.github.gncy2013.smartlock.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import io.github.gncy2013.smartlock.activity.LockPatternActivity;
import io.github.gncy2013.smartlock.activity.SetPatternActivity;

public class MainActivity extends FragmentActivity {

    SharedPreferences loc_size;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment fragment = new MainFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
        loc_size = this.getSharedPreferences("LOC_SIZE", MODE_PRIVATE);
        editor = loc_size.edit();
    }

    public void onPatternDemoClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), LockPatternActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onSettingClick(View view) {
        int checked = loc_size.getInt("SIZE", -1);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_pattern_size)
                .setSingleChoiceItems(R.array.pattern_size_array, checked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, String.valueOf(which), Toast.LENGTH_SHORT).show();
                        editor.putInt("SIZE", which);
                        editor.commit();
                    }
                })
                .setNeutralButton(R.string.setting_pattern_location, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onNextClick();
                    }
                })
                .show();
    }

    public void onNextClick() {
        int checked = loc_size.getInt("LOC", -1);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_pattern_location)
                .setSingleChoiceItems(R.array.pattern_loc_array, checked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, String.valueOf(which), Toast.LENGTH_SHORT).show();
                        editor.putInt("LOC", which);
                        editor.commit();
                    }
                })
                .setNeutralButton(R.string.setting_stealth_mode, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSetStealth();
                    }
                })
                .show();
    }

    public void onSetStealth() {
        int checked = loc_size.getInt("STEALTH", -1);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_stealth_mode)
                .setSingleChoiceItems(R.array.stealth_mode_array, checked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, String.valueOf(which), Toast.LENGTH_SHORT).show();
                        editor.putInt("STEALTH", which);
                        editor.commit();
                    }
                })
                .setPositiveButton(R.string.OK, null)
                .show();
    }

    public void onPatternSettingClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SetPatternActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
