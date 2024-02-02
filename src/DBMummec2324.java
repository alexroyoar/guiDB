
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author clase
 */
class DBMummec2324 implements AutoCloseable {

    static DBMummec2324 elSingleton = null;
    static Connection con;
    private static final String DATABASE_NAME = "MUMMEC2324";

    static DBMummec2324 newInstance() throws SQLException {
        if (elSingleton == null) {
            elSingleton = new DBMummec2324();
        }
        return elSingleton;
    }

    // Implementación del interface Closeable
    @Override
    public void close() throws IOException {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            throw new IOException("No puedo cerrar la conexion con la base de datos", ex);
        }
    }

    //ZONA PÚBLICA
    //Re-construcción del esquema de la base de datos.
    void instanciaEsquema() throws SQLException {
        con.createStatement().executeUpdate("DROP DATABASE IF EXISTS " + DATABASE_NAME + ";");
        con.createStatement().executeUpdate("CREATE DATABASE " + DATABASE_NAME + ";");
        con.createStatement().executeUpdate("USE " + DATABASE_NAME + ";");

        con.setAutoCommit(false);
        con.createStatement().executeUpdate("CREATE TABLE papersreferences ( ref_paper INTEGER, cited_ref_paper INTEGER, PRIMARY KEY(ref_paper,cited_ref_paper) );");
        con.createStatement().executeUpdate("CREATE TABLE publication ( ref_publication INTEGER PRIMARY KEY, title VARCHAR(200) );");
        con.createStatement().executeUpdate("CREATE TABLE papersauthors ( ref_paper INTEGER, ref_author INTEGER, PRIMARY KEY(ref_paper,ref_author) );");
        con.createStatement().executeUpdate("CREATE TABLE authorafiliation ( ref_author INTEGER, ref_afiliation INTEGER, PRIMARY KEY(ref_author, ref_afiliation) );");
        con.createStatement().executeUpdate("CREATE TABLE afiliations ( ref_afiliation INTEGER PRIMARY KEY, university VARCHAR(65), webpage VARCHAR(30), country VARCHAR(15) );");
        con.createStatement().executeUpdate("CREATE TABLE papersinfo ( ref_paper INTEGER PRIMARY KEY, title VARCHAR(200), publiyear INTEGER, ref_publication INTEGER, url VARCHAR(200));");
        con.createStatement().executeUpdate("CREATE TABLE authors ( ref_author INTEGER PRIMARY KEY, name VARCHAR(25) );");
        con.commit();
    }

    //PARA autores y publicaciones
    void arrayOfStringsToDB(String[] valores, String tableName) throws SQLException {
        PreparedStatement ps = con.prepareStatement("insert into " + tableName + " values (?,?);");
        int i = 0;
        for (String s : valores) {
            ps.setInt(1, ++i);
            ps.setString(2, s);
            ps.addBatch();
        }
        flush(ps);
    }

    // Función para obtener un resultset con los datos contenidos en la tabla authors
    java.sql.ResultSet getAuthors() throws SQLException {
        con.createStatement().executeUpdate("USE " + DATABASE_NAME + ";");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery("select * from authors;");
    }

    java.sql.ResultSet getProductividad(String autor) throws SQLException {
        con.createStatement().executeUpdate("USE " + DATABASE_NAME + ";");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery("SELECT authors.name AS 'Nombre del autor', COUNT(DISTINCT papersauthors.ref_paper) AS 'Número de artículos', SUM(1.0 / num_autores) AS 'Productividad Total'"
                + "FROM authors"
                + " INNER JOIN papersauthors ON authors.ref_author = papersauthors.ref_author"
                + " INNER JOIN ("
                + "    SELECT ref_paper, COUNT(DISTINCT ref_author) AS num_autores"
                + "    FROM papersauthors"
                + "    GROUP BY ref_paper"
                + ") AS pa ON papersauthors.ref_paper = pa.ref_paper"
                + " WHERE authors.name LIKE '%" + autor + "%'"
                + " GROUP BY authors.name;");

    }
    
    // Función para ver las colaboraciones de cada autor:
     java.sql.ResultSet getColaboracion(String autor) throws SQLException {
        con.createStatement().executeUpdate("USE " + DATABASE_NAME + ";");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery("SELECT A.name AS 'Nombre del autor', P.title AS 'Artículos publicados' " +
        "FROM papersauthors PA " +
        "JOIN authors A ON PA.ref_author = A.ref_author " +
        "JOIN papersinfo P ON PA.ref_paper = P.ref_paper " +
        "WHERE A.name LIKE '%" + autor + "%' " +
        "AND PA.ref_paper IN ( " +
        "    SELECT ref_paper " +
        "    FROM papersauthors " +
        "    GROUP BY ref_paper " +
        "    HAVING COUNT(DISTINCT ref_author) > 1 " +
        ") " +
        "AND PA.ref_author IN ( " +
        "    SELECT ref_author " +
        "    FROM papersauthors " +
        "    GROUP BY ref_author " +
        "    HAVING COUNT(DISTINCT ref_paper) > 1 " +
        ") " +
        "ORDER BY A.name;");     
     }
     
     // Función para ejecutar la consulta
     public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        consulta = (consulta == null) ? "" : consulta;

        con.createStatement().executeUpdate("USE " + DATABASE_NAME + ";");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(consulta);
    }

             
    
    

    //ZONA PRIVADA
    //El constructor (privado para ser Singleton)
    private DBMummec2324() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC", "localuser", "localpassword");
    }

    private void flush(PreparedStatement ps) throws SQLException {
        ps.executeBatch();
        con.commit();
    }

}
