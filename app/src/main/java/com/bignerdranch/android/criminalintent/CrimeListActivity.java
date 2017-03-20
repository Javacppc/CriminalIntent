package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

/**
 * Created by zhuxiaodong on 2017/1/5.
 */

public class CrimeListActivity extends SingleFragmentActivity{
    private UUID crimeId;
    private static final String TAG = "CrimeListActivity";
    @Override
    protected Fragment createFragment() {
        return  new CrimeListFragment();
    }


}
