package controller;

import dao.LineDAO;
import models.Line;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("lineBean")
@SessionScoped
public class LineBean implements Serializable {
    private LineDAO dao = new LineDAO();
    private Line newLine = new Line();
    private Line selectedLine = null;

    public List<Line> getLines() { return dao.getAllLines(); }

    public String saveLine() {
        if (getEditMode()) { dao.updateLine(newLine); }
        else { dao.addLine(newLine); }
        return cancelEdit();
    }

    public String editLine(Line line) {
        this.selectedLine = line;
        this.newLine = new Line(line.getId(), line.getName());
        return "lines?faces-redirect=true";
    }

    public String delete(int id) {
        dao.deleteLine(id);
        return "lines?faces-redirect=true";
    }

    public String cancelEdit() {
        newLine = new Line();
        selectedLine = null;
        return "lines?faces-redirect=true";
    }

    public boolean getEditMode() { return selectedLine != null; }
    public Line getNewLine() { return newLine; }
    public void setNewLine(Line newLine) { this.newLine = newLine; }
}
