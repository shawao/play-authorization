package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Desc: 相册实体，每个实例对应一张照片
 *      上传时需要限制其大小\
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 下午11:05
 */
@Entity
@Table(name = "t_sta_album")
public class StationAlbum extends AbstractEntity {

    @ManyToOne(optional = false)
    public AutoStation station;

    public int direction;//0：未设定，1,2,3,4分别代表东南西北，5：其它

    @Column(length = 100)
    public String remark;

    @Column(length = 200,name = "path")
    public String path;//相对地址，通过从配置文件中获取station.album.home地址，加上path获得绝对地址（包含文件名）


    @Column(nullable = false,length = 100)
    public String fileName;//上传时的文件名，如果有冲突则添加后缀


    public Long fileSize;//图片文件大小

}
