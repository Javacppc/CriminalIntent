package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zhuxiaodong on 2017/1ty/12.
 */

public class TimePickerFragment extends DialogFragment {
    private DatePicker mDatePicker;
    public static final String EXTRA_DATE_SECOND = "com.bignerdranch.android.crimalintent.TimePickerFragment";
    /**
     * 用於發送信息給CrimeFragment
     * @param resultCode
     * @param date
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment()==null)return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE_SECOND,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(2000,1,1,null);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_choose_time).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year,month,day).getTime();
                sendResult(Activity.RESULT_OK,date);
            }
        }).create();
    }
}
