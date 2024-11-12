package theo.androidproject.medictime.Controller;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import theo.androidproject.medictime.Db.MedicBaseHelper;
import theo.androidproject.medictime.Db.MedicDbSchema;
import theo.androidproject.medictime.Models.MedicItem;
import theo.androidproject.medictime.R;

public class MedicListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MedicAdapter mAdapter;
    private List<MedicItem> mMedicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medic_list, container, false);
        mRecyclerView = view.findViewById(R.id.medic_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Remplir mMedicList avec les données de la base de données
        mMedicList = getMedicDataWithMedications();

        mAdapter = new MedicAdapter(mMedicList);
        mRecyclerView.setAdapter(mAdapter);

        // Utilisation de findViewById depuis la vue du fragment
        Button openMedicFragmentButton = view.findViewById(R.id.openMedicFragmentButton);
        openMedicFragmentButton.setOnClickListener(v -> {
            // Créer une instance de MedicFragment
            MedicFragment medicFragment = new MedicFragment();

            // Remplacer le fragment actuel par MedicFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, medicFragment) // Assurez-vous que fragment_container existe dans votre activité
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private List<MedicItem> getMedicDataWithMedications() {
        List<MedicItem> medicList = new ArrayList<>();
        MedicBaseHelper dbHelper = new MedicBaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                MedicDbSchema.IntakeMedicTable.NAME,
                null,
                null,
                null,
                null,
                null,
                MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN + " ASC" // Ordonner par date
        );

        try {
            // Utiliser une TreeMap pour trier automatiquement par date
            TreeMap<String, MedicItem> groupedData = new TreeMap<>();

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String dateBegin = cursor.getString(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN));
                    @SuppressLint("Range") String dateEnd = cursor.getString(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.DATE_END));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.NAME));
                    @SuppressLint("Range") int morning = cursor.getInt(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.MORNING));
                    @SuppressLint("Range") int noon = cursor.getInt(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.NOON));
                    @SuppressLint("Range") int evening = cursor.getInt(cursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.EVENING));

                    // Générer toutes les dates entre dateBegin et dateEnd
                    List<String> dates = getDatesBetween(dateBegin, dateEnd);
                    for (String date : getDatesBetween(dateBegin, dateEnd)) {
                        MedicItem medicItem;
                        if (groupedData.containsKey(date)) {
                            medicItem = groupedData.get(date);
                        } else {
                            medicItem = new MedicItem(date,"", "", "", "");
                            groupedData.put(date, medicItem);
                        }

                        // Ajouter les médicaments dans les sections appropriées
                        if (morning == 1) {
                            medicItem.setMorningMedications(medicItem.getMorning() + (medicItem.getMorning().isEmpty() ? "" : "\n") + name);
                        }
                        if (noon == 1) {
                            medicItem.setNoonMedications(medicItem.getNoon() + (medicItem.getNoon().isEmpty() ? "" : "\n") + name);
                        }
                        if (evening == 1) {
                            medicItem.setEveningMedications(medicItem.getEvening() + (medicItem.getEvening().isEmpty() ? "" : "\n") + name);
                        }
                    }

                } while (cursor.moveToNext());
            }

            medicList.addAll(groupedData.values());

        } finally {
            cursor.close();
            db.close();
        }

        return medicList;
    }

    private List<String> getDatesBetween(String startDate, String endDate) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            // Ajouter la date de début à la liste
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (!calendar.getTime().after(end)) {
                dates.add(sdf.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1); // ajouter un jour
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }
}