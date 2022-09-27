package promillekalkulator.models;

public class Alcohol {

    private String alcoholType;
    private double percent;
    private double volume;
    private int numberOfUnits;

    public Alcohol(String alcoholType, int numberOfUnits, double percent, double volume) {
        this.alcoholType = alcoholType;
        this.percent = percent;
        this.volume = volume;
        this.numberOfUnits = numberOfUnits;
    }

    public void setAlcoholType(String alcoholType) {
        if (alcoholType == "ØL" || alcoholType == "VIN" || alcoholType == "BRENNEVIN" || alcoholType == "ANNET") {
            this.alcoholType = alcoholType;
            return;
        }
        throw new IllegalArgumentException();
    }

    public String getAlcoholType() {
        return alcoholType;
    }

    public void setPercent(Double percent) {
        if (!(percent.isNaN() == true || percent < 0.0 || percent > 60.0)) {
            this.percent = percent;
            return;
        }
        throw new IllegalArgumentException("percent is invalid");
    }

    public double getPercent() {
        return percent;
    }

    public void setVolume(Double volume) {
        if (!(volume.isNaN() == true || volume < 0.0 || volume > 10.0)) {
            this.volume = volume;
            return;
        }
        throw new IllegalArgumentException();
    }

    public double getVolume() {
        return volume;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        if (!(numberOfUnits < 0 || numberOfUnits > 20.0)) {
            this.numberOfUnits = numberOfUnits;
            return;
        }
        throw new IllegalArgumentException("Not valid number of units");
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

}
