package co.edu.eam.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String access_token;

    private String refresh_token;
}
