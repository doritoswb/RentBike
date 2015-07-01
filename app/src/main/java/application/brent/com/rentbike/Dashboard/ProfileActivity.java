package application.brent.com.rentbike.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import application.brent.com.rentbike.R;
import application.brent.com.rentbike.base.BaseActivity;
import application.brent.com.rentbike.base.BaseModel;

/**
 * Created by bwu on 2015/4/16.
 */
public class ProfileActivity extends BaseActivity {

    public static enum From {
        profileVersion, profileGetCard, profileServiceTime, profileChargeStandard, profileFrequentQuestions,
        profileRentHistory
    }

    public static enum Key {
        fromType
    }

    private LinearLayout profileRoot;
    private TextView commonTitleTextView;
    private ScrollView scrollView;
    private TextView textView;
    private ImageView imageView;

    public static void execute(DashBoardActivity activity, From from){
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(Key.fromType.name(), from.name());
        activity.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilecontent);

        profileRoot = (LinearLayout) this.findViewById(R.id.profile_root);
        commonTitleTextView = (TextView) this.findViewById(R.id.commonTitleTextView);
        scrollView = (ScrollView) this.findViewById(R.id.scrollView);
        textView = (TextView) this.findViewById(R.id.contentTextView);
        imageView = (ImageView) this.findViewById(R.id.imageView);

        String fromType = this.getIntent().getStringExtra(Key.fromType.name());
        From from = From.valueOf(fromType);
        switch (from){
            case profileVersion:
                commonTitleTextView.setText(R.string.profile_version);
                textView.setText(R.string.profile_product_info);
                break;
            case profileGetCard:
                commonTitleTextView.setText(R.string.profile_get_card);
                textView.setText(R.string.profile_get_card_content);
                break;
            case profileServiceTime:
                commonTitleTextView.setText(R.string.profile_service_time);
                textView.setText(R.string.profile_service_time_content);
                break;
            case profileChargeStandard:
                commonTitleTextView.setText(R.string.profile_charge_standard);
                textView.setText(R.string.profile_charge_standard_content);
                break;
            case profileFrequentQuestions:
                commonTitleTextView.setText(R.string.profile_frequent_questions);
                textView.setText(R.string.profile_frequent_questions_content);
                break;
            case profileRentHistory:
                commonTitleTextView.setText(R.string.profile_rent_history);
                scrollView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                profileRoot.setBackgroundColor(Color.parseColor("#FFF5F5F5"));
                break;
        }
    }

    protected BaseModel createModel(){
        return null;
    }

    protected boolean onPreExecute(String action){
        return false;
    }

    protected void onPostExecuteSuccessful(String action){
    }
}
