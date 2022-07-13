package com.nury.robotapi.controller;

import com.nury.robotapi.exception.RobotException;
import com.nury.robotapi.model.enumeration.Direction;
import com.nury.robotapi.model.Commands;
import com.nury.robotapi.model.Robot;
import com.nury.robotapi.model.response.Response;
import com.nury.robotapi.service.RobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/robot")
public class RobotController {

    private final Logger log = LoggerFactory.getLogger(RobotController.class);

    private final RobotService robotService;

    @Autowired
    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @PostMapping("/commands")
    public ResponseEntity<Response> getCommands(@RequestBody Commands commands) throws RobotException {
        log.debug("REST request to run commands : {}", commands);

        Robot robot = new Robot(0, 0, Direction.SOUTH);
        robotService.executeCommands(robot, commands);

        return ResponseEntity.ok(new Response(robot));
    }
}
