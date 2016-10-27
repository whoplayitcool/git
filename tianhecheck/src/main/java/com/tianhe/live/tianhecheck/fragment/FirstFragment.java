package com.tianhe.live.tianhecheck.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.tianhe.live.tianhecheck.R;
import com.tianhe.live.tianhecheck.x.Utils;

import static com.tianhe.live.tianhecheck.R.id.tv_result;
import static com.tianhe.live.tianhecheck.x.Utils.formatUTC;

//import android.support.annotation.Nullable;

/**
 * Created by ${谭姜维} on 2016/10/18.
 */
public class FirstFragment extends Fragment implements com.amap.api.maps2d.LocationSource,
        AMapLocationListener
        {


            private AMap aMap;
            private MapView mapView;
            private LocationSource.OnLocationChangedListener mListener;
            private AMapLocationClient mlocationClient;
            private AMapLocationClientOption mLocationOption;
            private double x,y;
            private TextView tvReult;
            private AMapLocationClient locationClient = null;
            private AMapLocationClientOption locationOption = new AMapLocationClientOption();
            private Button bt1;
            private TextView tv_result2;
            private StringBuffer sb = new StringBuffer();
            private int i=1;





            @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = View.inflate(container.getContext(), R.layout.first_fragment, null);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
                bt1 = (Button) view.findViewById(R.id.bt1);
                tvReult = (TextView) view.findViewById(tv_result);
                tv_result2 = (TextView) view.findViewById(R.id.tv_result2);
        init();
                initLocation();
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg){
                        super.handleMessage(msg);
                        if(msg.what == 1){
                            tv_result2.setText(sb);
                        }
                    }
                };


//                final Thread thread = new Thread(new Runnable(){
//
//
//                    @Override
//                    public void run() {
//                        Message message = new Message();
//                        message.what = 1;
//                        handler.sendMessage(message);
//
//                    }
//
//                });
                final Runnable sendable = new Runnable() {

                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                    }
                };
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Math.abs(x - 43.63)>0.03||(Math.abs(y - 122.28)>0.03)) {

                            Toast toast = Toast.makeText(getActivity(),"不在公司范围，签到失败" , Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP , 0, 250);
                            toast.show();

                            failed();

                        }else {
                            Toast toast = Toast.makeText(getActivity(),"签到成功" , Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP , 0, 250);
                            toast.show();
                            succeed();

                        }
//                        tv_result2.setText(sb);

                        new Thread(sendable).start();
                    }
                });












      return view;
    }

            private void failed() {
                sb.append(i+"签到失败"+ formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
                i++;
            }

            private void succeed() {
                sb.append(i+"签到成功"+ formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
                i++;
            }

            private void initLocation(){
                //初始化client
                locationClient = new AMapLocationClient(getActivity().getApplicationContext());
                //设置定位参数
                locationClient.setLocationOption(getDefaultOption());
                // 设置定位监听
                locationClient.setLocationListener(locationListener);
                startLocation();
            }
            private AMapLocationClientOption getDefaultOption(){
                AMapLocationClientOption mOption = new AMapLocationClientOption();
                mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
                mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
                mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
                mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
                mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
                mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
//                mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用

                return mOption;
            }

            /**
             * 定位监听
             */
            AMapLocationListener locationListener = new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation loc) {
                    if (null != loc) {
                        //解析定位结果
                        String result = Utils.getLocationStr(loc);
                        x=loc.getLatitude();
                        y=loc.getLongitude();
                        tvReult.setText(result);
                    } else {
                        tvReult.setText("定位失败，loc is null");
                    }
                }
            };



            /**
             * 开始定位
             *
             * @since 2.8.0
             * @author hongming.wang
             *
             */
            private void startLocation(){
                //根据控件的选择，重新设置定位参数

                locationClient.setLocationOption(locationOption);
                // 启动定位
                locationClient.startLocation();
            }

            /**
             * 停止定位
             *
             * @since 2.8.0
             * @author hongming.wang
             *
             */
            private void stopLocation(){
                // 停止定位
                locationClient.stopLocation();
            }

            /**
             * 销毁定位
             *
             * @since 2.8.0
             * @author hongming.wang
             *
             */
            private void destroyLocation(){
                if (null != locationClient) {
                    /**
                     * 如果AMapLocationClient是在当前Activity实例化的，
                     * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
                     */
                    locationClient.onDestroy();
                    locationClient = null;
                    locationOption = null;
                }
            }



            /**
             * 初始化AMap对象
             */
            private void init() {
                if (aMap == null) {
                    aMap = mapView.getMap();


                    setUpMap();

                }
            }

            /**
             * 设置一些amap的属性
             */
            private void setUpMap() {
                // 自定义系统定位小蓝点
                MyLocationStyle myLocationStyle = new MyLocationStyle();
                myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
                myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
                myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
                // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
                myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
                aMap.setMyLocationStyle(myLocationStyle);
                aMap.setLocationSource(this);// 设置定位监听

                aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
                aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

            }

            /**
             * 方法必须重写
             */
            @Override
            public void onResume() {
                super.onResume();
                mapView.onResume();
            }

            /**
             * 方法必须重写
             */
            @Override
            public void onPause() {
                super.onPause();
                mapView.onPause();
                deactivate();
            }

            /**
             * 方法必须重写
             */
            @Override
            public void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                mapView.onSaveInstanceState(outState);
            }

            /**
             * 方法必须重写
             */
            @Override
            public void onDestroy() {
                super.onDestroy();
                mapView.onDestroy();

            }

            /**
             * 定位成功后回调函数
             */
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (mListener != null && amapLocation != null) {
                    if (amapLocation != null
                            && amapLocation.getErrorCode() == 0) {
                        mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    } else {
                        String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                        Log.e("AmapErr",errText);
                    }
                }
            }

            /**
             * 激活定位
             */
            @Override
            public void activate(OnLocationChangedListener listener) {
                mListener = (LocationSource.OnLocationChangedListener) listener;
                if (mlocationClient == null) {
                    mlocationClient = new AMapLocationClient(getActivity());
                    mLocationOption = new AMapLocationClientOption();
                    //设置定位监听
                    mlocationClient.setLocationListener(this);
                    //设置为高精度定位模式
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    //设置定位参数
                    mlocationClient.setLocationOption(mLocationOption);
                    // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                    // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                    // 在定位结束后，在合适的生命周期调用onDestroy()方法
                    // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                    mlocationClient.startLocation();
                }
            }

            /**
             * 停止定位
             */
            @Override
            public void deactivate() {
                mListener = null;
                if (mlocationClient != null) {
                    mlocationClient.stopLocation();
                    mlocationClient.onDestroy();
                }
                mlocationClient = null;
            }





        }
