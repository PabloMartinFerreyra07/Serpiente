/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serpiente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author usuario
 */
public class juegoContenido extends JPanel implements ActionListener{
    //pantalla 
    static final int PANTALLA = 600;
    static final int CUADRITOS_SIZE = 25;
    static final int CUADRITOS_EN_PARALELO = (int)PANTALLA/CUADRITOS_SIZE;
    //serpiente
    static final int TOTAL_CUERPO_SERPIENTE =(PANTALLA*PANTALLA)/CUADRITOS_SIZE;
    int[] serpienteX = new int [TOTAL_CUERPO_SERPIENTE];
    int[] serpienteY = new int [TOTAL_CUERPO_SERPIENTE];
    int cuerpo_serpiente=3;
    char direccion = 'd';//awsd
    //comida
    int comidaX;
    int comidaY;
    //timer
    boolean running=true;
    static final int DELAY=100;
    Timer timer;
    
    //otros
    Random random= new Random();
    
    juegoContenido(){
         this.setPreferredSize(new Dimension(PANTALLA,PANTALLA));
         this.setBackground(Color.black);
         this.setFocusable(true);
         this.addKeyListener(new Controles());
         IniciarJuego();
    }
    public void IniciarJuego(){
        AgregarComida();
        timer= new Timer(DELAY,this);
        timer.start();
    }
    public void AgregarComida(){
        comidaX= random.nextInt(CUADRITOS_EN_PARALELO)*CUADRITOS_SIZE;
        comidaY= random.nextInt(CUADRITOS_EN_PARALELO)*CUADRITOS_SIZE;
    }
    public void MoverSerpiente(){
        for(int i=cuerpo_serpiente;i>0;i--){
            serpienteX[i]= serpienteX[i-1];
            serpienteY[i]= serpienteY[i-1];
        }
        switch(direccion){
            case 'd':
                serpienteX[0]=serpienteX[0]+CUADRITOS_SIZE;
                break;
            case 'a':
                serpienteX[0]=serpienteX[0]-CUADRITOS_SIZE;
                break;
            case 'w':
                serpienteY[0]=serpienteY[0]-CUADRITOS_SIZE;
                break;
            case 's':
                serpienteY[0]=serpienteY[0]+CUADRITOS_SIZE;
                break;
        }
    }
    public void ChecarComida(){
        if(serpienteX[0]==comidaX && serpienteY[0]==comidaY){
            cuerpo_serpiente++;
            AgregarComida();
        }
    }
    public void ChecarColisiones(){
        if(serpienteX[0]<0){
            running=false;
        }
        if(serpienteY[0]<0){
            running=false;
        }
        if (serpienteX[0]>PANTALLA-CUADRITOS_SIZE){
            running= false;
        }
        if (serpienteY[0]>PANTALLA-CUADRITOS_SIZE){
            running= false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            MoverSerpiente();
            ChecarComida();
            ChecarColisiones();
        }
        repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0;i<CUADRITOS_EN_PARALELO;i++){
            g.drawLine(0, CUADRITOS_SIZE*i, PANTALLA, CUADRITOS_SIZE*i);
            g.drawLine(CUADRITOS_SIZE*i,0, CUADRITOS_SIZE*i, PANTALLA);
        }
        g.setColor(Color.red);
        g.fillOval(comidaX, comidaY, CUADRITOS_SIZE, CUADRITOS_SIZE);
        g.setColor(Color.green);
        for (int i=0;i<cuerpo_serpiente;i++){
            g.fillRect(serpienteX[i], serpienteY[i], CUADRITOS_SIZE, CUADRITOS_SIZE);
        }
    }
            
            
    public class Controles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyChar()){
                case 'w':
                    direccion='w';
                    break;
                case 's':
                    direccion='s';
                    break;
                case 'd':
                    direccion='d';
                    break;
                case 'a':
                    direccion='a';
                    break;
            }
        }
    }
 
}