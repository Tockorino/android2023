package theo.androidproject.medictime.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MedicBaseHelper extends SQLiteOpenHelper {

        private static final int VERSION = 2;
        private static final String DATABASE_NAME = "medicBase.db";

        public MedicBaseHelper(Context context) { super(context, DATABASE_NAME, null, VERSION);}

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " + MedicDbSchema.MedicTable.NAME + " (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MedicDbSchema.MedicTable.cols.UUID + " TEXT, " +
                        MedicDbSchema.MedicTable.cols.NAME + " TEXT, " +
                        MedicDbSchema.MedicTable.cols.DURATION + " TEXT, " +
                        MedicDbSchema.MedicTable.cols.MORNING + " BOOLEAN, " +
                        MedicDbSchema.MedicTable.cols.NOON + " BOOLEAN, " +
                        MedicDbSchema.MedicTable.cols.EVENING + " BOOLEAN" +
                        ")"
                );
                Log.d("DB", "medictable created");

                db.execSQL("CREATE TABLE " + MedicDbSchema.IntakeMedicTable.NAME + " (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MedicDbSchema.IntakeMedicTable.cols.MEDIC_ID + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.NAME + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.DATE_END + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.MORNING + " BOOLEAN, " +
                        MedicDbSchema.IntakeMedicTable.cols.NOON + " BOOLEAN, " +
                        MedicDbSchema.IntakeMedicTable.cols.EVENING + " BOOLEAN" +
                        ")"
                );
                Log.d("DB", "intakemedictable created");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (oldVersion < newVersion) {
                        db.execSQL("ALTER TABLE " + MedicDbSchema.IntakeMedicTable.NAME + " ADD COLUMN " + MedicDbSchema.IntakeMedicTable.cols.NAME + " TEXT;");
                }
        }

}
