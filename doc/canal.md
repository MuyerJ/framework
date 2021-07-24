### canal安装
```
【canal.properties】
##################################################
#########                    MQ                      #############
##################################################
#canal.mq.servers = 10.255.xx.xx:9092,10.255.xx.xx:9092,10.255.xx.xx:9092
canal.mq.servers = 172.25.0.229:9876
canal.mq.retries = 3
canal.mq.producerGroup = tableChangeGroup



【instance.properties】

canal.instance.mysql.slaveId=1
canal.instance.master.address=92abdb20ca404d1cab6c4eaf5c83a449in01.internal.cn-east-2.mysql.rds.myhuaweicloud.com:3306


# username/password
canal.instance.dbUsername=dipao
canal.instance.dbPassword=dipao123
canal.instance.connectionCharset = UTF-8


# table regex
#canal.instance.filter.regex=.*\\..*
canal.instance.defaultDatabaseName = db_dipao
#canal.instance.filter.regex= db_dipao.t_driver,db_dipao.t_driver_team,db_dipao.t_main_order,db_dipao.t_waybill
canal.instance.filter.regex= db_dipao.t_driver,db_dipao.t_driver_team,db_dipao.t_main_order,db_dipao.t_waybill,db_dipao.t_start_warehouse,db_dipao.t_receive_truck_network

# table black regexi
canal.instance.filter.black.regex=
# table field filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)
#canal.instance.filter.field=test1.t_product:id/subject/keywords,test2.t_company:id/name/contac
#canal.instance.filter.field=dipao.t_driver:approval_flag/deposit_status/grab_ranking/often_run_city/drive_licence_type,dipao.t_driver_team:driver_id/captain_flag,dipao.t_main_order:order_status


# mq config
canal.mq.topic=tableChange
# dynamic topic route by schema or table regex
#canal.mq.dynamicTopic=mytest1.user,mytest2\\..*,.*\\..*
canal.mq.partition=0
# hash partition config
#canal.mq.partitionsNum=3
#canal.mq.partitionHash=test.table:id^name,.*\\..*
#################################################

```