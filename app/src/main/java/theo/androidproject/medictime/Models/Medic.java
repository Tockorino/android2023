package theo.androidproject.medictime.Models;

import java.util.UUID;
public class Medic {

    protected java.util.UUID mID;
    protected String mName;
    protected String mDuration;
    protected boolean mMorning;
    protected boolean mNoon;
    protected boolean mEvening;

    public Medic() {
        this(UUID.randomUUID());
        this.mName = "Nom par défaut";
        this.mDuration = "Durée par défaut";
        this.mMorning = false;
        this.mNoon = false;
        this.mEvening = false;
    }

    public Medic(UUID id) {
        mID = id;
    }

    public UUID getID() {
        return mID;
    }
    public String getName() {
        return mName;
    }
    public String getDuration() {
        return mDuration;
    }
    public boolean isMorning() {
        return mMorning;
    }
    public boolean isNoon() {
        return mNoon;
    }
    public boolean isEvening() {
        return mEvening;
    }
    public void setName(String name) {
        this.mName = name;
    }
    public void setDuration(String duration) {
        this.mDuration = duration;
    }
    public void setMorning(boolean morning) {
        this.mMorning = morning;
    }
    public void setNoon(boolean noon) {
        this.mNoon = noon;
    }
    public void setEvening(boolean evening) {
        this.mEvening = evening;
    }

}
