/**
 * 
 */
package zb.eptools.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resource.component.Component;
import resource.component.Component.Event;

/**
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 *
 */
public class ResCmpComponentHelper implements ResourceHelper {
	
	static Logger log=Logger.getLogger(ResCmpComponentHelper.class);
	
	static Class<?> clazz= Component.class;
	
	private String[] keyPaths=new String[] {
			"content\\resource\\component",
			"content/resource/component"};
	
	
	private List<Component> components=new ArrayList<Component>();
	

	/* (non-Javadoc)
	 * @see zb.eptools.helpers.ResourceHelper#process(java.io.File)
	 */
	@Override
	public void process(File resourceFile) {
		if(fit(resourceFile)){
			log.info(resourceFile.getAbsolutePath()+"-------------start");
			try{
				Component component=new Component();
				//String xml=FileUtil.readFile(resourceFile,FILE_ENCODING);
		        //log.info(xml);
		        
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		        Document document = documentBuilder.parse(resourceFile);
		        document.normalize();//去除格式化空白,Important!
		        
		        NodeList nodeListComponent = HelperTools.getNodesByName(document, Component.NODE_NAME);
		        if(nodeListComponent!=null && nodeListComponent.getLength()>0){
		        	Node compNode=nodeListComponent.item(0);
		        	NamedNodeMap cNnMap=compNode.getAttributes();
		        	  	
		        	HelperTools.fillAttributes(component, compNode);
		        	
		        	components.add(component);
		        	NodeList nodeListEvent=compNode.getChildNodes();
		        	if(nodeListEvent!=null && nodeListEvent.getLength()>0){
		        		List<Event> eventList=new ArrayList<Event>();
		        		for(int i=0;i<nodeListEvent.getLength();i++){
		        			Node eventNode=nodeListEvent.item(i);
		        			if("#text".equals(eventNode.getNodeName())){
		        				continue;
		        			}
		        			NamedNodeMap eNnMap=eventNode.getAttributes();
		        			
		        			Event event=new Event();
		        			HelperTools.fillAttributes(event, eventNode);
		        			
//		        			event.description=eNnMap.getNamedItem(Event.ATTR_DESCRIPTION)==null
//		        					?null:eNnMap.getNamedItem(Event.ATTR_DESCRIPTION).getNodeValue();
//		        			event.method=eNnMap.getNamedItem(Event.ATTR_METHOD)==null
//		        					?null:eNnMap.getNamedItem(Event.ATTR_METHOD).getNodeValue();
//		        			event.name=eNnMap.getNamedItem(Event.ATTR_NAME)==null
//		        					?null:eNnMap.getNamedItem(Event.ATTR_NAME).getNodeValue();
//		        			event.script=eNnMap.getNamedItem(Event.ATTR_SCRIPT)==null
//		        					?null:eNnMap.getNamedItem(Event.ATTR_SCRIPT).getNodeValue();
//		        			event.url=eNnMap.getNamedItem(Event.ATTR_URL)==null?
//		        					null:eNnMap.getNamedItem(Event.ATTR_URL).getNodeValue();
//		        			event.view=eNnMap.getNamedItem(Event.ATTR_VIEW)==null
//		        					?null:eNnMap.getNamedItem(Event.ATTR_VIEW).getNodeValue();
		        			
		        			eventList.add(event);
		        		}
		        		Event[] eventArray=new Event[eventList.size()];
		        		component.event=eventList.toArray(eventArray);
		        	}
		        	log.info("component output: "+(component));
		        }else{
		        	log.info("####ERROR, no \""+Component.NODE_NAME+"\" node in xml document");
		        }
			}catch(Exception e){
				log.error("Process Error",e);
			}
			
			//log.info(resourceFile.getAbsolutePath()+"-------------end");
		}
	}

	/* (non-Javadoc)
	 * @see zb.eptools.helpers.ResourceHelper#fit(java.io.File)
	 */
	@Override
	public boolean fit(File resourceFile) {
		String filePath=resourceFile.getAbsolutePath();
		return filePath.endsWith(".xml") && 
				(filePath.indexOf(keyPaths[0])>=0 ||filePath.indexOf(keyPaths[1])>=0);
	}

	/* (non-Javadoc)
	 * @see zb.eptools.helpers.ResourceHelper#getResources()
	 */
	@Override
	public List<Object> getResources() {
		return (List)components;
	}

	/* (non-Javadoc)
	 * @see zb.eptools.helpers.ResourceHelper#getResources(java.lang.Class)
	 */
	@Override
	public <T> List<T> getResources(Class<T> clazz) {
		return (List<T>)components;
	}

	/* (non-Javadoc)
	 * @see zb.eptools.helpers.ResourceHelper#getResourceClass()
	 */
	@Override
	public Class<?> getResourceClass() {
		return clazz;
	}

}
