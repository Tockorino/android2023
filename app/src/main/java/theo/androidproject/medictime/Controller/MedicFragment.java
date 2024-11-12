package theo.androidproject.medictime.Controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import theo.androidproject.medictime.Db.MedicBaseHelper;
import theo.androidproject.medictime.Db.MedicDbSchema;
import theo.androidproject.medictime.R;

public class MedicFragment extends Fragment {

    private AutoCompleteTextView medicNameAutoComplete;
    private TextView startDateText;
    private TextView endDateText;
    private CheckBox morningCheckBox;
    private CheckBox noonCheckBox;
    private CheckBox eveningCheckBox;
    private SQLiteDatabase database;
    private Calendar calendar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medic, container, false);
        Button addMedicButton = view.findViewById(R.id.AddMedicButton);
        Button validButton = view.findViewById(R.id.validButton);
        medicNameAutoComplete = view.findViewById(R.id.MedicNameAutoComplete);
        startDateText = view.findViewById(R.id.startDateText);
        endDateText = view.findViewById(R.id.endDateText);
        morningCheckBox = view.findViewById(R.id.MorningCheckBox);
        noonCheckBox = view.findViewById(R.id.NoonCheckBox);
        eveningCheckBox = view.findViewById(R.id.EveningCheckBox);

        // Initialiser la base de données
        MedicBaseHelper dbHelper = new MedicBaseHelper(getContext());
        database = dbHelper.getReadableDatabase();

        // Remplir AutoCompleteTextView avec les noms des médicaments
        fillMedicNames();

        medicNameAutoComplete.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedMedic = (String) parent.getItemAtPosition(position);
            fillMedicDetails(selectedMedic);
        });

        // Définir les écouteurs de clic sur les TextView pour afficher un calendrier
        startDateText.setOnClickListener(v -> showDatePickerDialog(startDateText));
        endDateText.setOnClickListener(v -> showDatePickerDialog(endDateText));

        addMedicButton.setOnClickListener(v -> openAddMedicFragment());
        validButton.setOnClickListener(v ->{saveIntakeDetails(); openMedicListFragment();});

        return view;
    }

    private void showDatePickerDialog(final TextView dateTextView) {
        // Initialiser le calendrier avec la date actuelle
        calendar = Calendar.getInstance();

        // Créer un DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Mettre à jour le TextView avec la date sélectionnée
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    dateTextView.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Afficher le DatePickerDialog
        datePickerDialog.show();
    }

    private void fillMedicNames() {
        Cursor cursor = null;
        try {
            cursor = database.query("medic", new String[]{"name"}, null, null, null, null, null);
            ArrayList<String> medicNames = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    medicNames.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                } while (cursor.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, medicNames);
            medicNameAutoComplete.setAdapter(adapter);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void fillMedicDetails(String medicName) {
        String selection = "name = ?";
        String[] selectionArgs = { medicName };
        Cursor cursor = null;
        try {
            cursor = database.query("medic", null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                boolean isMorning = cursor.getInt(cursor.getColumnIndexOrThrow("morning")) > 0;
                boolean isNoon = cursor.getInt(cursor.getColumnIndexOrThrow("noon")) > 0;
                boolean isEvening = cursor.getInt(cursor.getColumnIndexOrThrow("evening")) > 0;

                // Remplir les champs correspondants
                String startDate = getCurrentDate();
                startDateText.setText(startDate);
                String endDate = calculateEndDate(startDate, duration);
                endDateText.setText(endDate);

                morningCheckBox.setChecked(isMorning);
                noonCheckBox.setChecked(isNoon);
                eveningCheckBox.setChecked(isEvening);
            } else {
                // Si aucun médicament n'a été trouvé
                startDateText.setText("");
                endDateText.setText("");
                morningCheckBox.setChecked(false);
                noonCheckBox.setChecked(false);
                eveningCheckBox.setChecked(false);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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

    private void saveIntakeDetails() {
        String medicName = medicNameAutoComplete.getText().toString();
        if (medicName.isEmpty()) {
            // Assurez-vous qu'un médicament est sélectionné
            return;
        }

        // Récupérer l'ID du médicament dans la table medic
        String medicUuid = getMedicUuid(medicName);
        if (medicUuid == null) {
            // Aucun médicament trouvé
            return;
        }

        String startDate = startDateText.getText().toString();
        String endDate = endDateText.getText().toString();
        boolean isMorning = morningCheckBox.isChecked();
        boolean isNoon = noonCheckBox.isChecked();
        boolean isEvening = eveningCheckBox.isChecked();

        // Insertion dans la table intake_medic
        ContentValues contentValues = new ContentValues();
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.MEDIC_ID, medicUuid);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.NAME, medicName);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN, startDate);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.DATE_END, endDate);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.MORNING, isMorning ? 1 : 0);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.NOON, isNoon ? 1 : 0);
        contentValues.put(MedicDbSchema.IntakeMedicTable.cols.EVENING, isEvening ? 1 : 0);

        long newRowId = database.insert(MedicDbSchema.IntakeMedicTable.NAME, null, contentValues);
        if (newRowId == -1) {
            // Gérer l'erreur si l'insertion a échoué
        } else {
            // Succès
        }
    }

    private String getMedicUuid(String medicName) {
        String selection = MedicDbSchema.MedicTable.cols.NAME + " = ?";
        String[] selectionArgs = {medicName};
        Cursor cursor = null;
        try {
            cursor = database.query(MedicDbSchema.MedicTable.NAME, new String[]{MedicDbSchema.MedicTable.cols.UUID}, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(MedicDbSchema.MedicTable.cols.UUID));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}