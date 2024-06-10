package utilities;

public class LogUtils
{
    public static String info(String className,
                              String methodName,
                              String parameters)
    {
        String result;
        if (parameters == null || parameters.isEmpty())
            result = String.format("liquidBits -> %s -> %s", className, methodName);
        else
            result = String.format("liquidBits -> %s -> %s -> %s", className, methodName, parameters);
        return result;
    }
    public static String info(String className,
                              String methodName)
    {
        return LogUtils.info(className, methodName, "");
    }
}
