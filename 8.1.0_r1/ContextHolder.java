package javax.crypto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;


/**
 * ContextHolder
 * AndroidContextHolder <com.vilyever.vdcontextholder>
 * Created by vilyever on 2015/9/15.
 * change by fisho on 2021/1/20
 * Feature:
 */
public class ContextHolder {
    private final ContextHolder self = this;

    static Object ApplicationContext;
    static Logger logger = Logger.getLogger("fishso");

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     * @param context
     */
    public static void init(Object context) {
        ApplicationContext = context;
    }

    public static Object getContext() {
        if (ApplicationContext != null) {
            return ApplicationContext;
        }

        Object app = ContextHolder.getContextByAppGlobals();
        if (app == null){
            logWarning("getContextByAppGlobals return null.");
            app = ContextHolder.getContextByActivityThread();
            if (app == null){
                logWarning("getContextByActivityThread return null.");
            }
        }

        return ApplicationContext;

    }

    public static Object getContextByAppGlobals() {
        Object app = null;
        try {
            app =  Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
        } catch (final Exception e) {
            logWarning("exception:AppGlobals.getInitialApplication() Failed," + e.getMessage());
            e.printStackTrace();
        }
        return app;
    }

    public static Object getContextByActivityThread() {
        Object app = null;
        try {
            app = Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
        } catch (final Exception e) {
            logWarning("exception:AppGlobals.getInitialApplication() Failed," + e.getMessage());
            e.printStackTrace();
        }
        return app;
    }

    public static String getPackageName() {
        String packageName = "";
        try {
            String curPkgName = (String) Class.forName("android.app.ActivityThread")
                    .getMethod("currentPackageName").invoke(null);

            packageName = curPkgName;
            /**** 
            String curOpName = (String) Class.forName("android.app.ActivityThread")
                    .getMethod("currentOpPackageName").invoke(null);
            String curProcessName = (String) Class.forName("android.app.ActivityThread")
                    .getMethod("currentProcessName").invoke(null);

            logInfo(String.format("curPkgName:%s,curOpName:%s,curProcessName:%s", curPkgName,
                    curOpName, curProcessName));
            ******/

            
        } catch (Exception e) {
            e.printStackTrace();
            logWarning("exception:getPackageName Exception," + e.getMessage() );
            return "";
        }
        if(packageName.isEmpty()){
            logWarning("error:getPackageName is empty");
        }
        return packageName;
    }

    public static void logInfo(String msg){
        logger.info(msg);
    }

    public static void logWarning(String msg){
        logger.warning(msg);
    }

    public static void logError(String msg){
        logger.severe(msg);
    }

}
