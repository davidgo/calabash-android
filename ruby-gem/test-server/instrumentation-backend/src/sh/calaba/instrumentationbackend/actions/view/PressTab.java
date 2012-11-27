package sh.calaba.instrumentationbackend.actions.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.res.Resources;
import android.content.Context;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.wait.WaitForViewById;
import android.view.View;

/**
 * eg: performAction( 'press_tab', '3')  -> 3 is index of the tab 
 *
 * @author David Go
 */
public class PressTab extends WaitForViewById implements Action {
      
	@Override
	public Result execute(String... args) {
        
        try {
        	View view = InstrumentationBackend.solo.getView(android.R.id.tabs);
        	if( view != null ) {
        		return getPropertyValue("getChildTabViewAt", view, args);
        	} else {
        		return new Result(false, "Timed out while waiting for tab view");
        	}
        } catch( Exception e ) {
    		return Result.fromThrowable(e);
        }
	}
	
	/**
	 * @param propertyName
	 * @param view
	 * @param args - used by {@link AssertViewProperty#getPropertyValue}
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected Result getPropertyValue( String propertyName, View view, String[] args ) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			// resort to reflection
			Class<? extends View> clazz = view.getClass();
			Method method = null;
			try {
				String methodName = propertyName;
				method = clazz.getMethod( methodName , Integer.TYPE);
			} catch( NoSuchMethodException e ) {
				try {
					String methodName = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
					method = clazz.getMethod( methodName );
				} catch( NoSuchMethodException e2 ) {
					String methodName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
					method = clazz.getMethod( methodName );
				}
			}
			View valueObj = (View) method.invoke(view, Integer.parseInt(args[0]) - 1);
			return processProperty( propertyName, valueObj, args );
	}
	
	/**
	 * @param propertyName
	 * @param propertyValue
	 * @param args - used by {@link AssertViewProperty#getPropertyValue}
	 * @return a successful result with the requested property as the <code>message</code> field
	 */
	protected Result processProperty( String propertyName, View propertyValue, String[] args ) {
		String message = (propertyValue == null) ? "null" : propertyValue.toString();
        InstrumentationBackend.solo.clickOnView(propertyValue);
		return new Result( true, message );
	}
    
	@Override
	public String key() {
		return "press_tab";
	}
}
