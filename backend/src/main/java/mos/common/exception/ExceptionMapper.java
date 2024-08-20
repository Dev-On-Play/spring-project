package mos.common.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMapper {

    private static final Map<Class<? extends MosException>, ExceptionSituation> mapper = new HashMap<>();

    static {
        // initialize Exceptions
    }

    public static ExceptionSituation getSituationOf(MosException e) {
        return mapper.get(e.getClass());
    }
}
