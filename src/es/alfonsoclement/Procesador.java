/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.alfonsoclement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**

 @author Alfon
 */
public class Procesador implements Runnable{
   
   File file;
   JList lista;
   DefaultListModel<String> model;
   ArrayList<File> files;
   JTextArea areaError;
   String word;
   FinishListener finishListener;
   
   
   public Procesador(File file, JList lista, DefaultListModel<String> model, ArrayList<File> files, JTextArea areaError, String word, FinishListener finishListener){
      this.file = file;
      this.lista = lista;
      this.model = model;
      this.word = word;
      this.areaError = areaError;
      this.finishListener = finishListener;
      this.files = files;
   }
   
   void startSearch(File file){
      if(!file.isDirectory())
      {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String sCurrentLine;
                        int lineNumber = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains(word))
                                {
                                   files.add(file);
                                   model.addElement(file.getName() + " |   (line " +lineNumber+")   | " + file.getAbsolutePath() );
                                   lista.setModel(model);
                                  
                                   break;
                                }
                                
                                lineNumber++;
			}

		} catch (Exception e) {
			areaError.append("Error!!  -- in "+ file.getName() + " --- "+ e.getMessage()+"\n");
		}
      }
      else
      {
         File [] list = file.listFiles();
         for(File fi : list){
            startSearch(fi);
         }
      }
   }
  

   @Override
   public void run() {
      
      startSearch(file);
      finishListener.onFinish(new Date());
   }
   
   
}
