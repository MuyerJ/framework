package com.muyer.util;

/**
 * Description: 
 * date: 2021/4/17 23:25
 * @author YeJiang
 * @version
 */
public class CanalUtil {
//    public static void main(String[] args) throws InterruptedException {
//
//        // 第一步：与canal进行连接
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("47.110.129.240", 11111),
//                "example", "", "");
//        connector.connect();
//
//        // 第二步：开启订阅
//        connector.subscribe();
//
//        // 第三步：循环订阅
//        while (true) {
//            try {
//                // 每次读取 1000 条
//                Message message = connector.getWithoutAck(1000);
//
//                long batchID = message.getId();
//
//                int size = message.getEntries().size();
//
//                if (batchID == -1 || size == 0) {
//                    System.out.println("当前暂时没有数据");
//                    Thread.sleep(1000); // 没有数据
//                } else {
//                    System.out.println("-------------------------- 有数据啦 -----------------------");
//                    parse(message.getEntries());
//                }
//
//                // position id ack （方便处理下一条）
//                connector.ack(batchID);
//
//            } catch (Exception e) {
//                // TODO: handle exception
//
//            } finally {
//                Thread.sleep(1000);
//            }
//        }
//    }
//
//    // 获取每条打印的记录
//    @SuppressWarnings("static-access")
//    public static void PrintEntry(List<Entry> entrys) {
//
//        for (Entry entry : entrys) {
//
//            // 第一步：拆解entry 实体
//            Header header = entry.getHeader();
//            EntryType entryType = entry.getEntryType();
//
//            // 第二步： 如果当前是RowData，那就是我需要的数据
//            if (entryType == EntryType.ROWDATA) {
//
//                String tableName = header.getTableName();
//                String schemaName = header.getSchemaName();
//
//                RowChange rowChange = null;
//
//                try {
//                    rowChange = RowChange.parseFrom(entry.getStoreValue());
//                } catch (InvalidProtocolBufferException e) {
//                    e.printStackTrace();
//                }
//
//                EventType eventType = rowChange.getEventType();
//
//                System.out.println(String.format("当前正在操作 %s.%s， Action= %s", schemaName, tableName, eventType));
//
//
//                // 如果是‘查询’ 或者 是 ‘DDL’ 操作，那么sql直接打出来
//                if (eventType == EventType.QUERY || rowChange.getIsDdl()) {
//                    System.out.println("rowchange sql ----->" + rowChange.getSql());
//                    return;
//                }
//
//                // 第三步：追踪到 columns 级别
//                rowChange.getRowDatasList().forEach((rowData) -> {
//
//                    // 获取更新之前的column情况
//                    List<Column> beforeColumns = rowData.getBeforeColumnsList();
//
//                    // 获取更新之后的 column 情况
//                    List<Column> afterColumns = rowData.getAfterColumnsList();
//
//                    // 当前执行的是 删除操作
//                    if (eventType == EventType.DELETE) {
//                        PrintColumn(beforeColumns);
//                    }
//
//                    // 当前执行的是 插入操作
//                    if (eventType == eventType.INSERT) {
//                        PrintColumn(afterColumns);
//                    }
//
//                    // 当前执行的是 更新操作
//                    if (eventType == eventType.UPDATE) {
//                        PrintColumn(afterColumns);
//                    }
//                });
//            }
//        }
//    }
//
//    // 每个row上面的每一个column 的更改情况
//    public static void PrintColumn(List<Column> columns) {
//
//        columns.forEach((column) -> {
//
//            String columnName = column.getName();
//            String columnValue = column.getValue();
//            String columnType = column.getMysqlType();
//            boolean isUpdated = column.getUpdated(); // 判断 该字段是否更新
//
//            System.out.println(String.format("columnName=%s, columnValue=%s, columnType=%s, isUpdated=%s", columnName,
//                    columnValue, columnType, isUpdated));
//
//        });
//
//    }
}
