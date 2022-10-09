package com.raserei.popugjira.tracker.event;

public record TaskReassignedEvent(String publicId, String shortDescription, String assigneePublicId) {
}
