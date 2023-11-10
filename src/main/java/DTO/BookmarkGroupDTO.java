package DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookmarkGroupDTO {

    private String name;
    private int order;
    private LocalDateTime register_date;
    private LocalDateTime update_date;

}
