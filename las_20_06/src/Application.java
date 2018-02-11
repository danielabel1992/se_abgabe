import java.io.BufferedReader;
import java.net.Inet4Address;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public List<Record> getRecords() {
        return records;
    }

    private List<Record> records;
    private final static String fileName = "data/records.csv";

    public List<Record> loadRecords() {


        ArrayList<Record> data = new ArrayList<>();
        Path pathToFile = Paths.get(Application.fileName);
        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                Record rec = Record.build(attributes);
                if(rec != null){
                    data.add(rec);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    // count
    public long executeSQL01() {
        return records.stream().count();
    }

    // count, where
    public long executeSQL02() {
        Predicate<Record> recordSeverityMajor = record -> (record.getSeverity().equals("major"));
        Predicate<Record> recordAttackE = record -> (record.getAttackType().equals("e"));
        Predicate<Record> recordSourceSmallerOrEqual2 = record -> (record.getSource() <= 2 );
        Predicate<Record> recordShiftEqual4 = record -> (record.getShift() == 4 );
        return records.stream().filter(recordSeverityMajor).filter(recordAttackE).filter(recordSourceSmallerOrEqual2).filter(recordShiftEqual4).count();
    }

    // count, where, in
    public long executeSQL03() {
        Predicate<Record> recordSeverityMajorOrCritical = record -> (record.getSeverity().equals("major") || record.getSeverity().equals("critical"));
        Predicate<Record> recordAttackB = record -> (record.getAttackType().equals("b"));
        Predicate<Record> recordSourceEqual4 = record -> (record.getSource() == 4 );
        Predicate<Record> recordShiftGreaterOrEqual3 = record -> (record.getShift() >= 3);
        return records.stream().filter(recordSeverityMajorOrCritical).filter(recordAttackB).filter(recordSourceEqual4).filter(recordShiftGreaterOrEqual3).count();
    }

    // count, where, not in
    public long executeSQL04() {
        Predicate<Record> recordSeverityCritical = record -> (record.getSeverity().equals("critical"));
        Predicate<Record> recordAttackNotB = record -> (!record.getAttackType().equals("b"));
        Predicate<Record> recordAttackNotC = record -> (!record.getAttackType().equals("c"));
        Predicate<Record> recordAttackNotG = record -> (!record.getAttackType().equals("g"));
        Predicate<Record> recordSourceGreater2 = record -> (record.getSource() > 2 );
        Predicate<Record> recordShiftSmallerOrEqual2 = record -> (record.getShift() <= 2);
        return records.stream().filter(recordSeverityCritical).filter(recordAttackNotB).filter(recordAttackNotC).filter(recordAttackNotG).filter(recordSourceGreater2).filter(recordShiftSmallerOrEqual2).count();
    }

    // id, where, in, order by desc limit
    public int[] executeSQL05() {
        Predicate<Record> recordSeverityMinor = record -> (record.getSeverity().equals("minor"));
        Predicate<Record> recordAttackAOrB = record -> (record.getAttackType().equals("a") || record.getAttackType().equals("b"));
        Predicate<Record> recordSourceEqual1 = record -> (record.getSource() == 1 );
        Predicate<Record> recordShifttEqual4 = record -> (record.getShift() == 4);
        Predicate<Record> recordDownGreaterOrEqual195 = (record -> (record.getDowntimeInMinutes() >= 195));

        Comparator<Record> comparatorInt  =  Comparator.comparingInt((Record record) -> record.getDowntimeInMinutes()).reversed();

        int[] filtered = records.stream().filter(recordSeverityMinor).filter(recordAttackAOrB).filter(recordSourceEqual1).filter(recordShifttEqual4).filter(recordDownGreaterOrEqual195).sorted(comparatorInt).limit(3).mapToInt(Record::getId).toArray();

        return filtered;
    }

    public int[] executeSQL06() {
        Predicate<Record> recordSeverityMinorOrMajor = record -> (record.getSeverity().equals("minor") || record.getSeverity().equals("major"));
        Predicate<Record> recordAttackC = record -> (record.getAttackType().equals("c"));
        Predicate<Record> recordSourceEqual2 = record -> (record.getSource() == 2 );
        Predicate<Record> recordShifttEqual1 = record -> (record.getShift() == 1);
        Predicate<Record> recordIdSmallerEqual500 = record -> (record.getId() <= 500 );
        Comparator<Record> comparator = Comparator.comparing((Record r) -> r.getSeverity()).reversed();
        comparator = comparator.thenComparing((r01, r02) -> r01.getDowntimeInMinutes() - r02.getDowntimeInMinutes());
        Stream<Record> sr = records.stream().filter(recordSeverityMinorOrMajor).filter(recordAttackC).filter(recordSourceEqual2).filter(recordShifttEqual1).filter(recordIdSmallerEqual500);
        int[] ar = sr.sorted(comparator).mapToInt(Record::getId).toArray();
        return ar;
    }

    // count, group by
    public Map<String, Long> executeSQL07() {
        Map<String, Long> map =  records.stream().collect(Collectors.groupingBy( (Record record) -> record.getSeverity(), Collectors.counting() ));
        return map;
    }

    // count, where, group by
    public Map<Integer, Long> executeSQL08() {
        Predicate<Record> recordAttackType = record -> (record.getAttackType().equals("d"));
        Predicate<Record> recordSeverityMajor = record -> (record.getSeverity().equals("major"));
        Map<Integer, Long> map = records.stream().filter(recordAttackType).filter(recordSeverityMajor).collect(Collectors.groupingBy( (Record record ) -> record.getShift(), Collectors.counting()));
        return map;
    }

    // count, where, in, group by
    public Map<String, Long> executeSQL09() {
        Predicate<Record> recordAorBorC = record -> (record.getAttackType().equals("a") || record.getAttackType().equals("b") || record.getAttackType().equals("c"));
        Predicate<Record> recordSource3 = record -> (record.getSource() == 3);
        Map<String, Long> map =  records.stream().filter(recordAorBorC).filter(recordSource3).collect(Collectors.groupingBy( (Record record ) -> record.getAttackType(), Collectors.counting()));
        return map;
    }

    // count, where, not in, group by
    public Map<Integer, Long> executeSQL10() {
        Predicate<Record> recordNOTBorDorE = record -> (!record.getAttackType().equals("b") && !record.getAttackType().equals("d") && !record.getAttackType().equals("e"));
        Predicate<Record> recordShiftGreaterOrEqual2 = record -> record.getShift() >= 2;
        Predicate<Record> recordDownGreaterOrEqual30 = record -> record.getDowntimeInMinutes() >= 30;
        Predicate<Record> recordDownSmallerOrEqual90 = record -> record.getDowntimeInMinutes() <= 90;
        Map<Integer, Long> map =  records.stream().filter(recordNOTBorDorE).filter(recordShiftGreaterOrEqual2).filter(recordDownGreaterOrEqual30).filter(recordDownSmallerOrEqual90).collect(Collectors.groupingBy( (Record record ) -> record.getSource(), Collectors.counting()));
        return map;
    }

    // sum, where, not in, in, group by
    public Map<String, Long> executeSQL11() {
        Predicate<Record> recordNOTBorDorE = record -> (!record.getAttackType().equals("b") && (!record.getAttackType().equals("d")) && (!record.getAttackType().equals("e")));
        Predicate<Record> recordShiftEqual1 = record -> record.getShift() == 1;
        Predicate<Record> recordSource1or3 = record -> record.getSource() == 3 || record.getSource() == 1;
        Map<String, Long> map =  records.stream().filter(recordNOTBorDorE).filter(recordShiftEqual1).filter(recordSource1or3).collect(Collectors.groupingBy( (Record record ) -> record.getAttackType(), Collectors.summingLong((Record record ) -> record.getDowntimeInMinutes())));
        return map;
    }

    // avg, where, in, in, group by
    public Map<String, Double> executeSQL12() {
        Predicate<Record> recordServerityMinorOrMajor = record -> (record.getSeverity().equals("major") || record.getSeverity().equals("minor"));
        Predicate<Record> recordAttackAorborc = record -> (record.getAttackType().equals("a") || record.getAttackType().equals("b") || record.getAttackType().equals("c"));
        Predicate<Record> recordSourceEqual1 = record -> record.getSource() == 1;
        Predicate<Record> recordShiftGreaterEquals3 = record -> record.getShift() >= 3;
        Map<String, Double> map =  records.stream().filter(recordServerityMinorOrMajor).filter(recordAttackAorborc).filter(recordSourceEqual1).filter(recordShiftGreaterEquals3).collect(Collectors.groupingBy( (Record record ) -> record.getAttackType(), Collectors.averagingInt((Record record ) -> record.getDowntimeInMinutes())));
        return map;
    }

    public void execute() {
        System.out.println("1: " + executeSQL01());
        System.out.println("2: " + executeSQL02());
        System.out.println("3: " + executeSQL03());
        System.out.println("4: " + executeSQL04());
        System.out.println("5: " + executeSQL05());
        System.out.println("6: " + executeSQL06());
        System.out.println("7: " + executeSQL07());
        System.out.println("8: " + executeSQL08());
        System.out.println("9: " + executeSQL09());
        System.out.println("10: " + executeSQL10());
        System.out.println("11: " + executeSQL11());
        System.out.println("12: " + executeSQL12());

    }

    public Application(){
        records = loadRecords();
    }

    public static void main(String... args) {
        Application app = new Application();
        app.execute();

    }
}