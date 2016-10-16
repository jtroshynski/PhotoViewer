import java.sql.*;
import java.io.*;
 
public class DatabaseManager {
 
    private Connection con;
    private Statement stmt;
 
    // Load class and create a connection to the database
    public DatabaseManager() throws SQLException {
        String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/jctvh4";
        String userID = "jctvh4";
        String password = "wwBqMsu3Je";
   
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
       
        con = DriverManager.getConnection(url,userID,password);
        stmt = con.createStatement();
    }
 
    public void cleanup() throws SQLException {
        stmt.close();
        con.close();
    }
 
    public void createTables() throws SQLException {
    	con.setAutoCommit(false);
    	String sql1 = "create table Photos (id INT NOT NULL AUTO_INCREMENT, pictnum INT NOT NULL, date VARCHAR(24), description VARCHAR(256), photo LONG VARBINARY NULL, PRIMARY KEY (id))";
    	stmt.executeUpdate(sql1);
    	con.commit();
    }
    public void updateTable() throws SQLException {
    	
    }
    public void queryTable() throws SQLException { }
    
    public void deletePicture(int pictnum) throws SQLException {
    	
    	con.setAutoCommit(false);
    	
    	String sql1 = "delete from Photos where pictnum=" + pictnum;
    	String sql2 = "update Photos set pictnum=pictnum-1 where pictnum>" + pictnum;
    
        stmt.executeUpdate(sql1);
        stmt.executeUpdate(sql2);

        con.commit();
    }
    
    public void addPicture(Photo p) throws SQLException {
    	con.setAutoCommit(false);
    	String sql1 = "update Photos set pictnum=pictnum+1 where pictnum>1";
    	String sql2 = "insert into Photos (pictnum, date, description, photo) values(" + p.getDate() + ", " + p.getDescription() + ", " +p.getImgPath() + ")";
    	//need to add current pictnum into add function
    	//need to add actual photo to the db
    	con.commit();
    }
    
    public Photo getPicture(int currentPicture) throws SQLException {
    	con.setAutoCommit(false);
    	String sql1 = "SELECT * FROM `Photos` WHERE `pictnum` = " + currentPicture;
    	Photo p = new Photo();
    	
    	stmt.executeUpdate(sql1);
    	con.commit();
    	
    	return p;
    }
    
    public void updatePicture(int pictnum, String date, String description) throws SQLException {
    	con.setAutoCommit(false);
    	
    	String sql1 = "update Photos set date=" + date + ", set description =" + description + "WHERE `pictnum` = " + pictnum;

    	stmt.executeUpdate(sql1);
    	con.commit();
    }
    
    public int getPictureCount() throws SQLException {
    	con.setAutoCommit(false);

    	String sql1 = "SELECT COUNT(*) FROM Photos";
    	
    	stmt.executeUpdate(sql1);
    	con.commit();
    	return 0; //needs to return the result from the DB
    }
 
}

/*
Ideas:

Need to implement:
next/previous button will retrieve a single photo from the database based on pictnum
when you first open the app, it should figure out how many pictures are in the db and make that the picture count


*/