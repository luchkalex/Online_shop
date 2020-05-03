package ua.electro.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 * Class helper for Controllers
 */
public class ControllerUtil {

    /**
     * Method represent
     *
     * @param bindingResult as a
     * @return Map to add to Model
     */
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> errorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors().stream().collect(errorMapCollector);
    }
}
