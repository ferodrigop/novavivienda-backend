package com.novavivienda.backend.configurations.custom_annotations;

import jakarta.validation.Constraint;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotNull
@NotBlank
public @interface NotNullOrBlank {
    String message() default "{Value required}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
