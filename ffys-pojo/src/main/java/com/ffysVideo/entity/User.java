package com.ffysVideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private Integer status;
    private String img;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
