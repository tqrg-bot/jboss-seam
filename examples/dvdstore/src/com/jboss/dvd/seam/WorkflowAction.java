/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */ 
package com.jboss.dvd.seam;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.ejb.Interceptor;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.BeginTask;
import org.jboss.seam.annotations.CompleteTask;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.CreateProcess;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.ResumeTask;

import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelectionIndex;
import org.jboss.seam.core.ManagedJbpmSession;
import org.jboss.seam.ejb.SeamInterceptor;
import org.jboss.seam.jsf.ListDataModel;

import org.jbpm.db.JbpmSession;
import org.jbpm.db.JbpmSessionFactory;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.def.ProcessDefinition;    

import org.jbpm.taskmgmt.exe.TaskInstance;

@Stateful
@Name("workflow")
@Conversational(ifNotBegunOutcome="admin")
@LoggedIn
@Interceptor(SeamInterceptor.class)
public class WorkflowAction
    implements Workflow,
               Serializable
{
    @In(value="currentUser",required=false)
    Admin admin;

    @PersistenceContext(unitName="dvd")
    EntityManager em;

    @Out(required=false)
    Order order;

    // cant inject primitivee
    @In(required=false)
    Long orderId;

    String track;

    public String getTrack() {
        return track;
    }
    public void setTrack(String track) {
        this.track=track;
    }


    @ResumeTask
    @Begin
    public String viewTask() {
        System.out.println("VIEW TASK!!! " + orderId);

        order = (Order) em.createQuery("from Order o JOIN FETCH o.orderLines where o.orderId = :orderId")
            .setParameter("orderId", orderId.longValue())
            .getSingleResult();

        System.out.println("ORDER: " + order);
        return "ship";
    }


    @CompleteTask
    @End
    public String ship() {
        System.out.println("SHIPPED!");
        order.ship(track);
        
        return "admin";
    }

    @Destroy 
    @Remove
    public void destroy() {
    }
}
