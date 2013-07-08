package model;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Diese Klasse ist dazu da mp3 Musik aus einer Datei zu spielen.
 * 
 * @author Croitor Evgheni
 * 
 */
public class Music {
    static File[] musicFiles;
    private static AdvancedPlayer player; // Player
    private static boolean stopMusic;
    public static String CurrentSong;
    private static boolean pauseMusic;
    private static int pausePosition;
    static int counter;
    static String currentMusicFolder;

    /**
     * Eine statische methode um ein Lied auszuwählen. Achtun: hier wird nur ein
     * Lied ausgewählt, noch nicht abgespielt!
     * 
     * @param parNameOfTheSong
     *            Als Parameter bekommt die Methode den Name vom Lied.
     */
    public static void loadSong(String parNameOfTheSong) {
        stopMusic = false;
        String nameOfTheSong;
        if (parNameOfTheSong == null) {
            // Wenn Parameter null ist:
            nameOfTheSong = "StaticX_TheOnly";
        } else {
            // Wenn Parameter nicht null ist:
            nameOfTheSong = parNameOfTheSong;
        }
        try {
            // Damit kann man currDir rausfinden =)
            String currentDirectory = new File("").getAbsolutePath();
            // Das ist path für mp3 Datei.
            File musicFile = new File(currentDirectory
                    + "/src/resources/music/" + nameOfTheSong + ".mp3");
            // File in Stream
            FileInputStream mp3_file = new FileInputStream(musicFile);
            // Stream in Player
            player = new AdvancedPlayer(mp3_file);

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

    /**
     * Eine statische methode um ein Lied auszuwählen. Achtun: hier wird nur ein
     * Lied ausgewählt, noch nicht abgespielt!
     * 
     * @param parNameOfTheSong
     *            Als Parameter bekommt die Methode den Name vom Lied.
     */
    public static void loadSongFromPath(String Filepath, String songName) {
        stopMusic = false;
        try {

            File musicFile = new File(Filepath);
            // File in Stream
            FileInputStream mp3_file = new FileInputStream(musicFile);
            // Stream in Player
            player = new AdvancedPlayer(mp3_file);
            CurrentSong = musicFile.getName();

        } catch (FileNotFoundException e) {
            // Wenn die Datei nicht gefunden wird:
        } catch (JavaLayerException e) {
            // Wenn Layer Exception auftrit:
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Fehlermeldung", JOptionPane.OK_OPTION);
        }

    }

    /**
     * Eine statische Methode um mp3 Datei abzuspielen.
     */
    public static void play() {

        // Thread wird erzeugt
        new Thread() {
            public void run() {
                try {
                    stopMusic = false;
                    player.play();
                } catch (JavaLayerException e) {
                    // Wenn Layer Exception auftrit:
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            "Fehlermeldung", JOptionPane.OK_OPTION);
                }
            }
        };//.start();
    }

    /**
     * Eine statische Methode um Musik zu stoppen.
     */
    public static void stop() {
        stopMusic = true;
        player.close();
    }

    public static void Pause() {
        pauseMusic = true;
        player.close();

    }

    /**
     * play the music from a folder
     * 
     * @author Julia Gabriel
     * @param FolderPath
     */
    public static void PlayMusicFromFolder(String FolderPath,
            boolean resumeMusic) {
        stopMusic = false;
        currentMusicFolder = FolderPath;

        if (!resumeMusic) {

            File test = new File(FolderPath);
            FileFilter myfileFilter = new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    if ((pathname.isFile())
                            && pathname.getName().endsWith(".mp3"))
                        return true;
                    return false;
                }
            };
            musicFiles = test.listFiles(myfileFilter);

        }
        counter = 0;
        while (true) // endless loop to play again after the end 
        {
            for (File f : musicFiles) {
                // do whatever you want with each File f
                counter++;
                if ((resumeMusic && (counter == pausePosition)) || !resumeMusic) {
                    loadSongFromPath(f.getAbsolutePath(), f.getName());
                    try {
                        if (!stopMusic) {
                            player.play();
                        }
                        if (pauseMusic) {
                            pausePosition = counter;
                            return;
                        }
                        if (stopMusic) {
                            return;
                        }
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }

            }
            counter = 0;
        }

    }

    public static void ResumeMusic() {
        pauseMusic = false;
        PlayMusicFromFolder(currentMusicFolder, true);

    }
}
