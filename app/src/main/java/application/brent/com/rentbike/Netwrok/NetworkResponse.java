package application.brent.com.rentbike.Netwrok;

/**
 * Created by bwu on 2015/4/13.
 */

public class NetworkResponse {
    public static enum Status{
        success,
        failed
    }

    public byte[] data;
    public String errorMessage;
    public Status status;
    public long contentLength;

    public NetworkResponse() {
        data = null;
        status = Status.failed;
    }
}
