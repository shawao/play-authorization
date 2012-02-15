package utils;

import play.Play;
import play.mvc.Scope;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-12
 * Time  : 上午1:55
 */
public class PlayFileUtil {

    public static void putIntoSession(Scope.Session session,File file, String desc){
        //
    }


    public static String getContentType(File file){
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
    
    public static void main(String[] args){
        String unDecodedString="\u4e16\u754c\u4e0a\u7684\u4e00\u5207\uff0c\u8fd8\u662f\u771f\u7684\u5417\uff1f";
        try {
            String decodedString=new String(unDecodedString.getBytes("UTF-8"));
            System.out.println(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
