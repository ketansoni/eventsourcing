package com.ketan.ecom.axon.jpa;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.serializer.SerializedObject;

import javax.persistence.Entity;

@Entity
public class SnapshotEventEntry extends AbstractEventEntry {

    protected SnapshotEventEntry(){
    }

    public SnapshotEventEntry(String type, DomainEventMessage eventMessage,
                              SerializedObject<String> payload,
                              SerializedObject<String> metaData){
        super(type, eventMessage, payload, metaData);
    }
}
