package com.raserei.popugjira.tracker.event;

public record TaskCreatedEvent(String publicId, String shortDescription, String assigneePublicId) {
}
