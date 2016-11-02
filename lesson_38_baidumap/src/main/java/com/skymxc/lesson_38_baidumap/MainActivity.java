package com.skymxc.lesson_38_baidumap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";

    private TextureMapView mapView;
    private CheckBox cbSatellite;
    private BaiduMap mBaiduMap;
    private  CheckBox cbTraffic;
    private  CheckBox cbHeatMap;
    private MLocation mLocation;
    private boolean isFirst =true;
    private PoiSearch poiSearch;
    private EditText etKey;

    private  BitmapDescriptor icon;

    private BusLineSearch busLineSearch;
    //路线规划的搜索对象
    private RoutePlanSearch routePlanSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (TextureMapView) findViewById(R.id.map_view);
        cbSatellite = (CheckBox) findViewById(R.id.map_type_satellite);
        cbSatellite.setOnCheckedChangeListener(changeLis);
        mBaiduMap= mapView.getMap();
        cbTraffic = (CheckBox) findViewById(R.id.show_traffic);
        cbTraffic.setOnCheckedChangeListener(changeLis);
        cbHeatMap = (CheckBox) findViewById(R.id.show_head_map);
        cbHeatMap.setOnCheckedChangeListener(changeLis);
        etKey = (EditText) findViewById(R.id.key);
        //定义Maker坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
//        //构建Marker图标
          icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions oo = new MarkerOptions()
