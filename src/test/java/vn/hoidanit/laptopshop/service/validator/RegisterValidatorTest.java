package vn.hoidanit.laptopshop.service.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

class RegisterValidatorTest {

    UserService userService;
    RegisterValidator validator;
    ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        validator = new RegisterValidator(userService);
        context = mockValidationContext();
    }

    @Test
    void validRegistrationPasses() {
        RegisterDTO dto = dto("buyer@example.com", "secret1", "secret1");
        when(userService.checkEmailExist("buyer@example.com")).thenReturn(false);

        assertThat(validator.isValid(dto, context)).isTrue();
    }

    @Test
    void mismatchedPasswordsFailWithoutNullPointer() {
        RegisterDTO dto = dto("buyer@example.com", "secret1", "secret2");

        assertThat(validator.isValid(dto, context)).isFalse();
    }

    @Test
    void duplicateEmailFails() {
        RegisterDTO dto = dto("buyer@example.com", "secret1", "secret1");
        when(userService.checkEmailExist("buyer@example.com")).thenReturn(true);

        assertThat(validator.isValid(dto, context)).isFalse();
    }

    @Test
    void strongPasswordPolicyRejectsWeakPasswords() {
        StrongPasswordValidator passwordValidator = new StrongPasswordValidator();

        assertThat(passwordValidator.isValid("secret1", context)).isFalse();
        assertThat(passwordValidator.isValid("Password123!", context)).isTrue();
    }

    private RegisterDTO dto(String email, String password, String confirmPassword) {
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("Nguyen");
        dto.setLastName("Son");
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);
        return dto;
    }

    private ConstraintValidatorContext mockValidationContext() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
        NodeBuilderCustomizableContext node = mock(NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(node);
        when(node.addConstraintViolation()).thenReturn(context);
        return context;
    }
}
