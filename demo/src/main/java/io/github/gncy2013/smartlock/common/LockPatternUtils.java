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

    public static String eraseToString(List<EraseView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            EraseView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 5 + cell.getColumn());
        }
        return Arrays.toString(res);
    }

    public void saveLockPattern(List<LockPatternView.Cell> pattern){
        Editor editor = preference.edit();
        editor.putString("pattern_pwd", patternToString(pattern));
        editor.commit();
    }

    public void saveErasePattern(List<EraseView.Cell> pattern){
        Editor editor = preference.edit();
        editor.putString("erase_pattern", eraseToString(pattern));
        editor.commit();
    }

    public String getLockPatternString(){
        return preference.getString("pattern_pwd", "");
    }

    public int checkErase(){
        String pwdString = preference.getString("pattern_pwd", "");
        pwdString = pwdString.replace("[", "");
        pwdString = pwdString.replace("]", "");
        pwdString = pwdString.replace(" ", "");
        String[] oriArrayS = null;
        oriArrayS = pwdString.split(",");
        int[] oriArray = new int[oriArrayS.length];
        for (int i = 0; i < oriArrayS.length; i++) {
            oriArray[i] = Integer.parseInt(oriArrayS[i]);
            oriArray[i] = oriArray[i] * 2 + (oriArray[i] / 3) * 4;
        }
        int[] oriPoint = new int[oriArrayS.length - 1];
        for (int i = 0; i < oriArrayS.length - 1; i++) {
            oriPoint[i] = (oriArray[i] + oriArray[i + 1]) / 2;
            System.out.println("需要擦除的点" + oriPoint[i]);
        }

        String eraseString = preference.getString("erase_pattern", "");
        eraseString = eraseString.replace("[", "");
        eraseString = eraseString.replace("]", "");
        eraseString = eraseString.replace(" ", "");
        String[] eraseArrayS = null;
        eraseArrayS = eraseString.split(",");
        int [] eraseArray = new int[eraseArrayS.length];
        for (int i = 0; i < eraseArrayS.length; i++) {
            eraseArray[i] = Integer.parseInt(eraseArrayS[i]);
            System.out.println("被擦除的点" + eraseArray[i]);
        }

        int equal = 0;
        for (int anOriPoint : oriPoint) {
            for (int anEraseArray : eraseArray) {
                if (anOriPoint == anEraseArray)
                    equal++;
            }
        }

        System.out.println("符合率" + (double) equal / (double) oriPoint.length);
        if ((double) equal / (double) oriPoint.length > 0.0) {
            System.out.println("HEHEHEHEHEHE");
            return 1;
        }
        else
            return 2;
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

    public void clearErase() {
        saveErasePattern(null);
    }
}
