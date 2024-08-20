package mos.auth.exception;

import mos.common.exception.MosException;

public class OauthServerException extends MosException {

    private static final String MESSAGE = "Oauth 서버 요청에서 예외가 발생했습니다.";

    public OauthServerException(String externalOauthServerMessage) {
        super(MESSAGE + ": " + externalOauthServerMessage);
    }
}
