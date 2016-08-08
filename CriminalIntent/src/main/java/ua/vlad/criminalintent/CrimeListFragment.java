package ua.vlad.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView recyclerViewCrime;
    private CrimeAdapter crimeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        recyclerViewCrime = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        recyclerViewCrime.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        crimeAdapter = new CrimeAdapter(crimes);
        recyclerViewCrime.setAdapter(crimeAdapter);
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder crimeHolder, int i) {
            Crime crime = crimes.get(i);
            crimeHolder.textViewTitle.setText(crime.getTitle());
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;

        public CrimeHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView;
        }
    }
}
