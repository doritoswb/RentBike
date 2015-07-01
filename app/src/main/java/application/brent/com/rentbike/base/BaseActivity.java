package application.brent.com.rentbike.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

/**
 * Created by bwu on 2015/4/12.
 */
public abstract class BaseActivity extends Activity {

    private BaseModel model;
    private BaseActivityHelper helper;

    public BaseActivity() {
        this.helper = new BaseActivityHelper(this);
        this.model = createModel();
    }

    public BaseModel getModel() {
        return this.model;
    }

    protected abstract BaseModel createModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (this.model != null) {
            this.model.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.model != null) {
            this.model.onDestroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public BaseActivity getActivity() {
        return this;
    }

    /**
     * AsyncTask operations for any operation that is expensive and could block UI Thread.
     * The asynctask may in turn invoke service task to communicate with backend service.
     *
     * @param action action name.
     */
    public final void postAsync(final String action) {
        final BaseModel bm = this.model;
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            this.helper.postAsync(bm, action);
        } else {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    helper.postAsync(bm, action);
                }
            });
        }
    }

    public final void cancelAsync(String action) {
        this.helper.cancelAsync(action);
    }

    public final boolean isAsyncCancelled(String action) {
        return this.helper.isAsyncCancelled(action);
    }

    protected abstract boolean onPreExecute(String action);


    final public void performPostExecuteSuccessful(String action){
        Log.d("BaseActivity", "performPostExecuteSuccessful");
        this.onPostExecuteSuccessful(action);
    }

    protected abstract void onPostExecuteSuccessful(String action);

    final public void performPostExecuteFailed(String action){
        this.onPostExecuteFailed(action);
    }

    protected void onPostExecuteFailed(String action) {
    }

    public boolean needShowErrorToast(String action) {
        return false;
    }
}
