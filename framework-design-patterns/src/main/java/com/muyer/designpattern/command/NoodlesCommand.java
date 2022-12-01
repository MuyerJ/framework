package com.muyer.designpattern.command;

/**
 * Description: 
 * date: 2022/1/10 23:03
 * @author YeJiang
 * @version
 */
public class NoodlesCommand implements Command {
    private NoodlesChef chef;

    public NoodlesCommand() {
        this.chef = new NoodlesChef();
    }

    public NoodlesCommand(NoodlesChef chef) {
        this.chef = chef;
    }

    public void execute() {
        chef.cook();
    }
}
