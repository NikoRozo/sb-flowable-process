package com.test.app.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.app.model.HolidayRequest;
import com.test.app.model.Person;
import com.test.app.repository.PersonRepository;

@Service
@Transactional
public class SbFlowableService {

	@Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PersonRepository personRepository;

    public Map<String, String> startProcess(String assignee, HolidayRequest request) {

        Person person = personRepository.findByUsername(assignee);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        variables.put("employee", request.getEmployee());
		variables.put("nrOfHolidays", request.getNrOfHolidays());
		variables.put("description", request.getDescription());
        ProcessInstance inst = runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
        Map<String, String> result = new HashMap<String, String>();
        result.put("taksId", inst.getId());
        return result;
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
        	personRepository.save(new Person(null, "nrozo", "Nicolás", "Rozo", new Date()));
        	personRepository.save(new Person(null, "jbarrez", "Joram", "Barrez", new Date()));
            personRepository.save(new Person(null, "trademakers", "Tijs", "Rademakers", new Date()));
        }
    }
    
    public Map<String, Object> getTasksById(String taskid) {
    	Task task = taskService.createTaskQuery().processInstanceId(taskid).list().get(0);
    	Map<String, Object> processVariables = taskService.getVariables(task.getId());
    	
        return processVariables;
    }
    
    
    public Map<String, Object> completeTaskById(String taskid, Map<String, Object> variables) {
    	Task task = taskService.createTaskQuery().processInstanceId(taskid).list().get(0);

		taskService.complete(task.getId(), variables);
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("Status", "Complete");
    	result.put("taksId", task.getId());
        result.put("variable", variables);
        
        return result;
    }
    
}
