import java.io.*;
import java.sql.SQLException;

import javax.swing.*;

public class PhotoViewer extends PhotoFrame{

    private PhotoBook pm;

    public PhotoViewer(String title){
        super(title);

        addListeners();

        try {
			pm = new PhotoBook();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ran into some kind of error creating the database connection");
		}
        pm.loadSerializedPhotos();

        try {
			loadPhoto(pm.getCurrentPhoto());
		} catch (SQLException e) {
			System.out.println("Couldn't load the current photo from the database");
			e.printStackTrace();
		}
        updateDisplay();
    }

    public void addListeners() {
        nextButton.addActionListener(e -> {
        	final Runnable CodeForUIThread = new Runnable(){
        		public void run(){
        			updateDisplay();
        		}
        	};
        	
        	Runnable CodeForDBThread = new Runnable(){
        		public void run(){
                    try {
        				loadPhoto(pm.getNextPhoto());
        			} catch (SQLException e1) {
        				System.out.println("Couldn't load the next photo");
        				e1.printStackTrace();
        			}
                    SwingUtilities.invokeLater(CodeForUIThread);
        		}
        	};
        	
        	Thread DBThread = new Thread(CodeForDBThread);
        	DBThread.start();

        });

        prevButton.addActionListener(e -> {
        	final Runnable CodeForUIThread = new Runnable(){
        		public void run(){
        			updateDisplay();
        		}
        	};
        	
        	Runnable CodeForDBThread = new Runnable(){
        		public void run(){
                    try {
                    	loadPhoto(pm.getPreviousPhoto());
        			} catch (SQLException e1) {
        				System.out.println("Couldn't load the previous photo");
        				e1.printStackTrace();
        			}
                    SwingUtilities.invokeLater(CodeForUIThread);
        		}
        	};
        	
        	Thread DBThread = new Thread(CodeForDBThread);
        	DBThread.start();
        });

        maintainMenuItem.addActionListener(e -> buttonPane.setVisible(true));

        browseMenuItem.addActionListener(e -> buttonPane.setVisible(false));

        exitMenuItem.addActionListener(e -> System.exit(0));

        btnAddPhoto.addActionListener(e -> {
            int returnVal = fileBrowser.showOpenDialog(this);

            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileBrowser.getSelectedFile();
                try {
					pm.addPhoto(new Photo(file.getPath(), "<Replace with description>", "<mm/dd/yyyy>"));
				} catch (SQLException e1) {
					System.out.println("Ran into a problem adding a new photo");
					e1.printStackTrace();
				}
                pm.savePhotos();
                try {
					loadPhoto(pm.getLastPhoto());
				} catch (SQLException e1) {
					System.out.println("Couldn't load the last photo from the database");
					e1.printStackTrace();
				}
            }
            updateDisplay();
        });

        btnSaveChanges.addActionListener(e -> {
            try {
				pm.updatePhoto(descriptionTextArea.getText(), dateTextField.getText());
			} catch (SQLException e1) {
				System.out.println("Ran into an issue updating this photo");
				e1.printStackTrace();
			}
            pm.savePhotos();
            updateDisplay();
        });

        btnDelete.addActionListener(e-> {
            try {
				pm.deleteCurrentPhoto();
			} catch (SQLException e1) {
				System.out.println("Ran into a problem deleting this photo");
				e1.printStackTrace();
			}
            pm.savePhotos();
            try {
				loadPhoto(pm.getCurrentPhoto());
			} catch (SQLException e1) {
				System.out.println("Couldn't load the current photo from the database");
				e1.printStackTrace();
			}
            updateDisplay();
        });
    }

    public void loadPhoto(Photo p){
    	Photo photo = new Photo();
		try {
			photo = pm.getCurrentPhoto();
		} catch (SQLException e) {
			System.out.println("Couldn't load the current photo from the database");
			e.printStackTrace();
		}
        // Load image
        imageLabel.setIcon(photo.getImage());
        // Load description
        descriptionTextArea.setText(photo.getDescription());
        // Load date
        dateTextField.setText(photo.getDate());
    }

    public void updateButtons(){
    	int totalPhotoCount = 0;
        try {
        	totalPhotoCount = pm.totalPhotoCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			nextButton.setEnabled(pm.getPhotoNumber().equals(totalPhotoCount) ? false : true);
		} catch (SQLException e1) {
			System.out.println("Couldn't get the photo number from the photobook");
			e1.printStackTrace();
		}

        try {
			prevButton.setEnabled((pm.getPhotoNumber() <= 1) ? false : true);
		} catch (SQLException e) {
			System.out.println("Couldn't get the photo number from the photobook");
			e.printStackTrace();
		}
        btnDelete.setEnabled(totalPhotoCount <= 0 ? false : true);
        btnSaveChanges.setEnabled(totalPhotoCount <= 0 ? false : true);
    }

    public void updatePhotoNumberText(int i){
        pictureNumberTextField.setText(Integer.toString(i));
    }

    public void updatePhotoLabel(){
        try {
			pictureCountLabel.setText(" of " + pm.totalPhotoCount().toString());
		} catch (SQLException e) {
			System.out.print("Couldn't retrieve totalPhotoCount within updatePhotoLabel");
			e.printStackTrace();
		}
    }

    public void updateDisplay() {
        updatePhotoLabel();
        try {
			updatePhotoNumberText(pm.getPhotoNumber());
		} catch (SQLException e) {
			System.out.println("Couldn't get the current photo number");

			e.printStackTrace();
		}
        updateButtons();
    }

    public static void main(String[] args)
    {
        PhotoFrame frame = new PhotoViewer("Photo Viewer");

        frame.setJMenuBar(menuBar);

        frame.pack();

        frame.setVisible(true);
        System.out.print("HELLO WORLD");
    }


}