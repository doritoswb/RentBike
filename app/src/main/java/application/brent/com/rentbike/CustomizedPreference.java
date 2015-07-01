package application.brent.com.rentbike;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bwu on 2015/4/16.
 */

import android.widget.TextView;


public class CustomizedPreference extends Preference {

    public CustomizedPreference(Context context) {
        super(context);
        this.setLayoutResource(R.layout.profilecumtomizedpreference);
    }

    public CustomizedPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomizedPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);

        assert view != null;
        return view;
    }
}


