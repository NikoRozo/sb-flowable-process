package com.test.app.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectionMail implements JavaDelegate{
	
	public void execute(DelegateExecution execution) {
        System.out.println("Period not approved for "
            + execution.getVariable("employee"));
    }
	
}
