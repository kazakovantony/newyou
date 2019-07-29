package com.kazakov.newyou.app.model;

public class LogMessage {
    private final String id;
    private final String groupId;
    private final String name;
    private final String body;

    public LogMessage(String id, String groupId, String name, String body) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.body = body;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }
}
