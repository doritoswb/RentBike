package application.brent.com.rentbike.Dashboard;

/**
 * Created by bwu on 2015/4/18.
 */
public class OfflineBikeSite {
    private double latitude;
    private double longitude;
    private String location;
    private int siteId;
    private String siteName;

    public OfflineBikeSite() {
        latitude = 0;
        longitude = 0;
        location = "";
        siteId = 0;
        siteName = "";
    }

    public OfflineBikeSite(double latitude, double longitude, String location,int siteId, String siteName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.siteId = siteId;
        this.siteName = siteName;
    }

    public OfflineBikeSite getOfflineBikeSite(){
        return this;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getSiteId() {
        return this.siteId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return this.siteName;
    }
}
