package com.practica1.engine;

import com.practica1.graphics.IGraphics;
import com.practica1.sound.IAudioSystem;
/**
 * Interfaz para los engine de los diferentes modulos
 */
public interface IEngine {
   void Resume();

   void Pause();

   void SetScene(IScene IScene);

   IGraphics GetGraphics();

   IAudioSystem GetAudioSystem();

}
