package com.novavivienda.backend.services.authentication;

import com.novavivienda.backend.dtos.auth.ChangePasswordDto;
import com.novavivienda.backend.dtos.auth.ForgotPasswordDto;
import com.novavivienda.backend.dtos.auth.ResetPasswordDto;
import com.novavivienda.backend.entities.password_reset.PasswordReset;
import com.novavivienda.backend.entities.user.User;
import com.novavivienda.backend.exceptions.CurrentPasswordException;
import com.novavivienda.backend.exceptions.NewPasswordMismatchException;
import com.novavivienda.backend.repositories.user.UserRepository;
import com.novavivienda.backend.services.email.EmailDispatcher;
import com.novavivienda.backend.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PasswordService {
    private final PasswordEncoder passwordEncoder;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;
    private final EmailDispatcher emailDispatcher;

    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.newPassword().equals(changePasswordDto.newPasswordConfirmation())) {
            throw new NewPasswordMismatchException("The new password and its confirmation do not match");
        }
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(changePasswordDto.currentPassword(), user.getPassword())) {
            throw new CurrentPasswordException("Wrong password provided");
        }
        if (!passwordEncoder.matches(changePasswordDto.newPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            userRepository.save(user);
        }
    }

    @Transactional
    public void initiatePasswordReset(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByEmail(forgotPasswordDto.email())
                .orElse(null);
        if (Objects.nonNull(user)) {
            try {
                PasswordReset passwordReset = passwordResetService.createPasswordReset(user);
                emailDispatcher.dispatchResetPasswordEmail(user.getEmail(), passwordReset.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!resetPasswordDto.newPassword().equals(resetPasswordDto.newPasswordConfirmation())) {
            throw new NewPasswordMismatchException("The new password and its confirmation do not match");
        }
        PasswordReset passwordReset = passwordResetService.findPasswordResetByIdAndExpiresAtAfter(
                resetPasswordDto.token(),
                Instant.now()
        );
        User user = passwordReset.getUser();
        if (!passwordEncoder.matches(resetPasswordDto.newPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.newPassword()));
            userRepository.save(user);
        }
    }
}
