package models.fault;

import models.AbstractEntity;
import play.data.validation.Email;
import play.data.validation.MinSize;

import javax.persistence.Column;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-20 下午12:15
 */
public class LeaveWord extends AbstractEntity {

    @MinSize(value = 3)
    @Column(nullable = false,length = 50)
    public String nickName;

    @Email
    @Column(nullable = false,length = 50)
    public String email;

    @Column(nullable = false,length = 200)
    public String subject;

    @MinSize(value = 20)
    @Column(nullable = false,length = 1000)
    public String content;

}
