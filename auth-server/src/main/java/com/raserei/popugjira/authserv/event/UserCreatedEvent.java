package com.raserei.popugjira.authserv.event;

import java.util.List;

public record UserCreatedEvent(String publicId, List<String> roles) {
}