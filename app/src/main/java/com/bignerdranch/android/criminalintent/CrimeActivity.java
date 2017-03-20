package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.Serializable;
import java.util.UUID;
import java.util.zip.CRC32;


/**
 * 使用支持庫，該Activity需要託管UI Fragment
 * 支持庫的作用：可以兼容最低API 4級的安卓sdk版本
 * 必須做到在視圖中為fragment的視圖安排位置
 * 管理fragment實例的生命週期
 */
public class CrimeActivity extends SingleFragmentActivity {
    //public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private static final String EXTRA_RETURN_ID = "com.bignerdranch.android.criminalintent.return_crime_id";
    private static final String TAG = "CrimeActivity";
    private UUID crimeId;
    @Override
    protected Fragment createFragment() {
        //return new CrimeFragment();
        crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
    public static Intent newIntent(Context pageContext, UUID crimeId) {
        Intent intent = new Intent(pageContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }
    private void setAnswerShownResult(UUID returncrimeId) {
        Intent data = new Intent();
        data.putExtra(EXTRA_RETURN_ID,returncrimeId);
        setResult(RESULT_OK,data);
    }
    public static Serializable getIdShown(Intent result) {
        return result.getSerializableExtra(EXTRA_RETURN_ID);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"我發送信息給CrimeListActivity了,信息是"+crimeId);
        setAnswerShownResult(crimeId);
    }
}
