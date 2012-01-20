package models;

import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date  : 11-12-11
 * Time  : 下午7:20
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model implements Serializable {

    public Date createDate = new Date();
    public Date lastUpdate;

}
