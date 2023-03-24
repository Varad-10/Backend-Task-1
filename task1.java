import java.io.*;
import java.util.*;
import org.apache.commons.csv.*;

public class CSVProcessor {
    private List<CSVRecord> records;

    public CSVProcessor() {
        records = new ArrayList<>();
    }

    public void loadFile(String filename) throws IOException {
        Reader reader = new FileReader(filename);
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
        records = parser.getRecords();
        System.out.println("Loaded " + records.size() + " rows from " + filename);
    }

    public int countRows() {
        return records.size();
    }

    public double calculateMean(String columnName) {
        double sum = 0;
        int count = 0;
        for (CSVRecord record : records) {
            String value = record.get(columnName);
            try {
                sum += Double.parseDouble(value);
                count++;
            } catch (NumberFormatException e) {
                // Ignore non-numeric values
            }
        }
        return sum / count;
    }

    public void filterRows(String columnName, String value) {
        List<CSVRecord> filteredRecords = new ArrayList<>();
        for (CSVRecord record : records) {
            String columnValue = record.get(columnName);
            if (columnValue.equals(value)) {
                filteredRecords.add(record);
            }
        }
        records = filteredRecords;
        System.out.println("Filtered " + filteredRecords.size() + " rows");
    }

    public static void main(String[] args) throws IOException {
        CSVProcessor processor = new CSVProcessor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+");
            String operation = tokens[0];

            switch (operation) {
                case "load":
                    String filename = tokens[1];
                    processor.loadFile(filename);
                    break;
                case "count":
                    int rowCount = processor.countRows();
                    System.out.println("Row count: " + rowCount);
                    break;
                case "mean":
                    String columnName = tokens[1];
                    double mean = processor.calculateMean(columnName);
                    System.out.printf("Mean of column %s: %.2f\n", columnName, mean);
                    break;
                case "filter":
                    String filterColumn = tokens[1];
                    String filterValue = tokens[2];
                    processor.filterRows(filterColumn, filterValue);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
