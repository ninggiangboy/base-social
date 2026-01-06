package dev.ngb.system_admin.user.application.command.system_admin_auth.handler;

import dev.ngb.application.annonation.TransactionalScope;
import dev.ngb.application.command.CommandHandler;
import dev.ngb.application.dto.AccessToken;
import dev.ngb.application.port.AccessTokenGenerator;
import dev.ngb.application.port.PasswordEncoder;
import dev.ngb.domain.user.error.UserError;
import dev.ngb.domain.user.model.SystemAdmin;
import dev.ngb.domain.user.repository.SystemAdminRepository;
import dev.ngb.system_admin.user.application.command.system_admin_auth.CreateAuthSessionCommand;
import dev.ngb.system_admin.user.application.dto.AuthSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@TransactionalScope
public class CreateAuthSessionHandler implements CommandHandler<CreateAuthSessionCommand, AuthSession> {

    private final SystemAdminRepository systemAdminRepository;
    private final AccessTokenGenerator accessTokenGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthSession handle(CreateAuthSessionCommand command) {
        SystemAdmin user = systemAdminRepository.findByLoginId(command.loginId().toUpperCase())
                .orElseThrow(UserError.BAD_CREDENTIALS::exception);
        if (!passwordEncoder.matches(command.password(), user.getHashedPassword())) {
            throw UserError.BAD_CREDENTIALS.exception();
        }
        user.tryLogin();
        systemAdminRepository.save(user);
        AccessToken accessToken = accessTokenGenerator.generateToken(user.getLoginId(), user.getAuthClaims());
        return AuthSession.builder()
                .accessToken(accessToken.token())
                .duration(accessToken.duration())
                .build();
    }
}
