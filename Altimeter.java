package org.example;

import java.util.Scanner;

public class Altimeter {
    private static final double SEA_LEVEL_PRESSURE = 1013.25; // Standardowe ciśnienie na poziomie morza(hPa)
    private static final double TEMPERATURE_LAPSE_RATE = 0.0065; // Szybkość spadku temperatury (K/m)
    private static final double SEA_LEVEL_TEMPERATURE = 288.15; // Standardowa temperatura na poziomie morza (K)
    private static final double GAS_CONSTANT = 287.05; // Uniwersalna stała gazowa (J/(kg*K))
    private static final double GRAVITY = 9.80665; // Przyspieszenie swobodnego spadania (m/s²)

    private double pressure; // Aktualne ciśnienie atmosferyczne (hPa)
    private boolean gpwsEnabled; // Flaga systemu GPWS

    public Altimeter() {
        this.pressure = SEA_LEVEL_PRESSURE; // Inicjalizacja przy standardowym ciśnieniu
        this.gpwsEnabled = false;
    }

    // Metoda aktualizacji zmierzonego ciśnienia
    public void updatePressure(double pressure) {
        this.pressure = pressure;
    }

    //Metoda obliczania wysokości w oparciu o Międzynarodową Standardową Atmosferę (ISA)
    public double calculateAltitude() {
        return (SEA_LEVEL_TEMPERATURE / TEMPERATURE_LAPSE_RATE) *
                (1 - Math.pow(pressure / SEA_LEVEL_PRESSURE,
                        (GAS_CONSTANT * TEMPERATURE_LAPSE_RATE) / GRAVITY));
    }

    //wyświetlanie wysokości na desce rozdzielczej i GPWS
    public void displayAltitude() {
        double altitude = calculateAltitude();
        System.out.printf("Current height: %.2f meters%n", altitude);

        // Sprawdzanie systemu GPWS pod kątem ostrzeżeń o bliskości ziemi
        if (gpwsEnabled) {
            checkGPWS(altitude);
        }
    }

    // Metoda aktywacji systemu GPWS
    public void enableGPWS() {
        this.gpwsEnabled = true;
        System.out.println("GPWS on.");
    }

    // Metoda dezaktywacji GPWS
    public void disableGPWS() {
        this.gpwsEnabled = false;
        System.out.println("GPWS off.");
    }

    // GPWS sprawdza ostrzeżenia o bliskości ziemi
    private void checkGPWS(double altitude) {
        double alertThreshold = 500; // Próg wysokości dla ostrzeżenia (w metrach)
        if (altitude < alertThreshold) {
            System.out.println("WARNING: Close to the ground! Height below the safety threshold.");
        }
    }

    public static void main(String[] args) {
        Altimeter altimeter = new Altimeter();
        Scanner scanner = new Scanner(System.in);

        // Włączenie GPWS
        altimeter.enableGPWS();

        // Ręczne wprowadzanie ciśnienia i obliczanie wysokości
        while (true) {
            System.out.print("Enter the current atmospheric pressure (hPa) or 'exit' to exit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                double inputPressure = Double.parseDouble(input);
                altimeter.updatePressure(inputPressure);
                altimeter.displayAltitude();
            } catch (NumberFormatException e) {
                System.out.println("Incorrect value. Please enter a numerical pressure value.");
            }
        }

        // Wyłączenie GPWS przed wyjściem
        altimeter.disableGPWS();
        scanner.close();
    }
}