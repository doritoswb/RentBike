package application.brent.com.rentbike.Netwrok;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by bwu on 2015/4/13.
 */
public class HttpRequest {

    public static NetworkResponse sendRequest(URL url, byte[] requestBody) {
        NetworkResponse response = new NetworkResponse();

        if (url == null) {
            response.status = NetworkResponse.Status.failed;
            response.errorMessage = "无法连接到服务器……";
            return response;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);// 允许输出
            conn.setDoInput(true);
            conn.setUseCaches(false);// 不使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(requestBody.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("X-ClientType", "2");//发送自定义的头信息

            conn.getOutputStream().write(requestBody);
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            if (conn.getResponseCode() != 200) {
                response.status = NetworkResponse.Status.failed;
                response.errorMessage = conn.getResponseMessage();
                return response;
            }

            InputStream is = conn.getInputStream();// 获取返回数据

            // 使用输出流来输出字符(可选)
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            response.status = NetworkResponse.Status.success;
            response.data = out.toByteArray();

            //String string = out.toString("UTF-8");
            //System.out.println(string);

            out.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
