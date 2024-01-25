package com.hkit.lifetime.teacher;

import java.io.Serializable;

/**
 * DTO for {@link Teacher}
 */
public record TeacherDto(String uuid, String name, String tel) implements Serializable {
}