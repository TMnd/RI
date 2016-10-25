/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class DocProcessor {

    Pattern pattern = Pattern.compile("\\\"([^\\\"]*)\\\""); //Padrão para o regex

    private ArrayList<String> toTokeneizer = new ArrayList<String>(); //Arralist para que se possa guardar as strings completamente tratadas com regex e com o id e o nome do documento no sitio certo
    private ArrayList<String> menDocs2 = new ArrayList<String>(); //O arraylist server para armazenar todas as linhas como strings ja modificadas de todas as linhas.
    private String regex_inicial = "@";

    public String readFileZip(String file) throws IOException {

        ZipFile zipFile = new ZipFile(file);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        //Percorre todos os ficheiros do ficheiro zip
        while (entries.hasMoreElements()) {

            ZipEntry entry = entries.nextElement();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));

            String line; //Para cada linha (documentos, id e o texto em si incluidos) que contém cada ficheiro 
            String ficheiro = entry.getName(); //Para adquirir o nome de cada ficheiro ex: se o ficheiro tiver uma pasta sera: NomeDaPasta/NomeDoFicheiro. se for só o ficheiro aparecera só o ficheiro
            String line_rebuild = null; //Serve para reconstruir cada linha que sera inserida no arraylyst que ira acrescentar no principio o nome do ficheiro que esta a ser lido as linhas

            //Percorre todas as linhas de cada ficherio
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith(regex_inicial)) { //ler todas as linhas que nao tenham um @ (ISTO TEM DE SER MELHORADO)

                    if (line.length() != 0) { //let todas as linha que nao estejam vazias

                        line_rebuild = ' ' + ficheiro + ',' + line + ' '; //String modificadora que acrescenta o nome do ficheiro antes do conteudo de cada linhas
                        menDocs2.add(line_rebuild); //Acrscenta a linha modificada para o arraylist

                    }
                }
            }

        }
        //System.out.println(menDocs2);
        System.out.println("CorpusReader: Leu o zip todo");
        return null;
    }

    public ArrayList<String> getMenDocs2() {
        return menDocs2;
    }

    //Serve para dividir cada documento para poder ser tratado
    public void DivideLine(ArrayList<String> AllDocs) {
        //Percorrer o arraly list
        Iterator iter = AllDocs.iterator();
        while (iter.hasNext()) {

            String line = (String) iter.next(); //cada linha do documento que foi inserido na arraylist

            String[] teste = line.split(",", 3); //separar a string no ',' mas só os dois primeiros

            Matcher matcher = pattern.matcher(teste[2]); //Para activar o regex

            while (matcher.find()) {
                String rebuildString = teste[1] + "_" + teste[0] + "," + matcher.group(1); //ex: idDoc_NomeDoFicheiro, TextoDoDoc
                toTokeneizer.add(rebuildString);// Insere na arraylist a string com o id do documento com o formato correcto e o conteudo do mesmo já tratado
            }
        }
        System.out.println("DocProcessor: Processou a string");
    }

}

/*
 //Serve para que a class tokeneizer consiga adquirir a arraylist da class DocProcessor para que possa trabalhar.
 public ArrayList<String> getToTokeneizer() {
 return toTokeneizer;
 }
    
 //dunno
 public HashMap<String, HashMap<String, Float>> putinTable(HashMap<String, HashMap<String, String>> conteudo) {
 return null;
 }

 public static void checkLanguage(String data) {

 }

 public static void checkCharacterSet(String data) {

 }

 public String getTexto(String data) {
 return texto;
 }

 public HashMap<String, HashMap<String, Float>> getHashMap() {
 return null;
 }
    
 }
    
 /*Iterator iter = arrlist.iterator();
 while (iter.hasNext()) {
 System.out.println(iter.next());
 }*/
/*  while (entries.hasMoreElements()) {
 ZipEntry entry = entries.nextElement();
 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));

 String line;
            
 //Primeira passagem pelos ficheiros
 while ((line = bufferedReader.readLine()) != null) { 
 String[] id_aux = null;
 Matcher matcher = pattern.matcher(line); //Para activar o regex
        
 HashSet<String> idsLixo = new HashSet<>(); //Para dar valores nulos para cada key da hashmap
                
 if (!line.startsWith("@")) { //ler todas as linhas que nao tenham um @
 //GET text que se enzntra no meio das ""
 while (matcher.find()) {
                        
 StringTokenizer st = new StringTokenizer(matcher.group(1));  //Separa cada palavra pelo espaço
                
 while (st.hasMoreElements()) {
 String i = st.nextToken();
 //System.out.println(i);
 //Encher a has map SÓ com os termos mas com nenhum value por key
 if (rabodeboi.get(i) == null || !rabodeboi.containsKey(i)) {
 rabodeboi.put(i, new HashSet<String>());
 }
 }
 }
 }
 }*/
