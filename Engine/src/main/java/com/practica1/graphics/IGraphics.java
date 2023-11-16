package com.practica1.graphics;

public interface IGraphics {

    void RenderCircle(int x, int y, int r, ColorJ c);

    void RenderRect(int x, int y, int w, int h, ColorJ c);

    void RenderFillRect(int x, int y, int w, int h, ColorJ c, ColorJ insideC);

    void RenderImage(IImage image, int x, int y, int w, int h);
    IImage createImage(String route);

    void RenderLine(int x1, int y1, int x2, int y2, ColorJ c);

    //damos el nombre de la fuente una vez las fuentes guardadas
    void RenderText(int x, int y, int size, String txt, ColorJ c);

    void SetFont(IFont font);

    IFont CreateFont(String route, int size, boolean boldStyle);

    IFont GetFont(String name);

    int GetHeight();

    int GetWidth();

    int GetHeightRelative();

    int GetWidthRelative();

    void CleanScreen(ColorJ color);

    boolean startRender();

    void finishRender();

    void rescale();

    int getTranslateY();

    int getTranslateX();

    float getScale();

    void save();

    void restore();
}
