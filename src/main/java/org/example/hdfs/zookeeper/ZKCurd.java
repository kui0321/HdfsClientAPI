package org.example.hdfs.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZKCurd {
    //定义会话的超时时间
    private final static int SESSION_TIME = 30000;
    //定义zk集群的ip地址
    private final static String ZK_SERVERS = "node2:2181,node3:2181,node4:2181";
    //日志对象
    private final static Logger LOGGER = Logger.getLogger(ZKCurd.class);

    private  ZooKeeper zkCli = null;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            LOGGER.info("event"+watchedEvent.toString());
        }
    };

    @Before
    public void connect() throws IOException{
        zkCli = new ZooKeeper(ZK_SERVERS,SESSION_TIME,watcher);
        //获取当前会话的sessionID
        long sessionId = zkCli.getSessionId();
        LOGGER.info("sessionId"+sessionId);
        System.out.println("zkCli:"+zkCli);
    }

    @After
    public void close() throws InterruptedException{
        zkCli.close();
    }

    /**创建节点
     *final String path:要创建节点的全路径
     * byte data[]:节点中数据内容
     * List<ACL> acl：节点权限
     * CreateMode createMode:节点类型
     * PERSISTENT:普通持久几点
     * PERSISTENT_SEQUENTIAL：顺序持久节点
     * EPHEMERAL：普通临时节点
     * EPHEMERAL_SEQUENTIAL：顺序临时节点
     */
    @Test
    public  void create(){
        String result = "";
        String result1 = "";
        try {
            result1 = zkCli.create("/zk001","goods".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            result = zkCli.create("/zk002","goods".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        LOGGER.info("create result:{"+result+"}");
        LOGGER.info("create result:{"+result1+"}");
    }

    @Test
    /**
     * 判断节点是否存在
     */
    public void exists(){
        try{
            Stat stat = zkCli.exists("/zk001",false);
            if (stat == null){
                System.out.println("zk001 not exists");
            }else {
                System.out.println("zk001 exists");
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 查询节点值
     *
     */
    @Test
    public void getData(){
        String result = null;
        try{
            byte[] data = zkCli.getData("/zk001",null,null);
            result = new String(data);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("getData result" + result);
    }

    /**
     * 查询节点状态
     *
     */
    @Test
    public void getStatus(){
        String result = null;
        Stat stat = new Stat();
        try{
            byte[] data = zkCli.getData("/zk001",null,stat);
            result = new String(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
        //LOGGER.info("stat"+stat);
        LOGGER.info("status"+result);
    }

    /**
     * 获取子节点信息
     */
    @Test
    public void getChildren(){
        try{
            List<String> childrenList = zkCli.getChildren("/",true);
            for (String children:childrenList
                 ) {
                LOGGER.info("child"+children);
                System.out.println(children);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 遍历子节点信息
     */
    @Test
    public void getChildrenList(){
        try{
            List<String> chlidrenList = zkCli.getChildren("/",true);
            String data = null;
            for (String children: chlidrenList
                 ) {
                data = new String(zkCli.getData(children,null,null));
                LOGGER.info(children+data);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改节点
     */
    @Test
    public void setData(){
        Stat stat = null;
        try {
            //path：被修改节点的全路径，第二个参数：修改后的value，version：-1表示不管version的值为几都可以修改
            stat = zkCli.setData("/zk001","huanxuanyu".getBytes(),-1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("version"+stat.getAversion());
        System.out.println(stat.getAversion());
    }

    /**
     * 删除节点
     */
    @Test
    public void delete(){
        try {
            zkCli.delete("/zk001",-1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证注册事件被触发的一致性
     */
    @Test
    public void getDataWatchCher(){
        String result = null;
        System.out.println("get:");
        try {
            byte[] data = zkCli.getData("/zk001", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    LOGGER.info(watchedEvent.getType());
                    System.out.println(watchedEvent.getType());
                }
            },null);
            result = new String(data);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
        System.out.println(result);
        try {
            System.out.println("-------set1------");
            zkCli.setData("/zk001","hell0".getBytes(),-1);
            System.out.println("-------set2------");
            zkCli.setData("/zk001","hello2".getBytes(),-1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        System.out.println("test over");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        ZKCurd curd = new ZKCurd();
//        curd.create();
//    }
}
