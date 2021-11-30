package ua.edu.ucu.tempseries;


public final class TempSummaryStatistics {
    private final double avgTemp;
    private final double devTemp;
    private final double minTemp;
    private final double maxTemp;

    public TempSummaryStatistics(double av, double dev, double mn, double mx) {
        this.avgTemp = av;
        this.devTemp = dev;
        this.minTemp = mn;
        this.maxTemp = mx;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDevTemp() {
        return devTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
