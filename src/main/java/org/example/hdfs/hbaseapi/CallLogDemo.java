package org.example.hdfs.hbaseapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CallLogDemo {
    //命名空间的定义
    private String nameSpace = "itbaizhan";
    //表名称
    private String tableName = "phone_log";
    //表名称对应的TableName对象
    private TableName tableNameObj;
    //表DDL对象
    private Admin admin;
    //表数据的DML对象
    private Table table;
    //连接对象
    private Connection connection;

    @Before
    public void before(){
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","node2,node3,node4");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
            tableNameObj = TableName.valueOf(nameSpace+":"+tableName);
            table = connection.getTable(tableNameObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void after() throws IOException {
        if (admin != null) {
            admin.close();
        }
        if (table != null){
            table.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    private String family = "basic";
    //定义列族名

    @Test
    public void createTable() throws IOException {
        //1.定义命名空间描述器
        NamespaceDescriptor build = NamespaceDescriptor.create(nameSpace).build();
        try {
            //2.获取命名空间，如果存在则不抛出异常
            admin.getNamespaceDescriptor(nameSpace);
        } catch (IOException e) {
            //3.出现异常说明命名空间不存在
            admin.createNamespace(build);
        }
        //4.创建表描述器的Builder对象
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableNameObj);

        //5.创建列族的描述器对象
        ColumnFamilyDescriptor familyDescriptor= ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(family)).build();

        //6.将列族描述器添加到表描述器的Builder对象中
        tableDescriptorBuilder.setColumnFamily(familyDescriptor);

        //7.将表描述器的Builder对象转换为表描述器对象
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();

        //8.判断表是否存在
        if (admin.tableExists(tableNameObj)) {
            admin.disableTable(tableNameObj);
            admin.deleteTable(tableNameObj);
        }

        //10.创建表
        admin.createTable(tableDescriptor);

    }


    /**生成10个用户的在某一年内的通话记录，每个用户产生1000
     条通话记录
     * dnum:对方手机号码 type:呼叫类型 0主叫 1表示被叫
     length：通话时长   date:时间
     */
    private Random random = new Random();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void insert() throws ParseException, IOException {
        //1.定义一个List<Put>
        List<Put> putList = new ArrayList<>();
        //2.循环10次，模拟10个用户
        for (int i = 0; i < 10; i++) {
            //3.清空putList，防止上次操作的影响
            putList.clear();

            String phoneNumber = getPhoneNumber("158");

            //5.模拟每个用户的1000条数据
            for (int j = 0; j < 1000; j++) {
                //cf:length=,cf:dnum=,cf:date=,cf:type= 0表示主叫 1表示被叫
                //6生成每一行通话记录的数据

                String dnum = getPhoneNumber("199");
                int length = random.nextInt(200)+1;
                int type = random.nextInt(2);
                String date = getDate(2050);
                //7.rowkey设计
                String rowkey = phoneNumber+"_" +(Long.MAX_VALUE - simpleDateFormat.parse(date).getTime())+i+j;
                //8.创建put对象
                Put put = new Put(Bytes.toBytes(rowkey));
                put.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("dnum"),Bytes.toBytes(dnum));
                put.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("length"),Bytes.toBytes(length));
                put.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("type"),Bytes.toBytes(type));
                put.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("date"),Bytes.toBytes(date));
                //9将put对象添加putList   勿忘我
                putList.add(put);
            }
            //10.将当前用户的1000条通话记录提交
            table.put(putList);
        }
    }

    private String getDate(int year){
        Calendar calendar = Calendar.getInstance();

        //设置时间
        calendar.set(year,0,1);

        calendar.add(Calendar.MONTH,random.nextInt(12));
        calendar.add(Calendar.DAY_OF_MONTH,random.nextInt(31));
        calendar.add(Calendar.HOUR_OF_DAY,random.nextInt(12));
        Date time = calendar.getTime();
        return simpleDateFormat.format(time);
    }

    /**生成一个手机号码
     * @param prefix:手机号码的前三位
     * @return 生成的手机号码
     */
    private String getPhoneNumber(String prefix){
        return prefix+String.format("%08d",random.nextInt(99999999));
    }


    //查询某用户三月份的童通话记录
    @Test
    public void scanData() throws ParseException, IOException {
        //1.定义某用户的手机号码
        String phoneNumber = "15843658938";
        //2.定义startRow 包含
        String startRow = phoneNumber+"_"+(Long.MAX_VALUE-simpleDateFormat.parse("2050-04-01 00:00:00").getTime());
        //3.定义stopRow 包含
        String stopRow = phoneNumber+"_"+(Long.MAX_VALUE-simpleDateFormat.parse("2050-03-01 00:00:00").getTime());
        //4.创建Scan对象
        Scan scan = new Scan();

        //5.设置起始和结束行
        scan.withStartRow(Bytes.toBytes(startRow));
        scan.withStopRow(Bytes.toBytes(stopRow),true);

        //6.执行查询
        ResultScanner resultScanner = table.getScanner(scan);
        //7.解析resultScanner
        for (Result result: resultScanner
             ) {
            //8.解析result
            Cell[] cells = result.rawCells();
            String rowInfo = "rowkey:"+Bytes.toString(CellUtil.cloneRow(cells[0]));
            rowInfo +=","+Bytes.toString(CellUtil.cloneQualifier(cells[0]))
                    +":"+Bytes.toString(CellUtil.cloneValue(cells[0]));
            rowInfo +=","+Bytes.toString(CellUtil.cloneQualifier(cells[1]))
                    +":"+Bytes.toString(CellUtil.cloneValue(cells[1]));
            rowInfo +=","+Bytes.toString(CellUtil.cloneQualifier(cells[2]))
                    +":"+Bytes.toInt(CellUtil.cloneValue(cells[2]));
            rowInfo +=","+Bytes.toString(CellUtil.cloneQualifier(cells[3]))
                    +":"+Bytes.toInt(CellUtil.cloneValue(cells[3])
            );
            System.out.println(rowInfo);
        }
    }

    @Test
    public void deleteCell() throws IOException {
        Delete delete = new Delete(Bytes.toBytes("15843658938_92233695052026378076727"));

        //指定具体的咧
        delete.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("length"));
        //执行删除操作
        table.delete(delete);
    }

    @Test
    public void insertCell() throws IOException {
        Put put = new Put(Bytes.toBytes("15843658938_92233695052026378076727"));
        put.addColumn(Bytes.toBytes("basic"),Bytes.toBytes("length"),Bytes.toBytes("90"));
        table.put(put);
    }

    @Test
    public void deleteRow() throws IOException {
        Delete delete = new Delete(Bytes.toBytes("15843658938_92233695052026378076727"));
        table.delete(delete);
    }


}
