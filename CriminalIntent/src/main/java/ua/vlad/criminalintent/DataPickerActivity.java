package ua.vlad.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

public class DataPickerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        return DatePickerFragment.newInstance(date);
    }

    public static Intent newIntent(Context packageContext, Date date) {
        Intent i = new Intent(packageContext, DataPickerActivity.class);
        i.putExtra(DatePickerFragment.EXTRA_DATE, date);
        return i;
    }

}
