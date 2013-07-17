使用了dbutils1.5,数据库暂时支持mysql.

使用说明：
  Dao继承于BaseDao,Dto继承于BaseDto，IDao继承于IRepository(方便使用google.guicy);
  Dto需要实现BaseDto中的toMap()与getTbName(); 
<pre> 
         
         public class demoModel extends BaseDto {
         	public demoModel()
         	{
         		this.setTbName("demo");
         	}
         	@Override
         	public Map<String, String> toMap() 
         	{
         		Map<String, String> map = new HashMap<String, String>();
         		......
         		return map;
         	}
         }

</pre>
  Dao的实现比较简单：
<pre>  

        public class demoDto extends BaseDao<demoModel> {
 
}
 </pre>
 以下为实现的一些逻辑。
<pre> 

        demoModel model = new demoModel();
        demoDto Dto = new demoDto();    
        /* 新增 */
         Random random = new Random();
         model.setUserName("demo" + random.nextInt());
         model.setUserPwd("123456");
         int reslut = Dto.Add(model);
         System.out.println(reslut);
        /* 修改 */
         Random random = new Random();
         model.setUserName("update" + random.nextInt());
         model.setId(10);
         Dto.Save(model);
         /* 删除 */
         model.setId(1);
         Dto.Remove(model);
	 
</pre>
以往分页比较麻烦，现加入了PageList，内部实现了List<T>与count的。
<pre> 

        PageList<demoModel> plist = null;
        List<demoModel> list = null;
        {
            DataAction action = new DataAction();
            action.setTable(model).setfileds("*");
            /* 对in进行适当的优化,一条数据的时候自动会进入= */
            action.where("userName", "12", RelationEnum.In);
            action.where("userName", "wan", RelationEnum.LikeLeft);
            action.where("userName", "an", RelationEnum.Like);
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
             * 自动转换 'id,userName' 至'count(1)'
             */
            System.out.println(logic.Cast().setfileds("id,userName")
                    .order("id").where("userName", "wangjun")
                    .getCount());

            /* 执行count的结果 */
            System.out.println(logic.Cast().setfileds("count(id)")
                    .order("id").where("userName", "wangjun")
                    .getCount());

            /*自动判断是否加入and的条件 */
            System.out.println(logic.Cast().setfileds("count(id)")
                    .order("id").where("userName", "wangjun")
                    .where("and LENGTH(id)>=2")
                    .where("LENGTH(id)>=2").getCount());
        }
		
</pre>

如果执行简单的sql
<pre> 

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

</pre>


