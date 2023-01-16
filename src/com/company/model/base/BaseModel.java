package com.company.model.base;

import java.util.UUID;

public abstract class BaseModel {
    protected UUID id;
    protected boolean deleted;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
