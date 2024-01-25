package com.hkit.lifetime.teacher;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Teacher}
 */
public record TeacherDto(String uuid, String name, String tel) implements Serializable {
}