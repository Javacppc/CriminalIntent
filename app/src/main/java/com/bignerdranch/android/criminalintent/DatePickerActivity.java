package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created by zhuxiaodong on 2017/1/16.
 */

public class DatePickerActivity extends SingleFragmentActivity {
    private static final String EXTRA_DATE_ID = "com.bignerdranch.android.criminalintent.DatePickerActivity";
    public static Intent newIntent(Context pageContext, Date date) {
        Intent intent = new Intent(pageContext,DatePickerFragment.class);
        intent.putExtra(EXTRA_DATE_ID,date);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_DATE_ID);
        return DatePickerFragment.newInstance(date);
    }
}
