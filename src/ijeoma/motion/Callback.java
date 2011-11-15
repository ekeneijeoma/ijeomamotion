/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package ijeoma.motion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Callback extends Motion {

	private Object callbackObject;
	private Method callbackObjectMethod;
	private Field callbackObjectParameter;

	// protected Method callbackStartedMethod, callbackEndedMethod;

	public Callback(String _callbackObjectMethodName) {
		// setup(_name, _begin, _end, _duration, _durationMode, delay,
		// easing);
		super();
		setName(_callbackObjectMethodName);
		setupObject(parent, _callbackObjectMethodName, null);
	}

	public Callback(String _callbackObjectMethodName, float _time) {
		// setup(_name, _begin, _end, _duration, _durationMode, delay,
		// easing);
		super();
		setName(_callbackObjectMethodName);
		setPlayTime(_time);
		setupObject(parent, _callbackObjectMethodName, null);
	}

	public Callback(String _callbackObjectMethodName, float _time,
			float _duration) {
		// setup(_name, _begin, _end, _duration, _durationMode, delay,
		// easing);
		super();
		setName(_callbackObjectMethodName);
		setPlayTime(_time);
		setDuration(_duration);
		setupObject(parent, _callbackObjectMethodName, null);
	}

	private void setupObject(Object _object, String _callbackObjectMethodName,
			String _callbackParamters) {
		// Object[] args = { new Float(time), new
		// Float(properties[i].getBegin()), new
		// Float(properties[i].getChange()), new Float(duration), new
		// Float(properties[i].getBegin()), new Float(properties[i].getEnd()) };

		callbackObject = _object;

		try {
			callbackObjectMethod = _object.getClass().getMethod(
					_callbackObjectMethodName, null);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			callbackObjectMethod.invoke(callbackObject, null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		logger.println("dispatchMotionStartedEvent callback");
		// PApplet.println("started");
		update();
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		logger.println("dispatchMotionEndedEvent callback");
		// PApplet.println("ended");
		if (duration > 0)
			update();
	}

	@Override
	public String toString() {
		return "Callback[object: \""
				+ callbackObject.getClass().getSimpleName() + "\", method: \""
				+ callbackObjectMethod.getName() + "\", time: " + getPlayTime()
				+ "]";
	}
}
