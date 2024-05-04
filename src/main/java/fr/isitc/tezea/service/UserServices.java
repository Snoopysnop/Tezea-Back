package fr.isitc.tezea.service;

import jakarta.ws.rs.BadRequestException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.config.Credentials;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.service.data.UserData;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service

public class UserServices {

    @Autowired
    private UserDAO userDao;
    private Keycloak keycloak = null;
    @Value("${keycloak.url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Value("${keycloak.username}")
    private String userName;
    @Value("${keycloak.password}")
    private String password;

    public Keycloak getKeycloakInstance() {
        System.out.println(password);
        System.out.println(userName);

        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }
        return keycloak;
    }

    public UserData register(UserDTO userDTO, String password, MultipartFile file) throws BadRequestException {
        try {
            String photoId = "";
            List<User> verificationList = userDao.findAll();
            verificationList.forEach((e) -> {

                if (e.getEmail().equals(userDTO.getEmail())) {
                    throw new BadRequestException("Un utilisateur avec la même adresse e-mail existe déjà.");
                }

            });
            // if (file != null) {
            //     photoId = firebaseServices.uploadFile(file);
            // }
            RealmResource realmResource = getKeycloakInstance().realm(realm);
            UsersResource usersResource = realmResource.users();
            CredentialRepresentation credential = Credentials.createPasswordCredentials(password);
            UserRepresentation userRepresentation = new UserRepresentation();

            List<String> roles = new ArrayList<>();
            roles.add(userDTO.getRole().toString());

            userRepresentation.setFirstName(userDTO.getFirstName());
            userRepresentation.setLastName(userDTO.getLastName());
            userRepresentation.setEmail(userDTO.getEmail());
            userRepresentation.setRealmRoles(roles);
            userRepresentation.setCredentials(Collections.singletonList(credential));
            userRepresentation.setEnabled(true);
            usersResource.create(userRepresentation);

            User user = new User(userDTO);
            userDao.save(user);
            return new UserData(user);
        } catch (Exception e) {
            throw new BadRequestException("Create User Error : " + e.getMessage());
        }
    }
}