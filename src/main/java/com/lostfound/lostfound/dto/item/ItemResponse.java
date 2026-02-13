package com.lostfound.lostfound.dto.item;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    
    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private boolean status;

    private Long userId;
    private String userName;

    private Long categoryId;
    private String categoryName;

    private Long locationId;
    private String locationName;

    private Long reportId;
    private String reportedBy;
}


// Exercise 1 answer :- UserResponseDTO -> i 
// can use all User entities fields cause it's the 
//  the response when i want to get (for get http method )
//   so it's fine , but for UserResponseDTO -> cause it's need to not expose 
//   sensetive fields like user can't set isAdmin  true to assign them self
//    as admin the system will desice if they are admin or not and also the createdat 
//    filed so we protect sensetive that user must can't modify them so for this we  expose only
//  important filed should given by user in thie sensrio email,password,
