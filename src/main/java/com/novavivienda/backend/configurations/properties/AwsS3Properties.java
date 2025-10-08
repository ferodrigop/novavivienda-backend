package com.novavivienda.backend.configurations.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.aws.s3")
public class AwsS3Properties {
    @NotNull(message = "AWS S3 bucket name must not be null")
    private String bucketName;

    @NotNull(message = "AWS S3 region must not be null")
    private String region;

    @NotNull(message = "AWS S3 access key ID must not be null")
    private String accessKeyId;

    @NotNull(message = "AWS S3 secret access key must not be null")
    private String secretAccessKey;
}
