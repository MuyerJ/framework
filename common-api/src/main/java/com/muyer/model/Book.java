package com.muyer.model;

/**
 * Description: <br/>
 * date: 2020/8/10 21:53<br/>
 *
 * @author MuyerJ<br   />
 */
public class Book {
    private String id;
    private String name;
    private String dbName;

    public Book() {
    }

    public Book(String id, String name, String dbName) {
        this.id = id;
        this.name = name;
        this.dbName = dbName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
