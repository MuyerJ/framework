package com.muyer.designpattern.command;

/**
 * Description: 
 * date: 2022/1/10 23:05
 * @author YeJiang
 * @version
 */
public class Main {

    public static void main(String[] args) {
        //需要一碗面条
        NoodlesCommand noodlesCommand = new NoodlesCommand();
        noodlesCommand.execute();

        //需要一个饼子
        PieCommand pieCommand = new PieCommand();
        pieCommand.execute();
    }
}
