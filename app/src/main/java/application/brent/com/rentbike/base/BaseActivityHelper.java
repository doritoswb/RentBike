package application.brent.com.rentbike.base;

import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import application.brent.com.rentbike.ResponseStatus;

/**
 * Created by bwu on 2015/4/12.
 */
public class BaseActivityHelper {
    private BaseActivity baseActivity;
    private HashMap<String, Loader> asyncTaskHashMap = new HashMap<String, Loader>();

    public BaseActivityHelper(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    void postAsync(final BaseModel model, final String action) {
        final boolean isPrepareOk = this.baseActivity.onPreExecute(action);
        Loader loader = new AsyncTaskLoader<ResponseStatus>(this.baseActivity.getApplicationContext()) {

            private AtomicBoolean isCancelled = new AtomicBoolean(false);

            @Override
            public ResponseStatus loadInBackground() {
                if (!isPrepareOk) {
                    ResponseStatus responseStatus = new ResponseStatus();
                    responseStatus.setPrepareOK(false);
                    return responseStatus;
                }

                if (model != null) {
                    ResponseStatus responseStatus = model.baseDoInBackground(action);

                    boolean isFinishing = baseActivity.isFinishing();
                    boolean isChangingConfigurations = baseActivity.isChangingConfigurations();
                    long t = System.currentTimeMillis();
                    while ((isFinishing || isChangingConfigurations) && (System.currentTimeMillis() - t) < 3000) {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            Log.i(this.getClass().getName(), "postAsync failed." + e);
                        }
                    }

                    return responseStatus;
                }

                return null;
            }

            @Override
            public void deliverResult(ResponseStatus result) {
                if(!isCancelled.get()){
                    onActivityPostExecute(action, result);
                }

                asyncTaskHashMap.remove(action);
            }

            @Override
            public void onCanceled(ResponseStatus result) {
                asyncTaskHashMap.remove(action);
                isCancelled.set(true);
            }
        };

        asyncTaskHashMap.put(action, loader);

        loader.forceLoad();
    }

    final void onActivityPostExecute(String action, ResponseStatus result) {
        //long i = Thread.currentThread().getId();
/*        Log.d("DashBoardModel", "thread id = " + i);
        Log.d("DashBoardModel", "responseStatus.status = " + result.getStatus());
        Log.d("DashBoardModel", "responseStatus.message = " + result.getErrorMessage());
        Log.d("DashBoardModel", "responseStatus.isPrepareOK = " + result.isPrepareOK());*/
        if (result != null) {
            if (result.getErrorMessage() != null || !result.isPrepareOK()) {
                if (baseActivity.needShowErrorToast(action) && result.getErrorMessage() != null) {
                    Toast toast = Toast.makeText(baseActivity.getApplicationContext(), result.getErrorMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                baseActivity.performPostExecuteFailed(action);
            } else {
                baseActivity.performPostExecuteSuccessful(action);
            }
        } else {
            baseActivity.performPostExecuteSuccessful(action);
        }
    }

    void cancelAsync(String action) {
        if (asyncTaskHashMap.containsKey(action)) {
            Loader loader = asyncTaskHashMap.get(action);
            if (loader != null) {
                ((AsyncTaskLoader) loader).cancelLoad();
            }
            asyncTaskHashMap.remove(action);
        }
    }

    boolean isAsyncCancelled(String action) {
        return !asyncTaskHashMap.containsKey(action);
    }
}
