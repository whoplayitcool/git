package com.tianhe.live.tianhecheck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;

import com.tianhe.live.tianhecheck.fragment.FirstFragment;
import com.tianhe.live.tianhecheck.fragment.SecondFragment;
import com.tianhe.live.tianhecheck.fragment.ThirdFragment;


//public class MainActivity extends Activity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);
//
//}}
public class MainActivity extends FragmentActivity {
    private RadioGroup rg_1;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private int currentId;
    private FirstFragment fragment;
    private Fragment mContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        initView();
        initData();



    }



    private void initData() {
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fragment=firstFragment;
        switchFragment(mContent,fragment);


//        topViewpagerFragment=new TopViewpagerFragment();
        rg_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId!=currentId) {
                    currentId=checkedId;
                    Fragment fragment=null;
                    switch (checkedId) {
                        case R.id.rb_1 :
                             fragment=firstFragment;

                            break;
                        case R.id.rb_2 :
                            fragment=secondFragment;

                            break;
                        case R.id.rb_3 :
                            fragment=thirdFragment;
//                            fragment=topViewpagerFragment;

                            break;

                        default:
                            fragment=firstFragment;
                            break;
                    }
                    switchFragment(mContent,fragment);


                }

            }


        });


    }

    //    public void switchFragment(Fragment fragment) {
//        FragmentManager fm=getSupportFragmentManager();
//        fm.beginTransaction().replace(R.id.fl_main_activity,fragment).commit();
//
//    }
    private void switchFragment(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_1,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

    private void initView() {
        rg_1 = (RadioGroup) findViewById(R.id.rg_1);


    }

}

    

