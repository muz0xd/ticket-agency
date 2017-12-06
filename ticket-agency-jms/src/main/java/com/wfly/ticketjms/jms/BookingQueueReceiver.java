package com.wfly.ticketjms.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;

@MessageDriven(name = "BookingQueueReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = BookingQueueDefinition.BOOKING_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "messageSelector",
                propertyValue = "priority = 'HIGH'"),}
)
public class BookingQueueReceiver implements MessageListener {

    @Inject
    private Logger logger;

    @Override
    public void onMessage(Message message) {
        try {
            final String text = message.getBody(String.class);
            logger.info("Received message with HIGH priority: " + text);
        } catch (JMSException ex) {
            logger.severe(ex.toString());
        }
    }
}