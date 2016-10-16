import java.io.Serializable;

import javax.swing.ImageIcon;

public class Photo implements Serializable{
    private String imgPath;
    private String description;
    private String date;
    private ImageIcon image;

    public Photo(){  }

    public Photo(String img, String desc, String date){
        this.imgPath = img;
        this.description = desc;
        this.date = date;
    }

    public String getImgPath(){return this.imgPath;}
    public String getDescription(){return this.description;}
    public String getDate() {return date;}
    public ImageIcon getImage() {return image;}

    public void setImgPath(String path){this.imgPath = path;}
    public void setDescription(String desc){this.description = desc;}
    public void setDate(String date){this.date = date;}

}