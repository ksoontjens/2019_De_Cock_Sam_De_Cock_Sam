package hellotvxlet;

import java.awt.Color;
import java.awt.event.ActionEvent;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HStaticText;
import org.havi.ui.HTextButton;
import org.havi.ui.HVisible;
import org.havi.ui.event.HActionListener;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import java.util.Timer;
import org.dvb.event.UserEvent;



public class HelloTVXlet implements Xlet, HActionListener {
    
    HScene scene;
    int width = 250;
    int height = 100;
    int number = 0;
    int questionInt = 0;
    boolean createdButtons = false;
    boolean winner = true;
    boolean hasQuestions = true;
    int count = 0;
    int[] correctAnswers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Color buttonColor = new Color(255, 68, 0);
    
    HStaticText question;
    HStaticText points = new HStaticText("", 300, 150, 100, 50);
    HStaticText endText = new HStaticText("", 300, 250, 100, 100);
    HTextButton[] knop = new HTextButton[11];
    HStaticText timer = new HStaticText("", 200, 150, 740, 100);
    MyTimeTask mtt = new MyTimeTask(this);
    
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals("game")){
            //questionInt++;
            System.out.println("Start");
            for(int i = 0; i<2; i++){
                knop[i].setVisible(false);
            }
            
            nextQuestion("Hoeveel centimeters zitten er in een meter?", "10", "100", "50", "1000");
            correctAnswers[questionInt] = 2;
            scene.remove(points);
            String pointString = "Tijd: " + String.valueOf(count);
            scene.add(points);
            points.setTextContent(pointString, HVisible.NORMAL_STATE);
         
        }
        else if (hasQuestions){
            questionInt++;
            System.out.println("Next question.");
            
            switch(questionInt)
            {
                case 1:
                    nextQuestion("5+5=?", "2", "5", "10", "100");
                    correctAnswers[questionInt] = 3;
                    break;
                case 2:
                    nextQuestion("Welke rol speelde Fred van Kuyk in de reeks Mega Mindy?", "Toby", "Niemand", "Mega Mindy", "Opa Fonkel");
                    correctAnswers[questionInt] = 4;
                    break;
                case 3:
                    nextQuestion("Wie schreef het meesterwerk 'King Lear' in het jaar 1608?", "William Shakespeare", "Julian Barnes", "Jakov Lind", "David Lodge");
                    correctAnswers[questionInt] = 1;
                    break;
                case 4:
                    nextQuestion("20*0=?", "2", "20", "5", "0");
                    correctAnswers[questionInt] = 4;
                    break;
                case 5:
                    nextQuestion("Welke typische kleur hebben de bloemblaadjes van de papaver?", "Geel", "Zwart", "Paars", "Rood");
                    correctAnswers[questionInt] = 4;
                    break;
                case 6:
                    nextQuestion("Welke Vlaamse zangeres heet in het echte leven Chantal Vanlee?", "Anouk", "Dana Winner", "Natalia", "Andy");
                    correctAnswers[questionInt] = 2;
                    break;
                case 7:
                    nextQuestion("Vul de titel van het boek van Gabriel Garcia Marquez aan: Honderd jaar ...", "Leven", "Eenzaamheid", "En nog niet dood", "Gezond");
                    correctAnswers[questionInt] = 2;
                    break;
                case 8:
                    nextQuestion("Welke vogelsoort zingt volgens vlamingen steeds 'suskewiet'?", "Vink", "Mus", "Duif", "Raaf");
                    correctAnswers[questionInt] = 1;
                    break;
                case 9:
                    nextQuestion("KBC is een fusie van twee banken: Cera en ...?", "Kredietbank", "ING", "Axa", "Fortis");
                    correctAnswers[questionInt] = 1;
                    break;
                case 10:
                    nextQuestion("Welke Amerikaanse rocker was in 2013 de hoofdact op TW Classic?", "AD/DC", "guns 'n roses", "Bruce Springsteen", "Nickelback");
                    correctAnswers[questionInt] = 3;
                    break;
                case 11:
                    System.out.println("Last question.");
                    lastQuestion("Wat is de naam van de school?", "KdG", "UGent", "UAntwerpen", "VUB");
                    correctAnswers[questionInt] = 1;
                    Timer t = new Timer();
                    t.scheduleAtFixedRate(mtt, 0, 1000);
                    mtt.pause = false;
                    hasQuestions = false;
                    break;
                default:
                    endScreen();
                    hasQuestions = false;
                    System.out.println("Something went wrong! 2");
                    break;
                 
            }
            
            if(arg0.getActionCommand().equals("sluiten")){
                System.exit(0);
            }
        }
        
        if(arg0.getActionCommand().equals("1")||arg0.getActionCommand().equals("2")||arg0.getActionCommand().equals("3")||arg0.getActionCommand().equals("4")){
            if(arg0.getActionCommand().equals(Integer.toString(correctAnswers[questionInt-1]))){
                count+=10;
                System.out.println(String.valueOf(correctAnswers[questionInt]));
            }else{
                if (count > 0){
                    count -= 5;
                }
                System.out.println(String.valueOf(correctAnswers[questionInt]));
            }
        }
        
        if(arg0.getActionCommand().equals("6")||arg0.getActionCommand().equals("7")||arg0.getActionCommand().equals("8")||arg0.getActionCommand().equals("9")){
            mtt.pause = true;
            int answer = correctAnswers[questionInt-1] + 3;
            
            if(arg0.getActionCommand().equals(Integer.toString(answer))){
                winner = true;
            }else{
                winner = false;
            }
            endScreen();
        }
        
        scene.remove(points);
        String pointString = "Tijd: " + String.valueOf(count);
        scene.add(points);
        points.setTextContent(pointString, HVisible.NORMAL_STATE);
    }
    
    public void userEventReceives(UserEvent e){
        System.out.println("Event aangekomen");
    }
    
    public void run(){
        count--;
        if (count < 0){
            winner = false;
            endScreen();
        }
        points.setTextContent("Tijd: " + String.valueOf(count), HVisible.NORMAL_STATE);
        points.setVisible(true);
        points.repaint();
    }
    
    public void endScreen(){
        System.out.println("endScreen");
        for(int i = 6; i<10; i++){
            scene.remove(knop[i]);
        }
      
        mtt.pause = true;
        scene.add(endText);
        
        if(winner){
            System.out.println("You win!");
            
            endText.setTextContent("Winner!", HVisible.NORMAL_STATE);
            endText.setBackgroundMode(HVisible.BACKGROUND_FILL);
            endText.setBackground(Color.GREEN);
            endText.setVisible(true);
            endText.repaint();
        }
        else if (!winner){
            System.out.println("You lost!");
            
            endText.setTextContent("You lost!", HVisible.NORMAL_STATE);
            endText.setBackgroundMode(HVisible.BACKGROUND_FILL);
            endText.setBackground(Color.BLACK);
            endText.setVisible(true);
            endText.repaint();
        }
        else{
            System.out.println("Something went wrong!");
            
            endText.setTextContent("Something went wrong!", HVisible.NORMAL_STATE);
            endText.setBackgroundMode(HVisible.BACKGROUND_FILL);
            endText.setBackground(Color.GRAY);
            endText.setVisible(true);
            endText.repaint();
        }
        scene.repaint();
    }
    
    public void nextQuestion(String currentQuestion, String answ1, String answ2, String answ3, String answ4){
        
        if(!createdButtons){
            question = new HStaticText("", -50, 50, 800, height);
            knop[2] = new HTextButton("", 100, 250, width, height);
            knop[3] = new HTextButton("", 350, 250, width, height);
            knop[4] = new HTextButton("", 100, 375, width, height);
            knop[5] = new HTextButton("", 350, 375, width, height);
            
            scene.add(question);
            
            for(int i = 2; i<6; i++){
                scene.add(knop[i]);
                knop[i].setBackgroundMode(HVisible.BACKGROUND_FILL);
                knop[i].setForeground(Color.WHITE);
                knop[i].setBackground(buttonColor);
            }
        
            knop[2].setActionCommand("1");
            knop[2].addHActionListener(this);
            knop[3].setActionCommand("2");
            knop[3].addHActionListener(this);
            knop[4].setActionCommand("3");
            knop[4].addHActionListener(this);
            knop[5].setActionCommand("4");
            knop[5].addHActionListener(this);
            
            createdButtons = true;
        }
        
        scene.remove(points);
        String pointString = "Tijd: " + String.valueOf(count);
        scene.add(points);
        
        question.setTextContent(currentQuestion, HVisible.NORMAL_STATE);
        
        points.setTextContent(pointString, HVisible.NORMAL_STATE);

        knop[2].setTextContent(answ1, HVisible.NORMAL_STATE);
        knop[3].setTextContent(answ2, HVisible.NORMAL_STATE);
        knop[4].setTextContent(answ3, HVisible.NORMAL_STATE);
        knop[5].setTextContent(answ4, HVisible.NORMAL_STATE);
        
        knop[2].setFocusTraversal(null, knop[4], null, knop[3]); // up, down, left, right
        knop[3].setFocusTraversal(null, knop[5], knop[2], null);
        knop[4].setFocusTraversal(knop[2], null, null, knop[5]);
        knop[5].setFocusTraversal(knop[3], null, knop[4], null);
        
        knop[2].requestFocus();
        scene.repaint();
    }
    
    public void lastQuestion(String currentQuestion, String answ1, String answ2, String answ3, String answ4){
        
       for (int i = 2; i<6; i++){
            scene.remove(knop[i]);
       }

        knop[6] = new HTextButton("", 100, 250, width, height);
        knop[7] = new HTextButton("", 350, 250, width, height);
        knop[8] = new HTextButton("", 100, 375, width, height);
        knop[9] = new HTextButton("", 350, 375, width, height);
            
        for (int i = 6; i<10; i++){
            scene.add(knop[i]);
            knop[i].setBackgroundMode(HVisible.BACKGROUND_FILL);
            knop[i].setForeground(Color.WHITE);
            knop[i].setBackground(buttonColor);
        }
        
        knop[6].setActionCommand("6");
        knop[6].addHActionListener(this);
        knop[7].setActionCommand("7");
        knop[7].addHActionListener(this);
        knop[8].setActionCommand("8");
        knop[8].addHActionListener(this);
        knop[9].setActionCommand("9");
        knop[9].addHActionListener(this);
            
        question.setTextContent(currentQuestion, HVisible.NORMAL_STATE);
        knop[6].setTextContent(answ1, HVisible.NORMAL_STATE);
        knop[7].setTextContent(answ2, HVisible.NORMAL_STATE);
        knop[8].setTextContent(answ3, HVisible.NORMAL_STATE);
        knop[9].setTextContent(answ4, HVisible.NORMAL_STATE);
        
        knop[6].setFocusTraversal(null, knop[8], null, knop[7]); // up, down, left, right
        knop[7].setFocusTraversal(null, knop[9], knop[6], null);
        knop[8].setFocusTraversal(knop[6], null, null, knop[9]);
        knop[9].setFocusTraversal(knop[7], null, knop[8], null);
        
        knop[6].requestFocus();
        scene.repaint();
    }
    
    public HelloTVXlet() {
        
    }
    
    public void initXlet(XletContext context) {
      //resolutie = 720x576
        scene = HSceneFactory.getInstance().getDefaultHScene();
        HStaticText titel = new HStaticText("De Slimste Mens", 200, -50, 300, 200);
                                        //Tekst, x, y, b, h
        scene.add(titel);

        scene.setBackgroundMode(HVisible.BACKGROUND_FILL);
        scene.setBackground(Color.RED);
        
        knop[0] = new HTextButton("Spelen", 115, 250, width*2, height);
        knop[0].setBackgroundMode(HVisible.BACKGROUND_FILL);
        knop[0].setForeground(Color.WHITE);
        knop[0].setBackground(buttonColor);
        knop[1] = new HTextButton("Sluiten", 115, 375, width*2, height);
        knop[1].setBackgroundMode(HVisible.BACKGROUND_FILL);
        knop[1].setForeground(Color.WHITE);
        knop[1].setBackground(buttonColor);
        
        knop[0].setActionCommand("game");
        knop[0].addHActionListener(this);
        knop[1].setActionCommand("sluiten");
        knop[1].addHActionListener(this);
        
        for(int i = 0; i<=1; i++){
            scene.add(knop[i]);
        }
        
        knop[0].setFocusTraversal(null, knop[1], null, null); // up, down, left, right
        knop[1].setFocusTraversal(knop[0], null, null, null);
     
        scene.validate();
        scene.setVisible(true);
        knop[0].requestFocus();
    }

    public void startXlet() {
    
    }

    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) {
     
    }
}
