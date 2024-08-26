package theo.androidproject.medictime.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import theo.androidproject.medictime.R;

public class MedicFragment extends Fragment {

    private TextView medicNameTextView;
    private TextView startDateText;
    private TextView endDateText;
    private CheckBox morningCheckBox;
    private CheckBox noonCheckBox;
    private CheckBox eveningCheckBox;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medic, container, false);
        Button addMedicButton = view.findViewById(R.id.AddMedicButton);
        Button validButton = view.findViewById(R.id.validButton);
        medicNameTextView = view.findViewById(R.id.MedicNameTextView);
        startDateText = view.findViewById(R.id.startDateText);
        endDateText = view.findViewById(R.id.endDateText);
        morningCheckBox = view.findViewById(R.id.MorningCheckBox);
        noonCheckBox = view.findViewById(R.id.NoonCheckBox);
        eveningCheckBox = view.findViewById(R.id.EveningCheckBox);

        // Récupérer les informations du médicament depuis le bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String medicName = bundle.getString("medic_name");
            String duration = bundle.getString("duration");
            boolean isMorning = bundle.getBoolean("isMorning", false);
            boolean isNoon = bundle.getBoolean("isNoon", false);
            boolean isEvening = bundle.getBoolean("isEvening", false);

            medicNameTextView.setText(medicName);
            morningCheckBox.setChecked(isMorning);
            noonCheckBox.setChecked(isNoon);
            eveningCheckBox.setChecked(isEvening);

            String startDate = getCurrentDate();
            startDateText.setText(startDate);
            String endDate = calculateEndDate(startDate, Integer.parseInt(duration));
            endDateText.setText(endDate);

        }

        addMedicButton.setOnClickListener(v -> openAddMedicFragment());

        // Configurer les écouteurs de clic
        addMedicButton.setOnClickListener(v -> openAddMedicFragment());
        validButton.setOnClickListener(v -> openMedicListFragment());

        return view;
    }

    private void openAddMedicFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        Fragment fragment = new AddMedicFragment();

        // Remplacer le fragment actuel par AddMedicFragment
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)  // Assurez-vous que l'ID est correct
                .addToBackStack(null)
                .commit();
    }

    private void sendDataToMedicListFragment() {
        // Créer un bundle avec les données du médicament
        Bundle bundle = new Bundle();
        bundle.putString("medicName", medicNameTextView.getText().toString());
        bundle.putString("startDate", startDateText.getText().toString());
        bundle.putString("endDate", endDateText.getText().toString());
        bundle.putBoolean("isMorning", morningCheckBox.isChecked());
        bundle.putBoolean("isNoon", noonCheckBox.isChecked());
        bundle.putBoolean("isEvening", eveningCheckBox.isChecked());

        // Créer une instance de MedicListFragment et lui passer les données
        MedicListFragment medicListFragment = new MedicListFragment();
        medicListFragment.setArguments(bundle);

        // Remplacer le fragment actuel par MedicListFragment
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, medicListFragment) // Assurez-vous que l'ID est correct
                .addToBackStack(null)
                .commit();
    }

    private void openMedicListFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        Fragment fragment = new MedicListFragment();

        // Remplacer le fragment actuel par MedicListFragment
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment) // Assurez-vous que l'ID est correct
                .addToBackStack(null)
                .commit();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }
    // Fonction pour calculer la date de fin en fonction de la durée
    private String calculateEndDate(String startDate, int duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, duration);
        return sdf.format(calendar.getTime());
    }
}