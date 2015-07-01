package application.brent.com.rentbike.Dashboard;

import android.graphics.BitmapFactory;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

import application.brent.com.rentbike.R;

/**
 * Created by bwu on 2015/4/15.
 */
public class DashBoardHelper {

    private DashBoardActivity activity;
    public DashBoardHelper(DashBoardActivity activity){
        this.activity = activity;
    }

    public void DisplayMarkers(ArrayList<BikeSite> bikeSites){
        //Log.d("DashboardActivity", "bikeSites.size = " + bikeSites.size());
        if(bikeSites != null && bikeSites.size() > 0){
            for(int i = 0; i < bikeSites.size() ; i++){
                BikeSite site = bikeSites.get(i);

                LatLng latlng = new LatLng(site.getLatitude(), site.getLongitude());
                //构建Marker图标
                //构建MarkerOption，用于在地图上添加Marker
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.normalbks));
                OverlayOptions option = new MarkerOptions().position(latlng).icon(bitmap).zIndex(9);
                //在地图上添加Marker，并显示
                this.activity.getBaiduMap().addOverlay(option);
                bitmap.recycle();
            }
        }
    }

    public void DisplayMarker(BikeSite bikeSite){
        if(bikeSite != null){
            LatLng latlng = new LatLng(bikeSite.getLatitude(), bikeSite.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.normalbks));
            OverlayOptions option = new MarkerOptions().position(latlng).icon(bitmap).zIndex(9);
            //在地图上添加Marker，并显示
            this.activity.getBaiduMap().addOverlay(option);
            bitmap.recycle();
        }
    }
}
