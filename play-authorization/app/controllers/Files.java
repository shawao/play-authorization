package controllers;

import org.apache.commons.io.FileUtils;
import play.Play;
import utils.SecurityCodeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-5
 * Time  : 下午7:43
 */
public class Files extends Application {

    //file.upload.temp
    static String FILE_UPLOAD_TEMP = Play.configuration.getProperty("file.upload.temp");
    static File tempDir;

    static {
        tempDir = new File(FILE_UPLOAD_TEMP);
    }


    public static void upload(File file,String desc) {

        log.info("Files.upload......start");
        log.info("params: "+params);

        log.info("file.length: "+(file==null?"empty":file.length()));
        log.info("desc: "+(desc==null?"empty":desc));

        UploadResult ur=processUploadFile(file,desc);

        log.info("Files.upload......end");
        renderText(ur.key);
    }


    public static void showPhoto(String key){
        String path=session.get(key+"_path");
        String contentType=session.get(key+"_contentType");
        response.setContentTypeIfNotSet(contentType);
        try {
            renderBinary(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            log.error(e);
        }
    }


    public static void testResult(){
        renderJSON(new UploadResult("测试文件名",true,"上传成功",3000));
    }

    // StringBuilder: non thread safe, more efficient
    public static String descFile(File file) {
        if (file == null)
            return "No file";
        StringBuilder buf = new StringBuilder();
        buf.append("{name:\"").append(file.getName()).append("\", ");
        buf.append("size:").append(file.length()).append(", ");
        buf.append("path:\"").append(file.getAbsolutePath()).append("\"}");
        return buf.toString();
    }


    private static UploadResult processUploadFile(File file,String desc) {
        UploadResult ur=new UploadResult(file);
        if (file != null) {
            log.info(descFile(file));
//            try {
//                FileUtils.moveFileToDirectory(file, tempDir, true);
//            } catch (IOException e) {
//                log.error(e);
//            }

            // put it to session
            ur.key = SecurityCodeUtil.createRandomKey();
            session.put(ur.key+"_path",file.getAbsolutePath());
            session.put(ur.key+"_contentType",ur.contentType);
            
            log.info(ur.key+"_path: "+file.getAbsolutePath());
            log.info(ur.key+"_contentType: "+ur.contentType);
        }
        return ur;
    }
    
    
    public static class UploadResult{
        public String name;// file name
        public long length;// file length
        public String contentType;// jpeg,png,gif,bmp

        public boolean success;// upload result flag
        public String message;// upload result in message

        public String key;// key in session, to save path temporarily

        public UploadResult() {
        }

        public UploadResult(String name,boolean success, String message, int length) {
            this.name=name;
            this.success = success;
            this.message = message;
            this.length = length;
        }

        public UploadResult(File file) {
            this.name=file!=null?file.getName():"";
            this.success = file!=null;
            this.message = file!=null?"上传成功":"上传失败";
            this.length = file==null?0:file.length();
            this.contentType=getContentType(file);
        }
        
        public String getContentType(File file){
            int dotLastIndex=file.getName().lastIndexOf(".");
            String extName=file.getName().substring(dotLastIndex).trim().toLowerCase();
            
            String contentType=null;
            if("bmp".equals(extName)){
                contentType="image/bmp";
            }else if("png".equals(extName)){
                contentType="image/png";
            }else if("jpeg".equals(extName) || "jpg".equals(extName) ||"jpe".equals(extName)){
                contentType="image/jpeg";
            }else if("gif".equals(extName)){
                contentType="image/gif";
            }else{
                contentType="image/jpeg";// guess it^^
            }
            return contentType;
        }
    }
}