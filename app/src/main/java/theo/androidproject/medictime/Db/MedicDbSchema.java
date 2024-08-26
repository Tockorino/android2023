package theo.androidproject.medictime.Db;

public class MedicDbSchema {
    public static final class MedicTable {
        public static final String NAME = "medic";
        public static final class cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DURATION = "duration";
            public static final String MORNING = "morning";
            public static final String NOON = "noon";
            public static final String EVENING = "evening";
        }
    }

    public static final class IntakeMedicTable {
        public static final String NAME = "intake_medic";
        public static final class cols {
            public static final String MEDIC_ID = "medic_id";

            public static final String NAME = "name";
            public static final String DATE_BEGIN = "date_begin";
            public static final String DATE_END = "date_end";

            public static final String DURATION = "duration";
            public static final String MORNING = "morning";
            public static final String NOON = "noon";
            public static final String EVENING = "evening";

        }
    }
}