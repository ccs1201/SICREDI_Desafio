package br.com.ccs.sicredi.core.exception;

/**
 *<p><b> Classe responsável para pela representação
 * de erros de BeansValidation</b></p>
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 20/08/2022
 */
public class FieldValidationException extends RuntimeException {
    public FieldValidationException(String message) {
        super(message);
    }
}
