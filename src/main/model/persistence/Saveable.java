package model.persistence;

import java.io.PrintWriter;

//Reference: TellerApp
//Represents data can be saved in file
public interface Saveable {

    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}
