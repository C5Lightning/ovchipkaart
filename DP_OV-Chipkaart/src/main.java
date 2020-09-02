import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "20gianni02");
        testReizigerDAO(new ReizigerDAOPsql(connection));
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

        //rdao.deleteByID(77);

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";

        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum).toLocalDate());
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println("\n" + reizigers.size() + " reizigers\n");

        //updating sietske
        //want ze is recent getrouwd!
        sietske.setVoorletters("S");
        sietske.setTussenVoegsel("");
        sietske.setAchternaam("Bakker");
        sietske.setDate(LocalDate.now());
        rdao.update(sietske);


        System.out.println(rdao.findByID(sietske.getReiziger_id()));

        rdao.delete(sietske);

        System.out.println(rdao.findbyGbDatum("1998-08-11"));


    }
}