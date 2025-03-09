// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package networking.requests;

import java.util.Properties;

public class ConfigPizzeriaRequest implements CustomRequest
{
    private Properties _properties;

    public ConfigPizzeriaRequest(Properties properties) {
        super();
        _properties = properties;
    }

    public Properties getProperties() {
        return _properties;
    }
}
