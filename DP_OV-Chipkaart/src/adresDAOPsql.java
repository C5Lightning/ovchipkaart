import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class adresDAOPsql implements adresDAO {
    Connection conn;
    ReizigerDAO rdao;

    public adresDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public adresDAOPsql() {

    }

    @Override
    public ReizigerDAO getRdao(){
        return rdao;
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

        //datum
        r.setDate(Date.valueOf(set.getString("geboortedatum")).toLocalDate());

        return r;
    }
    @Override
    public Adres adresObject(ResultSet set) throws SQLException {
        Adres a = new Adres();
        // id
        a.setId(parseInt(set.getString("adres_id")));

        // postcode
        a.setPostcode(set.getString("postcode"));

        //huisnummer
        a.setHuisnummer(set.getString("huisnummer"));

        //straat
        a.setStraat(set.getString("straat"));

        //woonplaats
        a.setWoonplaats(set.getString("woonplaats"));

        //reiziger id
        a.setReiziger_id(parseInt(set.getString("reiziger_id")));

        return a;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        try
        {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?);");
        statement.setInt(1, adres.getId());
        statement.setString(2, adres.getPostcode());
        statement.setString(3, adres.getHuisnummer());
        statement.setString(4, adres.getStraat());
        statement.setString(5, adres.getWoonplaats());
        statement.setInt(6, adres.getReiziger_id());
        statement.executeUpdate();
        return true;
        } catch (SQLException e){
            System.out.println("Er is een SQL save fout opgetreden: " + e.getMessage());
            return false;
        } catch (Exception e){
            System.out.println("Er is een save fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {

            PreparedStatement mySt = conn.prepareStatement("UPDATE adres set postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?");

            mySt.setInt(5, adres.getId());
            mySt.setString(1, adres.getPostcode());
            mySt.setString(2, adres.getHuisnummer());
            mySt.setString(3, adres.getStraat());
            mySt.setString(4, adres.getWoonplaats());

            mySt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Er is een update fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM adres WHERE adres_id=?");
            mySt.setInt(1, adres.getId());
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
    public Reiziger findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM adres WHERE adres_id = ?;");
            statement.setInt(1, reiziger.getReiziger_id());
            statement.execute();
            ResultSet set = statement.getResultSet();
            if (!set.next()) return null;
            return reizigerObject(set);
        }catch (SQLException e){
            System.out.println("Er is een SQL fout opgetreden: " + e.getMessage());
            return null;
        }catch (Exception e){
            System.out.println("Er is een onbekende fout opgetreden: " + e.getMessage());
            return null;
        }

    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> aL = new ArrayList<>();

        Statement mySt = conn.createStatement();
        ResultSet rS = mySt.executeQuery("SELECT * FROM adres INNER JOIN reiziger ON adres.adres_id = reiziger.reiziger_id;");

        while (rS.next()) {
            Adres a = new Adres();
            // id
            a.setId(parseInt(rS.getString("adres_id")));

            // postcode
            a.setPostcode(rS.getString("postcode"));

            //huisnummer
            a.setHuisnummer(rS.getString("huisnummer"));

            //straat
            a.setStraat(rS.getString("straat"));

            // Woonplaats
            a.setWoonplaats(rS.getString("woonplaats"));

            //reiziger_id
            a.setReiziger_id(parseInt(rS.getString("reiziger_id")));


            aL.add(a);
        }
        return aL;
    }
    //Eigen functie
    @Override
    public boolean deleteByID(int id) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM adres WHERE adres_id=?");
            mySt.setInt(1, id);
            mySt.execute();


            return true;
        }
        catch (Exception e)
        {
            System.err.println("Er is een deleteID fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByID(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM adres WHERE adres_id=?;");
        statement.setInt(1,id);
        statement.execute();
        ResultSet set=statement.getResultSet();
        if(!set.next()) return null;
        return adresObject(set);
    }
}
