import javax.swing.*;
import java.awt.*;

public abstract class PhotoFrame extends JFrame {

    protected JLabel imageLabel, descriptionLabel, dateLabel, pictureCountLabel;
    protected JScrollPane scrollPane;
    protected JPanel controlPane, descriptionPane, datePane, buttonPane, leftRightPane, southButtonPanel;
    protected JButton prevButton, nextButton, btnDelete, btnSaveChanges, btnAddPhoto;
    protected JTextField dateTextField, pictureNumberTextField;
    protected JTextArea descriptionTextArea;
    protected static JMenuBar menuBar;
    protected JMenu fileMenu, viewMenu;
    protected JMenuItem exitMenuItem, browseMenuItem, maintainMenuItem;
    protected JFileChooser fileBrowser;
    private Color Blue = Color.decode("#1f51a3");
    private Color Grey = Color.decode("#868b93");
    private Color lightGrey = Color.decode("#cadbf7");

    public PhotoFrame(String title){
        super(title);

        fileBrowser = new JFileChooser();

        Container contentPane = getContentPane();

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        exitMenuItem = new JMenuItem("Exit");
        browseMenuItem = new JMenuItem("Browse");
        maintainMenuItem = new JMenuItem("Maintain");

        fileMenu.add(exitMenuItem);
        viewMenu.add(browseMenuItem);
        viewMenu.add(maintainMenuItem);

        imageLabel = new JLabel("", SwingConstants.CENTER);
        scrollPane = new JScrollPane(imageLabel);

        ImageIcon image = new ImageIcon("p1.jpg");
        imageLabel.setIcon(image);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        controlPane = new JPanel();
        controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));
        controlPane.setBackground(Blue);

        descriptionPane = new JPanel();
        descriptionPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        descriptionPane.setBackground(Blue);

        descriptionLabel = new JLabel("Description:");
        descriptionTextArea = new JTextArea(4,20);
        descriptionPane.add(descriptionLabel);
        descriptionPane.add(descriptionTextArea);
        descriptionTextArea.setBackground(lightGrey);

        datePane = new JPanel();

        dateLabel = new JLabel("Date:");
        dateLabel.setPreferredSize(new Dimension(descriptionLabel.getPreferredSize().width,dateLabel.getPreferredSize().height));
        dateTextField = new JTextField("");
        dateTextField.setBackground(lightGrey);
        datePane.add(dateLabel);
        datePane.add(dateTextField);
        datePane.setBackground(Blue);
        //datePane.add(Box.createHorizontalGlue());
        buttonPane = new JPanel();

        btnDelete = new JButton("Delete");
        btnDelete.setEnabled(false);
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.setEnabled(false);
        btnAddPhoto = new JButton("Add Photo");

        buttonPane.add(btnDelete);
        buttonPane.add(btnSaveChanges);
        buttonPane.add(btnAddPhoto);
        buttonPane.setVisible(false);
        buttonPane.setBackground(Blue);

        leftRightPane = new JPanel();
        leftRightPane.setLayout(new BorderLayout());
        leftRightPane.add(datePane,BorderLayout.WEST);
        leftRightPane.add(buttonPane,BorderLayout.EAST);
        leftRightPane.setBackground(Blue);


        southButtonPanel = new JPanel();
        southButtonPanel.setBackground(Grey);
        pictureNumberTextField = new JTextField("0",4);
        pictureNumberTextField.setBackground(lightGrey);
        pictureCountLabel = new JLabel(" of 0");
        prevButton = new JButton("<");
        prevButton.setEnabled(false);
        //prevButton.setBackground(lightGrey);
        nextButton = new JButton(">");
        nextButton.setEnabled(false);
        //nextButton.setBackground(lightGrey);


        southButtonPanel.add(pictureNumberTextField);
        southButtonPanel.add(pictureCountLabel);
        southButtonPanel.add(prevButton);
        southButtonPanel.add(nextButton);
        FlowLayout flowLayout = (FlowLayout) southButtonPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);


        controlPane.add(descriptionPane);
        controlPane.add(leftRightPane);
        controlPane.add(southButtonPanel);

        contentPane.add(controlPane, BorderLayout.SOUTH); // Or PAGE_END
    }

}