package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public class TemperatureSeriesAnalysis {
    static final int MINIMUM = -273;
    static final int MAXIMUM = 273;
    private int lastIdx = -1;
    private double[] temperatureSeries;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperature) {
        temperatureSeries = Arrays.copyOf(temperature, temperature.length);
        lastIdx = temperature.length-1;
        testTemps();
    }


    private void testTemps() {
        double[] tempsCopy = new double[temperatureSeries.length];
        int i = 0;
        boolean raise = false;

        for (double temp: temperatureSeries) {
            if (temp < MINIMUM) {
                raise = true;
            } else {
                tempsCopy[i] = temp;
                i += 1;
            }
        }

        temperatureSeries = tempsCopy;
        lastIdx = temperatureSeries.length-1;
        if (raise) {
            throw new InputMismatchException("False value!");
        }
    }

    public double average() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("No elements in array.");
        }

        int sum = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (i > lastIdx) {
                break;
            }
            sum += temperatureSeries[i];
        }
        return sum / (lastIdx+1);
    }

    public double deviation() {

        double average = average();
        double sum = 0;

        for (int i = 0; i < temperatureSeries.length; i++) {
            double x = Math.abs(temperatureSeries[i]-average);
            sum += x*x;
        }
        return Math.sqrt(sum / (lastIdx+1));
    }

    public double min() {
        return findTempClosestToValue(MINIMUM);
    }

    public double max() {
        return findTempClosestToValue(MAXIMUM);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("No elements in array.");
        }

        double dist = Double.MAX_VALUE;
        double closestVal = temperatureSeries[0];

        for (double temperature : temperatureSeries) {
            if (Math.abs(tempValue - temperature) < dist) {
                dist = Math.abs(tempValue - temperature);
                closestVal = temperature;
            } else if  (Math.abs(tempValue - temperature) == dist) {
                if (temperature > closestVal) {
                    closestVal = temperature;
                }
            }
        }
        return closestVal;

    }

    public double[] findTempsLessThen(double tempValue) {

        return reduceTemps("<", tempValue);
    }

    public double[] findTempsGreaterThen(double tempValue) {

        return reduceTemps(">", tempValue);
    }

    private double[] reduceTemps(String sign, double tempValue) {
        double[] reduced = new double[temperatureSeries.length];
        int i = 0;

        if (Objects.equals(sign, ">")) {
            for (double temp: temperatureSeries) {
                if (temp > tempValue) {
                    reduced[i] = temp;
                    i += 1;
                }
            }
        } else if (Objects.equals(sign, "<")) {
            for (double temp: temperatureSeries) {
                if (temp < tempValue) {
                    reduced[i] = temp;
                    i += 1;
                }
            }
        }
        reduced = Arrays.copyOf(reduced, i);
        return reduced;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    private int findSum(double[] temps) {
        int sum = 0;

        for (int i = 0; i < temps.length; i++) {
            if (i == lastIdx+1) {
                break;
            } else {
                sum += temps[i];
            }
        }

        return sum;
    }

    public int addTemps(double... temps) {
        boolean raise = false;

        if (lastIdx == -1) {
            temperatureSeries = new double[1];
            lastIdx = 0;
        }

        for (double temp: temps) {
            if (temperatureSeries.length-1 == lastIdx) {
                double[] copy = resizeArray(temperatureSeries);
                temperatureSeries = copy;
            }

            if (temp < MINIMUM) {
                raise = true;
            } else {
                temperatureSeries[lastIdx+1] = temp;
                lastIdx += 1;
            }
        }

        if (raise) {
            throw new InputMismatchException("False value!");
        }

        return findSum(temperatureSeries);
    }

    private double[] resizeArray(double[] oldArray) {
        int newSize = oldArray.length*2;
        double[] newTemps = new double[newSize];

        for (int i = 0; i < oldArray.length; i++) {
            newTemps[i] = oldArray[i];
        }

        return newTemps;
    }
}
