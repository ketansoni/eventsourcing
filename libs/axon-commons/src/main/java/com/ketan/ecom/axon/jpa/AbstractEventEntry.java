package com.ketan.ecom.axon.jpa;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventstore.jpa.AbstractEventEntryData;
import org.axonframework.serializer.SerializedMetaData;
import org.axonframework.serializer.SerializedObject;
import org.axonframework.serializer.SimpleSerializedObject;
import org.joda.time.DateTime;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class AbstractEventEntry extends AbstractEventEntryData<String> {

    private String payload;

    @Basic(optional = false)
    private String metaData;

    protected AbstractEventEntry() {
    }

    /**
     * Initialize an Event entry for the given <code>event</code>.
     *
     * @param type     The type identifier of the aggregate root the event belongs to
     * @param event    The event to store in the EventStore
     * @param payload  The serialized payload of the Event
     * @param metaData The serialized metaData of the Event
     */
    protected AbstractEventEntry(String type, DomainEventMessage event,
                                 SerializedObject<String> payload, SerializedObject<String> metaData) {
        this(type, event, event.getTimestamp(), payload, metaData);
    }

    /**
     * Initialize an Event entry for the given <code>event</code>.
     *
     * @param type      The type identifier of the aggregate root the event belongs to
     * @param event     The event to store in the EventStore
     * @param timestamp The timestamp to store
     * @param payload   The serialized payload of the Event
     * @param metaData  The serialized metaData of the Event
     */
    protected AbstractEventEntry(String type, DomainEventMessage event, DateTime timestamp,
                                 SerializedObject<String> payload, SerializedObject<String> metaData) {
        super(event.getIdentifier(),
                type,
                event.getAggregateIdentifier().toString(),
                event.getSequenceNumber(),
                timestamp, payload.getType()
        );
        this.metaData = metaData.getData();
        this.payload = payload.getData();
    }

    @Override
    public SerializedObject<String> getPayload() {
        return new SimpleSerializedObject<>(payload, String.class, getPayloadType());
    }

    @Override
    public SerializedObject<String> getMetaData() {
        return new SerializedMetaData<>(metaData, String.class);
    }
}
