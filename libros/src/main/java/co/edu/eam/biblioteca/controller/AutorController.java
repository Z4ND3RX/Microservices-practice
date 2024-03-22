package co.edu.eam.biblioteca.controller;

import co.edu.eam.biblioteca.dto.AutorDTO;
import co.edu.eam.biblioteca.dto.Respuesta;
import co.edu.eam.biblioteca.model.Autor;
import co.edu.eam.biblioteca.servicio.AutorServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autor")
@AllArgsConstructor
public class AutorController {
    private final AutorServicio autorServicio;

    @PostMapping
    public ResponseEntity<Respuesta<Autor>> save(@RequestBody AutorDTO AutorDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body( new Respuesta<>("Autor creado correctamente", autorServicio.save(AutorDTO)) );
    }

    @GetMapping
    public ResponseEntity<Respuesta<List<Autor>>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findAll()) );
    }

    @GetMapping("/{isbnAutor}")
    public ResponseEntity<Respuesta<Autor>> findAll(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", autorServicio.findById(id)) );
    }
}
