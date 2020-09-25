import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    public boolean save(Product p) throws SQLException;

    public boolean update(Product p);

    public boolean delete(Product p);

    public List<Product> findAll()throws SQLException;

    //eigen functies

    public boolean deleteByID(int id);

    public Product productObject(ResultSet set) throws SQLException;

    public List<Product> findByProductNummer(int pN) throws SQLException;
}
