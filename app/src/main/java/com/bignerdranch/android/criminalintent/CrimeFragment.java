package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuxiaodong on 2017/1/4.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTextField;
    private Button mDateButton;
    private Button mDateSecondButton;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DATE_SECOND = "DialogDateSecond";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DATE_SECOND = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();
        /*UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);*/
        //使用fragment argument
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();;
        bundle.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            if (data==null)return;
            Date date = (Date) DatePickerFragment.getReturnData(data);
            //Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_DATE_SECOND) {
            if (data==null)return;
            Date date = (Date) DatePickerFragment.getReturnData(data);
            mCrime.setDate(date);
            updateDate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //第一個參數表示傳入佈局的資源ID，第二個參數是視圖的父視圖，我們通常需要父視圖來正確的配置組件。第三個參數是告之
        //佈局生成器是否將生成的視圖添加給父視圖，在這裡我們將以Activity代碼的方式添加視圖
        View v = inflater.inflate(R.layout.fragment_crime,container,false);
        mTextField = (EditText) v.findViewById(R.id.crime_title);
        mTextField.setText(mCrime.getTitle());
        mTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             *
             * @param s 用戶的輸入
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton  = (Button) v.findViewById(R.id.crime_date);
        updateDate();

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getFragmentManager();
                //DatePickerFragment dpf = new DatePickerFragment();
                DatePickerFragment fragment = DatePickerFragment.newInstance(mCrime.getDate());
                fragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                fragment.show(fm,DIALOG_DATE);*/
                Intent intent = DatePickerActivity.newIntent(getActivity(),mCrime.getDate());
                startActivityForResult(intent,REQUEST_DATE);
            }
        });
        mDateSecondButton = (Button) v.findViewById(R.id.crime_date_second);
        mDateSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getFragmentManager();
                TimePickerFragment fragment = new TimePickerFragment();
                fragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE_SECOND);
                fragment.show(fm,DIALOG_DATE_SECOND);*/
                Intent intent = DatePickerActivity.newIntent(getActivity(),mCrime.getDate());
                startActivityForResult(intent,REQUEST_DATE_SECOND);
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.format("yyyy年MM月dd日,kk:mm",mCrime.getDate()));
    }
}
