package heitor.projetofinal.meli.infra.excepetion;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrors {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityHandler404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationHandler400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(HandlerValidation:: new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationHandlerBsinessRuller(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    private  record HandlerValidation(String campo, String msg){
        public HandlerValidation(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
