package co.edu.eam.biblioteca.service.excepciones;

public class ClienteNoEncontradoException extends RuntimeException{

    public ClienteNoEncontradoException(String mensaje){
        super(mensaje);
    }

}
