package com.nury.robotapi.logic;

import com.nury.robotapi.exception.RobotException;
import com.nury.robotapi.model.Robot;
import com.nury.robotapi.model.enumeration.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RobotManager {

    private final Logger log = LoggerFactory.getLogger(RobotManager.class);

    private Robot robot;

    private RobotManager() {
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public void runCommand(String command, int gridWidth, int gridHeight) throws RobotException {

        String positionRegex = "^POSITION\\s\\d\\s\\d\\s(NORTH|EAST|WEST|SOUTH)$";
        String rotationRegex = "^(TURNAROUND|RIGHT|LEFT)$";
        String movementRegex = "^(WAIT|FORWARD\\s\\d)$";

        if(command.matches(positionRegex)) {
            updatePosition(command, gridWidth, gridHeight);
        } else if(command.matches(rotationRegex)) {
            setDirection(robot, command);
        } else if(command.matches(movementRegex)) {
            moveRobot(robot, command, gridWidth, gridHeight);
        } else {
            log.error("Invalid command: {}", command);
            throw new RobotException("Invalid command.");
        }
    }

    private void updatePosition(String command, int gridWidth, int gridHeight) throws RobotException {
        log.debug("Update robot position: {}", command);

        String[] newPosition = command.split("\\s");

        int x = Integer.parseInt(newPosition[1]);
        int y = Integer.parseInt(newPosition[2]);

        if(x >= gridWidth || y >= gridHeight || x < 0 || y <0) {
            log.error("Out of bounds: {}x{}", x, y);
            throw new RobotException("Invalid command. Out of bounds.");
        }

        robot.setX(x);
        robot.setY(y);
        robot.setDirection(Direction.valueOf(newPosition[3]));
    }

    private void setDirection(Robot robot, String command) throws RobotException {
        log.debug("Set robot direction: {}", command);

        switch(command) {
            case "TURNAROUND":
                robot.setDirection(Direction.getByCode((robot.getDirection().getCode() + 2) % 4));
                break;
            case "RIGHT":
                robot.setDirection(Direction.getByCode((robot.getDirection().getCode() + 1) % 4));
                break;
            case "LEFT":
                robot.setDirection(Direction.getByCode((robot.getDirection().getCode() - 1 + 4) % 4));
                break;
            default:
                log.error("Invalid command: {}", command);
                throw new RobotException("Invalid command.");
        }
    }

    private void moveRobot(Robot robot, String command, int gridWidth, int gridHeight) throws RobotException {
        log.debug("Move robot: {}", command);

        if(command.equals("WAIT")) {
            log.debug("Waiting...");
        } else {
            String[] movement = command.split("\\s");
            int steps = Integer.parseInt(movement[1]);

            switch(robot.getDirection()) {
                case NORTH:
                    robot.setY(Math.max((robot.getY() - steps), 0));
                    break;
                case EAST:
                    robot.setX(Math.min((robot.getX() + steps), gridWidth - 1));
                    break;
                case SOUTH:
                    robot.setY(Math.min((robot.getY() + steps), gridHeight - 1));
                    break;
                case WEST:
                    robot.setX(Math.max((robot.getX() - steps), 0));
                    break;
                default:
                    log.error("Invalid direction: {}", robot.getDirection());
                    throw new RobotException("Invalid direction.");
            }
        }
    }
}
