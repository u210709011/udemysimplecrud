package com.etiya.udemy.business.dtos.responses.user;

import com.etiya.udemy.entities.enums.ProfileName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserListItemResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private ProfileName profileName;
}
