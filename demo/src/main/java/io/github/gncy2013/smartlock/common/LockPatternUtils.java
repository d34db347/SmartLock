package io.github.gncy2013.smartlock.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SmartLock
 * Created by Chengyu on 14-4-10.
 * Copyright (c) 2014 Chengyu. All rights reserved.
 */

public class LockPatternUtils {

    //private static final String TAG = "LockPatternUtils";
    private static Context mContext;
    private static SharedPreferences preference;
    //private final ContentResolver mContentResolver;

    public LockPatternUtils(Context context) {
        mContext = context;
        preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        //mContentResolver = context.getContentResolver();
        //SharedPreferences PATTERN_PWD;
        //SharedPreferences.Editor editor;
    }

    /**
     * Deserialize a pattern.
     * @param string The pattern serialized with {@link #patternToString}
     * @return The pattern.
     */
    public static List<LockPatternView.Cell> stringToPattern(String string) {
        List<LockPatternView.Cell> result = new ArrayList<LockPatternView.Cell>();
        final byte[] bytes = string.getBytes();
        for (byte b : bytes) {
            result.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return result;
    }

    /**
     * Serialize a pattern.
     * @param pattern The pattern.
     * @return The pattern in string form.
     */
    public static String patternToString(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return Arrays.toString(res);
    }

    public void saveLockPattern(List<LockPatternView.Cell> pattern){
        Editor editor = preference.edit();
        editor.putString("pattern_pwd", patternToString(pattern));
        editor.commit();
    }

    public String getLockPatternString(){
        return preference.getString("pattern_pwd", "");
    }

    public void toEraseArray(){
        String pwdString = preference.getString("pattern_pwd", "");
        pwdString = pwdString.replace("[", "");
        pwdString = pwdString.replace("]", "");
        pwdString = pwdString.replace(" ", "");
        String[] eraseArrayS = null;
        eraseArrayS = pwdString.split(",");
        int[] eraseArray = new int[eraseArrayS.length];
        for (int i = 0; i < eraseArrayS.length; i++) {
            eraseArray[i] = Integer.parseInt(eraseArrayS[i]);
            eraseArray[i] = eraseArray[i] * 2 + (eraseArray[i] / 3) * 4;
        }
        int[] erasePoint = new int[eraseArrayS.length - 1];
        for (int i = 0; i < eraseArrayS.length - 1; i++) {
            erasePoint[i] = (eraseArray[i] + eraseArray[i + 1]) / 2;
            System.out.println(erasePoint[i]);
        }
    }

    public int checkPattern(List<LockPatternView.Cell> pattern) {
        String stored = getLockPatternString();
        if(!stored.isEmpty()){
            return stored.equals(patternToString(pattern))?1:0;
        }
        return -1;
    }

    public void clearLock() {
        saveLockPattern(null);
    }
}
