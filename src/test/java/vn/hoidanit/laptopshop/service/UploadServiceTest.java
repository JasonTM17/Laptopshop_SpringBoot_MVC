package vn.hoidanit.laptopshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

class UploadServiceTest {

    @TempDir
    Path uploadRoot;

    UploadService uploadService;

    @BeforeEach
    void setUp() {
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRealPath("/resources/images")).thenReturn(uploadRoot.toString());
        uploadService = new UploadService(servletContext);
    }

    @Test
    void savesImageWhenContentTypeExtensionAndSignatureMatch() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cover.png",
                "image/png",
                pngBytes());

        String savedName = uploadService.handleSaveUploadFile(new MultipartFile[]{file}, "product");

        assertThat(savedName).endsWith("-cover.png");
        assertThat(Files.exists(uploadRoot.resolve("product").resolve(savedName))).isTrue();
    }

    @Test
    void rejectsImageUploadWhenContentIsSpoofed() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "avatar.jpg",
                "image/jpeg",
                "<script>alert(1)</script>".getBytes(StandardCharsets.UTF_8));

        assertThatThrownBy(() -> uploadService.handleSaveUploadFile(new MultipartFile[]{file}, "avatar"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Noi dung file anh");
    }

    @Test
    void rejectsImageUploadWhenExtensionIsNotAllowed() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cover.jsp",
                "image/png",
                pngBytes());

        assertThatThrownBy(() -> uploadService.handleSaveUploadFile(new MultipartFile[]{file}, "product"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phan mo rong");
    }

    private byte[] pngBytes() {
        return new byte[]{
                (byte) 0x89, 0x50, 0x4E, 0x47,
                0x0D, 0x0A, 0x1A, 0x0A,
                0x00, 0x00, 0x00, 0x00
        };
    }
}
