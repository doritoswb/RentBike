package application.brent.com.rentbike.Dashboard;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

import application.brent.com.rentbike.BikeMarker;
import application.brent.com.rentbike.MyConnectivityListener;
import application.brent.com.rentbike.MyConnectivityManager;
import application.brent.com.rentbike.R;
import application.brent.com.rentbike.RentBikeApplication;
import application.brent.com.rentbike.Xmlutility;
import application.brent.com.rentbike.base.BaseActivity;
import application.brent.com.rentbike.base.BaseModel;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by bwu on 2015/4/12.
 */
public class DashBoardActivity extends BaseActivity
        implements View.OnClickListener, BaiduMap.OnMarkerClickListener,
        BaiduMap.OnMapStatusChangeListener, MyConnectivityListener {

    private static final String TAG = "DashBoardActivity";
    private static final String OFFLINE_SITE_DATA_FILE = "offline.xml";

    private MapView mapView = null;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="bd09ll";

    private BaiduMap baiduMap;

    public static BDLocation currentlocation;

    private boolean isFirstTimeLocator = true;

    private DrawerLayout drawerLayout;

    private DashBoardHelper dashBoardHelper;

    private EditText commonFilterBoxTextView;

    private RelativeLayout progressMarker;

    private Button nearMe;

    static enum Keys {
        profileFileName
    }

    static enum Actions {
        findBikeSites
    }

    private static final String FRAGMENT_TAG = "profileFragmentTag";

    public static void execute(BaseActivity activity){
        Intent intent = new Intent(activity, DashBoardActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    protected BaseModel createModel(){
        return new DashBoardModel(this);
    }

    public DashBoardActivity(){
        this.dashBoardHelper = new DashBoardHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard);

        mLocationClient = ((RentBikeApplication)getApplication()).locationClient;
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        commonFilterBoxTextView = (EditText) this.findViewById(R.id.commonFilterBoxTextView);
        progressMarker = (RelativeLayout) this.findViewById(R.id.progressMarker);

        initMap();
        initLocation();
        initLeftMenu();
        initProfileLayout(savedInstanceState);

        MyConnectivityManager.getInstance().addListener(this);
        MyConnectivityManager.getInstance().start();
        registerListener();

/*        if (!MyConnectivityManager.getInstance().isNetworkAvailable()) {   //offline mode is not supported, because baiduMap.getProjection() return null when no network.
            this.loadOfflineBikeSiteFromAssetFile();
        } else */
        if(!MyConnectivityManager.getInstance().isNetworkAvailable()){
            Toast toast = Toast.makeText(DashBoardActivity.this, "亲，您还没有打开网络哦！",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else
        {
            progressMarker.setVisibility(View.VISIBLE);
            this.postAsync(Actions.findBikeSites.name());         //online mode
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        mLocationClient.start();
    }

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
    protected void onStop(){
        super.onStop();
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        MyConnectivityManager.getInstance().stop();
        mLocationClient.unRegisterLocationListener(myLocationListener);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.dashboardProfileButton: {
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
            case R.id.center_current: {
                //centerLocationInMap(currentlocation);
	            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
			            .setTitleText("Are you sure?")
			            .setContentText("Won't be able to recover this file!")
			            .setConfirmText("Yes,delete it!")
			            .show();
                break;
            }

            case R.id.zoom_in: {
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.zoomIn();
                baiduMap.setMapStatus(mMapStatusUpdate);
                //displayBikeSites(false);
                break;
            }

            case R.id.zoom_out: {
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.zoomOut();
                baiduMap.setMapStatus(mMapStatusUpdate);
                //displayBikeSites(false);
                break;
            }
            case R.id.search_site: {
                if(!MyConnectivityManager.getInstance().isNetworkAvailable()){
                    Toast toast = Toast.makeText(DashBoardActivity.this, "亲，您还没有打开网络哦！",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                if(this.commonFilterBoxTextView.getText() != null && this.commonFilterBoxTextView.getText().length() > 0){
                    String searchText = this.commonFilterBoxTextView.getText().toString();
                    // 创建地理编码检索实例
                    GeoCoder geoCoder = GeoCoder.newInstance();
                    // 设置地理编码检索监听者
                    geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                        // 反地理编码查询结果回调函数
                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                        }

                        // 地理编码查询结果回调函数
                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult result) {
                            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                // 没有检测到结果
                                Toast toast = Toast.makeText(DashBoardActivity.this, "抱歉，未能找到结果",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                LatLng latlng = new LatLng(result.getLocation().latitude, result.getLocation().longitude);

                                MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(15).build();
                                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                                // 改变地图状态
                                baiduMap.setMapStatus(mMapStatusUpdate);
                                displayBikeSites(false);
                            }
                        }
                    });

                    //查询
                    geoCoder.geocode(new GeoCodeOption().city("西安").address(searchText));
                    // 释放地理编码检索实例
                    //geoCoder.destroy();
                } else {
                    Toast toast = Toast.makeText(DashBoardActivity.this, R.string.input_search_text_prompt, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            }
            case R.id.near_me: {
                centerLocationInMap(currentlocation);

                nearMe = (Button) this.findViewById(R.id.near_me);
                nearMe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayBikeSites(true);
                    }
                }, 10);
                break;
            }
            case R.id.refresh: {
                if(!MyConnectivityManager.getInstance().isNetworkAvailable()){
                    Toast toast = Toast.makeText(DashBoardActivity.this, "亲，您还没有打开网络哦！",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                progressMarker.setVisibility(View.VISIBLE);
                baiduMap.clear();
                this.postAsync(Actions.findBikeSites.name());
                break;
            }
        }
    }

    private void initProfileLayout(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            ProfileFragment profileFragment = new ProfileFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(Keys.profileFileName.name(), R.xml.profile);
            profileFragment.setArguments(bundle);

            this.getFragmentManager().beginTransaction().replace(R.id.profile_root, profileFragment, FRAGMENT_TAG).commit();
        }
    }

    protected boolean onPreExecute(String action){
        return true;
    }


    protected void onPostExecuteSuccessful(final String action){
        //Log.d("DashboardActivity", "onPostExecuteSuccessful");
        //Log.d("DashboardActivity", "action = " + action);
        progressMarker.setVisibility(View.GONE);
        progressMarker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(action.equals(Actions.findBikeSites.name())){
                    //show Bike sites on map
                    displayBikeSites(false);
                }
            }
        }, 500);
    }

    protected void onPostExecuteFailed(final String action){

    }

    private ArrayList<BikeSite> filterBikeSite(List<BikeSite> bikeSites){
        ArrayList<BikeSite> visibleBikeSites = new ArrayList<BikeSite>();
        for(BikeSite site : bikeSites){
            Projection projection = baiduMap.getProjection();
            Point point = baiduMap.getProjection().toScreenLocation(new LatLng(site.getLatitude(), site.getLongitude()));
            if(isPointInVisibleMap(point)){
                visibleBikeSites.add(site);
            }
        }
        return visibleBikeSites;
    }

    private boolean isPointInVisibleMap(Point point){
        int mapWidth = mapView.getWidth();
        int mapHeight = mapView.getHeight();

        if(point.x > 0 && point.x < mapWidth && point.y > 0 && point.y < mapHeight){
            return true;
        } else {
            return false;
        }

    }

    public BaiduMap getBaiduMap(){
        return this.baiduMap;
    }

    @Override
    public boolean needShowErrorToast(String action) {
        if(action.equals(Actions.findBikeSites)){
            return true;
        }
        return false;
    }

    private void initMap(){
        // 地图初始化
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.showZoomControls(false);
        // 设置地图的类型
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);// 开启定位图层
        baiduMap.setMaxAndMinZoomLevel(19,15);
        mapView.removeViewAt(1);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，bd09ll
        option.setScanSpan(3000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private void initLeftMenu(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void registerListener(){
        baiduMap.setOnMapStatusChangeListener(this);
        baiduMap.setOnMarkerClickListener(this);
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null){
                return;
            }

            currentlocation = location;

            if(isFirstTimeLocator) {
                centerLocationInMap(location);
                isFirstTimeLocator = false;
            }
        }


    }

    private void centerLocationInMap(BDLocation location){
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());//设置纬度
        builder.longitude(location.getLongitude());//设置经度
        builder.accuracy(location.getRadius());//设置精度（半径）
        builder.direction(location.getDirection());//设置方向
        builder.speed(location.getSpeed());//设置速度
        MyLocationData locationData = builder.build();

        baiduMap.setMyLocationData(locationData);

        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

        //配置定位图层显示方式(显示模式（普通，罗盘，跟随）)
        // mode - 定位图层显示方式, 默认为 LocationMode.NORMAL 普通态
        //enableDirection - 是否允许显示方向信息
        //customMarker - 设置用户自定义定位图标，可以为 null
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true,null ));

        //设置我的位置为地图的中心点
        MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.animateMapStatus(mMapStatusUpdate);
        displayBikeSites(false);
    }

    @Override
    public boolean onMarkerClick(com.baidu.mapapi.map.Marker marker){
        LatLng location = marker.getPosition();
        BikeSite site = ((DashBoardModel)this.getModel()).getSite(location.latitude, location.longitude);
        if(site == null){
            return false;
        }

        InfoWindow mInfoWindow;

        BikeMarker bikeMarker = new BikeMarker(this, site);

        //将marker所在的经纬度的信息转化成屏幕上的坐标
        final LatLng latLng = marker.getPosition();
        Point point = baiduMap.getProjection().toScreenLocation(latLng);
        point.y -= 22;
        LatLng llInfo = baiduMap.getProjection().fromScreenLocation(point);

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(bikeMarker.toMarkerBitmap());
        //为弹出的InfoWindow添加点击事件
        mInfoWindow = new InfoWindow(bitmap, llInfo, -22, new InfoWindow.OnInfoWindowClickListener(){
                    @Override
                    public void onInfoWindowClick(){
                        //隐藏InfoWindow
                        baiduMap.hideInfoWindow();
                    }
                });
        //显示InfoWindow
        baiduMap.showInfoWindow(mInfoWindow);
        return true;
    }

    @Override
    public void onMapStatusChangeStart(com.baidu.mapapi.map.MapStatus mapStatus){

    }

    @Override
    public void onMapStatusChange(com.baidu.mapapi.map.MapStatus mapStatus){

    }

    @Override
    public void onMapStatusChangeFinish(com.baidu.mapapi.map.MapStatus mapStatus){
        if(progressMarker.getVisibility() == View.GONE){
            displayBikeSites(false);
        }
    }

    private void displayBikeSites(boolean isNearby) {
        baiduMap.clear();
        List<BikeSite> bikeSites = ((DashBoardModel)getModel()).getBikeSites();
        if(bikeSites != null && bikeSites.size() > 0){
            ArrayList<BikeSite> visibleBikeSites = filterBikeSite(bikeSites);
            dashBoardHelper.DisplayMarkers(visibleBikeSites);

            if(isNearby){
                double longDistance = 1000000000.0;  //unit: m
                BikeSite nearestSite = visibleBikeSites.get(0);
                for(BikeSite site : visibleBikeSites){
                    double latitude = site.getLatitude();
                    double longitude = site.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    LatLng currentLatLng = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());

                    double distance = DistanceUtil.getDistance(latLng, currentLatLng);
                    if(distance < longDistance){
                        longDistance = distance;
                        nearestSite = site;
                    }
                }

                MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(nearestSite.getLatitude(), nearestSite.getLongitude())).zoom(19).build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                // 改变地图状态
                baiduMap.setMapStatus(mMapStatusUpdate);
            }
        }
    }

    @Override
    public void updateConnectivityStatus(final boolean isNetworkAvailable, final boolean isWifiAvailable){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateViewNetworkStatus(isNetworkAvailable || isWifiAvailable);
            }
        });
    }

    private void updateViewNetworkStatus(boolean isConnected){
        //setButtonStatus(this.findViewById(R.id.refresh), isConnected);
        //setButtonStatus(this.findViewById(R.id.search_site), isConnected);

        TextView offLineModeTextView = (TextView)this.findViewById(R.id.offline_mode_text);
        if(offLineModeTextView != null){
            if(isConnected){
                offLineModeTextView.setVisibility(View.GONE);
            } else {
                offLineModeTextView.setVisibility(View.VISIBLE);
            }
        }

        List<BikeSite> bikeSites = ((DashBoardModel)this.getModel()).getBikeSites();
        if(isConnected && (bikeSites == null || bikeSites.size() <= 0)){
            this.postAsync(Actions.findBikeSites.name());
        }
    }

    protected void setButtonStatus(View view, boolean isConnected) {
        if (view != null) {
            view.setClickable(isConnected);
            view.setEnabled(isConnected);
        }
    }

    private void loadOfflineBikeSiteFromAssetFile(){
/*        try{
            ArrayList<BikeSite> sites = Xmlutility.getOfflineBikeSites(this, OFFLINE_SITE_DATA_FILE);
            ((DashBoardModel)getModel()).setBikeSites(sites);

            progressMarker.setVisibility(View.GONE);
            progressMarker.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //displayBikeSites(false);
                }
            }, 10);
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
