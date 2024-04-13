package co.edu.eam.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Libro {
    private String isbn;
    private String nombre;
    private String genero;
    private int unidades;
    private String fechaPublicacion;
    private List<Autor> autor;
}
