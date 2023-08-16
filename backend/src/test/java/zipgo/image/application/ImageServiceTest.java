package zipgo.image.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import zipgo.pet.application.ImageClient;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageServiceTest {

    @MockBean
    private ImageClient imageClient;

    @Autowired
    private ImageService imageService;

    @Test
    void 이미지를_업로드_할_수_있다() {
        // given
        var 사진 = new MockMultipartFile("사진", "사진.png", "image/png", "사진".getBytes());
        when(imageClient.upload(any(), any()))
                .thenReturn("생성된파일이름");

        // when
        String 저장된_사진 = imageService.save(사진);

        // then
        Assertions.assertThat(저장된_사진).isEqualTo("생성된파일이름");
    }

}
