import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.TreeMap;

public class EventHandler implements ActionListener {

    HandwashApp app;
    WashFrame f;
    public EventHandler(WashFrame washFrame) {
        f = washFrame;
        app = new HandwashApp();
    }

    public void printFile() {
        app.download();

        f.output.setText("\tTime\tID\tDuration\n");
        for (Handwash wash : app.readFile()) {
            //System.out.println(wash.toString());
            f.output.append("\n\t" + wash.toString());
        }
    }

    public void search() {
        String ID = f.input.getText();

        TreeMap<String, List<Handwash>> map = null;
        try {
            map = app.eventMap();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        List<Handwash> washList;

        if (map.containsKey(ID)) {
            washList = map.get(ID);

            double avg = 0;
            for (Handwash g : washList) {
                double compliance = g.duration*3.33<100? (g.duration * 3.33) : 100;
                avg += compliance / washList.size();
            }

            f.output.setText("This ID has " + washList.size() + " recorded handwash event(s) with compliance rating of " + Math.round(avg) + "%\n\n");
            f.output.append("\tTime\tID\tDuration\n");
            for (Handwash wash : washList) {
                //System.out.println(wash.toString());
                f.output.append("\n\t" + wash.toString());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == f.Search) {
            search();
        } else if (e.getSource() == f.loadList) {
            printFile();
        }

    }
}
