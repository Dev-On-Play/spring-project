package mos.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mos.auth.exception.*;
import mos.category.exception.CategoryNotFoundException;
import mos.mogako.exception.MogakoNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMapper {

    private static final Map<Class<? extends MosException>, ExceptionSituation> mapper = new HashMap<>();

    static {
        // initialize Exceptions
        setUpAuthExceptions();
        setUpCategoryExceptions();
        setUpMogakoExceptions();
    }

    private static void setUpAuthExceptions() {
        mapper.put(AccessTokenExpiredException.class,
                ExceptionSituation.of("Access Token의 유효기간이 만료되었습니다.", BAD_REQUEST));
        mapper.put(InvalidAccessTokenException.class,
                ExceptionSituation.of("부적절한 Access Token 정보입니다.", HttpStatus.UNAUTHORIZED));
        mapper.put(InvalidAuthorizationHeaderException.class,
                ExceptionSituation.of("인증헤더 타입이 적절하지 않습니다.", BAD_REQUEST));
        mapper.put(InvalidRefreshTokenException.class,
                ExceptionSituation.of("부적절한 Refresh Token 정보입니다.", HttpStatus.UNAUTHORIZED));
        mapper.put(OauthServerException.class,
                ExceptionSituation.of("Oauth 외부 서버 요청에서 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR));
        mapper.put(RefreshTokenExpiredException.class,
                ExceptionSituation.of("Refresh Token 유효 기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED));
        mapper.put(RefreshTokenNotExistsInCookieException.class,
                ExceptionSituation.of("쿠키에 refresh token이 존재하지 않습니다.", BAD_REQUEST));
    }

    private static void setUpCategoryExceptions() {
        mapper.put(CategoryNotFoundException.class,
                ExceptionSituation.of("존재하지 않는 카테고리 id 입니다.", BAD_REQUEST));
    }

    private static void setUpMogakoExceptions() {
        mapper.put(MogakoNotFoundException.class,
                ExceptionSituation.of("존재하지 않는 모각코 id 입니다.", BAD_REQUEST));
    }

    public static ExceptionSituation getSituationOf(MosException e) {
        return mapper.get(e.getClass());
    }
}
