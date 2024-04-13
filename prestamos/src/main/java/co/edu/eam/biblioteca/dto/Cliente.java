package co.edu.eam.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Cliente {
    private String codigo;
    private String nombre;
    private String email;
    private String telefono;
}
