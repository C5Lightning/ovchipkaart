import java.time.LocalDate;

public class OVchipkaart {

    private int kaartnummer;
    private LocalDate geldig_tot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVchipkaart(int kN, LocalDate gT, int kl, double sal, Reiziger rId){
        kaartnummer = kN;
        geldig_tot = gT;
        klasse = kl;
        saldo = sal;
        reiziger = rId;
    }

    public OVchipkaart(){
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public LocalDate getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(LocalDate geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "OVchipkaart{" +
                "kaartnummer = " + kaartnummer +
                ", geldig_tot = " + geldig_tot +
                ", klasse = " + klasse +
                ", saldo = " + saldo +
                ", reiziger_id = " + reiziger +
                '}';
    }
}
