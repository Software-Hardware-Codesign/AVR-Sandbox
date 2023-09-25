#ifndef LOGGER

#define LOGGER

#include<stdio.h>
#include<stdint.h>
#include<stdlib.h>
#include<ErrnoUtils.h>
#include<BufferUtils.h>

typedef const char* AlertType;

#define INFO ((AlertType) "Info")
#define WARNING ((AlertType) "Warning")
#define OK ((AlertType) "Status Ok")
#define FAILURE ((AlertType) "Failed")
#define SUCCESS ((AlertType) "Succeeded")

#define LOGGER_FORMAT ((const char*) "%s::%s: %s \n\r")

struct Logger {

    int* loggingEnabled;

    void init() {
        loggingEnabled = (int*) calloc(1, sizeof(int*));
    }

    void destroy() {
        BufferUtils::deleteBuffer(loggingEnabled);
    }

    void setLoggingEnabled() {
        *(this->loggingEnabled) = 1;
    }

    void setLoggingDisabled() {
        *(this->loggingEnabled) = 0;
    }

    int* isLoggingEnabled() {
        return this->loggingEnabled;
    }

    int LOG(const char* API_LABEL, AlertType ALERT_TYPE, const char* MSG) {
        if (!*(this->isLoggingEnabled())) {
            return LOGGER_DISABLED;
        }
        return printf(LOGGER_FORMAT, API_LABEL, ALERT_TYPE, MSG);
    }

    int LOGI(const char* API_LABEL, const char* MSG) {
        return LOG(API_LABEL, INFO, MSG);
    }

    int LOGW(const char* API_LABEL, const char* MSG) {
        return LOG(API_LABEL, WARNING, MSG);
    }

    int LOGF(const char* API_LABEL, const char* MSG) {
        return LOG(API_LABEL, FAILURE, MSG);
    }  

    int LOGS(const char* API_LABEL, const char* MSG) {
        return LOG(API_LABEL, SUCCESS, MSG);
    }   
 
    int LOG_OK(const char* API_LABEL, const char* MSG) {
        return LOG(API_LABEL, OK, MSG);
    }
};

#endif