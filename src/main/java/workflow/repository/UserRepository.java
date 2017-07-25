package workflow.repository;

import org.springframework.data.repository.CrudRepository;
import workflow.domain.Authority;
import workflow.domain.User;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by sramalin on 30/05/17.
 */


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface UserRepository extends CrudRepository<User, String> {


    List<User> findByfirstName(String firstName);
    List<User> findBylastName(String lastName);
    List<User> findBydob(Date dob);
    List<User> findByactivationStatus(Boolean activationStatus);



    List<User> findByusername(String lowercaseLogin);




}

