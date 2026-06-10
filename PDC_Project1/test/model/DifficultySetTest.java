/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class DifficultySetTest {

    @Test
    public void constructorWithEasyEnum_setsEasyModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(Difficulty.EASY);

        // Assert
        assertEquals(30, DifficultySet.enemyModifier);
        assertEquals(50, DifficultySet.playerModifier);
        assertEquals(5, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithMediumEnum_setsMediumModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(Difficulty.MEDIUM);

        // Assert
        assertEquals(50, DifficultySet.enemyModifier);
        assertEquals(35, DifficultySet.playerModifier);
        assertEquals(10, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithHardEnum_setsHardModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(Difficulty.HARD);

        // Assert
        assertEquals(100, DifficultySet.enemyModifier);
        assertEquals(25, DifficultySet.playerModifier);
        assertEquals(15, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithIntOne_setsEasyModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(1);

        // Assert
        assertEquals(30, DifficultySet.enemyModifier);
        assertEquals(50, DifficultySet.playerModifier);
        assertEquals(5, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithIntTwo_setsMediumModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(2);

        // Assert
        assertEquals(50, DifficultySet.enemyModifier);
        assertEquals(35, DifficultySet.playerModifier);
        assertEquals(10, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithIntThree_setsHardModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(3);

        // Assert
        assertEquals(100, DifficultySet.enemyModifier);
        assertEquals(25, DifficultySet.playerModifier);
        assertEquals(15, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithInvalidInt_defaultsToEasyModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(99);

        // Assert
        assertEquals(30, DifficultySet.enemyModifier);
        assertEquals(50, DifficultySet.playerModifier);
        assertEquals(5, DifficultySet.xpModifier);
    }

    @Test
    public void constructorWithZero_defaultsToEasyModifiers() {
        // Act
        DifficultySet difficultySet = new DifficultySet(0);

        // Assert
        assertEquals(30, DifficultySet.enemyModifier);
        assertEquals(50, DifficultySet.playerModifier);
        assertEquals(5, DifficultySet.xpModifier);
    }
}
