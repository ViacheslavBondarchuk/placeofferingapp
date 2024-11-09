package io.homeproject.placeofferingapp.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import static io.homeproject.placeofferingapp.constants.LoggerName.UPSTREAM_LOGGER_NAME;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:46 PM
 **/

public final class LoggingService {
    public static final Logger upstreamLogger = LoggerFactory.getLogger(UPSTREAM_LOGGER_NAME);

    public static final String NULL_MESSAGE = null;

    private LoggingService() {}

    public static void info(Logger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void info(Logger logger, Marker marker, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(marker, message);
        }
    }

    public static void info(Logger logger, Marker marker) {
        LoggingService.info(logger, marker, NULL_MESSAGE);
    }

    public static void debug(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public static void debug(Logger logger, Marker marker, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(marker, message);
        }
    }

    public static void debug(Logger logger, Marker marker) {
        LoggingService.debug(logger, marker, NULL_MESSAGE);
    }


    public static void warn(Logger logger, String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public static void warn(Logger logger, Marker marker, String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(marker, message);
        }
    }

    public static void warn(Logger logger, Marker marker) {
        LoggingService.warn(logger, marker, NULL_MESSAGE);
    }

    public static void error(Logger logger, String message, Throwable ex) {
        if (logger.isErrorEnabled()) {
            logger.error(message, ex);
        }
    }

    public static void error(Logger logger, Marker marker, Throwable ex, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(marker, message, ex);
        }
    }

    public static void error(Logger logger, Marker marker, Throwable ex) {
        LoggingService.error(logger, marker, ex, NULL_MESSAGE);
    }

}
