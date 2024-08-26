package theo.androidproject.medictime.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicBaseHelper extends SQLiteOpenHelper {

        private static final int VERSION = 1;
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

                db.execSQL("CREATE TABLE " + MedicDbSchema.IntakeMedicTable.NAME + " (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MedicDbSchema.IntakeMedicTable.cols.MEDIC_ID + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.DATE_BEGIN + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.DATE_END + " TEXT, " +
                        MedicDbSchema.IntakeMedicTable.cols.MORNING + " BOOLEAN, " +
                        MedicDbSchema.IntakeMedicTable.cols.NOON + " BOOLEAN, " +
                        MedicDbSchema.IntakeMedicTable.cols.EVENING + " BOOLEAN" +
                        ")"
                );
        }

        @Override
        public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

        }

}
