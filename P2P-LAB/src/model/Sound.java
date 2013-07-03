package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * everything for the sound of the game is here
 * 
 */
public class Sound {
    /**
     * plays the music
     */
    private static Sequencer sequencer;
    private static AdvancedPlayer player; // Player

    /**
     * start the music
     * 
     * @param parSound
     *            filename for the file
     */
    public static void playMidi(final String parSound) {

        new Thread() {
            public void run() {
                try {
                    String currDir = new File("").getAbsolutePath();
                    File soundFile = new File(currDir
                            + "/src/resources/sounds/" + parSound + ".mid");
                    Sequence sequence;
                    sequence = MidiSystem.getSequence(soundFile);
                    sequencer = MidiSystem.getSequencer();
                    sequencer.open();
                    sequencer.setSequence(sequence);
                    sequencer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void play(String parNameOfTheSong) {

        String nameOfTheSong;
        if (parNameOfTheSong == null) {
            // Wenn Parameter null ist:
            nameOfTheSong = "";
        } else {
            // Wenn Parameter nicht null ist:
            nameOfTheSong = parNameOfTheSong;
        }
        try {
            // Damit kann man currDir rausfinden =)
            String currentDirectory = new File("").getAbsolutePath();
            // Das ist path für mp3 Datei.
            File musicFile = new File(currentDirectory
                    + "/src/resources/sounds/" + nameOfTheSong + ".mp3");
            // File in Stream
            FileInputStream mp3_file = new FileInputStream(musicFile);
            // Stream in Player

            player = new AdvancedPlayer(mp3_file);
            // infoPanel.labelCurrentSong.setText("Current Song : " +
            // currentSong);
            
            new Thread() {
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }.start();

        } catch (FileNotFoundException e) {
            // Wenn die Datei nicht gefunden wird:
            JOptionPane
                    .showMessageDialog(
                            null,
                            "Ich will nicht jetzt schon mp3 musik hochladen, gruß Evgheni =)",
                            "Fehlermeldung", JOptionPane.OK_OPTION);
        } catch (JavaLayerException e) {
            // Wenn Layer Exception auftrit:
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Fehlermeldung", JOptionPane.OK_OPTION);
        }
 

    }
    
    public static void stop(){
    	player.close();
    }
}
