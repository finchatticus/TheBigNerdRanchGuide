package ua.vlad.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = DatePickerFragment.class.getCanonicalName() + ".date";

    private static final String ARG_DATE = "date";

    private DatePicker datePicker;
    private Button buttonOk;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getTargetFragment() != null) {
            getDialog().setTitle(R.string.date_picker_title);
        }

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        View v = inflater.inflate(R.layout.dialog_date, container, false);

        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        datePicker.init(year, month, day, null);

        buttonOk = (Button) v.findViewById(R.id.dialog_date_button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();
                sendResult(Activity.RESULT_OK, date);
                if(getTargetFragment() == null) {
                    getActivity().finish();
                }
                else {
                    getDialog().dismiss();
                }
            }
        });

        return v;
    }

    private void sendResult(int resultCode, Date date) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        if(getTargetFragment() == null) {
            getActivity().setResult(resultCode, intent);
        }
        else {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

}
