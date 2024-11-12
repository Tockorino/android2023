package theo.androidproject.medictime.Models;

public class MedicItem {

private int ID;
    private String dateBegin;
    private String dateEnd;
    private String morningMedic;
    private String noonMedic;
    private String eveningMedic;

    // Constructeur
    public MedicItem(String dateBegin, String dateEnd, String morningMedic, String noonMedic, String eveningMedic) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.morningMedic = morningMedic;
        this.noonMedic = noonMedic;
        this.eveningMedic = eveningMedic;
    }

    // Getters
    public int getID() {
        return ID;
    }
    public String getDateBegin() {
        return dateBegin;
    }
    public String getDateEnd() {return dateEnd;}

    public String getMorning() {
        return morningMedic;
    }

    public String getNoon() {
        return noonMedic;
    }

    public String getEvening() {
        return eveningMedic;
    }

    // Setters
    public void setMorningMedications(String morningMedic) {
        this.morningMedic = morningMedic;
    }

    public void setNoonMedications(String noonMedic) {
        this.noonMedic = noonMedic;
    }

    public void setEveningMedications(String eveningMedic) {
        this.eveningMedic = eveningMedic;
    }
}