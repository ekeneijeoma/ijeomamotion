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
 
package ijeoma.motion.tween;

import java.lang.reflect.Method;

import ijeoma.geom.Path2D;
import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;

import processing.core.PApplet;
import processing.core.PVector;

public class Path2DTween extends Motion { // implements Comparable {
	private Method tweenPathStartedMethod, tweenPathEndedMethod,
			tweenPathChangedMethod, tweenPathRepeatedMethod;

	private Path2D path;

	public Path2DTween(String _name, Path2D _path, float _begin, float _end, float _duration, float _delay, String _easing) {
		super(_name, _begin, _end, _duration, _delay, _easing);
		setupPath(_path);
	}
	
	public Path2DTween(String _name, Path2D _path, float _begin, float _end, float _duration, float _delay) {
		super(_name, _begin, _end, _duration, _delay);
		setupPath(_path);
	}
	
	public Path2DTween(String _name, Path2D _path, float _begin, float _end, float _duration) {
		super(_name, _begin, _end, _duration);
		setupPath(_path);
	}

	protected void setupPath(Path2D _path) {
		path = _path;
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenPathStartedMethod = parentClass.getMethod(MotionEvent.TWEEN_STARTED, new Class[] { Path2DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPathEndedMethod = parentClass.getMethod(MotionEvent.TWEEN_ENDED, new Class[] { Path2DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPathChangedMethod = parentClass.getMethod(MotionEvent.TWEEN_CHANGED, new Class[] { Path2DTween.class });
		} catch (Exception e) {
		}
		
		try {
			tweenPathRepeatedMethod = parentClass.getMethod(MotionEvent.TWEEN_REPEATED, new Class[] { Path2DTween.class });
		} catch (Exception e) {
		}
	}

	public PVector getPoint() {
		return path.getPoint(getPosition());
	}

	public float getX() {
		return getPoint().x;
	}

	public float getY() {
		return getPoint().y;
	}

	public void setPath(Path2D _path) {
		path = _path;
	}

	public Path2D getPath() {
		return path;
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		logger.println("dispatchMotionStartedEvent tweengroup");

		if (tweenPathStartedMethod != null) {
			try {
				tweenPathStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenPathEndedMethod != null) {
			try {
				tweenPathEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenPathEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenPathChangedMethod != null) {
			try {
				tweenPathChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenPathRepeatedMethod != null) {
			try {
				tweenPathRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
