import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenVoegsel;
    private String achternaam;
    private LocalDate geboorteDatum;

    private List<OVchipkaart> kaart;

    public Reiziger(int id, String vL, String tV, String aN, LocalDate gD){
        reiziger_id = id;
        voorletters = vL;
        tussenVoegsel = tV;
        achternaam = aN;
        geboorteDatum = gD;
    }

    public Reiziger(){
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenVoegsel() {
        return tussenVoegsel;
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel = tussenVoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getDate() {
        return Date.valueOf(geboorteDatum);
    }

    public void setDate(LocalDate date) {
        this.geboorteDatum = date;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public List<OVchipkaart> getKaart() {
        return kaart;
    }

    public void setKaart(List<OVchipkaart> kaart) {
        this.kaart = kaart;
    }

    @Override
    public String toString() {
        return "reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenVoegsel='" + tussenVoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                '}';
    }
}
