package application.brent.com.rentbike.Dashboard;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import application.brent.com.rentbike.R;

/**
 * Created by bwu on 2015/4/12.
 */
public class ProfileFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int profileFileName = this.getArguments().getInt(DashBoardActivity.Keys.profileFileName.name());
        addPreferencesFromResource(profileFileName);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        ProfileActivity.From from = ProfileActivity.From.profileGetCard;
        if(getResources().getString(R.string.profile_key_version).equals(preference.getKey())){
            from = ProfileActivity.From.profileVersion;
        } else if (getResources().getString(R.string.profile_key_get_card).equals(preference.getKey())) {
            from = ProfileActivity.From.profileGetCard;
        } else if (getResources().getString(R.string.profile_key_service_time).equals(preference.getKey())) {
            from = ProfileActivity.From.profileServiceTime;
        } else if (getResources().getString(R.string.profile_key_charge_standard).equals(preference.getKey())) {
            from = ProfileActivity.From.profileChargeStandard;
        } else if (getResources().getString(R.string.profile_key_frequent_questions).equals(preference.getKey())) {
            from = ProfileActivity.From.profileFrequentQuestions;
        } else if (getResources().getString(R.string.profile_key_version).equals(preference.getKey())){
            return true;
        } else if (getResources().getString(R.string.profile_key_rent_history).equals(preference.getKey())) {
            from = ProfileActivity.From.profileRentHistory;
        }

        ProfileActivity.execute((DashBoardActivity) this.getActivity(), from);
        return true;
    }
}
