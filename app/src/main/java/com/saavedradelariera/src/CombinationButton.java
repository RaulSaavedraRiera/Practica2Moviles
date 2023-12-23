package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.messages.InputColorMessage;
import com.saavedradelariera.src.messages.PlaySoundMessage;

/*Clase que hereda de botón y sirve tanto para los botones de las filas como para los botones
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
    AndroidImage image;

    //generamos el boton con unos valores predeterminados
    public CombinationButton(int x, int y, int w, int h, float smallCircle, int rowN) {
        super(x, y, w, h, smallCircle);
        initialSmallCirclePercent = smallCircle;
        number = 0;
        color.setRGB(85, 85, 85);
        backgroundColor.setRGB(200, 200, 200);
        this.rowN = rowN;
    }


    @Override
    public void render(AndroidGraphics graphics) {
        if (!enable || image == null)
            super.render(graphics);
        else
            graphics.RenderImage(image, posX, posY, width, height);
    }

    @Override
    public void update(AndroidEngine IEngine, float deltaTime) {
    }

    //Permite cambiar el modo daltónico
    void changeDaltonicMode() {
        daltonicEnable = !daltonicEnable;
        if (daltonicEnable)
            colorText.txt = String.valueOf(number);
        else
            colorText.txt = "";
    }

    //Activa el botón con diferentes modalidades de comportamiento y color
    public void enableCombinationButton(ColorJ c, int n, float smallCircle, boolean input, boolean deleteColor, boolean daltonic) {
        enable = true;
        number = n;
        color = c;
        backgroundColor = new ColorJ(0, 0, 0);
        setSmallCircleSize(smallCircle);
        inputEnable = input;
        daltonicEnable = daltonic;
        deleteColorButton = deleteColor;
        SceneManager.getInstance().registerToMessage(this);
        String s;

        if (daltonicEnable)
            s = String.valueOf(number);
        else
            s = "";

        colorText = new Text("Night.ttf", posX + (3 * width) / 8, posY + (3 * height) / 8, width / 2,
                height / 2, s, new ColorJ(0, 0, 0));
    }

    //para botones con imagen
    public void enableCombinationButton(AndroidImage i, int n, float smallCircle, boolean input, boolean deleteColor) {
        enable = true;
        number = n;
        image = i;

        backgroundColor = new ColorJ(0, 0, 0);
        setSmallCircleSize(smallCircle);

        inputEnable = input;
        deleteColorButton = deleteColor;

        SceneManager.getInstance().registerToMessage(this);
        String s;

        if (daltonicEnable)
            s = String.valueOf(number);
        else
            s = "";

        colorText = new Text("Night.ttf", posX + (3 * width) / 8, posY + (3 * height) / 8, width / 2,
                height / 2, s, new ColorJ(0, 0, 0));
    }

    // Desactiva el input del boton.
    public void disableInput() {
        inputEnable = false;
    }

    //Desactiva el botón y lo devuelve al estado "default"
    void disableCombinationButton() {
        enable = false;
        number = 0;
        setColor(new ColorJ(85, 85, 85));
        setBackgroundColor(new ColorJ(200, 200, 200));
        SceneManager.getInstance().unRegisterToMessage(this);
        SceneManager.getInstance().unRegisterToMessage(this);
        setSmallCircleSize(initialSmallCirclePercent);
        colorText.txt = "";

    }

    public int getNumber() {
        return number;
    }

    //Gestiona el input del boton
    @Override
    public boolean handleInput(TouchEvent e) {
        if (!inputEnable)
            return super.handleInput(e);
        else {
            if (e.getType() == TouchEvent.TouchEventType.TOUCH_UP &&
                    isOver(e.getX(), e.getY())) {
                //Da una nueva entrada de input
                if (!deleteColorButton) {
                    SceneManager.getInstance().sendMessageToActiveScene(new InputColorMessage(number));
                    SceneManager.getInstance().sendMessageToActiveScene(new PlaySoundMessage("playButton.wav", false));
                }
                //Desactiva el botón
                else {
                    disableCombinationButton();
                    SceneManager.getInstance().sendMessageToActiveScene(new PlaySoundMessage("playButton.wav", false));
                }
                return true;
            } else {
                return false;
            }
        }
    }

    //Recibe mensajes de cambio en el modo daltonico para actualizarse
    @Override
    public void receiveMessage(Message m) {
        switch (m.id) {
            case "DaltonicChangeSolicitate":
                changeDaltonicMode();
                break;
        }
    }
}
