package hellotvxlet;

import java.util.TimerTask;

public class MyTimeTask extends TimerTask {
    HelloTVXlet htv;
    boolean pause = true;
    
    public MyTimeTask(HelloTVXlet htv){
        this.htv = htv;
    }
    
    public void run(){
        if(!pause) htv.run();
    }
}