package theo.androidproject.medictime.Controller;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import theo.androidproject.medictime.Db.MedicDbSchema;
import theo.androidproject.medictime.Models.IntakeMedic;
import theo.androidproject.medictime.Models.IntakeMedicList;
import theo.androidproject.medictime.R;

public class MedicAddFragmentToList extends Fragment {
    private EditText mMedicNameEditText;
    private Spinner mDurationSpinner;
    private CheckBox mMorningCheckBox;
    private CheckBox mNoonCheckBox;
    private CheckBox mEveningCheckBox;
    private Button mAddMedicButton;
    private UUID mMedicID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addmedic, container, false);
        mMedicNameEditText = view.findViewById(R.id.MedicNameEditText);
        mDurationSpinner = view.findViewById(R.id.DurationSpinner);
        mMorningCheckBox = view.findViewById(R.id.MatinCheckBox);
        mNoonCheckBox = view.findViewById(R.id.MidiCheckBox);
        mEveningCheckBox = view.findViewById(R.id.SoirCheckBox);
        mAddMedicButton = view.findViewById(R.id.AddMedicButton);

        mAddMedicButton.setOnClickListener(v -> saveIntakeMedic());

        return view;
    }

    private void saveIntakeMedic() {
        String medicName = mMedicNameEditText.getText().toString();
        String duration = mDurationSpinner.getSelectedItem().toString();
        boolean isMorning = mMorningCheckBox.isChecked();
        boolean isNoon = mNoonCheckBox.isChecked();
        boolean isEvening = mEveningCheckBox.isChecked();

        if (medicName.isEmpty()) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        IntakeMedic intakeMedic = new IntakeMedic();
        intakeMedic.setMedicID(UUID.randomUUID());
        intakeMedic.setName(medicName);
        intakeMedic.setDuration(duration);
        intakeMedic.setMorning(isMorning);
        intakeMedic.setNoon(isNoon);
        intakeMedic.setEvening(isEvening);

        IntakeMedicList intakeMedicList = IntakeMedicList.get(getContext());
        ContentValues values = new ContentValues();
        values.put(MedicDbSchema.IntakeMedicTable.cols.MEDIC_ID, intakeMedic.getMedicID().toString());
        values.put(MedicDbSchema.IntakeMedicTable.cols.NAME, intakeMedic.getName());
        values.put(MedicDbSchema.IntakeMedicTable.cols.DURATION, intakeMedic.getDuration());
        values.put(MedicDbSchema.IntakeMedicTable.cols.MORNING, intakeMedic.isMorning());
        values.put(MedicDbSchema.IntakeMedicTable.cols.NOON, intakeMedic.isNoon());
        values.put(MedicDbSchema.IntakeMedicTable.cols.EVENING, intakeMedic.isEvening());

        intakeMedicList.addIntakeMedic(values);

        // Envoyer les informations à MedicFragment
        Bundle bundle = new Bundle();
        bundle.putString("medic_name", medicName);
        bundle.putString("duration", duration);
        bundle.putBoolean("isMorning", isMorning);
        bundle.putBoolean("isNoon", isNoon);
        bundle.putBoolean("isEvening", isEvening);

        MedicFragment medicFragment = new MedicFragment();
        medicFragment.setArguments(bundle);

        // Remplacer le fragment actuel par MedicFragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, medicFragment)
                .addToBackStack(null)
                .commit();
        Toast.makeText(getContext(), "Médicament ajouté", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
