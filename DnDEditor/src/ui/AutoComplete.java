package ui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

import processing.BuildDictionary;

public class AutoComplete extends JPanel{
	//declare member variables for the UI
	private JTextField tf=null;
	private JComboBox combo= new JComboBox();
	private Vector<String > listOfWords= new Vector<String>();

	//declare a list for holding words
	private List<String> contentDictionary = new ArrayList<String>();



	public List<String> getContentDictionary() {
		return contentDictionary;
	}








	public Vector<String> getListOfWords() {
		return listOfWords;
	}








	public void setListOfWords(Vector<String> listOfWords) {
		this.listOfWords = listOfWords;
	}








	public void setContentDictionary(List<String> contentDictionary) {
		this.contentDictionary = contentDictionary;
	}


	private boolean hide_flag = false;

	//constructor
	public AutoComplete(){
		super(new BorderLayout());
		combo.setEditable(true);
		



		//get the editor component and cat it to textField
		tf=(JTextField) combo.getEditor().getEditorComponent();

		//add a key listener , later it can be changed to documentListener,Keep in TODO
		tf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						String text = tf.getText();
						if(text.length()==0) {
							combo.hidePopup();
							setModel(new DefaultComboBoxModel(listOfWords), "");
						}else{
							DefaultComboBoxModel m = getSuggestedModel(listOfWords, text);
							if(m.getSize()==0 || hide_flag) {
								combo.hidePopup();
								hide_flag = false;
							}else{
								setModel(m, text);
								combo.showPopup();
							}
						}
					}
				});
			}
			public void keyPressed(KeyEvent e) {

				String text = tf.getText();
				System.out.println("text is "+text);
				int code = e.getKeyCode();
				if(code==KeyEvent.VK_ENTER) {
					 
					if(!listOfWords.contains(text)) {
						listOfWords.addElement(text);
						Collections.sort(listOfWords);
						setModel(getSuggestedModel(listOfWords, text), text);
						System.out.println("here");
					}
					
					if(text.equalsIgnoreCase("commandButton")){
						System.out.println("commandButton is selected");
					}
					
					
					hide_flag = true; 
				}else if(code==KeyEvent.VK_ESCAPE) {
					hide_flag = true; 
				}else if(code==KeyEvent.VK_RIGHT) {
					for(int i=0;i<listOfWords.size();i++) {
						String str = listOfWords.elementAt(i);
						if(str.startsWith(text)) {

							combo.setSelectedIndex(-1);
							tf.setText(str);
							return;
						}
					}
				}
			}
		});       


		//declare a sample array to hold different country names

		String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola","Argentina"
				,"Armenia","Austria","Bahamas","Bahrain", "Bangladesh","Barbados", "Belarus","Belgium",
				"Benin","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","Bulgaria",
				"Burkina Faso","Burma","Burundi","Cambodia","Cameroon","Canada", "China","Colombia",
				"Comoros","Congo","Croatia","Cuba","Cyprus","Czech Republic","Denmark", "Georgia",
				"Germany","Ghana","Great Britain","Greece","Hungary","Holland","India","Iran","Iraq",
				"Italy","Somalia", "Spain", "Sri Lanka", "Sudan","Suriname", "Swaziland","Sweden",
				"Switzerland", "Syria","Uganda","Ukraine","United Arab Emirates","United Kingdom",
				"United States","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam",
				"Yemen","Zaire","Zambia","Zimbabwe"};

		for(int i=0;i<countries.length;i++){
			listOfWords.addElement(countries[i]);
		}

		//adding as per vikas ways
		//get the file to parse
	/*	  File f= new File("src//Content//Language//xhtml//XHTML_components.txt");

	      BuildDictionary bd = new BuildDictionary();
	      Vector<String> listWords=bd.parseFile(f);
	      System.out.println("list of words retrieved here is "+listWords);
		  setListOfWords(listWords);
	     
		  listOfWords =listWords;*/
	    
		//ends vikas ways


		setModel(new DefaultComboBoxModel(listOfWords), "");
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder("AutoSuggestion Box"));
		p.add(combo, BorderLayout.NORTH);
		add(p);
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setPreferredSize(new Dimension(300, 150));






	}//constructor ends






	//set Model
	private void setModel(DefaultComboBoxModel mdl, String str) {
		combo.setModel(mdl);
		combo.setSelectedIndex(-1);
		tf.setText(str);
	}


	private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		for(String s: list) {
			if(s.startsWith(text)||s.toLowerCase().startsWith(text.toLowerCase())){
				m.addElement(s);
			}
		}
		return m;
	}


	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(new AutoComplete());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
