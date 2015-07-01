package application.brent.com.rentbike;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import application.brent.com.rentbike.Dashboard.BikeSite;
import application.brent.com.rentbike.Dashboard.OfflineBikeSite;
import application.brent.com.rentbike.base.BaseActivity;

/**
 * Created by bwu on 2015/4/18.
 */
public class Xmlutility {

    private static int count = 0;
    /** Dom方式，解析 XML, 拿到Offline Bike sites*/
    public static ArrayList<BikeSite> getOfflineBikeSites(BaseActivity activity, String fileName) throws Exception {
        ArrayList<BikeSite> sites = null;

        InputStream xml = activity.getResources().getAssets().open(fileName);;
        BikeSite site = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xml, "UTF-8"); //为Pull解释器设置要解析的XML数据
        int event = pullParser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT){
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    sites = new ArrayList<BikeSite>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("dict".equals(pullParser.getName())){
                        site = new BikeSite();
                        count = 0;
                    }
                    if ("key".equals(pullParser.getName())){
                        count++;
                    }
                    if ("string".equals(pullParser.getName())){
                        switch (count){
                            case 1:
                                double latitude = Double.valueOf(pullParser.nextText());
                                site.setLatitude(latitude);
                                break;
                            case 2:
                                String location = pullParser.nextText();
                                site.setLocation(location);
                                break;
                            case 3:
                                double longitude = Double.valueOf(pullParser.nextText());
                                site.setLongitude(longitude);
                                break;
                            case 4:
                                int siteId = Integer.valueOf(pullParser.nextText());
                                site.setSiteId(siteId);
                                break;
                            case 5:
                                String siteName = pullParser.nextText();
                                site.setSiteName(siteName);
                                break;
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if ("dict".equals(pullParser.getName())){
                        sites.add(site);
                        site = null;
                    }
                    break;

            }
            event = pullParser.next();
        }
        return sites;
    }
}
