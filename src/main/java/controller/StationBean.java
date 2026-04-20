package controller;

import dao.LineDAO;
import dao.StationDAO;
import models.Line;
import models.Station;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("stationBean")
@SessionScoped
public class StationBean implements Serializable {

    private StationDAO dao = new StationDAO();
    private LineDAO lineDAO = new LineDAO();
    private Station newStation = new Station();
    private Station selectedStation = null;
    private String searchQuery = "";

    public List<Line> getAllLines() { return lineDAO.getAllLines(); }

    public List<Station> getStations() {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return dao.getAllStations();
        }
        return dao.searchStations(searchQuery);
    }

    public String saveStation() {
        if (getEditMode()) { dao.updateStation(newStation); }
        else { dao.addStation(newStation); }
        return cancelEdit();
    }

    public String editStation(Station station) {
        this.selectedStation = station;
        this.newStation = new Station(station.getId(), station.getName(), station.getZone(), station.getLineId());
        return "index?faces-redirect=true";
    }

    public String delete(int id) {
        dao.deleteStation(id);
        return "index?faces-redirect=true";
    }

    public String cancelEdit() {
        newStation = new Station();
        selectedStation = null;
        searchQuery = "";
        return "index?faces-redirect=true";
    }

    public boolean getEditMode() { return selectedStation != null; }
    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }
    public Station getNewStation() { return newStation; }
    public void setNewStation(Station newStation) { this.newStation = newStation; }
}