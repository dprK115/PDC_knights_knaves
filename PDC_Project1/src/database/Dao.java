/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */
import java.util.List;


public interface Dao <E>{
    
    List<E> loadAll();
    boolean elementExists(E e);
    void save(E e);
    void update(E e);
    void insert(E e);
    E loadByID(int id);
    void delete(E e);
    
}
