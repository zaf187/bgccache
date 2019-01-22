package com.cache.bgc.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public class ObjectEntry {
    private String objectKey;
    private Object objectValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastFetchedAt;
    private long versionNumber;

    public ObjectEntry(String objectKey, Object objectValue) {
        this.objectKey = objectKey;
        this.objectValue = objectValue;
        this.createdAt = LocalDateTime.now(Clock.systemUTC());
        this.updatedAt = createdAt;
        this.lastFetchedAt = createdAt;
        this.versionNumber = 1;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDateTime getLastFetchedAt() {
        return lastFetchedAt;
    }

    public void setLastFetchedAt(LocalDateTime lastFetchedAt) {
        this.lastFetchedAt = lastFetchedAt;
    }

    public void incrementVersion() {
        this.versionNumber++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectEntry that = (ObjectEntry) o;
        return versionNumber == that.versionNumber &&
                Objects.equals(objectKey, that.objectKey) &&
                Objects.equals(objectValue, that.objectValue) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(lastFetchedAt, that.lastFetchedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(objectKey, objectValue, createdAt, updatedAt, lastFetchedAt, versionNumber);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ObjectEntry{");
        sb.append("objectKey='").append(objectKey).append('\'');
        sb.append(", objectValue=").append(objectValue);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", lastFetchedAt=").append(lastFetchedAt);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append('}');
        return sb.toString();
    }
}
