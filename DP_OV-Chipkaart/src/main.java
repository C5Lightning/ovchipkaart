import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.parseInt;


public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "20gianni02");
            testReizigerDAO(new ReizigerDAOPsql(connection));
//            testAdresDAO(new adresDAOPsql(connection));
            testOVDAO(new OVchipkaartDAOPsql(connection));

        }catch (SQLException e){
            System.out.println("Er is een SQL fout opgetreden: " + e.getMessage());

        }catch (ClassNotFoundException e){
            System.out.println("Er is een ClassNotFound fout opgetreden: " + e.getMessage());

        }catch (Exception e){
            System.out.println("Er is een onbekende fout in de MAIN opgetreden: " + e.getMessage());
        }

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");



        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";

        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum).toLocalDate());
//        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
//        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println("\n" + reizigers.size() + " reizigers\n");

        //updating sietske
        //want ze is recent getrouwd!

//        sietske.setVoorletters("S");
//        sietske.setTussenVoegsel("");
//        sietske.setAchternaam("Bakker");
//        sietske.setDate(LocalDate.now());
//        rdao.update(sietske);


        System.out.println(rdao.findByID(sietske.getReiziger_id()));



        System.out.println(rdao.findbyGbDatum("2002-12-03"));
    }

//    private static void testAdresDAO(adresDAO adao) throws SQLException {
//        List<Adres> adres = adao.findAll();
//
//
//        for (Adres ad : adres){
//            System.out.println(ad);
//        }
//        System.out.println();
//
//
//
//
//        Adres aSietske = new Adres(77, "1217 GL", "103", "Naarderweg", "Hilversum", 77);
//        System.out.print("[Test] Eerst " + adres.size() + " adressen, na adresDAO.save() ");
//
//        adao.save(aSietske);
//        adres = adao.findAll();
//
//
//        System.out.println("\n" + adres.size() + " adressen\n");
//
//        aSietske.setHuisnummer("103B");
//        aSietske.setPostcode("1297RM");
//        aSietske.setStraat("Havenstraat");
//        aSietske.setWoonplaats("Rotterdam");
//
//        adao.update(aSietske);
//
//        System.out.println(adao.findByID(aSietske.getId()));
//
//        adao.delete(aSietske);
//
//        adres = adao.findAll();
//
//        //uitkomst moet false zijn. voor het deleten van Sietske
//        System.out.println(adres.contains(aSietske));
//
//        String gbdatum = "1999-09-10";
//        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum).toLocalDate());
//
//
//        System.out.println(adao.findByReiziger(sietske));
//
//        //Mijn oplossing voor FindByReiziger
//        //Want ik snap de werking niet
//        //Als ik met een docent zou kunnen zitten hiervoor zou dat fijn zijn!
//        System.out.println(adao.findByID(1));
//
//    }

    private static void testOVDAO(OVchipkaartDAO odao) throws SQLException {
        System.out.println("\n---------- Test OVchipkaartDAO -------------");

        List<OVchipkaart> ov = odao.findAll();


        for (OVchipkaart o : ov){
//            o.setReiziger(new ReizigerDAOPsql(conn).findByID(parseInt(set.getString("reiziger_id"))));
            System.out.println(o);
        }
        System.out.println();


        odao.findByID(5);

        // deze doen het maar om errors tegen te gaan als ik elke keer de main load heb ik ze uitgecomment
        // findbyID(77) laat de werking zien van deze queries


        String gbdatum = "1981-03-14";
        Reiziger jelle = new Reiziger(88, "S", "", "Boers", Date.valueOf(gbdatum).toLocalDate());

        OVchipkaart OVs = new OVchipkaart(5566, LocalDate.now(), 1, 530.43, jelle);
//
//        odao.save(OVs);
        System.out.println();
        System.out.println("Voor de update");
        System.out.println(odao.findByID(77));
        // Uitwerking: OVchipkaart{kaartnummer = 5566, geldig_tot = 2020-09-10, klasse = 1, saldo = 530.43, reiziger_id = 77}
        System.out.println();

        OVs.setReiziger(jelle);
        OVs.setKlasse(2);
        OVs.setGeldig_tot(Date.valueOf("2020-09-12").toLocalDate());
        OVs.setSaldo(520.40);

        //uiwerking bij findbyID hieronder
//        odao.update(OVs);

        System.out.println("Na de update");
        System.out.println(odao.findByID(77));
        // Uitwerking: OVchipkaart{kaartnummer = 5566, geldig_tot = 2020-09-12, klasse = 2, saldo = 520.40, reiziger_id = 77}


        OVchipkaart OVs2 = new OVchipkaart(5666, LocalDate.now(), 3, 20.20, jelle);

//        odao.save(OVs2);

        // met de SQL query SELECT * FROM ov_chipkaart WHERE kaart_nummer = 5666;
        //laat de bovenstaande informatie bij OVs2 in de database zien

//        odao.delete(OVs2);

        //nadat deze functie is uitgevoerd
        //kan er geen informatie worden opgehaald uit de data base.
        //immers is de informatie verwijderd.

        System.out.println(odao.findByReiziger(jelle));
        //informatie van Jelle wordt teruggegeven.

    }
}