//                .position(point)
//                .icon(icon)
//                .draggable(true);
//        mBaiduMap.addOverlay(oo);

        mBaiduMap.setOnMapClickListener(onMapClickListener);
        mBaiduMap.setOnMarkerClickListener(markerClickLis);
        mLocation = new MLocation(this);
        //开启显示我的位置 巩华城地铁
        mBaiduMap.setMyLocationEnabled(true);

        registerReceiver(receiver,new IntentFilter(MLocation.RECEIVER_LOCATION));

    }
    //marker 点击事件 沙河地铁站
    private BaiduMap.OnMarkerClickListener markerClickLis = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
           Log.i(TAG, "onMarkerClick: ");
            PoiInfo info = marker.getExtraInfo().getParcelable("info");
            routePlanSearch(info.location);

            return true;
        }
    };

    //地图的点击事件
    private BaiduMap.OnMapClickListener onMapClickListener = new BaiduMap.OnMapClickListener() {
        //不是图标
        @Override
        public void onMapClick(LatLng latLng) {
            Log.i(TAG, "onMapClick: "+latLng.latitude+";="+latLng.longitudeE6);
        }

        //点击到 图标
        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            Toast.makeText(MainActivity.this, "名字="+mapPoi.getName(), Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    private CompoundButton.OnCheckedChangeListener changeLis = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.map_type_satellite:
                    if (!b){
                        //普通地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    }else{
                        //卫星地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    }
                    break;
                case R.id.show_traffic:
                        //交通图
                        mBaiduMap.setTrafficEnabled(b);
                    break;
                case R.id.show_head_map:
                    //热力图
                    mBaiduMap.setBaiduHeatMapEnabled(b);
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocation.stop();
        unregisterReceiver(receiver);
        if (poiSearch!=null){
            poiSearch.destroy();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BDLocation loc = intent.getParcelableExtra("loc");
            Log.i(TAG, "onReceive: action="+intent.getAction());
            if (loc.getLocType() == 161 || loc.getLocType() == 61){
                Log.e(TAG, "onReceive: getLatitude="+loc.getLatitude()+"/getLatitude="+loc.getLatitude());
                Toast.makeText(context, loc.getLatitude()+"/"+loc.getLongitude(), Toast.LENGTH_SHORT).show();
                MyLocationData locData = new MyLocationData.Builder()
                        .latitude(loc.getLatitude())
                        .longitude(loc.getLongitude())
                        .accuracy(loc.getRadius())  //定位精度范围
                        .build();
                //设置我的位置
                mBaiduMap.setMyLocationData(locData);
                //第一次定位成功 ，移动到我的位置
                if (isFirst){
                    isFirst=false;
                    moveToMyLocation();
                }
            }else{
                Log.e(TAG, "onReceive: 定位失败");
                Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void moveToMyLocation() {
        //获取我的位置数据
      MyLocationData data=  mBaiduMap.getLocationData();
        //创建 LatLng 40.123577 116.256607 data.latitude,data.longitude
        LatLng ll= new LatLng(data.latitude,data.longitude);
        //地图状态更新对象
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll,18);
        //动画变到指定地图状态
        mBaiduMap.animateMapStatus(msu);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.my_location:
                moveToMyLocation();
                break;
            case R.id.search:
                poSearch();
                break;
        }
    }

    private void poSearch() {
        if(poiSearch==null) {
            poiSearch = PoiSearch.newInstance();
            //设置结果监听
            poiSearch.setOnGetPoiSearchResultListener(poiSearchResult);
        }

        PoiNearbySearchOption o = new PoiNearbySearchOption()
                .keyword(etKey.getText().toString())    //关键字
                .location(new LatLng(mBaiduMap.getLocationData().latitude,mBaiduMap.getLocationData().longitude))    //搜索位置
                .radius(1000*2*10)         ;    //2万米
        poiSearch.searchNearby(o);




    }


    private OnGetPoiSearchResultListener poiSearchResult = new OnGetPoiSearchResultListener() {
        /**
         * 获取到结果
         * @param poiResult
         */
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            mBaiduMap.clear();
//            List<PoiInfo> infos = poiResult.getAllPoi();
            PoiOverlay poiOverlay = new PoiOverlay(mBaiduMap);
            poiOverlay.setData(poiResult);
            poiOverlay.addToMap();
//            for (PoiInfo info :infos){
//
//                if (info.type == PoiInfo.POITYPE.BUS_LINE || info.type == PoiInfo.POITYPE.SUBWAY_LINE){
//                    //如果是公交 或地铁路线 ，则进行路线搜索
//                    busLineSearch(info.uid);
//                }else {
//                        Bundle b = new Bundle();
//                    b.putParcelable("info",info);
//                    OverlayOptions oo = new MarkerOptions()
//                            .position(info.location)
//                            .icon(icon)
//                            .extraInfo(b);
//                    mBaiduMap.addOverlay(oo);
//                }
//            }
        }

        /**
         * 获取到poi详情的搜索结果
         * @param poiDetailResult
         */
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        /**
         * 获取到室内搜索结果
         * @param poiIndoorResult
         */
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    /**
     * 搜索公交路线
     * @param uid
     */
    private void busLineSearch(String uid) {
        if (busLineSearch ==null){
            busLineSearch = BusLineSearch.newInstance();
            busLineSearch .setOnGetBusLineSearchResultListener(onBusResult);
        }
        BusLineSearchOption o = new BusLineSearchOption()
                .city("北京")
                .uid(uid);

        busLineSearch.searchBusLine(o);
    }

    /**
     * 公交路线 搜索结果
     */
    private OnGetBusLineSearchResultListener onBusResult = new OnGetBusLineSearchResultListener() {
        @Override
        public void onGetBusLineResult(BusLineResult busLineResult) {
            if(busLineResult.error == SearchResult.ERRORNO.NO_ERROR){
                BusLineOverlay bo = new BusLineOverlay(mBaiduMap);
                //设置公交路线数据 专
                bo.setData(busLineResult);
                //添加到地图上
                bo.addToMap();
            }
        }
    };

    /**
     * 路线规划对象 的初始化
     */
      private void  routePlanSearch(LatLng ll){
          if (routePlanSearch == null){
              routePlanSearch = RoutePlanSearch.newInstance();
              routePlanSearch.setOnGetRoutePlanResultListener(onGetRoutePlanResultListener);
          }

          PlanNode pnStart = PlanNode.withLocation(new LatLng(mBaiduMap.getLocationData().latitude,mBaiduMap.getLocationData().longitude));
//          PlanNode pnEnd = PlanNode.withCityNameAndPlaceName("北京","巩华城");
          PlanNode pnEnd = PlanNode.withLocation(ll);
//
          DrivingRoutePlanOption doo = new DrivingRoutePlanOption()
                  .from(pnStart)
                  .to(pnEnd);
          WalkingRoutePlanOption woo = new WalkingRoutePlanOption()
                  .from(pnStart)
                  .to(pnEnd);
//          routePlanSearch.drivingSearch(doo);
          routePlanSearch.walkingSearch(woo);
      }

    private OnGetRoutePlanResultListener onGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                mBaiduMap.clear();
              List<WalkingRouteLine> lines =  walkingRouteResult.getRouteLines();
                for (WalkingRouteLine line :lines){
                    WalkingRouteOverlay wro = new WalkingRouteOverlay(mBaiduMap);
                    wro.setData(line);
                    wro.addToMap();
                }
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                mBaiduMap.clear();
                List<DrivingRouteLine> lines = drivingRouteResult.getRouteLines();
                if (lines!=null && lines.size()>0){
                    DrivingRouteOverlay dro = new DrivingRouteOverlay(mBaiduMap);
                    dro.setData(lines.get(0));
                    dro.addToMap();
                }
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

}
