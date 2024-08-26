package theo.androidproject.medictime.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import theo.androidproject.medictime.R;
import theo.androidproject.medictime.Models.MedicItem;

public class MedicListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MedicAdapter mAdapter;
    private List<MedicItem> mMedicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medic_list, container, false);
        mRecyclerView = view.findViewById(R.id.medic_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Exemple de données
        mMedicList = new ArrayList<>();
        mMedicList.add(new MedicItem("2024-08-25", "Paracetamol, Vitamines", "Duspatalin", "Strepsils"));

        mAdapter = new MedicAdapter(mMedicList);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
}