import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OVchipkaartDAOPsql implements OVchipkaartDAO {
    Connection conn;



    public OVchipkaartDAOPsql(Connection conn){
        this.conn = conn;
    }




    @Override
    public OVchipkaart OVObject(ResultSet set) throws SQLException {
        OVchipkaart o = new OVchipkaart();
        // kaartnummer
        o.setKaartnummer(parseInt(set.getString("kaart_nummer")));

        // geldig_tot
        o.setGeldig_tot(Date.valueOf(set.getString("geldig_tot")).toLocalDate());

        //klasse
        o.setKlasse(parseInt(set.getString("klasse")));

        //saldo
        o.setSaldo(Double.parseDouble(set.getString("saldo")));


        return o;
    }


    @Override
    public List<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVchipkaart> ov = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ov_chipkaart INNER JOIN reiziger ON reiziger.reiziger_id = ov_chipkaart.reiziger_id WHERE reiziger.reiziger_id=?;");
            statement.setInt(1, reiziger.getReiziger_id());
            statement.execute();
            ResultSet set = statement.getResultSet();
            while (set.next()){
                ov.add(OVObject(set));
            }

        }catch (SQLException e){
            System.out.println("Er is een SQL fout opgetreden: " + e.getMessage());
            return null;
        }catch (Exception e){
            System.out.println("Er is een onbekende fout opgetreden: " + e.getMessage());
            return null;
        }
        return ov;
    }

    @Override
    public boolean save(OVchipkaart ov) throws SQLException {
        try
        {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ov_chipkaart(kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES (?,?,?,?,?);");
            statement.setInt(1, ov.getKaartnummer());
            statement.setDate(2, Date.valueOf(ov.getGeldig_tot()));
            statement.setInt(3, ov.getKlasse());
            statement.setDouble(4, ov.getSaldo());
            statement.setInt(5, ov.getReiziger().getReiziger_id());
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
    public boolean update(OVchipkaart ov) {
        try {

            PreparedStatement mySt = conn.prepareStatement("UPDATE ov_chipkaart set reiziger_id = ?, geldig_tot = ?, klasse = ?, saldo = ? WHERE kaart_nummer = ?");

            mySt.setInt(5, ov.getKaartnummer());

            mySt.setInt(1, ov.getReiziger().getReiziger_id());
            mySt.setDate(2, Date.valueOf(ov.getGeldig_tot()));
            mySt.setInt(3, ov.getKlasse());
            mySt.setDouble(4, ov.getSaldo());

            mySt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Er is een update fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVchipkaart OV) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?");
            mySt.setInt(1, OV.getKaartnummer());
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
    public List<OVchipkaart> findAll() throws SQLException {
        List<OVchipkaart> oL = new ArrayList<>();

        Statement mySt = conn.createStatement();
        ResultSet rS = mySt.executeQuery("SELECT * FROM ov_chipkaart INNER JOIN reiziger ON ov_chipkaart.reiziger_id = reiziger.reiziger_id;");

        while (rS.next()) {
            OVchipkaart o = new OVchipkaart();
            //kaartnummer
            o.setKaartnummer(parseInt(rS.getString("kaart_nummer")));

            //geldig tot
            o.setGeldig_tot(Date.valueOf(rS.getString("geldig_tot")).toLocalDate());

            //klasse
            o.setKlasse(parseInt(rS.getString("klasse")));

            //saldo
            o.setSaldo(Double.parseDouble(rS.getString("saldo")));

            //reiziger_id
            o.setReiziger(new ReizigerDAOPsql(conn).findByID(parseInt(rS.getString("reiziger_id"))));


            oL.add(o);
        }
        return oL;
    }


    //eigen functies

    @Override
    public boolean deleteByID(int id) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE adres_id=?");
            mySt.setInt(1, id);
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
    public List<OVchipkaart> findByID(int id) throws SQLException {
        List<OVchipkaart> ov = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id=?;");
        statement.setInt(1,id);
        statement.execute();
        ResultSet set=statement.getResultSet();

        while (set.next()){
            ov.add(OVObject(set));
        }

        return ov;

    }

}
