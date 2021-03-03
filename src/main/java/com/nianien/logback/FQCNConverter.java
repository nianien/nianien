package com.nianien.logback;

import ch.qos.logback.classic.pattern.NamedConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 打印业务代码所在类,方法以及行信息<br/>
 * 用于解决被第三方类库封装后无法正确打印FQCN的问题
 *
 * @author scorpio
 */
public class FQCNConverter extends NamedConverter {
    protected String getFullyQualifiedName(ILoggingEvent event) {
        StackTraceElement[] cda = event.getCallerData();
        if (cda != null && cda.length > 0) {
            for (StackTraceElement stackTraceElement : cda) {
                if (!stackTraceElement.getClassName().endsWith("Logger")) {
                    return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
                }
            }
            return cda[0].getClassName() + "." + cda[0].getMethodName() + ":" + cda[0].getLineNumber();
        } else {
            return CallerData.NA;
        }
    }

}

