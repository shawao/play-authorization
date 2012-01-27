package controllers;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-27
 * Time  : 下午12:29
 */
public class StationAlbums extends Application{

    public static void index(){
        show(1);
    }

    public static void show(Integer page){
        render();
    }
}
