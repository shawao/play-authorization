package utils;

import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Desc  : 生成验证码
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-11
 * Time  : 上午1:41
 */
public class SecurityCodeUtil {
    
    public static int PHOTO_WIDTH=85;
    public static int PHOTO_HEIGHT=20;
    
    public static int SECURITY_CODE_LENGTH=6;
    public static int RANDOM_KEY_LENGTH=18;


    public static String createRandomKey(){
        return RandomStringUtils.random(RANDOM_KEY_LENGTH,true,true);
    }


    public static SecurityCode createRandomSecurityCode(String givenKey) {
        int width = PHOTO_WIDTH;
        int height = PHOTO_HEIGHT;
        // 取得一个4位随机字母数字字符串
        String randomCode = RandomStringUtils.random(SECURITY_CODE_LENGTH
                , true, true);//这个s的值就是页面验证码上显示的值

        // 保存入session,用于与用户的输入进行比较.
        // 注意比较完之后清除session.

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设定字体
        Font mFont = new Font("Times New Roman", Font.BOLD, 18);// 设置字体
        g.setFont(mFont);

        // 画边框
        // g.setColor(Color.BLACK);
        // g.drawRect(0, 0, width - 1, height - 1);

        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        // 生成随机类
        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            int x3 = random.nextInt(12);
            int y3 = random.nextInt(12);
            g.drawLine(x2, y2, x2 + x3, y2 + y3);
        }

        // 将认证码显示到图象中
        g.setColor(new Color(20 + random.nextInt(110), 20 + random
                .nextInt(110), 20 + random.nextInt(110)));

        g.drawString(randomCode, 10, 16);

        // 图象生效
        g.dispose();

        SecurityCode securityCode=null;

        // 输出图象到页面
        String contentType="JPEG";
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            ImageIO.write(image, contentType, out);

            securityCode = new SecurityCode();
            securityCode.contentType = contentType;
            securityCode.securityCode = randomCode;
            securityCode.key = givenKey==null?createRandomKey():givenKey.trim();
            securityCode.inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return securityCode;

    }
    
    
    public static SecurityCode createRandomSecurityCode2(String givenKey){
        int width = PHOTO_WIDTH, height = PHOTO_HEIGHT;

        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 16);
        }


        SecurityCode securityCode=null;

        // 输出图象到页面
        String contentType="JPEG";
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            ImageIO.write(image, contentType, out);

            securityCode = new SecurityCode();
            securityCode.contentType = contentType;
            securityCode.securityCode = sRand;
            securityCode.key = givenKey==null?createRandomKey():givenKey.trim();
            securityCode.inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return securityCode;
    }


    public static class SecurityCode {
        public String key;// 用于标记验证码和验证图片的唯一标记
        public String securityCode;// 验证码
        public InputStream inputStream;
        public String contentType;//mime type of photo

        @Override
        public String toString() {
            return "SecurityCode{" +
                    "key='" + key + '\'' +
                    ", securityCode='" + securityCode + '\'' +
                    ", contentType='" + contentType + '\'' +
                    '}';
        }
    }


    /**
     * **************
     */

    private static Color getRandColor(int fc, int bc) {
        // 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
