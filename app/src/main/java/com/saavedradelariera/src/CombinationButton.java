package com.saavedradelariera.src;

import com.saavedradelariera.src.messages.Message;
import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;
import com.saavedradelariera.src.messages.InputColorMessage;
import com.saavedradelariera.src.messages.PlaySoundMessage;

/*Clase que hereda de botón y sirve tanrto para los botones de las filas como para los botones que
* a tráves de los cuales el jugador da el input en InputSolution*/
public class CombinationButton extends Button {

    //almacena su fila y su numero de color
    int number, rowN;
    Text colorText;

    //diferentes booleanos para controlar el comportamiento del boton
    boolean inputEnable = false;
    boolean daltonicEnable = false;
    boolean deleteColorButton = false;

    private float initialSmallCirclePercent;


    //generamos el boton con unos valores predeterminados
    public CombinationButton(int x, int y, int w, int h, float smallCircle, int rowN){
        super(x,y,w,h, smallCircle);

        initialSmallCirclePercent = smallCircle;

        number = 0;
        color.setRGB(85, 85, 85);
        backgroundColor.setRGB(200, 200, 200);
        this.rowN = rowN;
    }


    @Override
    public void Render(IGraphics graphics) {
        super.Render(graphics);
    }

    @Override
    public void Update(IEngine IEngine, float deltaTime) {
    }


    //Permite cambiar el modo daltónico
    void ChangeDaltonicMode(){
        daltonicEnable = !daltonicEnable;
        if(daltonicEnable)
            colorText.txt = String.valueOf(number);
        else
            colorText.txt = "";
    }


    //Activa el botón con diferentes modalidades de comportamiento y color
    public void EnableCombinationButton(ColorJ c, int n, float smallCircle, boolean input, boolean deleteColor, boolean daltonic)
    {
        enable = true;
        number = n;
        color = c;

        backgroundColor = new ColorJ(0,0,0);
        SetSmallCircleSize(smallCircle);

        inputEnable = input;
        daltonicEnable = daltonic;
        deleteColorButton = deleteColor;

        SceneManager.getInstance().RegisterToMessage(this);

        String s;
        if(daltonicEnable)
            s = String.valueOf(number);
        else
            s = "";

        colorText = new Text("Night.ttf", posX + (3*width)/8 , posY + (3*height)/8, width/2,
                        height/2, s, new ColorJ(0, 0, 0));
    }

    public void DisableInput(){
        inputEnable = false;
    }

    //Desactiva el botón y lo devuelve al estado "default"
    void DisableCombinationButton()
    {
        enable = false;
        number = 0;
        SetColor(new ColorJ(85, 85, 85));
        SetBackgroundColor(new ColorJ(200, 200, 200));
        SceneManager.getInstance().UnRegisterToMessage(this);
        SceneManager.getInstance().UnRegisterToMessage(this);
       SetSmallCircleSize(initialSmallCirclePercent);
       colorText.txt = "";

    }

    //Gestiona el input del boton
    @Override
    public boolean HandleInput(TouchEvent e) {
        if(!inputEnable)
            return super.HandleInput(e);
        else
        {

            if(e.getType() == TouchEvent.TouchEventType.TOUCH_UP &&
                    ISOver(e.getX(), e.getY()))
            {

                //Da una nueva entrada de input
                if(!deleteColorButton)
                {
                    SceneManager.getInstance().SendMessageToActiveScene(new InputColorMessage(number));
                    SceneManager.getInstance().SendMessageToActiveScene(new PlaySoundMessage("playButton.wav", false));
                }
                //Desactiva el botón
                else
                {
                    DisableCombinationButton();
                    SceneManager.getInstance().SendMessageToActiveScene(new PlaySoundMessage("playButton.wav", false));
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    //Recibe mensajes de cambio en el modo daltonico para actualizarse
    @Override
    public void ReceiveMessage(Message m) {
        switch(m.id)
        {
            case "DaltonicChangeSolicitate":
                ChangeDaltonicMode();
                break;
        }
    }
}
