package controllers;

import utils.SecurityCodeUtil;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-11
 * Time  : 上午1:49
 */
public class SecurityCodes extends Application {

    public static void createSecurityCode(String scKey) {
        SecurityCodeUtil.SecurityCode sc = SecurityCodeUtil.createRandomSecurityCode(scKey);

        log.debug(">> "+sc);
        // 将键值和验证码保存到session
        session.put(scKey,sc.securityCode);
        response.setContentTypeIfNotSet(sc.contentType);
        renderBinary(sc.inputStream);
    }
}
