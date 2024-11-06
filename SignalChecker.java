package org.example;

public class SignalChecker {

    // Metoda obliczania średniej z dwóch sygnałów
    public static double calculateAverage(double signalA, double signalB) {
        return (signalA + signalB) / 2.0;
    }

    // Metoda sprawdzania sygnałów i identyfikowania uszkodzonych sygnałów
    public static void checkSignals(double signalA, double signalB, double signalC) {
        // Порог расхождения между сигналами
        double threshold = 2;

        // Oblicz różnicę między sygnałami
        double diffAB = Math.abs(signalA - signalB);
        double diffAC = Math.abs(signalA - signalC);
        double diffBC = Math.abs(signalB - signalC);

        // Jeśli wszystkie sygnały są równe (różnica między dowolnymi dwoma sygnałami jest mniejsza niż próg)
        if (diffAB <= threshold && diffAC <= threshold && diffBC <= threshold) {
            System.out.println("All signals are the same.");
            double average = calculateAverage(signalA, signalB);  // Можно взять любое, так как все одинаковы
            System.out.println("Average value: " + average);
        }
        // Jeśli dwa sygnały są takie same, a jeden jest inny, obliczamy średnią dwóch identycznych sygnałów
        else if (diffAB <= threshold && diffAC > threshold && diffBC > threshold) {
            System.out.println("Broken signal: C");
            double average = calculateAverage(signalA, signalB);
            System.out.println("the average value between A and B: " + average);
        } else if (diffAB > threshold && diffAC <= threshold && diffBC > threshold) {
            System.out.println("Broken signal: B");
            double average = calculateAverage(signalA, signalC);
            System.out.println("Average value between A and C: " + average);
        } else if (diffAB > threshold && diffAC > threshold && diffBC <= threshold) {
            System.out.println("Broken signal: A");
            double average = calculateAverage(signalB, signalC);
            System.out.println("The average between B and C: " + average);
        }
        // Jeśli wszystkie trzy sygnały bardzo się od siebie różnią
        else {
            System.out.println("Further investigation is needed. All the signals are very different.");
        }
    }

    public static void main(String[] args) {
        // Przykład sygnałów
        double signalA = 2.03;
        double signalB = 3;
        double signalC = 6;

        // Sprawdź sygnały
        checkSignals(signalA, signalB, signalC);



    }
}