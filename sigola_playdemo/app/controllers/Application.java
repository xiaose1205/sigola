package controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import common.baseController;
import common.judgeRequest;

import play.mvc.*;

import data.DataAction;
import data.PageList;
import data.dataEnum.OrderByEnum;
import data.dataEnum.RelationEnum;

import logic.demoLogic;
import models.*;

public class Application extends baseController {

    // CREATE TABLE `demo` (
    // `id` INT(11) NOT NULL AUTO_INCREMENT,
    // `username` VARCHAR(50) DEFAULT NULL,
    // `userpwd` VARCHAR(50) DEFAULT NULL,
    // `createdate` DATETIME DEFAULT NULL,
    // PRIMARY KEY (`id`)
    // ) ENGINE=INNODB AUTO_INCREMENT=940 DEFAULT CHARSET=latin1

    public static void index() throws SQLException {
 

        demoModel model = new demoModel();
        demoLogic logic = new demoLogic();

        for (int i = 0; i < 1; i++) {

            { /* 新增 */
                Random random = new Random();
                model.setUserName("demo" + random.nextInt());
                model.setUserPwd("123456");
                int reslut = logic.Add(model);
                System.out.println(reslut);
            }
        }

        {/* 修改 */
            Random random = new Random();
            model.setUserName("update" + random.nextInt());
            model.setId(10);
            logic.Save(model);
        }
        {
            /* 删除 */
            model.setId(1);
            logic.Remove(model);
        }
        PageList<demoModel> plist = null;
        List<demoModel> list = null;
        {
            DataAction action = new DataAction();
            action.setTable(model).setfileds("*");
            /* 对in进行适当的优化,一条数据的时候自动会进入= */
            action.where("userName", "12", RelationEnum.In);
            // action.where("userName", "wan", RelationEnum.LikeLeft);
            // action.where("userName", "an", RelationEnum.Like);
            action.order("id", OrderByEnum.Desc);
            /* list */
            list = action.getList(demoModel.class);
            showlist(list);
            /* list with count 对count查找的结果适当的优化，减少一次查询 */
            plist = action.getPageList(demoModel.class);
            showlist(plist);
            System.out.println(plist.getTotalCount());
        }

        {
            /* list */
            list = logic.FindList(0, 10);
            showlist(list);
            /* list with count */
            plist = logic.FindPageList(0, 10);
            showlist(plist);

            /*
             * select count(1) from demo where name='wangjun' order by id desc;
             * auto changge 'id,userName' to 'count(1)'
             */
            System.out.println(logic.Cast().setfileds("id,userName")
                    .order("id").where("userName", "wangjun")
                    .getCount());

            /* or count(id) we don't change to count(1) */
            System.out.println(logic.Cast().setfileds("count(id)")
                    .order("id").where("userName", "wangjun")
                    .getCount());

            /*auto  add and */
            System.out.println(logic.Cast().setfileds("count(id)")
                    .order("id").where("userName", "wangjun")
                    .where("and LENGTH(id)>=2")
                    .where("LENGTH(id)>=2").getCount());
        }

        {
            /* execute with nomarl sql */
            DataAction action = new DataAction();
            /* select into List<E> */
            list = action.getList(demoModel.class,
                    "select * from demo  limit 2,3");
            showlist(list);
            /* delete without params */
            action.excute("delete from demo where id =1");

            /* insert with params */
            Object[] obj = new Object[2];
            obj[0] = 1;
            obj[1] = "123jdhfjh";
            action.excute(
                    "insert into demo (id,username)values(?,?)", obj);

        }

        System.out.println(plist.getTotalCount());
        renderJSON(plist);
    }
    private static void showlist(List<demoModel> list) {
        for (demoModel demoModel : list) {
            System.out.println(demoModel.getId() + ":"
                    + demoModel.getUserName());
        }

    }

}