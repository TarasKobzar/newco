package com.andreitop.newco.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutContainer {

    @Pointcut("execution( * com.andreitop.newco.repository.*Repo*.find*())")
    public void repositoryFind() {
    }

    @Pointcut("execution( * com.andreitop.newco.repository.*Repo*.findById(..))")
    public void repositoryFindById() {
    }

    @Pointcut("execution( * *.*.*.*.TripRepository.save(..))")
    public void repositorySave() {
    }

    @Pointcut("execution( * com.andreitop.newco.repository.TripRepository.delete(..))")
    public void repositoryDelete() {
    }

    @Pointcut("execution( * com.andreitop.newco.repository.TripRepository.update(..))")
    public void repositoryUpdate() {
    }
}
