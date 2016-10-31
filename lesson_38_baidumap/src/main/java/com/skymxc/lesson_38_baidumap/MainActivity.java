package com.skymxc.lesson_38_baidumap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";

    private TextureMapView mapView;
    private CheckBox cbSatellite;
    private BaiduMap mBaiduMap;
    private  CheckBox cbTraffic;
    private  CheckBox cbHeatMap;
    private MLocation mLocation;

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

        //定义Maker坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
//        //构建Marker图标
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions oo = new MarkerOptions()
//                .position(point)
//                .icon(icon)
//                .draggable(true);
//        mBaiduMap.addOverlay(oo);

        mBaiduMap.setOnMapClickListener(onMapClickListener);
        mBaiduMap.setOnMarkerClickListener(markerClickLis);
        mLocation = new MLocation(this);

    }
    //marker 点击事件
    private BaiduMap.OnMarkerClickListener markerClickLis = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
           Log.i(TAG, "onMarkerClick: ");
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
    }
}
