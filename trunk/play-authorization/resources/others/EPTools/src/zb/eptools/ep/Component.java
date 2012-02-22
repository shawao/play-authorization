package zb.eptools.ep;

import java.util.List;
import java.util.Map;

/**
 * ¿ØÖÆÆ÷
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class Component {
	
	public String controllerClass;
	
	public List<Bo> bos;
	
	public Map<String,String> otherProperties;
	
	public List<Event> events;
	
	public String configFile;
	
	
	public class Event{
		public String name;
		public String method;
		public String description;
		public String jspFile;
		public String jsFile;
		
		// com.neusoft.component.view.AjaxView
		// and other types?
		public String viewType;
	}
}
