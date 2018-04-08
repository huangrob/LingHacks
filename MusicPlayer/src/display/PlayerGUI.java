package display;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager; 

import javazoom.jl.player.advanced.AdvancedPlayer;

public class PlayerGUI {

	private JFrame frame;
	private JTextField pathFieldSong;
	private File songFile;
	private File lyricFile;
	private JTextField pathFieldLyric;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					PlayerGUI window = new PlayerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public PlayerGUI() throws Exception {
		initialize();
	}


	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setTitle("MP3 Player");
		frame.setBounds(100, 100, 289, 179);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		
		JButton PlayButton = new JButton("Play");
		PlayButton.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread() { 
			        public void run() {
			        	playSong();
			        }
			    }.start();
			    new Thread() { 
			        public void run() {
			            playLyric();
			        }
			    }.start();
			}
		});
		PlayButton.setBounds(10, 71, 253, 63);
		frame.getContentPane().add(PlayButton);
		
		JButton OpenButtonSong = new JButton("Open");
		OpenButtonSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSong();
			}
		});
		OpenButtonSong.setBounds(174, 8, 89, 23);
		frame.getContentPane().add(OpenButtonSong);
		
		JButton OpenButtonLyric = new JButton("Open");
		OpenButtonLyric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openLyric();
			}
		});
		OpenButtonLyric.setBounds(174, 39, 89, 23);
		frame.getContentPane().add(OpenButtonLyric);
		
		pathFieldSong = new JTextField();
		pathFieldSong.setEditable(false);
		pathFieldSong.setForeground(Color.BLACK);
		pathFieldSong.setText("SongPath");
		pathFieldSong.setBounds(10, 9, 154, 20);
		frame.getContentPane().add(pathFieldSong);
		pathFieldSong.setColumns(10);
		
		pathFieldLyric = new JTextField();
		pathFieldLyric.setText("LyricPath");
		pathFieldLyric.setForeground(Color.BLACK);
		pathFieldLyric.setEditable(false);
		pathFieldLyric.setColumns(10);
		pathFieldLyric.setBounds(10, 40, 154, 20);
		frame.getContentPane().add(pathFieldLyric);
		
	}
		
	private void openSong() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose Song to Play");
			chooser.showOpenDialog(null);
			songFile = chooser.getSelectedFile();
			pathFieldSong.setText(songFile.getAbsolutePath());
			if (!songFile.getName().endsWith(".mp3")){
				JOptionPane.showMessageDialog(null, "Invalid File Type Selected", "Error", JOptionPane.ERROR_MESSAGE);
				openSong();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void openLyric(){
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose Lyric to Play");
			chooser.showOpenDialog(null);
			lyricFile = chooser.getSelectedFile();
			pathFieldLyric.setText(lyricFile.getAbsolutePath());
			if (!lyricFile.getName().endsWith(".mp3")){
				JOptionPane.showMessageDialog(null, "Invalid File Type Selected", "Error", JOptionPane.ERROR_MESSAGE);
				openLyric();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void playSong(){
		try {
			AdvancedPlayer SongPlayer = new AdvancedPlayer(new FileInputStream(songFile));
			SongPlayer.play();
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
			JOptionPane.showMessageDialog(null, "No File Selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void playLyric(){
		try {
			AdvancedPlayer LyricPlayer = new AdvancedPlayer(new FileInputStream(lyricFile));
			LyricPlayer.play();
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
			JOptionPane.showMessageDialog(null, "No File Selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}