package models.sys;

import play.db.jpa.Model;

/**
 * Desc: 当用户被分配角色后，更新用户与功能的直接关联
 *      此表仅用来提高系统启动时，权限数据的载入速度
 * --------
 * User: zou bo
 * Date: 12-1-19 下午2:26
 */
public class UserFunction extends Model {
    
    public Long userId;
    public Long funcId;
}
