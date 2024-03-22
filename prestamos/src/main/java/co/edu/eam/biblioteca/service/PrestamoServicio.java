package co.edu.eam.biblioteca.service;

import co.edu.eam.biblioteca.dto.ClienteGetDTO;
import co.edu.eam.biblioteca.dto.PrestamoPostDTO;
import co.edu.eam.biblioteca.dto.PrestamoQueryDTO;
import co.edu.eam.biblioteca.dto.Respuesta;
import co.edu.eam.biblioteca.model.Prestamo;
import co.edu.eam.biblioteca.repo.PrestamoRepo;
import co.edu.eam.biblioteca.service.excepciones.PrestamoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PrestamoServicio {

    private final RestTemplate restTemplate;

    private final PrestamoRepo prestamoRepo;
    public long save(PrestamoPostDTO prestamoDTO){

        validarCodigoCliente(prestamoDTO.clienteID());
        validarIsbnLibros(prestamoDTO.isbnLibros());

        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoCliente(prestamoDTO.clienteID());
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setIsbnLibros(prestamoDTO.isbnLibros());
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());

        return prestamoRepo.save(prestamo).getCodigo();
    }

    public List<Prestamo> findByCodigoCliente(String codigoCliente){

        validarCodigoCliente(codigoCliente);

        List<PrestamoQueryDTO> lista = prestamoRepo.findByCodigoCliente(codigoCliente);
        List<Prestamo> respuesta = new ArrayList<>();

        for(PrestamoQueryDTO q : lista){
            if(respuesta.stream().noneMatch(r -> r.getCodigo() == q.getPrestamoID())){
                ArrayList<String> libros = new ArrayList<>();
                libros.add(q.getIsbnLibro());
                respuesta.add( new Prestamo(q.getPrestamoID(), q.getClienteID(), q.getFechaCreacion(), q.getFechaDevolucion(), libros ) );
            }else{
                respuesta.stream().findAny().get().getIsbnLibros().add( q.getIsbnLibro() );
            }
        }

        return new ArrayList<>();

    }

    public List<Prestamo> findAll(){
        return prestamoRepo.findAll();
    }

    public Prestamo findById(long codigoPrestamo){
        return prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new PrestamoNoEncontradoException("No existe un préstamo con el código: "+codigoPrestamo));
    }

    public List<Prestamo> findByDate(LocalDate codigoPrestamo){
        return prestamoRepo.findByDate(codigoPrestamo);
    }

    public long lendingCount(String isbn){
        return prestamoRepo.lendingCount(isbn);
    }

    public long update(long codigoPrestamo, PrestamoPostDTO prestamoPostDTO){
        Prestamo prestamo = prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new PrestamoNoEncontradoException("No existe un préstamo con el código: "+codigoPrestamo));

        validarIsbnLibros(prestamoPostDTO.isbnLibros());

        prestamo.setIsbnLibros(prestamoPostDTO.isbnLibros());
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion(prestamoPostDTO.fechaDevolucion());

        return prestamo.getCodigo();
    }

    private void validarIsbnLibros(List<String> isbns){

        StringBuilder noEncontrados = new StringBuilder();

        try{

            for(String isbn: isbns) {

                Respuesta<Boolean> respuesta = restTemplate.exchange(
                        "http://localhost:9092/api/libro/" + isbn,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Respuesta<Boolean>>() {} ).getBody();

                if(!respuesta.getDato()){
                    noEncontrados.append(isbn).append(" ");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Hubo un error recuperando la información del libro");
        }


        if(noEncontrados.length() > 0){
            throw new RuntimeException("Estos isbn no existen: "+noEncontrados);
        }

    }

    private void validarCodigoCliente(String codigoCliente){

        boolean encontrado;

        try {

            Respuesta<Boolean> respuesta = restTemplate.exchange(
                    "http://localhost:9094/api/cliente/" + codigoCliente,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Respuesta<Boolean>>() {}).getBody();

            encontrado = respuesta.getDato();

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Hubo un error recuperando la información del cliente");
        }

        if(!encontrado){
            throw new RuntimeException("El código "+codigoCliente+" no pertenece a ningún usuario");
        }

    }

}
