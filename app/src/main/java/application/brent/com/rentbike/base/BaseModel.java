package application.brent.com.rentbike.base;

import application.brent.com.rentbike.ResponseStatus;

/**
 * Created by bwu on 2015/4/12.
 */
public abstract class BaseModel {
    private BaseActivity activity;

    public BaseModel(BaseActivity activity) {
        this.activity = activity;
    }

    public BaseActivity getActivity(){
        return this.activity;
    }
    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onDestroy() {

    }

    final ResponseStatus baseDoInBackground(String action) {
        ResponseStatus responseStatus = doInBackground(action);
        return responseStatus;
    }

    protected abstract ResponseStatus doInBackground(String action);
}
