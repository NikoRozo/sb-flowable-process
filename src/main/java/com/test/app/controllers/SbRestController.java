package com.test.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
    	return SbFlowableService.startProcess(startProcessRepresentation.getAssignee(), 
    			new HolidayRequest(startProcessRepresentation.getEmployee(), 
    					startProcessRepresentation.getNrOfHolidays(), 
    					startProcessRepresentation.getDescription()));
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
        task.remove("person");
        return task;
    }
    
    @GetMapping(value="/metrices")
    public Map<String, Object> metricesTaskById(@RequestParam String taskid) {
    	Map<String, Object> metrices = SbFlowableService.metrices(taskid);
        
        return metrices;
    }

    static class StartProcessRepresentation {

    	private String assignee;
    	private String employee;
    	private Integer nrOfHolidays;
    	private String description;
    	
    	public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
        
        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }
        
        public Integer getNrOfHolidays() {
            return nrOfHolidays;
        }

        public void setNrOfHolidays(Integer nrOfHolidays) {
            this.nrOfHolidays = nrOfHolidays;
        }
        
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        
    }

}

