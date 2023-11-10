package DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookmarkDTO {

    private int bookmarkGroupId;
    private String wifiId;
    private LocalDateTime registerDate;

}
