package co.edu.eam.biblioteca.feignclients;


import co.edu.eam.biblioteca.dto.TokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "TokenKeycloak", url = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
public interface TokenFeignClient {


    @PostMapping(
            path = "/protocol/openid-connect/token",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    ResponseEntity<TokenResponseDTO> sendRequest(String ruta);


}

