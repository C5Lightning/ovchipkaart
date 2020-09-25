import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ov_chipkaart_product {

    private int kaartNummer;
    private int productNummer;
    private ProductDAO p;

    Connection conn;

    public List<Product> findByOVChipkaart(OVchipkaart ov) throws SQLException {
        List<Product> pL = new ArrayList<>();
        PreparedStatement mySt = conn.prepareStatement("SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs " +
                "FROM product " +
                "JOIN ov_chipkaart_product " +
                "ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                "JOIN ov_chipkaart " +
                "ON ov_chipkaart_product.kaart_nummer = ?");
        mySt.setInt(1, ov.getKaartnummer());

        mySt.execute();
        ResultSet set=mySt.getResultSet();

        while (set.next()){
            pL.add(p.productObject(set));
        }

        return pL;

    }

}
