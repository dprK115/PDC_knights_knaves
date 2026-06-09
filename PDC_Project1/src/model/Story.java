/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vishw
 */
public class Story extends Encounter {
    
    private String text;
    
    public Story(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    
    @Override
    public void start(Player player){
        System.out.println(text);
    }
}
