package models;

import lombok.Data;

@Data
public class UserResponse {
    private UserData data;
    private Support support;
}

@Data
class Support {
    private String url;
    private String text;
}
