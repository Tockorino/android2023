package theo.androidproject.medictime.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import theo.androidproject.medictime.Db.MedicBaseHelper;
import theo.androidproject.medictime.Db.MedicCursorWrapper;
import theo.androidproject.medictime.Db.MedicDbSchema;

public class IntakeMedicList {
    private static IntakeMedicList sIntakeMedicList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static IntakeMedicList get(Context context) {
        if (sIntakeMedicList == null) {
            sIntakeMedicList = new IntakeMedicList(context);
        }
        return sIntakeMedicList;
    }

    private IntakeMedicList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MedicBaseHelper(mContext).getWritableDatabase();
    }

    public List<IntakeMedic> getAllIntakeMedics() {
        List<IntakeMedic> list = new ArrayList<>();
        MedicCursorWrapper cursor = queryIntakeMedics(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getIntakeMedic());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<IntakeMedic> getAllIntakeMedicsByDate(String date) {
        List<IntakeMedic> list = new ArrayList<>();
        Log.i("IntakeMedicList", "getAllIntakeMedicsByDate: date = " + date);
        String whereClause = "date_begin <= ? AND date_end >= ?";
        MedicCursorWrapper cursor = queryIntakeMedics(whereClause, new String[]{date, date});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getIntakeMedic());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void addIntakeMedic(ContentValues values) {
        mDatabase.insert(MedicDbSchema.IntakeMedicTable.NAME, null, values);
    }

    public void updateIntakeMedic(IntakeMedic intakeMedic, ContentValues values) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{intakeMedic.getID().toString()};
        mDatabase.update(MedicDbSchema.IntakeMedicTable.NAME, values, whereClause, whereArgs);
    }

    private MedicCursorWrapper queryIntakeMedics(String whereClause, String[] whereArgs) {
        return new MedicCursorWrapper(mDatabase.query(
                MedicDbSchema.IntakeMedicTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        ));
    }
}
