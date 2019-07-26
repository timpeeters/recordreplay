package be.xplore.recordreplay;

import javax.jws.WebService;

import java.util.List;

@WebService(
        endpointInterface = "be.xplore.recordreplay.UserService",
        portName = "UserPort",
        serviceName = "UserService",
        targetNamespace = "http://user.xplore.be")
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findUsers() {
        return List.of(
                User.builder().id(1L).firstName("Jeroen").lastName("Timmers").role("Administrator").build(),
                User.builder().id(2L).firstName("Stijn").lastName("Schack").build());
    }
}
