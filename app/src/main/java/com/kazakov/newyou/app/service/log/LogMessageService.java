package com.kazakov.newyou.app.service.log;

import com.kazakov.newyou.app.model.LogMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class LogMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageService.class);
    private static final String TIMED_START_MESSAGE = "TIMED START %s";
    private static final String TIMED_STOP_MESSAGE = "TIMED STOP %s";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    public String logTimedStart(String name) {
        String messageId = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        LOGGER.debug(new LogMessage(messageId, groupId, name, String.format(
                TIMED_START_MESSAGE, currentDateStr())).toString());
        return groupId;
    }

    public String logTimedStop(String groupId, String name) {
        String messageId = UUID.randomUUID().toString();
        LOGGER.debug(new LogMessage(messageId, groupId, name, String.format(
                TIMED_STOP_MESSAGE, currentDateStr())).toString());
        return groupId;
    }

    private static String currentDateStr() {
        return FORMATTER.format(LocalDate.now());
    }
}
