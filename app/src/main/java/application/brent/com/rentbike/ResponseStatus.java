package application.brent.com.rentbike;

/**
 * Created by bwu on 2015/4/12.
 */
public class ResponseStatus {

    public static enum Status{
        OK,
        Error
    }

    private boolean isPrepareOK = true;
    private String errorMessage = null;
    private Status status;

    public ResponseStatus() {
        status = Status.Error;
    }

    public void setPrepareOK(boolean isPrepareOK){
        this.isPrepareOK = isPrepareOK;
    }

    public boolean isPrepareOK(){
        return this.isPrepareOK;
    }

    public void setErrorMessage(String message){
        this.errorMessage = message;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus(){
        return this.status;
    }
}
