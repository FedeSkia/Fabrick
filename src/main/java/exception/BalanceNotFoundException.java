package exception;

public class BalanceNotFoundException extends RuntimeException {

    public BalanceNotFoundException(String message){
        super(message);
    }

    public BalanceNotFoundException(){
        super();
    }

}
