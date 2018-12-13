/**
 * 
 */
package jp.mosp.framework.js;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DirectJsに出力するフィールド
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DirectJs {
	
	/**
	 * @return フィールド名
	 */
	String value() default "";
}
