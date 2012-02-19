package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;
import org.apache.commons.io.FileUtils;
import utils.PlayFileUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @Column(length = 30)
    public String contentType;


    public Long fileSize;//图片文件大小


    @ManyToOne
    public SysUser submitter;// who submit it

    public StationAlbum(AutoStation station, int direction, String remark,
                        String path, String fileName, String contentType,
                        Long fileSize, SysUser submitter) {
        this.station = station;
        this.direction = direction;
        this.remark = remark;
        this.path = path;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.submitter = submitter;
    }
    
    public StationAlbum(AutoStation station,File file,int desc,SysUser submitter){
        this.station=station;
        this.direction = desc;
//        this.path=saveFile(file,albumHomeDir,station);
        this.fileName=file.getName();
        this.contentType= PlayFileUtil.getContentType(file);
        this.fileSize=file.length();
        this.submitter = submitter;
    }

    public static StationAlbum saveAlbum(AutoStation station, File file, int desc, SysUser submitter, File albumHomeDir) {
        StationAlbum stationAlbum = new StationAlbum(station, file, desc, submitter);
        stationAlbum.path = saveFile(file, albumHomeDir, station);
        stationAlbum.save();
        return stationAlbum;
    }


    public static void saveAlbum(AutoStation station,File[] file,int[] desc,SysUser submitter, File albumHomeDir){
        play.Logger.info("file.length: "+(file==null?0:file.length));
        for(int i=0;i<file.length;i++){
            File f=file[i];
            int d=0;
            if(i<desc.length)
                d=desc[i];
            StationAlbum stationAlbum=new StationAlbum(station,f,d,submitter);

            stationAlbum.path=saveFile(f,albumHomeDir,station);
            stationAlbum.save();
        }
    }

    private static String saveFile(File file, File albumHomeDir,AutoStation station) {
        String path=null;
        try {
            String stationAlbumPath=albumHomeDir.getAbsolutePath()+File.separator+station.id;
            File stationAlbumFolder=new File(stationAlbumPath);

            if(!stationAlbumFolder.exists()){
                FileUtils.forceMkdir(stationAlbumFolder);
            }
            
            FileUtils.moveFileToDirectory(file, stationAlbumFolder, true);
//            path=stationAlbumPath+File.separator+file.getName();
            path=station.id+File.separator+file.getName();
        } catch (IOException e) {
            play.Logger.error(e.getMessage());
        }
        return path;
    }
    
    
    public static List<StationAlbum> findByStation(AutoStation station){
        return StationAlbum.find("byStation",station).fetch();
    }
    
    public String showDirection(){
        switch (direction){
            case 1: return "东";
            case 2: return "南";
            case 3: return "西";
            case 4: return "北";
            case 5: return "其它";
            default: return "";
        }
    }

    @Override
    public String toString() {
        return "StationAlbum{" +
                "station=" + station +
                ", direction=" + direction +
                ", remark='" + remark + '\'' +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", submitter=" + submitter +
                '}';
    }
}
