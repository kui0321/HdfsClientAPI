package org.example.hdfs.hbaseapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

public class HBaseApiDemo {
    //声明一个静态HBase的连接对象
    private static Connection connection;
    //通过静态代码块创建创建出来连接对象
    static {
        //创建配置文件对象
        Configuration configuration = HBaseConfiguration.create();
        //设置zookeeper集群
        configuration.set("hbase.zookeeper.quorum","node2,node3,node4");
        //创建连接对象
        try{
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *创建命名空间
     * @param namespace 命名空间的名称
     * @throws IOException
     */
    public static void createNameSpace(String namespace) throws IOException {
        //1.获取HBaseAdmin对象：对命名空间、表的CRUD操作使用
        Admin admin = connection.getAdmin();
        //2.创建命名空间的描述器对象
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
        //3.创建命名空间
        try {
            admin.createNamespace(namespaceDescriptor);
            System.out.println("命名空间创建完成");

        }catch (NamespaceExistException e){
            System.out.println(""+namespace+"已存在");
        }
        //查看所有的命名空间
        NamespaceDescriptor[] namespaces = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor names : namespaces
        ) {
            System.out.println(names.getName());
        }
        //4.关闭
        admin.close();
        connection.close();
    }

    /**
     * 删除命名空间对象
     * @param namespace 命名空间对象
     * @throws IOException
     */
    public static void deleteNamespace(String namespace) throws IOException {
        //1.参数的合法性校验
        if (namespace == null || namespace.trim().length() == 0){
            System.out.println("命名空间不能为空");
        }
        //2.声明Admin
        Admin admin = null;
        try{
            //3.创建HbaseAdmin对象
            admin = connection.getAdmin();
            //4.删除命名空间
            admin.deleteNamespace(namespace);
        } catch (IOException e) {
            System.out.println("命名空间不存在");
            throw new RuntimeException(e);
        }
        listNamespace();
        admin.close();
        connection.close();
    }

    /**
     * 查看所有命名空间
     */
    public static void listNamespace(){
        //声明Admin对象
        try {
            Admin admin = connection.getAdmin();
            NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
            for (NamespaceDescriptor namespaces : namespaceDescriptors
                 ) {
                System.out.println("所有的命名空间如下：\n"+namespaces);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断表是否存在，如果纯在则返回ture
     * @param tableName
     * @return
     */
    public static boolean tableExists(String tableName){
        //1.声明Admin对象
        Admin admin = null;
        try {
            //2.通过connection对象回去HBaseAdmin对象
            admin = connection.getAdmin();
            //将String类型的TableName转化成Tablename对象
            TableName tableNameobj = TableName.valueOf(tableName);

            return admin.tableExists(tableNameobj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建Hbase数据表
     * @param tableName 表名称
     * @param cfs  列族信息
     * @throws IOException
     */
    public static void createTable(String tableName, String... cfs) throws IOException {
        //1.判断表名的是否为空
        if (tableName == null || "".equals(tableName.trim())) {
            System.out.println("表明不能为空");
        }
        //2.验证列族信息的合法性
        if (cfs == null || cfs.length <= 0) {
            System.out.println("至少有一个列族");
        }
        //3.将字符串类型的表名转为TableName对象
        TableName tableNameObj = TableName.valueOf(tableName);

        Admin admin = connection.getAdmin();

        //5.判断表是否存在
        boolean flag = tableExists(tableName);
        if (flag) {
            System.out.println("表不存在");
            return;
        }

        //6.创建表描述器对象
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableNameObj);

        //7.遍历cfs，设置列族信息
        for (String cf: cfs
             ) {
            //8.使用列族名称，构造ColumnFamilyDescriptorBuilder对象
            ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
            //9.将列族信息添加到表描述器对象
            tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptorBuilder.build());
        }

        //10.tableDescriptorBuilder构造成TableDescriptor
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();

        //创建表
        admin.createTable(tableDescriptor);

        //关闭资源
        admin.close();
        connection.close();
    }


    /**
     * 判断一个表是否存在(重载tableExists)
     * @param tableName  表名称
     * @param CloseConn 是否关闭connection对象，true表示关闭，false 表示不关闭
     * @return
     */
    public static boolean tableExists(String tableName, boolean CloseConn) {
        //1.声明Admin对象
        Admin admin = null;
        //2.通过connection对象回去HBaseAdmin对象
        try {
            admin = connection.getAdmin();
            //3.将String类型tableName转换为TableName类的对象
            TableName tableNameObj = TableName.valueOf(tableName);
            //4.判断一个表是否存在
            return admin.tableExists(tableNameObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (CloseConn && connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * 删除指定的表
     * @param tableName 表名称
     */
    public static void deleteTable(String tableName) throws IOException {
        //1.校验表名的合法性
        if (tableName == null || "".equals(tableName.trim())) {
            System.out.println("表名不能为空");
        }
        //2.判断表是否存在,重载方法tableExist()
        if (!tableExists(tableName,false)) {
            System.out.println("指定表已经不存在");
        }
        //3.获取Admin
        Admin admin = connection.getAdmin();
        //4.将String类型的tableName转换为TableName类型的对象
        TableName tableNameObj = TableName.valueOf(tableName);
        //5.禁用表
        admin.disableTable(tableNameObj);
        //6.删除表
        admin.deleteTable(tableNameObj);
        //7.关闭资源
        admin.close();
        connection.close();
    }

    /**
     * 添加数据
     * @param tableName 表名称
     * @param cf 列族名称
     * @param cd 列族描述
     * @param rowKey 数据的唯一表示rowkey
     * @param value 值
     */
    public static void putCellData(String tableName,String cf, String cd, byte[] rowKey, byte[] value) throws IOException {
        //2.将String类型的tableName转换为TableName类型的对象
        TableName tableNameObj = TableName.valueOf(tableName);
        //3.通过connection对象获取HTable对象，对数据的CRUD都是通过该对象完成
        Table table = connection.getTable(tableNameObj);
        //4.创建Put对象
        Put put = new Put(rowKey);
        //5.将数据封装到put对象
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cd),value);
        //6.添加数据
        table.put(put);
        //7.关闭资源
        table.close();
        connection.close();
    }

    /**
     * get查询一个单元格中的数据
     * @param tableName  表名称
     * @param rowKey 数据的唯一表示
     * @param family 列族名称
     * @param cd 列描述名称
     * @throws IOException
     */
    public static void getData(String tableName, String rowKey,String family,String cd) throws IOException {
        //2.获取HTable对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //3.创建Get对象
        Get get = new Get(Bytes.toBytes(rowKey));
        //4.指定查询的列族，不指定的话获取rowkey对应这条数据的所有列族下的数据
        get.addFamily(Bytes.toBytes(family));
        //5.指定列描述符,不设置该值则查询对应列族下的所有列的数据
        get.addColumn(Bytes.toBytes(family),Bytes.toBytes(cd));
        //6.执行查询
        Result result = table.get(get);
        //7.解析result对象
        for (Cell cell: result.rawCells()
             ) {
            System.out.println("row:"+Bytes.toString(CellUtil.cloneRow(cell))+
                    ",cf:"+Bytes.toString(CellUtil.cloneFamily(cell))+
                    ",cq:"+Bytes.toString(CellUtil.cloneQualifier(cell))+
                    ",value:"+Bytes.toString(CellUtil.cloneValue(cell))
            );
        }
        //8.关闭资源
        table.close();
        connection.close();
    }

    /**
     * scan 扫描表数据
     * @param tableName 表名称
     * @param startRow 起始row 包含
     * @param stopRow  结束row 不包含
     */
    public static void scanTableData(String tableName, String startRow, String stopRow) throws IOException {
        //2.获取table对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //3.创建Scan对象
        Scan scan = new Scan();
        //4.设置查询的范围
        //包含startRow对应的数据
        scan.withStartRow(Bytes.toBytes(startRow));
        //不包含stopRow
        scan.withStartRow(Bytes.toBytes(stopRow),true); //true包含stoprow ，false 不包含 stoprow
        //5.执行查询操作
        ResultScanner resultScanner = table.getScanner(scan);
        //6.解析resultScanner
        for (Result result: resultScanner
             ) {
            //7.解析result对象
            for (Cell cell: result.rawCells()
                 ) {
                System.out.println(
                        "rowkey:"+Bytes.toString(CellUtil.cloneRow(cell))+
                        ",family:"+Bytes.toString(CellUtil.cloneFamily(cell))+
                        ",qualifier:"+Bytes.toString(CellUtil.cloneQualifier(cell))+
                                ",value:"+Bytes.toString(CellUtil.cloneValue(cell))
                );
            }
        }

        //8.关闭资源
        table.close();
        connection.close();

    }

    /**
     * 删除整行数据
     * @param tablename 表名称
     * @param rowKey 代表对应行数据的唯一标识
     * @throws IOException
     */
    public static void deleteData(String tablename, String rowKey) throws IOException {
        //2.获取HTable对象
        Table table = connection.getTable(TableName.valueOf(tablename));
        //3.创建删除对象
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //4.执行删除操作
        table.delete(delete);
        //5.关闭资源
        table.close();
        connection.close();
    }

    /**
     * 删除指定行的指定列族下所有的数据
     * @param tableName 表名
     * @param rowKey 代表对应行数据的唯一标识
     * @param family 列族名称
     * @throws IOException
     */
    public static void deleteData(String tableName, String rowKey,String  family) throws IOException {
        //2.获取HTable对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //3.创建删除对象
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //6.指定列族名称
        delete.addFamily(Bytes.toBytes(family));
        //4.执行删除操作
        table.delete(delete);
        //5.关闭资源
        table.close();
        connection.close();
    }

    /**
     * 删除指定行的指定列族指定列的数据
     * @param tableName 表名称
     * @param rowKey 代表对应行数据的唯一标识
     * @param family 列族名称
     * @param qualifier  列族描述
     * @throws IOException
     */
    public static void deleteData(String tableName, String rowKey,String family, String qualifier) throws IOException {
        //2.获取HTable对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //3.创建删除对象
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //6.指定列族名称
        delete.addFamily(Bytes.toBytes(family));
        //7.指定列描述符
        delete.addColumn(Bytes.toBytes(family),Bytes.toBytes(qualifier));
        //4.执行删除操作
        table.delete(delete);
        //5.关闭资源
        table.close();
        connection.close();
    }


    public static void main(String[] args) throws IOException {
        //System.out.println(connection);
        //createNameSpace("mydb1");
        //deleteNamespace("mydb1");

        //System.out.println(tableExists("test"));
        //createTable("test","cf");
        //deleteTable("tmp");
        //putCellData("test","cf","name",Bytes.toBytes("rk004"),Bytes.toBytes("shangxyet"));
        scanTableData("test","rk001", "rk002");
    }
}
