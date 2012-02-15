package controllers;

import models.fault.SysConstant;
import models.fault.Vendor;
import models.sys.District;
import models.sys.Organization;
import models.sys.SysUser;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import utils.SecurityCodeUtil;

import java.util.List;
import java.util.Map;

public class Application extends Controller {

    protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    static Integer pageSize = Integer.parseInt(Play.configuration.getProperty("auth.pageSize", "10"));
    //station.album.home
    static String ALBUM_HOME = Play.configuration.getProperty("station.album.home");

    /**
     * Application初始化时，载入下面全局域
     */
    // 系统启动时载入常量列表
    public static List<SysConstant> memConstList = null;
    public static List<District> memProvinces = null;
    public static List<Organization> memOrganizations = null;
    // <disId,disName>
    public static Map<String, String> memDistrictMap = null;
    public static List<Vendor> memVendors = null;

    /**
     * 初始化系统全局域
     *
     * @return
     */
    public static String reInitialize() {
        StringBuilder buf = new StringBuilder();
        String info = refreshMemConstList();
        buf.append(info).append("<br/>");

        info = refreshMemProvinces();
        buf.append(info).append("<br/>");

        info = refreshMemOrganizations();
        buf.append(info).append("<br/>");

        info = refreshMemVendors();
        buf.append(info);

        // return to ajax dialog
        return buf.toString();
    }

    public static String refreshMemConstList() {
        memConstList = SysConstant.findAll();
        String info = "...memConstList(" + memConstList.size() + ") loaded in memory";
        log.info(info);
        return info;
    }

    public static String refreshMemProvinces() {
        memProvinces = District.availableProvinces();
        String info = "...available memProvinces(" + memProvinces.size() + ") loaded in memory";
        log.info(info);

        memDistrictMap = District.availableMap();
        return info;
    }

    public static String refreshMemOrganizations() {
        memOrganizations = Organization.findAll();
        String info = "...available memOrganizations(" + memOrganizations.size() + ") loaded in memory";
        log.info(info);
        return info;
    }

    public static String refreshMemVendors() {
        memVendors = Vendor.findAll();
        String info = "...available memVendors(" + memVendors.size() + ") loaded in memory";
        log.info(info);
        return info;
    }


    // ~~~~~~~~~~~~ @Before interceptors

    @Before
    static void globals() {
        // 已登录用户信息（从数据库获取信息，session中仅保存ID）
        renderArgs.put("connected", connectedUser());

        // 系统预加载项
        renderArgs.put("pageSize", pageSize);
        renderArgs.put("memConstList", memConstList);
        renderArgs.put("memProvinces", memProvinces);
        renderArgs.put("memDistrictMap", memDistrictMap);
        renderArgs.put("memOrganizations", memOrganizations);
        renderArgs.put("memVendors", memVendors);
    }


    @Before()
    static void logAccess() {
        log.info("");
        log.info("=========================================================");
        log.info("url = " + request.url);
        log.info("controllerClass.actionMethod = " + request.controllerClass + "." + request.actionMethod);
        log.info("remoteAddress = " + request.remoteAddress);
        log.info("loginFlag = " + (connectedUser()!=null));
        log.info("===============================");
    }


    @Before(unless = {"/","login", "authenticate", "logout","SecurityCodes.createSecurityCode"})
    static void checkLogin() {
        SysUser user = connectedUser();
        if (user == null) {
            flash.error("您尚未登录系统，或登录已超时");
            log.info("checkLogin false");

            // 这里记录用户初始请求地址，登录后自动跳转到该页面
            // --如果有权限的话，否则会被权限检查拦截
            String visitUrl=request.url;
            login(visitUrl);
        }
    }

    /**
     * 检查权限
     */
    @Before
    static void checkSecure() {
        // TODO
        // 获取本次访问的url-->确定唯一FunctionID
        // 获取用户-->获取用户角色-->确定RoleID
        // Role(1)----Function(0..*)
        // -------------------------------------------------------
        // 系统内存中保存一组权限相关Map：
        // Map<RoleID,Map<Url,PermissionFlag>>
        // 其中每一个Url确定唯一一个Function，启动时加载权限Map


//        Secure secure = getActionAnnotation(Secure.class);
//        if (secure != null) {
//            if (connectedUser() == null || (secure.admin() && !connectedUser().isAdmin())) {
//                forbidden();
//            }
//        }
    }


    // ~~~~~~~~~~~~ Actions

    public static void signup() {
        render();
    }

    public static void register(
            @Required @Email String email,
            @Required @MinSize(5) String password,
            @Equals("password") String password2,
            @Required String name) {
        // 不提供自助注册，目前是管理员负责创建用户
    }

    public static void confirmRegistration(String uuid) {
        // 需要确认邮箱时使用
    }

    public static void login(String visitUrl) {
        String scKey= SecurityCodeUtil.createRandomKey();
        render(scKey,visitUrl);
    }

    public static void logout() {
        flash.success("您已经退出系统");
        session.clear();
        login(null);
    }


    public static void authenticate(
            String loginName, 
            String password, 
            String scKey, 
            String securityCode,
            String visitUrl) {
        SysUser user = SysUser.findByLoginName(loginName);
        if (user == null || !user.checkPassword(password)) {
            flash.error("用户不存在或密码错误");
            flash.put("loginName", loginName);

            // 清空session中的一切设置，包括验证码
            session.clear();
            login(visitUrl);
        } else if (!user.checkStatus()) {
            flash.error("用户未被授权访问系统");
            session.clear();
            login(visitUrl);
        } else if (!session.get(scKey).equals(securityCode)) {
            flash.error("验证码错误");
            session.clear();
            login(visitUrl);
        } else {
            connect(user);
            flash.success("欢迎登录系统，%s !", user.nickName);
            
            if(StringUtils.isEmpty(visitUrl)){
                Welcome.index(user.id);
            }else{
                // redirect to url user wanna visit before logged in
                redirect(visitUrl);
            }
        }
    }


    public static void resendConfirmation(String uuid) {
    }


    public static void reload() {
        renderHtml(reInitialize());
    }


    // ~~~~~~~~~~~~ Some utils

    static void connect(SysUser user) {
        session.put("loggedUserId", user.id);
    }

    static SysUser connectedUser() {
        String userId = session.get("loggedUserId");
        return userId == null ? null : (SysUser) SysUser.findById(Long.parseLong(userId));
    }
}
