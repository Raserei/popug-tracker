package com.raserei.popugjira.tracker.event.user;


import java.util.List;
import java.util.UUID;

public record UserCreatedEvent(String publicId, List<String> roles) {
}
