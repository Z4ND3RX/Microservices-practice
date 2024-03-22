package co.edu.eam.biblioteca.servicio;

import co.edu.eam.biblioteca.dto.AutorDTO;
import co.edu.eam.biblioteca.model.Autor;
import co.edu.eam.biblioteca.repo.AutorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AutorServicio {
    private final AutorRepo autorRepo;

    public Autor save(AutorDTO autor){

        Optional<Autor> guardado = autorRepo.findById(autor.id());

        if(guardado.isPresent()){
            throw new RuntimeException("El autor con el isbn "+autor.id()+" ya existe");
        }

        return autorRepo.save( convertir(autor) );
    }

    public Autor findById(Long id){
        return autorRepo.findById(id).orElse(null);
    }

    public List<Autor> findAll(){
        return autorRepo.findAll();
    }

    public Autor update(AutorDTO autor){
        return autorRepo.save( convertir(autor) );
    }

    private Autor convertir(AutorDTO autor){

        Autor nuevo = Autor.builder()
                .id(autor.id())
                .nombre(autor.nombre())
                .build();

        return nuevo;
    }
}
