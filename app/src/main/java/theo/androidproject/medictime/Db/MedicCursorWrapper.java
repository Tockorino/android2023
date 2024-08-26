package theo.androidproject.medictime.Db;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import theo.androidproject.medictime.Models.IntakeMedic;
import theo.androidproject.medictime.Models.Medic;

public class MedicCursorWrapper extends CursorWrapper {

    private Cursor mCursor;

    public MedicCursorWrapper(Cursor cursor) {
        super(cursor);
        mCursor = cursor;
    }

    public Medic getMedic() {
        @SuppressLint("Range") String uuidString = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.UUID));
        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.NAME));
        @SuppressLint("Range") String duration = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.DURATION));
        @SuppressLint("Range") boolean morning = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.MORNING)) > 0;
        @SuppressLint("Range") boolean noon = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.NOON)) > 0;
        @SuppressLint("Range") boolean evening = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.MedicTable.cols.EVENING)) > 0;

        Medic medic = new Medic(UUID.fromString(uuidString));
        medic.setName(name);
        medic.setDuration(duration);
        medic.setMorning(morning);
        medic.setNoon(noon);
        medic.setEvening(evening);

        return medic;
    }

    public IntakeMedic getIntakeMedic() {
        @SuppressLint("Range") String uuidString = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.MEDIC_ID));
        UUID medicID = UUID.fromString(uuidString);
        @SuppressLint("Range") String dateBegin = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN));
        @SuppressLint("Range") String dateEnd = mCursor.getString(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.DATE_END));
        @SuppressLint("Range") boolean morning = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.MORNING)) > 0;
        @SuppressLint("Range") boolean noon = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.NOON)) > 0;
        @SuppressLint("Range") boolean evening = mCursor.getInt(mCursor.getColumnIndex(MedicDbSchema.IntakeMedicTable.cols.EVENING)) > 0;

        IntakeMedic intakeMedic = new IntakeMedic();
        intakeMedic.setMedicID(medicID);
        intakeMedic.setDateBegin(dateBegin);
        intakeMedic.setDateEnd(dateEnd);
        intakeMedic.setMorning(morning);
        intakeMedic.setNoon(noon);
        intakeMedic.setEvening(evening);

        return intakeMedic;
    }

    public void close() {
        mCursor.close();
    }
}