import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ReizigerDAOPsql implements ReizigerDAO {
    Connection conn;


    public ReizigerDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public ReizigerDAOPsql() {
    }


    public Reiziger reizigerObject(ResultSet set) throws SQLException {
        Reiziger r = new Reiziger();
        // id
        r.setReiziger_id(parseInt(set.getString("reiziger_id")));

        // voorletters
        r.setVoorletters(set.getString("voorletters"));

        //tussenvoegsel
        r.setTussenVoegsel(set.getString("tussenvoegsel"));

        //achternaam
        r.setAchternaam(set.getString("achternaam"));

        // geboortedatum
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
//        String date = set.getString("geboortedatum");
//        LocalDate localDate = LocalDate.parse(date, formatter);

        r.setDate(Date.valueOf(set.getString("geboortedatum")).toLocalDate());

        r.setKaart(new OVchipkaartDAOPsql(conn).findByReiziger(r));

        return r;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?);");
        statement.setInt(1, reiziger.getReiziger_id());
        statement.setString(2, reiziger.getVoorletters());
        statement.setString(3, reiziger.getTussenVoegsel());
        statement.setString(4, reiziger.getAchternaam());
        statement.setDate(5, reiziger.getDate());
        statement.executeUpdate();
        return true;
    }



    @Override
    public boolean update(Reiziger reiziger) {
        try {

            PreparedStatement mySt = conn.prepareStatement("UPDATE reiziger set voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            mySt.setInt(5, reiziger.getReiziger_id());

            mySt.setString(1, reiziger.getVoorletters());
            mySt.setString(2, reiziger.getTussenVoegsel());
            mySt.setString(3, reiziger.getAchternaam());
            mySt.setDate(4, reiziger.getDate());

            mySt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Er is een update fout opgetreden: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            mySt.setInt(1, reiziger.getReiziger_id());
            mySt.execute();


            return true;
        }
        catch (Exception e)
        {
            System.err.println("Er is een delete fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findByID(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=?;");
        statement.setInt(1,id);
        statement.execute();
        ResultSet set=statement.getResultSet();
        if(!set.next()) return null;
        return reizigerObject(set);
    }

    @Override
    public List<Reiziger> findbyGbDatum(String datum) throws SQLException {
        List<Reiziger> rL = new ArrayList<>();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum=?;");
        statement.setDate(1, Date.valueOf(datum));
        statement.execute();
        ResultSet set=statement.getResultSet();
        while(set.next()) {
            rL.add(reizigerObject(set));
        }
        return rL;

    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> rL = new ArrayList<>();


        Statement mySt = conn.createStatement();
        ResultSet rS = mySt.executeQuery("SELECT * FROM reiziger");

        while (rS.next()) {
            Reiziger r = new Reiziger();
            // id
            r.setReiziger_id(parseInt(rS.getString("reiziger_id")));

            // voorletters
            r.setVoorletters(rS.getString("voorletters"));

            //tussenvoegsel
            r.setTussenVoegsel(rS.getString("tussenvoegsel"));

            //achternaam
            r.setAchternaam(rS.getString("achternaam"));

            // geboortedatum
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = rS.getString("geboortedatum");
            LocalDate localDate = LocalDate.parse(date, formatter);

            r.setDate(localDate);

//            r.setKaart(new OVchipkaartDAOPsql(conn).findByReiziger(r));

            rL.add(r);
        }
        return rL;

    }

    //een eigen gemaakte functie om een bestaand object te verwijderen.
    @Override
    public boolean deleteByID(int id){
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            mySt.setInt(1, id);
            mySt.executeUpdate();


            return true;
        }
        catch (Exception e)
        {
            System.err.println("Er is een DELETEBYID fout opgetreden: " + e.getMessage());
            return false;
        }

    }

    @Override
    public Reiziger getReiziger(Reiziger reiziger) {
        return reiziger;
    }


}
