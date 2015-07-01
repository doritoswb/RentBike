package application.brent.com.rentbike;

/**
 * Created by bwu on 2015/4/15.
 */

import android.preference.PreferenceCategory;
import android.view.View;
import android.widget.TextView;

public class CustomizedPreferenceCategory extends PreferenceCategory {

    public CustomizedPreferenceCategory(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomizedPreferenceCategory(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizedPreferenceCategory(android.content.Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        if(view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(getContext().getResources().getColor(R.color.profileCategoryTitleColor));
            tv.setTextSize(20);
        }
    }

}

