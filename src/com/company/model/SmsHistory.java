package com.company.model;

import com.company.enums.SmsStatus;
import com.company.model.base.BaseModel;

import java.time.LocalDateTime;

public class SmsHistory extends BaseModel {
    private SimCard from;
    private SimCard to;
    private LocalDateTime timestamp;
    private String message;
    private SmsStatus status;

    public SmsHistory(SimCard from, SimCard to, LocalDateTime timestamp, String message, SmsStatus status) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
    }

    public SimCard getFrom() {
        return from;
    }

    public void setFrom(SimCard from) {
        this.from = from;
    }

    public SimCard getTo() {
        return to;
    }

    public void setTo(SimCard to) {
        this.to = to;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SmsStatus getStatus() {
        return status;
    }

    public void setStatus(SmsStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SmsHistory{" +
                "id=" + id +
                ", from=" + from.getNumber() +
                ", to=" + to.getNumber() +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
