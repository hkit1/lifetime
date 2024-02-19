package com.hkit.lifetime.lecture;

import java.io.Serializable;



public record LectureOutputDto(Integer id,
                              String name,
                              String description,
                              String created_at,
                              String closed_at,
                              String teacher_name,
                              String sub_category,
                              String company_name) implements Serializable {

}


