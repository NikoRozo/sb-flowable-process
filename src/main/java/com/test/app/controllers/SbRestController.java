package com.test.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.model.TaskRepresentation;
import com.test.app.services.SbFlowableService;

@RestController
public class SbRestController {

	@Autowired
    private SbFlowableService SbFlowableService;

    @PostMapping(value="/process")
    public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
    	SbFlowableService.startProcess(startProcessRepresentation.getAssignee());
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = SbFlowableService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    static class StartProcessRepresentation {

    	private String assignee;
    	
    	public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }

}

