package co.edu.eam.biblioteca.services;


import co.edu.eam.biblioteca.dto.LoginDTO;
import co.edu.eam.biblioteca.dto.TokenDTO;
import co.edu.eam.biblioteca.dto.TokenResponseDTO;
import co.edu.eam.biblioteca.feignclients.TokenFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServicio {
    private final TokenFeignClient tokenFeignClient;


    public TokenDTO login(LoginDTO loginDTO){


        String clientID = "springboot-keycloak-client";
        String usernameAdmin = loginDTO.getUsername();
        String passwordAdmin  = loginDTO.getPassword();
        String grantType = "password";


        String requestBody = String.format("client_id=%s&username=%s&password=%s&grant_type=%s", clientID, usernameAdmin, passwordAdmin, grantType);


        ResponseEntity<TokenResponseDTO> responseEntity = tokenFeignClient.sendRequest(requestBody);


        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            TokenResponseDTO response = responseEntity.getBody();
            return new TokenDTO( response.access_token(), response.refresh_token() );
        }


        throw new RuntimeException("Error en la petición "+responseEntity.getStatusCode());


    }

}
