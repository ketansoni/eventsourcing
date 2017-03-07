package com.ketan.ecom.axon.jpa;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.serializer.SerializedObject;
import org.joda.time.DateTime;

import javax.persistence.Entity;

@Entity
public class DomainEventEntry extends AbstractEventEntry {

    protected DomainEventEntry(){
    }

    public DomainEventEntry(String type, DomainEventMessage eventMessage,
                            SerializedObject<String> payload,
                            SerializedObject<String> metaData){
        super(type, eventMessage, payload, metaData);
    }

    public DomainEventEntry(String type, DomainEventMessage eventMessage,
                            DateTime dateTime,
                            SerializedObject<String> payload,
                            SerializedObject<String> metaData){
        super(type, eventMessage, dateTime, payload, metaData);
    }

}