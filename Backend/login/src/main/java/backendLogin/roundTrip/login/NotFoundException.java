package backendLogin.roundTrip.login;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
