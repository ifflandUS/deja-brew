package dejabrew.models;

import java.time.LocalDate;
import java.util.List;

public class Visit {

    private int visitId;


    private int userId;


    private String breweryId;


    private LocalDate date;

    private List<Beer> beerList;

    public Visit() {

    }

    public Visit(int visitId, int userId, String breweryId, LocalDate date, List<Beer> beerList) {
        this.visitId = visitId;
        this.userId = userId;
        this.breweryId = breweryId;
        this.date = date;
        this.beerList = beerList;
    }


    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(String breweryId) {
        this.breweryId = breweryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Beer> getBeerList() {
        return beerList;
    }

    public void setBeerList(List<Beer> beerList) {
        this.beerList = beerList;
    }
}
