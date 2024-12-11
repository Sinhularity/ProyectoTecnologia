package uv.tcs.dognload.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResouceNotFoundExcepcion extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResouceNotFoundExcepcion(String mensaje) {
        super(mensaje);
    }
}
