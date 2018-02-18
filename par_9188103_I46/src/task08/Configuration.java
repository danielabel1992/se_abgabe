package task08;

public enum Configuration {
    instance;

    public final int maximumI = 10000000; // Maximale Wiederholungen
    public final int maxGeneratedPrimeNumbers = 10000; // Wie viele Primzahlen sollen generiert werden
    public final boolean useBigInt = false; // Falls größere Primzahlen erzeugt werden sollen. Dauert aber länger und der Heap hat nicht das Säbelzahnmuster
    String splitter = "|";
}