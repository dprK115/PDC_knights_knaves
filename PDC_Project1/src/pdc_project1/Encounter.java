/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
import java.io.Serializable;

public abstract class Encounter implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int id;
    public abstract void start(Player player);
}
