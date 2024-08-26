package theo.androidproject.medictime.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import theo.androidproject.medictime.Db.MedicBaseHelper;
import theo.androidproject.medictime.Db.MedicCursorWrapper;
import theo.androidproject.medictime.Db.MedicDbSchema;

public class MedicList {
    private static MedicList sMedicList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MedicList get(Context context) {
        if (sMedicList == null) {
            sMedicList = new MedicList(context);
        }
        return sMedicList;
    }

    private MedicList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MedicBaseHelper(mContext).getWritableDatabase();
    }

    public Medic getMedicById(UUID id) {
        MedicCursorWrapper cursor = queryMedics(" _id = ?", new String[]{id.toString()});
        cursor.moveToFirst();
        Medic medic = cursor.getMedic();
        cursor.close();
        return medic;
    }

    public Medic getMedicByName(String name) {
        MedicCursorWrapper cursor = queryMedics(MedicDbSchema.MedicTable.cols.NAME + " = ?", new String[]{name});
        cursor.moveToFirst();
        Medic medic = cursor.getMedic();
        cursor.close();
        return medic;
    }

    public void addMedic(ContentValues values) {
        mDatabase.insert(MedicDbSchema.MedicTable.NAME, null, values);
    }

    public List<Medic> getAllMedics() {
        List<Medic> list = new ArrayList<>();
        MedicCursorWrapper cursor = queryMedics(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getMedic());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void updateMedic(Medic medic, ContentValues values) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{medic.getID().toString()};
        mDatabase.update(MedicDbSchema.MedicTable.NAME, values, whereClause, whereArgs);
    }

    private MedicCursorWrapper queryMedics(String whereClause, String[] whereArgs) {
        return new MedicCursorWrapper(mDatabase.query(
                MedicDbSchema.MedicTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        ));
    }
}