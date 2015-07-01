package application.brent.com.rentbike.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import application.brent.com.rentbike.Dashboard.DashBoardActivity;
import application.brent.com.rentbike.base.BaseActivity;
import application.brent.com.rentbike.base.BaseModel;
import application.brent.com.rentbike.R;

/**
 * Created by bwu on 2015/4/12.
 */
public class SplashActivity extends BaseActivity {

    private final static String TAG = "SplashActivity";
    private RelativeLayout rootView;
    //private Button enterApp;

/*    public static boolean execute(Activity ownerActivity) {
        Intent intent = new Intent(ownerActivity, SplashActivity.class);
        ownerActivity.startActivity(intent);

        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

/*        enterApp = (Button) this.findViewById(R.id.enter_app);
        enterApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.execute(SplashActivity.this);
            }
        });*/

/*        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }*/

        rootView = (RelativeLayout) this.findViewById(R.id.rootView);
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DashBoardActivity.execute(SplashActivity.this);
            }
        }, 2000);

        //this.finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected BaseModel createModel() {
        return new SplashModel(this);
    }

    @Override
    protected boolean onPreExecute(String action) {
        return true;
    }

    @Override
    protected void onPostExecuteSuccessful(String action) {
    }

    @Override
    protected void onPostExecuteFailed(String action){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
