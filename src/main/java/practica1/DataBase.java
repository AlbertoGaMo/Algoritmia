package practica1;

import java.sql.*;

public class DataBase {

  public org.json.JSONArray init() {

    String connectionURL = "jdbc:derby:memory:derbyDB;create=true";
    org.json.JSONArray documents = new org.json.JSONArray();

    try (
            // gets connection
            Connection conn = DriverManager.getConnection(connectionURL);
            // uses connection
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            )
    ){

      // create table
      stmt.executeUpdate("CREATE table DOCUMENT (id int, cod_eu varchar(10), indicator_name varchar(10), family_id varchar (5),family_name varchar (250), name varchar (250))");


      // insert (CREATE)
      stmt.executeUpdate("INSERT into DOCUMENT values (1,'D001','01','A',' Identificación oficial','INE')");
      stmt.executeUpdate("INSERT into DOCUMENT values (2,'D002','00','B','Comprobante de compra','Ticket de compra')");
      stmt.executeUpdate("INSERT into DOCUMENT values (3,'D001','01','A','Identificación oficial','Pasaporte')");
      stmt.executeUpdate("INSERT into DOCUMENT values (4,'D003','00','C','Estado de cuenta','Estado de cuenta')");
      stmt.executeUpdate("INSERT into DOCUMENT values (5,'D001','01','A','Identificación oficial','Cédula profesional')");
      stmt.executeUpdate("INSERT into DOCUMENT values (6,'D004','01','D','Comprobante de domicilio','Recibo de luz')");
      stmt.executeUpdate("INSERT into DOCUMENT values (7,'D004','01','D','Comprobante de domicilio','Recibo de teléfono')");

      // query (READ)
      ResultSet rs = stmt.executeQuery("SELECT * FROM DOCUMENT");


      org.json.JSONObject document = null;


      // print out query result
      while(rs.next()) {
        document =  new org.json.JSONObject();
        document.put("id",rs.getInt("id"));
        document.put("cod_eu",rs.getString("cod_eu"));
        document.put("indicator_name",rs.getString("indicator_name"));
        document.put("family_id",rs.getString("family_id"));
        document.put("family_name",rs.getString("family_name"));
        document.put("name",rs.getString("name"));
        documents.put(document);
      }


    } catch (SQLException e) {
      e.printStackTrace();
    }

    return documents;
  }

}
