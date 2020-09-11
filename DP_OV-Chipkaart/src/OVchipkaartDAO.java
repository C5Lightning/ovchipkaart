import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OVchipkaartDAO {

    public List<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    public boolean save(OVchipkaart ov) throws SQLException;

    public boolean update(OVchipkaart ov);

    public boolean delete(OVchipkaart OV);

    public List<OVchipkaart> findAll()throws SQLException;


    //Eigen functies

    public boolean deleteByID(int id);

    public OVchipkaart OVObject(ResultSet set) throws SQLException;

    public List<OVchipkaart> findByID(int id) throws SQLException;


}