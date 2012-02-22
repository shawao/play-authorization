/**
 * 
 */
package zb.eptools.helpers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import zb.eptools.utils.FileUtil;
import zb.eptools.utils.StringUtil;

/**
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 *
 */
public class HelperTools {

	static Logger log=Logger.getLogger(HelperTools.class);
	
	static final String INVALID_NODE_NAME="#text";
	
	
	/**
	 * 给定采用递归方法自动分析给定xml文件和
	 * @param clazz
	 * @param document
	 * @param nodeNames
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> T autoParse(Class<T> clazz,File resourceFile) throws 
			InstantiationException, IllegalAccessException, 
			SecurityException, NoSuchFieldException, 
			ParserConfigurationException, SAXException, 
			IOException, IllegalArgumentException, ClassNotFoundException {
		log.debug(FileUtil.readFile(resourceFile, "GBK"));
		T bean=(T)clazz.newInstance();

		// retrieve node defination, if null using class name
		String[] nodeNames=null;
		Field nodeNameField=clazz.getField("NODE_NAME");
		if(nodeNameField!=null){
			nodeNames=(String[])nodeNameField.get(bean);
		}else{
			nodeNames=new String[]{clazz.getName()};
		}
		
		log.debug(StringUtil.toString(nodeNames));
		
		// parse xml file with Dom mode
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        Document document = documentBuilder.parse(resourceFile);
        document.normalize();//remove blanks,Important!
		
		NodeList rootNodeList=getNodesByName(document,nodeNames);
		List<Node> validNodes=toValidList(rootNodeList);
		autoFillRecursively(new Object[]{bean},validNodes);
		return bean;
	}
	
	
	
	private static void autoFillRecursively(Object[] beans, List<Node> nodeList) throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
		
		if (nodeList != null && nodeList.size() > 0) {
			for (int i = 0; i < nodeList.size(); i++) {
				Node tmpNode = nodeList.get(i);
				log.debug("NodeName: "+tmpNode.getNodeName());
				if(INVALID_NODE_NAME.equals(tmpNode.getNodeName())){
					log.debug("continue loop");
					continue;
				}
				
				fillAttributes(beans[i], tmpNode);

//				NodeList childNodes = tmpNode.getChildNodes();
//				log.debug(nodeListToString(childNodes));
				List<Node> childNodesList= toValidList(tmpNode.getChildNodes());
				
				if (childNodesList != null && childNodesList.size() > 0) {
					for (int j = 0; j < childNodesList.size(); j++) {
						String nodeName=childNodesList.get(j).getNodeName().toLowerCase();
						log.debug("##"+nodeName);
						Field field=null;
						try{
							field = beans[i].getClass().getField(nodeName);
						}catch(Exception e){
							;
						}
						
						if (field != null) {
							log.debug("field: "+field);
							
							Object[] subObjects=null;
							Object fieldObj=field.get(beans[i]);
							log.debug("%%-1 "+fieldObj);
							if(fieldObj instanceof Object[]){
								subObjects=(Object[])fieldObj;
							}else{
								subObjects=new Object[]{fieldObj};
							}
							
							// Init field instance
							for(int f=0;f<subObjects.length;f++){
								log.debug("%%-2 "+field.getGenericType());
								subObjects[f]=Class.forName(""+field.getGenericType()).newInstance();
							}
							
							autoFillRecursively(subObjects, childNodesList);
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 输入对象，Xml节点和属性类型配置，自动填值到对象
	 * @param xmlBean
	 * @param node
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void fillAttributes(Object xmlBean,Node node) 
			throws IllegalArgumentException, IllegalAccessException{
		if(node==null || node.getAttributes()==null) return;
		
		NamedNodeMap nnMap=node.getAttributes();
		Field[] fields=xmlBean.getClass().getFields();
		
		for(int i=0;i<nnMap.getLength();i++){
			Node tmpNode=nnMap.item(i);
			log.debug("KeyValue: "+tmpNode.getNodeName()+" = "+tmpNode.getNodeValue());
			
			for(Field field:fields){
				if(field.getName().equals(tmpNode.getNodeName())){
					if(field.getType().equals(Integer.class)){
						field.set(xmlBean, Integer.parseInt(tmpNode.getNodeValue()));
					}else if(field.getType().equals(String.class)){
						field.set(xmlBean, tmpNode.getNodeValue());
					}else if(field.getType().equals(Boolean.class)){
						field.set(xmlBean, Boolean.parseBoolean(tmpNode.getNodeValue()));
					}else if(field.getType().equals(Long.class)){
						field.set(xmlBean, Long.parseLong(tmpNode.getNodeValue()));
					}else if(field.getType().equals(Short.class)){
						field.set(xmlBean, Short.parseShort(tmpNode.getNodeValue()));
					}else if(field.getType().equals(Byte.class)){
						field.set(xmlBean, Byte.parseByte(tmpNode.getNodeValue()));
					}else if(field.getType().equals(Double.class)){
						field.set(xmlBean, Double.parseDouble(tmpNode.getNodeValue()));
					}else if(field.getType().equals(Float.class)){
						field.set(xmlBean, Float.parseFloat(tmpNode.getNodeValue()));
					}else{
						log.error("Invalid field type? "+field.getName());
					}
					break;
				}
			}
		}
		
		for(Field field:fields){
			log.debug("field: "+field.getName()+", "+field.getType());
		}
	}
	
	
	/**
	 * 获取某一节点，可以通过nodeNames指定超过一个的别名
	 * @param document
	 * @param nodeNames
	 * @return
	 */
	public static NodeList getNodesByName(Document document,String... nodeNames){
		NodeList nodeList=null;
		for(int i=0;i<nodeNames.length;i++){
			nodeList = document.getElementsByTagName(nodeNames[i].trim());
			if(nodeList!=null && nodeList.getLength()>0)
				break;
		}
		log.debug(nodeListToString(nodeList));
		return nodeList;
	}
	
	public static String nodeListToString(NodeList nodeList){
		if(nodeList==null || nodeList.getLength()==0) return "";
		
		StringBuilder buf=new StringBuilder();
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			buf.append(nodeToString(node));
			if(i<nodeList.getLength()-1)
				buf.append(",");
		}
		return buf.toString();
	}
	
	public static String nodeToString(Node node){
		StringBuilder buf=new StringBuilder();
		buf.append(node.getNodeName()).append(":").append(node.getNodeValue());
		return buf.toString();
	}
	
	public static List<Node> toValidList(NodeList nodeList){
		if(nodeList==null) return null;
		List<Node> nodes=new ArrayList<Node>();
		for(int i=0;i<nodeList.getLength();i++){
			Node node=nodeList.item(i);
			if(!INVALID_NODE_NAME.equals(node.getNodeName()))
				nodes.add(node);
		}
		return nodes;
	}
}
