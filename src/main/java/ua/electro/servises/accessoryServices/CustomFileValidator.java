package ua.electro.servises.accessoryServices;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CustomFileValidator {

    public static final String PNG_MIME_TYPE = "image/png";
    public static final String JPG_MIME_TYPE = "image/jpeg";
    public static final long TEN_MB_IN_BYTES = 10485760;

    public void validate(MultipartFile file, BindingResult errors) {

        if (!(PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType()))) {
            errors.addError(new FieldError("product", "photo", "Photo have to be .png or .jpg"));
        } else if (file.getSize() > TEN_MB_IN_BYTES) {
            errors.addError(new FieldError("product", "photo", "Size is too large (< 10 MB)"));
        }

    }

}