package org.example.hdfs.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSDeleteFolder {

    public void HdfsDelete(Configuration configuration) throws URISyntaxException, IOException, InterruptedException {
        //创建配置对象
        configuration = new Configuration();
        // 获取文件系统对象
        FileSystem fs = FileSystem.get(configuration);

        // 指定要删除的目录路径
        Path folderPath = new Path("/wordcount/output");

        // 判断目标文件夹是否存在
        if (fs.exists(folderPath)) {
            // 递归删除目录及其内容
            boolean result = fs.delete(folderPath, true);

            if (result) {
                System.out.println("成功删除文件夹！");
            } else {
                System.err.println("删除文件夹失败！");
            }
        } else {
            System.err.println("目标文件夹不存在！");
        }
        fs.close();
    }
}
