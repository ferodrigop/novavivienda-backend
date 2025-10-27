CREATE TABLE app_user
(
    id                UUID         NOT NULL,
    first_name        VARCHAR(50)  NOT NULL,
    last_name         VARCHAR(50)  NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(120) NOT NULL,
    role_id           UUID         NOT NULL,
    is_email_verified BOOLEAN      NOT NULL,
    is_deleted        BOOLEAN      NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE password_reset
(
    id         UUID NOT NULL,
    user_id    UUID NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_password_reset PRIMARY KEY (id)
);

CREATE TABLE refresh_token
(
    id         UUID NOT NULL,
    user_id    UUID NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE TABLE role
(
    id        UUID         NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    id         UUID        NOT NULL,
    permission VARCHAR(50) NOT NULL,
    role_id    UUID        NOT NULL,
    CONSTRAINT pk_role_permission PRIMARY KEY (id)
);

CREATE TABLE verification_code
(
    id          UUID       NOT NULL,
    code        VARCHAR(6) NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id     UUID       NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_verification_code PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE role_permission
    ADD CONSTRAINT uc_role_permission_permission UNIQUE (permission);

ALTER TABLE role
    ADD CONSTRAINT uc_role_rolename UNIQUE (role_name);

ALTER TABLE verification_code
    ADD CONSTRAINT uc_verification_code_code UNIQUE (code);

ALTER TABLE verification_code
    ADD CONSTRAINT uc_verification_code_user UNIQUE (user_id);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE password_reset
    ADD CONSTRAINT FK_PASSWORD_RESET_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE refresh_token
    ADD CONSTRAINT FK_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE role_permission
    ADD CONSTRAINT FK_ROLE_PERMISSION_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE verification_code
    ADD CONSTRAINT FK_VERIFICATION_CODE_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);