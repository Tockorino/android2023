package theo.androidproject.medictime.Models;

import java.util.UUID;

public class IntakeMedic {
    private UUID mID;
    private UUID mMedicID;
    private String mName;
    private String mDuration;

    private String mDateBegin;

    private String mDateEnd;
    private boolean mMorning;
    private boolean mNoon;
    private boolean mEvening;

    public IntakeMedic() {
        this(UUID.randomUUID());
    }

    public IntakeMedic(UUID id) {
        mID = id;
    }

    public UUID getID() {
        return mID;
    }

    public UUID getMedicID() {
        return mMedicID;
    }

    public void setMedicID(UUID medicID) {
        this.mMedicID = medicID;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public boolean isMorning() {
        return mMorning;
    }

    public void setMorning(boolean morning) {
        this.mMorning = morning;
    }

    public boolean isNoon() {
        return mNoon;
    }

    public void setNoon(boolean noon) {
        this.mNoon = noon;
    }

    public boolean isEvening() {
        return mEvening;
    }

    public void setEvening(boolean evening) {
        this.mEvening = evening;
    }

    public void setDateBegin(String dateBegin) {
        this.mDateBegin = dateBegin;

    }

    public void setDateEnd(String dateEnd) {
        this.mDateEnd = dateEnd;

    }
}
