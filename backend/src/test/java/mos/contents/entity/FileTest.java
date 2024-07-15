package mos.contents.entity;

import mos.member.entity.Member;
import mos.mogako.entity.Mogako;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class FileTest {
    Mogako mogako;

    @BeforeEach
    void setUp(){
        mogako = mock(Mogako.class);
    }

    @Test
    void 파일_생성_테스트(){assertDoesNotThrow(() -> SavedFile.createNewFile(mogako,"파일이름","downloadurl"));}


}
