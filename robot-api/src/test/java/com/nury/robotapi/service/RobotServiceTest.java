package com.nury.robotapi.service;

import com.nury.robotapi.exception.RobotException;
import com.nury.robotapi.model.Commands;
import com.nury.robotapi.model.Robot;
import com.nury.robotapi.model.enumeration.Direction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RobotServiceTest {

    @Autowired
    private RobotService robotService;

    private static Robot robot;
    private static Commands commands;

    @BeforeAll
    static void beforeAll() {
        robot = new Robot(0, 0, Direction.SOUTH);

        List<String> commandList = Arrays.asList("POSITION 1 3 EAST", "FORWARD 3", "WAIT", "TURNAROUND", "FORWARD 1", "RIGHT", "FORWARD 2");
        commands = new Commands(5, 5, commandList);
    }

    @Test
    void executeCommands() throws RobotException {
        robotService.executeCommands(robot, commands);

        assertNotNull(robot);
        assertEquals(robot.getX(), 3);
        assertEquals(robot.getY(), 1);
        assertEquals(robot.getDirection(), Direction.NORTH);
    }

    @Test
    void executeCommands_out_of_bounds() {
        assertThrows(RobotException.class, () -> robotService.executeCommands(robot, new Commands(5, 5, Collections.singletonList("POSITION 1 7 EAST"))));
    }
}
