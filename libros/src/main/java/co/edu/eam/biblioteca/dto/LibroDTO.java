package co.edu.eam.biblioteca.dto;

import co.edu.eam.biblioteca.model.Genero;
import java.time.LocalDate;
import java.util.List;

public record LibroDTO(String isbn, String nombre, Genero genero, int unidades, LocalDate fechaPublicacion, List<Long> idAutores){

}
