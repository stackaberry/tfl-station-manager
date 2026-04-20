package models;
import java.io.Serializable;
public class Station implements Serializable {
    private int id;
    private String name;
    private int zone;
    private String lineName; // Add this field
    private int lineId;
    public Station() {}
    public Station(int id, String name, int zone, int lineId) {
        this.id = id; this.name = name; this.zone = zone; this.lineId = lineId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getZone() { return zone; }
    public void setZone(int zone) { this.zone = zone; }
    public int getLineId() { return lineId; }
    public void setLineId(int lineId) { this.lineId = lineId; }

    public String getLineName() { return lineName; }
    public void setLineName(String lineName) { this.lineName = lineName; }
}
