package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemberDto {

    private long id;
    private String username;
    private String teamName;
}
