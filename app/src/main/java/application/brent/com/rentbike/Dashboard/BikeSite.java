package application.brent.com.rentbike.Dashboard;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bwu on 2015/4/13.
 */
public class BikeSite extends JSONObject {

    private int siteId;
    private String siteName;
    private String location;
    private double latitude;
    private double longitude;
    private int lockNum = -1;
    private int emptyLockNum = -1;

    public BikeSite(){
    }

    public void setSiteId(int siteId){
        this.siteId = siteId;
    }

    public int getSiteId(){
        return this.siteId;
    }

    public void setSiteName(String siteName){
        this.siteName = siteName;
    }

    public String getSiteName(){
        return this.siteName;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLockNum(int lockNum){
        this.lockNum = lockNum;
    }

    public int getLockNum(){
        return this.lockNum;
    }

    public void setEmptyLockNum(int emptyLockNum){
        this.emptyLockNum = emptyLockNum;
    }

    public int getEmptyLockNum(){
        return this.emptyLockNum;
    }

    public JSONObject toJsonPacket() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    public void fromJSonPacket(JSONObject json) throws JSONException{
        if (json.has("siteid")) {
            this.siteId = json.getInt("siteid");
        }
        if(json.has("sitename")){
            this.siteName = json.getString("sitename");
        }
        if(json.has("location")){
            this.location = json.getString("location");
        }
        if (json.has("latitude")) {
            this.latitude = json.getDouble("latitude");
        }
        if(json.has("longitude")){
            this.longitude = json.getDouble("longitude");
        }
        if(json.has("locknum")){
            this.lockNum = json.getInt("locknum");
        }
        if(json.has("emptynum")){
            this.emptyLockNum = json.getInt("emptynum");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.siteId);
        dest.writeString(this.siteName);
        dest.writeString(this.location);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.lockNum);
        dest.writeInt(this.emptyLockNum);
    }

    protected BikeSite(Parcel in) {
        this.siteId = in.readInt();
        this.siteName = in.readString();
        this.location = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.lockNum = in.readInt();
        this.emptyLockNum = in.readInt();
    }
}
