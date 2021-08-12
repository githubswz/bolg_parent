package com.swz.blog.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Archives {

    private Integer year;

    private Integer month;

    private Integer count;
}