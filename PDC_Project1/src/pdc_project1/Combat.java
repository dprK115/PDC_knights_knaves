/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
public class Combat extends Encounter {
    
    Player player;
    Enemy enemy;
    
    public Combat(Player player, Enemy enemy){
        this.player = player;
        this.enemy = enemy;
    }
    
    @Override
    public void start(Player player){
        Combat_Menu menu = new Combat_Menu();
        menu.startCombat(this.player, this.enemy);
    }
}
