package exception;

import java.util.Map;

public interface ExceptionFactory
{
    CustomException createException(ExceptionType type, Map<String, Object> params);
}
