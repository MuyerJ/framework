### 1.导入maven

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.2.1</version>
</dependency>
```

### 2.创建dto
```java

@ExcelProperty(value = "叶")
value ==> Excel中列的名字
```

### 3.读取

```java
List<ExcelDto2> excel = EasyExcel.read(new File("E:\\交易记录.xlsx"))
                                    .autoTrim(true)
                                    .head(ExcelDto2.class)
                                    .sheet()
                                    .doReadSync();
```