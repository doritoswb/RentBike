package application.brent.com.rentbike.Dashboard;

import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import application.brent.com.rentbike.Netwrok.HttpRequest;
import application.brent.com.rentbike.Netwrok.NetworkResponse;
import application.brent.com.rentbike.ResponseStatus;
import application.brent.com.rentbike.base.BaseModel;

/**
 * Created by bwu on 2015/4/12.
 */
public class DashBoardModel extends BaseModel {

    private static final String bikeSitesUrl = "http://www.xazxc.com/service/IBikeSitesService";

    private List<BikeSite> bikeSites = new Vector<BikeSite>();

    public DashBoardModel(DashBoardActivity activity){
        super(activity);
    }

    public List<BikeSite> getBikeSites(){
        return this.bikeSites;
    }

    //public void setBikeSites(ArrayList<BikeSite> bikeSites){
    //    this.bikeSites = bikeSites;
    //}

    protected ResponseStatus doInBackground(String action) {
        ResponseStatus responseStatus = new ResponseStatus();

        DashBoardActivity.Actions actions = DashBoardActivity.Actions.valueOf(action);
        switch (actions) {
            case findBikeSites: {
                byte[] requestContent = buildFindBikeSitesRequest();

                try {
                    URL url = new URL(bikeSitesUrl);
                    NetworkResponse response = HttpRequest.sendRequest(url, requestContent);

                    if(response != null){
                        if(response.status.equals(NetworkResponse.Status.success)){
                            responseStatus.setStatus(ResponseStatus.Status.OK);

                            if(response.data != null && response.data.length > 0){
                                parseBikeSites(response.data);
                            }
                        } else {
                            responseStatus.setStatus(ResponseStatus.Status.Error);
                            responseStatus.setErrorMessage(response.errorMessage);
                        }
                    }
                } catch (MalformedURLException e){
                    e.printStackTrace();
                }
                break;
            }
        }
/*        long i = Thread.currentThread().getId();
        Log.d("DashBoardModel", "thread id = " + i);
        Log.d("DashBoardModel", "responseStatus.status = " + responseStatus.getStatus());
        Log.d("DashBoardModel", "responseStatus.message = " + responseStatus.getErrorMessage());*/
        return responseStatus;
    }

    private byte[] buildFindBikeSitesRequest(){
        //组建xml数据
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://ws.itcast.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        xml.append("<soapenv:Body>");
        xml.append("<q0:findBikeSites />");
        xml.append("</soapenv:Body>");
        xml.append("</soapenv:Envelope>");

        byte[] xmlByte = null;
        try {
            xmlByte = xml.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return xmlByte;
    }

    private void parseBikeSites(byte[] data){
        // xml解析
        String bikeSiteStr = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new ByteArrayInputStream(data), "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("out".equals(parser.getName())) {
                        bikeSiteStr = parser.nextText();
                        break;
                    }
                    //Log.d("DashBoardModel", "name = " + parser.getName());
                }
                eventType = parser.next();
            }

            //Log.d("DashBoardModel ", bikeSiteStr);
            bikeSites.clear();
            if(bikeSiteStr != null && bikeSiteStr.length() > 0){
                JSONArray jsonArray = new JSONArray(bikeSiteStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    BikeSite site = new BikeSite();
                    site.fromJSonPacket(jsonArray.getJSONObject(i));

                    //Log.d("DashBoardModel ", "site id = " + site.getSiteId() + ", site name = " + site.getSiteName() + ", location = " + site.getLocation());
                    bikeSites.add(site);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public BikeSite getSite(double latitude, double longitude){
        BikeSite bikeSite = null;
        for(BikeSite site : bikeSites){
            if((site.getLatitude() == latitude) && (site.getLongitude() == longitude)){
                bikeSite = site;
                break;
            }
        }
        return bikeSite;
    }
}
