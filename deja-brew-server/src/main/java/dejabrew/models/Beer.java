package dejabrew.models;

import java.util.Objects;

public class Beer {
    private int beerId;
    private String beerName;
    private double abv;
    private String type;
    private String breweryId;

    public Beer(){}

    public Beer(String beerName, double abv, String type, String breweryId) {
        this.beerName = beerName;
        this.abv = abv;
        this.type = type;
        this.breweryId = breweryId;
    }

    public int getBeerId() {
        return beerId;
    }

    public String getBeerName() {
        return beerName;
    }

    public double getAbv() {
        return abv;
    }

    public String getType() {
        return type;
    }

    public String getBreweryId() {
        return breweryId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBreweryId(String breweryId) {
        this.breweryId = breweryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return beerId == beer.beerId && Double.compare(beer.abv, abv) == 0 && beerName.equals(beer.beerName) && type.equals(beer.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beerId, beerName, abv, type);
    }
}
