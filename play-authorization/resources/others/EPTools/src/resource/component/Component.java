/**
 * 
 */
package resource.component;

import java.util.Arrays;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 * ¿ØÖÆÆ÷xmlÃèÊö
 */
public class Component {

	/*
<?xml version="1.0" encoding="UTF-8"?>

<component controller="ep.component.payment.RecordQueryController" concurrent="1">
	<event name="show" method="show" description="ÏÔÊ¾Ò³Ãæ" url="view.jsp" script="proc.js"/>
	<event name="query" method="query" view="com.neusoft.component.view.AjaxView"/>
</component>
	 */
	
	public static final String[] NODE_NAME=new String[]{"component","bean"};
	public static final String ATTR_CONTROLLER="controller";
	public static final String ATTR_DESCRIPTION="description";
	public static final String ATTR_CONCURRENT="concurrent";
	public static final String ATTR_PARENT="parent";
	
	
	public String controller;
	public String description;
	public String parent;
	public int concurrent; // default value?
	
	public Event[] event;
	
	public static class Event{
		public static final String[] NODE_NAME=new String[]{"event"};
		
		public static final String ATTR_NAME="name";
		public static final String ATTR_METHOD="method";
		public static final String ATTR_DESCRIPTION="description";
		public static final String ATTR_URL="url";
		public static final String ATTR_SCRIPT="script";
		public static final String ATTR_VIEW="view";
		
		public String name;
		public String method;
		public String description;
		public String url;
		public String script;
		public String view;
		
		
		@Override
		public String toString() {
			return "Event [name=" + name + ", method=" + method
					+ ", description=" + description + ", url=" + url
					+ ", script=" + script + ", view=" + view + "]";
		}
		
	}
	
	@Override
	public String toString() {
		return "Component [controller=" + controller + ", description="
				+ description + ", concurrent=" + concurrent + ", events="
				+ Arrays.toString(event) + "]";
	}

	
}
