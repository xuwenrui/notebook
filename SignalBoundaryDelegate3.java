package com.frank.springactiviti7.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class SignalBoundaryDelegate3 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("信号边界事件3。。" + LocalDateTime.now());
    }
}
