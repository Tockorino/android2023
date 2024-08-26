package theo.androidproject.medictime.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MedicItem {

    private String startDate;
    private String endDate;
    private String morningMedic;
    private String noonMedic;
    private String eveningMedic;

    // Constructeur
    public MedicItem(String startDate, String morningMedic, String noonMedic, String eveningMedic) {
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate, 7); // Exemple: 7 jours par défaut
        this.morningMedic = morningMedic;
        this.noonMedic = noonMedic;
        this.eveningMedic = eveningMedic;
    }

    // Getters
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getMorning() {
        return morningMedic;
    }

    public String getNoon() {
        return noonMedic;
    }

    public String getEvening() {
        return eveningMedic;
    }

    // Setters (si nécessaire)
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMorningMedic(String morningMedic) {
        this.morningMedic = morningMedic;
    }

    public void setNoonMedic(String noonMedic) {
        this.noonMedic = noonMedic;
    }

    public void setEveningMedic(String eveningMedic) {
        this.eveningMedic = eveningMedic;
    }

    private String calculateEndDate(String startDate, int duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, duration);
        return sdf.format(calendar.getTime());
    }
}