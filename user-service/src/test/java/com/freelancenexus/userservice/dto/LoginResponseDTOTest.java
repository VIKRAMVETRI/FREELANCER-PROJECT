package com.freelancenexus.userservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseDTOTest {

    @Test
    void shouldCreateLoginResponseDTO() {
        UserResponseDTO userDTO = new UserResponseDTO();
        LoginResponseDTO dto = new LoginResponseDTO(
                "access-token",
                "refresh-token",
                3600L,
                "Bearer",
                userDTO
        );

        assertEquals("access-token", dto.getAccessToken());
        assertEquals("refresh-token", dto.getRefreshToken());
        assertEquals(3600L, dto.getExpiresIn());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(userDTO, dto.getUser());
    }

    @Test
    void shouldTestGettersAndSetters() {
        LoginResponseDTO dto = new LoginResponseDTO();
        UserResponseDTO userDTO = new UserResponseDTO();

        dto.setAccessToken("token");
        dto.setRefreshToken("refresh");
        dto.setExpiresIn(7200L);
        dto.setTokenType("Bearer");
        dto.setUser(userDTO);

        assertEquals("token", dto.getAccessToken());
        assertEquals("refresh", dto.getRefreshToken());
        assertEquals(7200L, dto.getExpiresIn());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(userDTO, dto.getUser());
    }

    @Test
    void shouldTestNoArgsConstructor() {
        LoginResponseDTO dto = new LoginResponseDTO();
        assertNotNull(dto);
        assertEquals("Bearer", dto.getTokenType());
    }

    @Test
    void shouldHaveDefaultTokenType() {
        LoginResponseDTO dto = new LoginResponseDTO();
        assertEquals("Bearer", dto.getTokenType());
    }
}