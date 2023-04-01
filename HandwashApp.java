import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HandwashApp {

    public void download() {
        String link = "https://onedrive.live.com/download?resid=DCBE98D5823317F9!34499&authkey=!ACMfz-NxGV5cUqM&ithint=file%2cxlsx&e=aw2MtX";
        String outPath = "src/Data.xlsx";
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(link).openStream());

            //Create file if doesn't exist
            File outFile = new File(outPath);
            outFile.createNewFile();

            FileOutputStream fileOS = new FileOutputStream(outPath);
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            fileOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {

        String value = "";
        try {
            Cell cell = row.getCell(c);//get cell
            DataFormatter formatter = new DataFormatter();
            value = formatter.formatCellValue(cell);//format data to be exactly as shown in excel file
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        return value;
    }

    public List<Handwash> readFile() {

        download();

        List<Handwash> list = new ArrayList();

        File inputFile = new File("src/Data.xlsx");

        try {
            InputStream stream = new FileInputStream(inputFile);

            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(1);       // only second sheet is relevant
            int rows = sheet.getPhysicalNumberOfRows();

            String[] line = new String[3];
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (int r = 7; r < rows; r++) {     // relevant data starts from eighth row

                Row row = sheet.getRow(r);
                //System.out.println(r);      // remove once unnecessary

                for (int c = 0; c < 3; c++) { //store each column value to array

                    String value = getCellAsString(row, c, formulaEvaluator);
                    line[c] = value;
                }

                list.add(new Handwash(line[0], line[1], Double.parseDouble(line[2])));
            }

            stream.close();

        } catch (Exception e) {

            System.out.println(e.toString());

        }
        return list;
    }

    public TreeMap<String, List<Handwash>> eventMap() throws FileNotFoundException {
        TreeMap<String, List<Handwash>> map = new TreeMap<>();

        List<Handwash> eventList = readFile();
        List<String> listOfIDs = new ArrayList<>();

        for (Handwash event : eventList) {
            if (!listOfIDs.contains(event.id)) {
                listOfIDs.add(event.id);
            }
        }
        for (String ID : listOfIDs) {
            List<Handwash> IDlist = new ArrayList<>();
            for (Handwash event : eventList) {
                if (event.id.equals(ID)) {
                    IDlist.add(event);
                }
            }
            map.put(ID, IDlist);
        }
        return map;
    }


}
