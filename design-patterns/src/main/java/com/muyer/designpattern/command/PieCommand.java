package com.muyer.designpattern.command;

/**
 * Description: 
 * date: 2022/1/10 23:04
 * @author YeJiang
 * @version
 */
public class PieCommand implements Command {
    private PieChef chef;

    public PieCommand() {
        this.chef = new PieChef();
    }

    public PieCommand(PieChef chef) {
        this.chef = chef;
    }

    public void execute() {
        chef.cook();
    }
}
