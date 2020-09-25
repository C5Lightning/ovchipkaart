import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ProductDAOPsql implements ProductDAO {

    Connection conn;


    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Product productObject(ResultSet set) throws SQLException {
        Product p = new Product();
        // productNummer
        p.setProduct_nummer(parseInt(set.getString("product_nummer")));

        // naam
        p.setNaam(set.getString("naam"));

        //beschrijving
        p.setBeschrijving(set.getString("beschrijving"));

        //prijs
        p.setPrijs(Double.parseDouble(set.getString("prijs")));

        return p;
    }


    @Override
    public boolean save(Product p) throws SQLException {
        try
        {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO product(product_nummer,naam,beschrijving,prijs) VALUES (?,?,?,?) " +
                    "JOIN ov_chipkaart_product AS o ON product.product_nummer = o.product_nummer;");
            statement.setInt(1, p.getProduct_nummer());
            statement.setString(2, p.getNaam());
            statement.setString(3, p.getBeschrijving());
            statement.setDouble(4, p.getPrijs());
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
    public boolean update(Product p) {
        try {

            PreparedStatement mySt = conn.prepareStatement("UPDATE product set naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");

            mySt.setInt(4, p.getProduct_nummer());

            mySt.setString(1, p.getNaam());
            mySt.setString(2, p.getBeschrijving());
            mySt.setDouble(3, p.getPrijs());


            mySt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Er is een update fout opgetreden: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Product p) {
        try
        {
            PreparedStatement mySt = conn.prepareStatement("DELETE FROM product JOIN ov_chipkaart_product AS o" +
                    " ON product.product_nummer = o.product_nummer " +
                    "WHERE product_nummer=?");
            mySt.setInt(1, p.getProduct_nummer());
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
    public List<Product> findAll() throws SQLException {
        List<Product> pL = new ArrayList<>();


        Statement mySt = conn.createStatement();
        ResultSet rS = mySt.executeQuery("SELECT * FROM product INNER JOIN ov_chipkaart ON ov_chipkaart.reiziger_id = product.product_nummer;");

        while (rS.next()) {
            Product p = new Product();

            // productNummer
            p.setProduct_nummer(parseInt(rS.getString("product_nummer")));

            // naam
            p.setNaam(rS.getString("naam"));

            //beschrijving
            p.setBeschrijving(rS.getString("beschrijving"));

            //prijs
            p.setPrijs(Double.parseDouble(rS.getString("prijs")));



            pL.add(p);
        }
        return pL;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }



    @Override
    public List<Product> findByProductNummer(int pN) throws SQLException {
        List<Product> pL = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM product WHERE product_nummer=?;");
        statement.setInt(1, pN);
        statement.execute();
        ResultSet set=statement.getResultSet();

        while (set.next()){
            pL.add(productObject(set));
        }

        return pL;

    }
}
