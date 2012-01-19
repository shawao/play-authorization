package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc:
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 下午11:05
 */
@Entity
@Table(name = "t_sta_album")
public class StationAlbum extends AbstractEntity {

    @Column(name = "sta_id")
    public Long stationId;

    public int direction;//1,2,3,4分别代表东南西北，5表示其他

    @Column(length = 50)
    public String remark;
    @Column(length = 200)
    public String photoPath;

}
