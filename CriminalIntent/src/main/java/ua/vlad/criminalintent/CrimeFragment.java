package ua.vlad.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;

    private Crime crime;

    private EditText editTextTitle;
    private Button buttonCrimeDate;
    private Button buttonCrimeTime;
    private CheckBox checkBoxSolved;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        editTextTitle = (EditText) v.findViewById(R.id.edit_text_crime_title);
        editTextTitle.setText(crime.getTitle());
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                crime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonCrimeDate = (Button) v.findViewById(R.id.button_crime_date);
        updateDate();
        buttonCrimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialogDate = DatePickerFragment.newInstance(crime.getDate());
                dialogDate.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialogDate.show(fragmentManager, DIALOG_DATE);
            }
        });

        buttonCrimeTime = (Button) v.findViewById(R.id.button_crime_time);
        updateTime();
        buttonCrimeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment dialogTime = TimePickerFragment.newInstance(crime.getDate());
                dialogTime.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialogTime.show(fragmentManager, DIALOG_TIME);
            }
        });

        checkBoxSolved = (CheckBox) v.findViewById(R.id.checkbox_crime_solved);
        checkBoxSolved.setChecked(crime.isSolved());
        checkBoxSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                crime.setSolved(b);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            updateDate();
        }

        if(requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            crime.setDate(date);
            updateTime();
        }
    }

    private void updateDate() {
        SimpleDateFormat formatDate = new SimpleDateFormat("E dd-MM-yyyy", Locale.ENGLISH);
        buttonCrimeDate.setText(formatDate.format(crime.getDate()));
    }

    private void updateTime() {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        buttonCrimeTime.setText(formatTime.format(crime.getDate()));
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
