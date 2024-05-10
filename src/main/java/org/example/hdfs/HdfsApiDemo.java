package org.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsApiDemo {

    @Test
    public static void mkdirs() throws IOException{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.指定NameNode
        configuration.set("fs.defaultFS","hdfs://node1:9820");
        //3.创建文件系统对象
        FileSystem fileSystem = FileSystem.get(configuration);
        //4.调用创建目录的API方法
        fileSystem.mkdirs(new Path("/aip/show"));
        //5.勿忘我：关闭文件系统对象
        fileSystem.close();
    }

    @Test
    public static void mkdir1() throws IOException, URISyntaxException,InterruptedException {
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.创建文件系统对象,指定URI,conf,userName
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        //3.调用创建目录的API方法
        fileSystem.mkdirs(new Path("/hdfsapi/show"));
        //4.关闭文件系统对象
        fileSystem.close();
    }

    /**
     * 上传文件
     * @throws Exception
     */
    @Test
    public static void uploadFIle() throws Exception{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.创建文件系统对象,指定URI,conf,userName
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration, "root");
        //3.创建本地输入流对象
        FileInputStream fileInputStream = new FileInputStream("D:\\hh.txt");
        //4.创建HDFS文件系统的输出流对象
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/api/show/hh.txt"));
        //5.创建字节数组，临时存储中间缓存数据
        byte[] data = new byte[1024];
        int len  = -1;
        while ((len = fileInputStream.read(data))!= -1){
            fsDataOutputStream.write(data,0,len);
        }
        //6.关闭输入流对象
        fileInputStream.close();
        //7.刷新输出流中的数据
        fsDataOutputStream.flush();
        //8.关闭输出流
        fsDataOutputStream.close();
        //9.断开和HDFS之间的连接
        fileSystem.close();
    }

    @Test
    /**
     * 上传文件
     */
    public static void copyFromLoaclFile() throws Exception{
        //1.创建文件系统对象
        Configuration configuration = new Configuration();
        //2.设置block大小为1MB，上传时候使用，仅对当前方法有效
        configuration.set("dfs.blocksize","1048576");
        //3.设置副本数
        configuration.set("dfs.blocksize","1048576");
        //4.创建文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        //5.上传文件
        fileSystem.copyFromLocalFile(new Path("D:\\hh.txt"),new Path("/api/show/hh.txt"));
        //6.关闭资源
        fileSystem.close();
    }

    @Test
    public static void rename() throws  Exception{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.生成文件系统对象
        FileSystem fileSystem  = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        //3.修改名称:相同路径下
        //fileSystem.rename(new Path("/api/show/hh.txt"),new Path("/api/show/hh_new.txt"));
        //3.移动文件：不同路径下
        fileSystem.rename(new Path("/api/show/hh_new.txt"),new Path("/api/hh_new.txt"));
        //4.关闭
        fileSystem.close();
    }

    @Test
    /**
     * hdfs 文件的下载
     */
    public static void copyToLocal() throws  Exception{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.生成文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        //3.调用下载API方法
        /**3.调用下载API方法
         * delSrc – whether to delete the src 是否原
         文件，false不删除，true表示删除原文件
         * src – path：被下载文件的全路径名对应的Path类
         的对象
         * dst – path：文件下载到的目标全路径名对应的
         Path类的对象
         * useRawLocalFileSystem – 是否开启文件校验.
         */
        fileSystem.copyToLocalFile(false,new Path("/api/hh_new.txt"),new Path("D:\\hh_new.txt"),true);
        //4.关闭资源
        fileSystem.close();

    }


    @Test
    /**
     * 文件删除
     */
    public static void delete() throws Exception{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.获取文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        /**3.调用删除方法
         *Path f :被删除的文件或目录对应的Path类的对象
         *boolean recursive:
         *     false: 删除目录如果是一个非空的目录，抛出以下异常：
         *     true:表示递归删除子文件或目录
         */
        fileSystem.delete(new Path("/hdfsapi/show"),false);
        //4.关闭资源
        fileSystem.close();
    }


    /**
     * 获取指定文件的详情
     * @throws Exception
     */
    public static void getFileInfo() throws Exception{
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.获取文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        //3.获取指定文件的详细信息的对象
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/api/hh_new.txt"));
        //4.从fileStatus获取文件的信息
        String name = fileStatus.getPath().getName() + "\n" +
                fileStatus.getOwner()+"\n" +
                fileStatus.getGroup()+"\n"+
                fileStatus.getBlockSize()+"\n"+
                fileStatus.getLen()+"\n"+
                fileStatus.getPermission()+"\n"+
                fileStatus.getReplication();
        System.out.println(name);
        //关闭资源
        fileSystem.close();
    }

    @Test
    /**
     * 文件和目录的判断
     */
    public static void fileOrDir() throws Exception{

        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.获取文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration, "root");
        //3.获取指定目录下的子文件或子目录的信息
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        //4.遍历数组
        for (FileStatus filestatus: fileStatuses) {
            if (filestatus.isFile()){
                System.out.println(filestatus.getPath().getName()+"is file");
            } else if (filestatus.isDirectory()) {
                System.out.println(filestatus.getPath().getName()+"is directory");
            } else {
                System.out.println(filestatus.getPath().getName()+"is other");
            }

        }
        //5.关闭资源
        fileSystem.close();
    }


    /**
     * v获取指定目录下文件详细
     */
    @Test
    public static void listFiles() throws Exception {
        //1.创建配置文件对象
        Configuration configuration = new Configuration();
        //2.获取文件系统对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:9820"),configuration,"root");
        /**3.获取指定目录下文件列表信息
         *Path f:指定目录对应的Path类的对象
         *boolean recursive：
         *   true表示迭代“后代级别的文件“
         *   false表示仅仅获取指定目录下的文件
         */
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/api"),true);
        //4.遍历迭代器
        while (listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPath().getName() +"\n"+
                    fileStatus.getLen()+"\n"+
                    fileStatus.getOwner()+"\n"+
                    fileStatus.getGroup()+"\n"+
                    fileStatus.getPermission()+"\n");
            //5.获取文件块s信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            //6.遍历文件块
            for (BlockLocation blockLocation:blockLocations
                 ) {
                System.out.println(blockLocation.getOffset());
                String[] hosts = blockLocation.getHosts();
                System.out.println("==================host start==============");
                for (String host:hosts){
                    System.out.println(host);
                }
                System.out.println("==================host end=======================");
                String[] names = blockLocation.getNames();

                System.out.println(names.toString());

                System.out.println("======================================");
            }


        }



        //6.遍历文件块
    }

    public static void main(String[] args) throws Exception {
        listFiles();
    }
}
