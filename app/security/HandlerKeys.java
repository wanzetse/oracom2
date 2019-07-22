package security;

import be.objectify.deadbolt.java.ConfigKeys;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public enum HandlerKeys
{
    DEFAULT(ConfigKeys.DEFAULT_HANDLER_KEY);

    public final String key;

    HandlerKeys(final String key)
    {
        this.key = key;
    }
}
