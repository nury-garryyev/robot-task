package com.nury.robotapi.service;

import com.nury.robotapi.exception.RobotException;
import com.nury.robotapi.logic.RobotManager;
import com.nury.robotapi.model.Commands;
import com.nury.robotapi.model.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    private final Logger log = LoggerFactory.getLogger(RobotService.class);

    private final RobotManager robotManager;

    @Autowired
    public RobotService(RobotManager robotManager) {
        this.robotManager = robotManager;
    }

    public void executeCommands(Robot robot, Commands commands) throws RobotException {
        log.debug("Execute commands {} for robot {}", commands, robot);

        robotManager.setRobot(robot);

        for(String command : commands.getCommands()) {
            robotManager.runCommand(command, commands.getGridWidth(), commands.getGridHeight());
        }
    }
}
