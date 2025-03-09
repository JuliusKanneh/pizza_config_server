// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package exception;

import java.util.Map;

public interface ExceptionFactory
{
    CustomException createException(ExceptionType type, Map<String, Object> params);
}
