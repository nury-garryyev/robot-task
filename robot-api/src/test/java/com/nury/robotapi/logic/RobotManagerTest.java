package com.nury.robotapi.logic;

import com.nury.robotapi.exception.RobotException;
import com.nury.robotapi.model.Robot;
import com.nury.robotapi.model.enumeration.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RobotManagerTest {

    @Autowired
    private RobotManager robotManager;

    private Robot robot;
    int gridWidth = 5, gridHeight = 5;

    @BeforeEach
    void setUp() {
        robot = new Robot(0, 0, Direction.SOUTH);
        robotManager.setRobot(robot);
    }

    @Test
    void runCommand_position() throws RobotException {
        robotManager.runCommand("POSITION 0 0 SOUTH", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.SOUTH);

        robotManager.runCommand("POSITION 2 2 NORTH", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 2);
        assertEquals(robot.getY(), 2);
        assertEquals(robot.getDirection(), Direction.NORTH);

        robotManager.runCommand("POSITION 4 4 EAST", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 4);
        assertEquals(robot.getY(), 4);
        assertEquals(robot.getDirection(), Direction.EAST);

        robotManager.runCommand("POSITION 2 3 WEST", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 2);
        assertEquals(robot.getY(), 3);
        assertEquals(robot.getDirection(), Direction.WEST);

        assertThrows(Exception.class, () -> robotManager.runCommand("POSITION 1 7 EAST", gridWidth, gridHeight));

        assertThrows(Exception.class, () -> robotManager.runCommand("POSITION 1 -3 EAST", gridWidth, gridHeight));
    }

    @Test
    void runCommand_rotation() throws RobotException {
        robotManager.runCommand("TURNAROUND", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.NORTH);

        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.EAST);

        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.NORTH);

        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        robotManager.runCommand("LEFT", gridWidth, gridHeight);
        robotManager.runCommand("LEFT", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.WEST);
    }

    @Test
    void runCommand_movement() throws RobotException {
        robotManager.runCommand("WAIT", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.SOUTH);

        robotManager.runCommand("FORWARD 1", gridWidth, gridHeight);
        robotManager.runCommand("FORWARD 1", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 0);
        assertEquals(robot.getY(), 2);
        assertEquals(robot.getDirection(), Direction.SOUTH);

        robotManager.runCommand("LEFT", gridWidth, gridHeight);
        robotManager.runCommand("FORWARD 3", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 3);
        assertEquals(robot.getY(), 2);
        assertEquals(robot.getDirection(), Direction.EAST);

        robotManager.runCommand("FORWARD 3", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 4);
        assertEquals(robot.getY(), 2);
        assertEquals(robot.getDirection(), Direction.EAST);

        robotManager.runCommand("RIGHT", gridWidth, gridHeight);
        robotManager.runCommand("FORWARD 3", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 4);
        assertEquals(robot.getY(), 4);
        assertEquals(robot.getDirection(), Direction.SOUTH);

        robotManager.runCommand("TURNAROUND", gridWidth, gridHeight);
        robotManager.runCommand("FORWARD 9", gridWidth, gridHeight);
        assertNotNull(robot);
        assertEquals(robot.getX(), 4);
        assertEquals(robot.getY(), 0);
        assertEquals(robot.getDirection(), Direction.NORTH);
    }
}
