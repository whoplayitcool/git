package com.tianhe.live.tianhecheck.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tianhe.live.tianhecheck.CheckInfoActivity;
import com.tianhe.live.tianhecheck.R;
import com.tianhe.live.tianhecheck.calendar.CalendarCard;
import com.tianhe.live.tianhecheck.calendar.CalendarViewAdapter;
import com.tianhe.live.tianhecheck.calendar.CustomDate;


/**
 * Created by ${谭姜维} on 2016/10/18.
 */
public class SecondFragment extends Fragment implements View.OnClickListener, CalendarCard.OnCellClickListener {
    private ViewPager mViewPager;
    private int mCurrentIndex = 498;
    private CalendarCard[] mShowViews;
    private CalendarViewAdapter<CalendarCard> adapter;
    private SecondFragment.SildeDirection mDirection = SecondFragment.SildeDirection.NO_SILDE;
    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }

    private ImageButton preImgBtn, nextImgBtn;
    private TextView monthText;
    private ImageButton closeImgBtn;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

//        View view=View.inflate(container.getContext(), R.layout.first_fragment, null);
        View view = View.inflate(container.getContext(), R.layout.second_fragment, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_calendar);
        preImgBtn = (ImageButton) view.findViewById(R.id.btnPreMonth);
        nextImgBtn = (ImageButton) view.findViewById(R.id.btnNextMonth);
        monthText = (TextView) view.findViewById(R.id.tvCurrentMonth);

        preImgBtn.setOnClickListener(this);
        nextImgBtn.setOnClickListener(this);


        CalendarCard[] views = new CalendarCard[3];
        for (int i = 0; i < 3; i++) {
            views[i] = new CalendarCard(getActivity(), this);
        }
        adapter = new CalendarViewAdapter<>(views);
        setViewPager();

        return view;

    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);
//        mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);
//        preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);
//        nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);
//        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);
//        closeImgBtn = (ImageButton) this.findViewById(R.id.btnClose);
//        preImgBtn.setOnClickListener(this);
//        nextImgBtn.setOnClickListener(this);
//        closeImgBtn.setOnClickListener(this);
//
//        CalendarCard[] views = new CalendarCard[3];
//        for (int i = 0; i < 3; i++) {
//            views[i] = new CalendarCard(this, this);
//        }
//        adapter = new CalendarViewAdapter<>(views);
//        setViewPager();
//
//    }
//
    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(498);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPreMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.btnNextMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
//            case R.id.btnClose:
//                finish();
//                break;
            default:
                break;
        }
    }

    @Override

    public void clickDate(CustomDate date) {
        Toast.makeText(getActivity(), date+"被点了", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),CheckInfoActivity.class);
        startActivity(intent);

    }

    @Override
    public void changeDate(CustomDate date) {
        monthText.setText(date.month + "月");
    }

    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0) {

        if (arg0 > mCurrentIndex) {
            mDirection = SecondFragment.SildeDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = SecondFragment.SildeDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        mShowViews = adapter.getAllItems();
        if (mDirection == SecondFragment.SildeDirection.RIGHT) {
            mShowViews[arg0 % mShowViews.length].rightSlide();
        } else if (mDirection == SecondFragment.SildeDirection.LEFT) {
            mShowViews[arg0 % mShowViews.length].leftSlide();
        }
        mDirection = SecondFragment.SildeDirection.NO_SILDE;
    }
}