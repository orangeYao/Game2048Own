/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author User
 */
public class BackgroundMusic {

        AudioClip clip = null;

        //  static boolean play=true;

        public BackgroundMusic() {
            try {
                setAudioClip(Applet.newAudioClip((new java.io.File("BGM.wav")).toURL()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //loop();
        }

        public AudioClip getAudioClip() {
            return this.clip;
        }

        public void setAudioClip(AudioClip clip) {
            this.clip = clip;
        }

        public void play() {
            if (getAudioClip() != null) {
                getAudioClip().play();
            }
        }

        public void loop() {
            if (getAudioClip() != null) {
                getAudioClip().loop();
            }
        }

        public void stop() {
            if (getAudioClip() != null) {
                getAudioClip().stop();
            }
        }
    }
