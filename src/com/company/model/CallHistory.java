package com.company.model;

import com.company.enums.CallStatus;
import com.company.model.base.BaseModel;

import java.time.LocalDateTime;

public class CallHistory extends BaseModel {
    private SimCard from;
    private SimCard to;
    private LocalDateTime timestamp;
    private CallStatus callStatus;
    private int callDuration; // by minute

    public CallHistory(SimCard from, SimCard to, LocalDateTime timestamp, CallStatus callStatus, int callDuration) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
        this.callStatus = callStatus;
        this.callDuration = callDuration;
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

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    @Override
    public String toString() {
        return "CallHistory{" +
                "id=" + id +
                ", from=" + from.getNumber() +
                ", to=" + to.getNumber() +
                ", timestamp=" + timestamp +
                ", callStatus=" + callStatus +
                ", callDuration=" + callDuration +
                ", deleted=" + deleted +
                '}';
    }
}
