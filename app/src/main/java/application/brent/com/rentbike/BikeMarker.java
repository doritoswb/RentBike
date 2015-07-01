package application.brent.com.rentbike;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import application.brent.com.rentbike.Dashboard.BikeSite;
import application.brent.com.rentbike.Dashboard.DashBoardActivity;

/**
 * Created by bwu on 2015/4/15.
 */

public class BikeMarker {
    private View markerView;
    private BikeSite site;

    private static final String strSiteNum = "网点编号：";
    private static final String strSiteName = "网点名称：";
    private static final String strSiteAddress = "网点地址：";
    private static final String strSiteTotalBikeNum = "自行车数：";
    private static final String strSiteLeftBikeNum = "剩余车数：";
    private static final String strSiteDistance = "网点距离：";

    public BikeSite getSite(){
        return this.site;
    }

    public BikeMarker(Activity activity, BikeSite site) {
        LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
        this.markerView = inflater.inflate(R.layout.dashboardmarker, null);

        this.site = site;
    }

    public View getInfoView(){
        View focusView = this.markerView.findViewById(R.id.dashboard0MarkerFocusContainer);

        focusView.setVisibility(View.VISIBLE);

        TextView siteNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerSiteNum);
        TextView siteName = (TextView) focusView.findViewById(R.id.dashboard0MarkerName);
        TextView siteAddress = (TextView) focusView.findViewById(R.id.dashboard0MarkerAddress);
        TextView siteTotalBikeNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerTotalBikeNum);
        TextView siteLeftBikeNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerLeftBikeNum);
        TextView siteDistance = (TextView) focusView.findViewById(R.id.dashboard0MarkerDistance);

        siteNum.setText(strSiteNum + this.site.getSiteId());
        siteName.setText(strSiteName + this.site.getSiteName());
        siteAddress.setText(strSiteAddress + this.site.getLocation());
        siteTotalBikeNum.setText(strSiteTotalBikeNum + this.site.getLockNum());
        siteLeftBikeNum.setText(strSiteLeftBikeNum + (this.site.getLockNum() - this.site.getEmptyLockNum()));

        LatLng latLng = new LatLng(site.getLatitude(), site.getLongitude());
        LatLng currentLatLng = new LatLng(DashBoardActivity.currentlocation.getLatitude(), DashBoardActivity.currentlocation.getLongitude());

        int distance = (int)DistanceUtil.getDistance(latLng, currentLatLng);
        StringBuilder sp = new StringBuilder();
        if(distance < 1000){
            sp.append(strSiteDistance).append(distance).append(" 米");
            siteDistance.setText(sp.toString());
        } else {
            sp.append(strSiteDistance).append(distance/1000).append( "千米 ").append(distance%1000).append( "米");
            siteDistance.setText(sp.toString());
        }
        return focusView;
    }

    public Bitmap toMarkerBitmap() {
        View focusView = this.markerView.findViewById(R.id.dashboard0MarkerFocusContainer);

        focusView.setVisibility(View.VISIBLE);

        TextView siteNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerSiteNum);
        TextView siteName = (TextView) focusView.findViewById(R.id.dashboard0MarkerName);
        TextView siteAddress = (TextView) focusView.findViewById(R.id.dashboard0MarkerAddress);
        TextView siteTotalBikeNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerTotalBikeNum);
        TextView siteLeftBikeNum = (TextView) focusView.findViewById(R.id.dashboard0MarkerLeftBikeNum);
        TextView siteDistance = (TextView) focusView.findViewById(R.id.dashboard0MarkerDistance);

        siteNum.setText(strSiteNum + this.site.getSiteId());
        siteName.setText(strSiteName + this.site.getSiteName());
        siteAddress.setText(strSiteAddress + this.site.getLocation());

        if(MyConnectivityManager.getInstance().isNetworkAvailable()){
            siteTotalBikeNum.setText(strSiteTotalBikeNum + this.site.getLockNum());
        } else {
            siteTotalBikeNum.setVisibility(View.GONE);
        }

        if(MyConnectivityManager.getInstance().isNetworkAvailable()){
            siteLeftBikeNum.setText(strSiteLeftBikeNum + (this.site.getLockNum() - this.site.getEmptyLockNum()));
        } else {
            siteLeftBikeNum.setVisibility(View.GONE);
        }


        LatLng latLng = new LatLng(site.getLatitude(), site.getLongitude());
        LatLng currentLatLng = new LatLng(DashBoardActivity.currentlocation.getLatitude(), DashBoardActivity.currentlocation.getLongitude());

        int distance = (int)DistanceUtil.getDistance(latLng, currentLatLng);
        StringBuilder sp = new StringBuilder();
        if(distance < 1000){
            sp.append(strSiteDistance).append(distance).append(" 米");
            siteDistance.setText(sp.toString());
        } else{
            sp.append(strSiteDistance).append((double)distance/1000).append("公里");
            //sp.append(strSiteDistance).append(distance/1000).append(".").append((double)(distance%1000)/1000).append("公里");
            siteDistance.setText(sp.toString());
        }

        this.markerView.measure(0, 0);

        int measureWidth = this.markerView.getMeasuredWidth();
        int measureHeight = this.markerView.getMeasuredHeight();
        Bitmap bitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);

        this.markerView.layout(0, 0, measureWidth, measureHeight);
        this.markerView.draw(canvas);

        return bitmap;
    }
}

