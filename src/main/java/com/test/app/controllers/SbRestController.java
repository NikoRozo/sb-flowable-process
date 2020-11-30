package com.test.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.model.HolidayRequest;
import com.test.app.model.TaskRepresentation;
import com.test.app.services.SbFlowableService;

@RestController
public class SbRestController {

	@Autowired
    private SbFlowableService SbFlowableService;

    @PostMapping(value="/process")
    public Map<String, String> startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
    	return SbFlowableService.startProcess(startProcessRepresentation.getAssignee(), startProcessRepresentation.getRequest());
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
    
    @RequestMapping(value="/tasks/task", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTasksById(@RequestParam String id) {
    	
    	Map<String, Object> task = SbFlowableService.getTasksById(id);
        
        return task;
    }    
    
    @PostMapping(value="/complete")
    public Map<String, Object> completeTaskById(@RequestParam String taskid, @RequestBody Map<String, Object> variables) {
    	Map<String, Object> task = SbFlowableService.completeTaskById(taskid, variables);
        
        return task;
    }

    static class StartProcessRepresentation {

    	private String assignee;
    	private HolidayRequest request;
    	
    	public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
        
        public HolidayRequest getRequest() {
        	return request;
        }

        public void setRequest(HolidayRequest request) {
        	this.request = request;
        }
    }

}

