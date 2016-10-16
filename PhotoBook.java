import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;


public class PhotoBook {

    private int imgIndex = 0;
    private int maxIndex = 0;
    private DatabaseManager db;

    public PhotoBook() throws SQLException{
        db = new DatabaseManager();
    }

    public Photo getCurrentPhoto() throws SQLException{
        if(db.getPictureCount() > 0)
            return db.getPicture(imgIndex);
        
        return new Photo();
    }

    public Photo getNextPhoto() throws SQLException {
        if (++imgIndex < db.getPictureCount())
            return db.getPicture(imgIndex);
        db.getPicture(imgIndex++);
        return new Photo();
    }

    public Photo getPreviousPhoto() throws SQLException{
        if (--imgIndex >= 0)
            return db.getPicture(imgIndex);
        db.getPicture(imgIndex--);
        return new Photo();
    }

    public Photo getLastPhoto() throws SQLException{
        if(db.getPictureCount() > 0)
            return db.getPicture(db.getPictureCount()-1);
        return new Photo();
    }

    public Integer getPhotoNumber() throws SQLException{
        if(db.getPictureCount() > 0)
            return imgIndex+1;
        else
            return 0;
    }

    public Integer totalPhotoCount() throws SQLException{
        return db.getPictureCount();
    }

    public void deleteCurrentPhoto() throws SQLException{
    	db.deletePicture(imgIndex);
        imgIndex = imgIndex > 0 ? imgIndex-1 : 0;
    }

    public void addPhoto(Photo p) throws SQLException{
        imgIndex = db.getPictureCount()-1;
        db.addPicture(p);
    }

    public void updatePhoto(String desc, String date) throws SQLException{
        getCurrentPhoto().setDescription(desc);
        getCurrentPhoto().setDate(date);
        db.updatePicture(imgIndex,date,desc);
    }

    public void savePhotos() {

        try {
        	
            //ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("photos.ser.txt"));
            //os.writeObject(photos);
            //os.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void loadSerializedPhotos() {
    	
        try {
        	db.createTables();
        	maxIndex = db.getPictureCount();
            //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("photos.ser.txt"));
            //photos = (ArrayList<Photo>) ois.readObject();
            //ois.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.print("Couldn't load the photos from the database");
        }
    }
}