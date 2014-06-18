package ui;
import ui.AutoComplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import processing.BuildDictionary;

public class MainCanvas extends JFrame implements DropTargetListener {
  DropTarget dt;
  JTextArea ta = new JTextArea();
  
  public DropTarget getDt() {
	return dt;
}

public void setDt(DropTarget dt) {
	this.dt = dt;
}

public JTextArea getTa() {
	return ta;
}

public void setTa(JTextArea ta) {
	this.ta = ta;
}

  public MainCanvas() {
    super("Drop Test");
    
    
 // get the screen size as a java dimension
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     
    // get 2/3 of the height, and 2/3 of the width
    int height = screenSize.height * 2 / 3;
    int width = screenSize.width * 2 / 3;
     
    // set the jframe height and width
    setPreferredSize(new Dimension(width, height));
    
    setSize(1000, 500);
    getContentPane()
        .add(new JLabel("Drop something here:"), BorderLayout.NORTH);
    
    ta.setBackground(Color.white);
    getContentPane().add(ta, BorderLayout.CENTER);
    dt = new DropTarget(ta, this);
    setVisible(true);
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    System.out.println("Drag Enter");
  }

  public void dragExit(DropTargetEvent dte) {
    System.out.println("Drag Exit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    System.out.println("Drag Over");
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    System.out.println("Drop Action Changed");
  }

  public void drop(DropTargetDropEvent dtde) {
    try {
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
        if (flavors[i].isFlavorJavaFileListType()) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          List list = (List) tr.getTransferData(flavors[i]);
          for (int j = 0; j < list.size(); j++) {
            ta.append(list.get(j) + "\n");
          }
          dtde.dropComplete(true);
          return;
        } else if (flavors[i].isFlavorSerializedObjectType()) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          Object o = tr.getTransferData(flavors[i]);
          ta.append("Object: " + o);
          dtde.dropComplete(true);
          return;
        } else if (flavors[i].isRepresentationClassInputStream()) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          ta.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])),
              "from system clipboard");
          dtde.dropComplete(true);
          return;
        }
      }
      dtde.rejectDrop();
    } catch (Exception e) {
      e.printStackTrace();
      dtde.rejectDrop();
    }
  }
  public static void main(String args[]) {
	
	  
      MainCanvas canvas= new MainCanvas();
      canvas.getContentPane();
      canvas.setLayout(new BorderLayout());
      
      //get the file to parse
      File f= new File("src//Content//Language//xhtml//XHTML_components.txt");
      
      BuildDictionary bd = new BuildDictionary();
      Vector<String> listWords=bd.parseFile(f);
      
      
      AutoComplete ac = new AutoComplete();
      ac.setListOfWords(listWords);
      
      
      
      canvas.add(ac,BorderLayout.SOUTH);
      
      
      canvas.pack();
      canvas.setLocationRelativeTo(null);
      canvas.setVisible(true);
  }
}
