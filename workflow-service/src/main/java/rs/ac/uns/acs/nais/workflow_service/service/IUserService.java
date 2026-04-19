package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.UserDTO;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import java.util.Map;
import java.util.List;

public interface IUserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(UserDTO userDTO);

    User updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);


}