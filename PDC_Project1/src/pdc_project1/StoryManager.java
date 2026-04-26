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

public class StoryManager {
    
    ArrayList<Encounter> encounters = new ArrayList<>();
    int index = 0;
    
    public void addEncounter(Encounter e){
        encounters.add(e);
    }
    
    public Encounter getNextEncounter(){
        if(index < encounters.size()){
            return encounters.get(index++);
        }
        return null;
    }
}