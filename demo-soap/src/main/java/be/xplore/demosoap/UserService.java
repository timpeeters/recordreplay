package be.xplore.demosoap;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.util.List;

@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@WebService(name = "Hello", targetNamespace = "http://user.xplore.be")
public interface UserService {
    @WebResult(name = "users")
    @WebMethod(action = "urn:findUsers")
    List<User> findUsers();
}
