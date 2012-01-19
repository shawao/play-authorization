package controllers;

import models.sys.Function;

import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午11:00
 */
public class Functions extends Application {

    public static void index() {
        render();
    }

    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;
        List<Function> entityList = Function.all().from(from).fetch(pageSize);
        Long entityCount = Function.count();
        render(entityCount, entityList, pageSequence);
    }
}
