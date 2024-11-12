package theo.androidproject.medictime.Controller;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import theo.androidproject.medictime.Db.MedicDbSchema;
import theo.androidproject.medictime.Models.Medic;
import theo.androidproject.medictime.Models.MedicList;
import theo.androidproject.medictime.R;

public class AddMedicFragment extends Fragment {
    private EditText mNameEditText;
    private Spinner mDurationSpinner;
    private Button mSaveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addmedic, container, false);

        mNameEditText = view.findViewById(R.id.MedicNameEditText);
        mDurationSpinner = view.findViewById(R.id.DurationSpinner);
        mSaveButton = view.findViewById(R.id.AddMedicButton);

        setupDurationSpinner();

        mSaveButton.setOnClickListener(v -> saveMedic());

        return view;
    }

    private void setupDurationSpinner() {
        List<String> durationList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            durationList.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, durationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDurationSpinner.setAdapter(adapter);
    }

    private void saveMedic() {
        String name = mNameEditText.getText().toString();
        String duration = mDurationSpinner.getSelectedItem().toString(); // Get the selected duration

        if (name.isEmpty() || duration.isEmpty()) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer l'état des cases à cocher
        boolean morningChecked = ((CheckBox) getView().findViewById(R.id.MatinCheckBox)).isChecked();
        boolean noonChecked = ((CheckBox) getView().findViewById(R.id.MidiCheckBox)).isChecked();
        boolean eveningChecked = ((CheckBox) getView().findViewById(R.id.SoirCheckBox)).isChecked();

        Medic medic = new Medic(UUID.randomUUID());
        medic.setName(name);
        medic.setDuration(duration);
        medic.setMorning(morningChecked); // Met à jour l'état du matin
        medic.setNoon(noonChecked); // Met à jour l'état de midi
        medic.setEvening(eveningChecked); // Met à jour l'état du soir

        MedicList medicList = MedicList.get(getContext());
        ContentValues values = new ContentValues();
        values.put(MedicDbSchema.MedicTable.cols.UUID, medic.getID().toString());
        values.put(MedicDbSchema.MedicTable.cols.NAME, medic.getName());
        values.put(MedicDbSchema.MedicTable.cols.DURATION, medic.getDuration());
        values.put(MedicDbSchema.MedicTable.cols.MORNING, morningChecked ? 1 : 0); // Stocke 1 si coché, sinon 0
        values.put(MedicDbSchema.MedicTable.cols.NOON, noonChecked ? 1 : 0); // Idem pour midi
        values.put(MedicDbSchema.MedicTable.cols.EVENING, eveningChecked ? 1 : 0); // Idem pour soir

        medicList.addMedic(values);
        Toast.makeText(getContext(), "Médicament ajouté à la liste", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

}