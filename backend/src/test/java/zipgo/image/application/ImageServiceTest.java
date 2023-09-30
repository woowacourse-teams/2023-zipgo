package zipgo.image.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageServiceTest {

    @Mock
    private ImageClient imageClient;

    @InjectMocks
    private ImageService imageService;

    @Test
    void 이미지를_업로드_할_수_있다() {
        // given
        var 사진 = new MockMultipartFile("사진", "사진.png", "image/png", "사진".getBytes());
        when(imageClient.upload(any(), any(), any()))
                .thenReturn("생성된파일이름");

        // when
        String 저장된_사진 = imageService.save(사진, "디렉토리");

        // then
        Assertions.assertThat(저장된_사진).isEqualTo("생성된파일이름");
    }

}
