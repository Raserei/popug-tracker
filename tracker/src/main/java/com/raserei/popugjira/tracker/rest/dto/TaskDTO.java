package com.raserei.popugjira.tracker.rest.dto;

import com.raserei.popugjira.tracker.domain.TaskStatus;

public record TaskDTO(String shortDescription, String longDescription, String assigneePublicId, TaskStatus status) {}
