package com.raserei.popugjira.tracker.event;

public record TaskClosedEvent(String publicId, String closedByPublicId) {
}
