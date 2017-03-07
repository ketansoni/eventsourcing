package com.ketan.ecom.axon.jpa;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.serializer.SerializedObject;
import org.joda.time.DateTime;
import org.axonframework.eventstore.jpa.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class DefaultEventEntryFactory implements EventEntryFactory<String> {
    @Override
    public Class<String> getDataType() {
        return String.class;
    }

    @Override
    public Object createDomainEventEntry(String aggregateType, DomainEventMessage event, SerializedObject<String> serializedPayload, SerializedObject<String> serializedMetaData) {
        DateTime timestamp = event.getTimestamp();
        return new DomainEventEntry(aggregateType, event, timestamp, serializedPayload, serializedMetaData);
    }

    @Override
    public Object createSnapshotEventEntry(String aggregateType, DomainEventMessage snapshotEvent, SerializedObject<String> serializedPayload, SerializedObject<String> serializedMetaData) {
        return new SnapshotEventEntry(aggregateType, snapshotEvent, serializedPayload, serializedMetaData);
    }

    @Override
    public String getDomainEventEntryEntityName() {
        return "DomainEventEntry";
    }

    @Override
    public String getSnapshotEventEntryEntityName() {
        return "SnapshotEventEntry";
    }

    @Override
    public Object resolveDateTimeValue(DateTime dateTime) {
        return dateTime.toString();
    }
}


