package heitor.projetofinal.meli.infra.excepetion;

public class ValidationExcepetion  extends RuntimeException {
    public ValidationExcepetion(String msg) {
        super(msg);
    }
}
