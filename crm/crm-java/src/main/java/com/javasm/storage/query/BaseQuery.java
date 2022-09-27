package com.javasm.storage.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
public class BaseQuery {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy:MM:dd HH:mm:ss",timezone = "UTC")
    private LocalDateTime starTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy:MM:dd HH:mm:ss",timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private Integer currentPage;

    private Integer pageSize;

}
