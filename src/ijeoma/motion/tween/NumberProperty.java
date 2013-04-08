/**
is * ##library.name##
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

import java.lang.reflect.Field;

import processing.core.PApplet;

public class NumberProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected Class<?> fieldType;

	String name = "";

	protected float begin, end, change;
	protected float position;

	protected float value;

	protected int order = 0;

	boolean hasSetupValue = false;

	public NumberProperty() {

	}

	public NumberProperty(Object object, String name, float end) {
		setupObject(object, name);
		setup(name, end);
	}

	public NumberProperty(String name, float begin, float end) {
		setup(name, begin, end);
	}

	private void setup(String name, float end) {
		this.name = name;

		setEnd(end);

		position = 0;
	}

	private void setup(String name, float begin, float end) {
		this.name = name;

		setEnd(end);
		setBegin(begin);

		position = 0;
	}

	private void setupObject(Object propertyObject, String propertyName) {
		object = propertyObject;
		objectType = object.getClass();

		boolean found = false;

		while (objectType != null) {
			for (Field f : objectType.getDeclaredFields())
				if (f.getName().equals(propertyName)) {
					fieldType = f.getType();
					found = true;
					break;
				}

			if (found)
				break;
			else
				objectType = objectType.getSuperclass();
		}

		if (found)
			try {
				field = objectType.getDeclaredField(propertyName);

				try {
					field.setAccessible(true);
				} catch (java.security.AccessControlException e) {
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
	}

	public void updateValue() {
		value = PApplet.lerp(begin, end, position);

		if (field != null)
			try {
				if ((position > 0 && position < 1)
						|| (position == 0 && order == 0))
					field.setFloat(object, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String _name) {
		name = _name;
	}

	public Float getBegin() {
		return begin;
	}

	public void setBegin() {
		try {
			begin = field.getFloat(object);
			change = end - begin;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setBegin(Object _begin) {
		begin = (Float) _begin;
		change = end - begin;
	}

	public Float getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		if (field != null) {
			try {
				begin = field.getFloat(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else
			begin = value;

		end = (_end instanceof Integer) ? new Float(_end.toString())
				: (Float) _end;
		change = end - begin;
	}

	public Float getChange() {
		return change;
	}

	public void setChange(Object _change) {
		change = (Float) _change;
	}

	public Float getPosition() {
		return position;
	}

	@Override
	public void setPosition(Object _position) {
		position = (Float) _position;

		updateValue();
	}

	public Float getValue() {
		if (field != null) {
			try {
				return field.getFloat(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		} else
			return value;
	}

	public Object getObject() {
		return object;
	}

	public void setOrder(int index) {
		order = index;
	}

	public int getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "NumberParameter[name: " + name + ", begin: " + begin
				+ ", end: " + end + ", change: " + change + ", position: "
				+ position + "]";
	}
}
