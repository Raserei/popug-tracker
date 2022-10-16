package com.raserei.popugjira.authserv.event;

import java.util.List;

public record UserCreatedEvent(String publicId, String name, String surname, List<String> roles) {
}