import java.sql.SQLException;
import java.util.List;

interface ReizigerDAO {

    public boolean save(Reiziger reiziger) throws SQLException;

    public boolean update(Reiziger reiziger);

    public boolean delete(Reiziger reiziger);

    public Reiziger findByID(int id) throws SQLException;

    public List<Reiziger> findbyGbDatum(String datum) throws SQLException;

    public List<Reiziger> findAll() throws SQLException;

    public boolean deleteByID(int id);



}
