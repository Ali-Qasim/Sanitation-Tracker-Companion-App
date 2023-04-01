import javax.swing.*;
import java.awt.*;

public class WashFrame extends JFrame {

    //  Declaring JComponents
    JPanel jPanel1;
    JPanel jPanel2;
    JScrollPane scrPane;

    JTextField input;
    JTextArea output;

    JButton Search;
    JButton loadList;

    JLabel inLabel;

    EventHandler e;

    public WashFrame() throws InterruptedException {

        setTitle("Hygiene Recorder");
        addComponents();
        setSize(450, 580);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e.printFile();
    }

    //  Adds components to JFrame
    public void addComponents() throws InterruptedException {

        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 1));

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        scrPane = new JScrollPane(jPanel2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        inLabel = new JLabel("ID: ");
        input = new JTextField(15);
        Search = new JButton("Search");

        loadList = new JButton("Read Data");

        output = new JTextArea(30, 40);
        output.setEditable(false);


        input.setToolTipText("Retrieve data for a specific ID");
        Search.setToolTipText("Retrieve data for a specific ID");
        loadList.setToolTipText("Load and display event data from file");

        add(jPanel1);
        add(scrPane);

        jPanel1.add(inLabel);
        jPanel1.add(input);
        jPanel1.add(Search);
        jPanel1.add(loadList);


        jPanel2.add(output);

        e = new EventHandler(this); //creating event handler for input fields and buttons
        input.addActionListener(e);
        Search.addActionListener(e);
        loadList.addActionListener(e);
    }
}
