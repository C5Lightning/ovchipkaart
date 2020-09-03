import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface adresDAO {

    public boolean save(Adres adres) throws SQLException;

    public boolean update(Adres adres);

    public boolean delete(Adres adres);

    public Reiziger findByReiziger(Reiziger reiziger) throws SQLException;

    public List<Adres> findAll() throws SQLException;

    //eigen functies
    public boolean deleteByID(int id);

    public Adres adresObject(ResultSet set) throws SQLException;

    public Adres findByID(int id) throws SQLException;

    public ReizigerDAO getRdao();



}
