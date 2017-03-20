package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * 顯示列表項的Fragment
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    public static final int REQUEST_CODE = 1;
    private static UUID mUUID;
    private static final String TAG = "CrimeListFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //LayoutManager用來在屏幕上定位列表項，還負責定義屏幕滾動行為
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"喚醒CrimeListFragment");
        updateUI();
    }

    /**
     * 將Adapter與RecyclerView關聯起來
     */
    private void updateUI() {
        CrimeLab crimes = CrimeLab.get(getActivity());
        List<Crime> listCrimes = crimes.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(listCrimes);
            mCrimeRecyclerView.setAdapter(mAdapter);//將Adapter與RecycleView綁定
        } else {
            mAdapter.notifyDataSetChanged();//刷新顯示列表項（模型層的數據在修改過明細Activity返回此Fragment之後刷新到此）
            //只刷新列表項中單個crime項--這樣會大大增加效率
            /*UUID id = (UUID) getArguments().getSerializable(ARG_CRIME_ID);*/
            //mAdapter.notifyItemChanged(CrimeLab.getIndex(mUUID));
        }
    }



    private class CrimeHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        /**
         * 用於在CrimeHolder中綁定視圖
         */
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            itemView.setOnClickListener(this);
        }

        /**
         * 在CimeHolder中綁定視圖層
         * @param crime
         */
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(DateFormat.format("yyyy年MM月dd日,kk:mm",mCrime.getDate()));
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivityForResult(intent,REQUEST_CODE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG,"調用onActivityResult()方法");
        if (requestCode==REQUEST_CODE){
            if (data == null) {
                return;
            }
            mUUID = (UUID) CrimeActivity.getIdShown(data);
            Log.i(TAG,""+mUUID);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            this.mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layout = LayoutInflater.from(getActivity());
            View view = layout.inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }

        /**
         * 把ViewHolder的View視圖和模型層數據綁定起來
         * @param holder RecycleView會傳來ViewHolder
         * @param position  RecycleView會傳來ViewHolder的位置
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);//綁定ViewHolder至模型層數據
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
