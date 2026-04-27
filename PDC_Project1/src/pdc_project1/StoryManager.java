/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
import java.util.ArrayList;
import java.io.Serializable;

public class StoryManager implements Serializable {

    private static final long serialVersionUID = 1L;
    ArrayList<Encounter> encounters = new ArrayList<>();
    int index = 0;

    public void addEncounter(Encounter e) {
        encounters.add(e);
    }
    
    public Encounter getCurrentEncounter(){
        return encounters.get(index);
    }
    
    public Encounter getNextEncounter() {
        if (index < encounters.size()) {
            
            return encounters.get(index++);
        }
        return null;
    }
}
