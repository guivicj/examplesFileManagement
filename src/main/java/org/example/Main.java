package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        try {
            FileInputStream fileInputStream = new FileInputStream("f1.txt");
            String entradaTexto = "Este es un array de bytes";
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(entradaTexto.getBytes());
            SequenceInputStream sequenceInputStream = new SequenceInputStream(fileInputStream, byteArrayInputStream);
            int byteData = byteArrayInputStream.read();
            while (byteData != -1) {
                System.out.print((char) byteData);
                byteData = byteArrayInputStream.read();
            }
            sequenceInputStream.close();
            fileInputStream.close();
            byteArrayInputStream.close();

            System.out.println("------------");

            FileInputStream fileInputStream2 = new FileInputStream("f2.txt");
            byte[] buffer = new byte[30];
            int bytesRead = fileInputStream2.read(buffer);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    System.out.print((char) buffer[i]);
                }
                System.out.println();
                bytesRead = fileInputStream2.read(buffer);
            }
            fileInputStream2.close();

            System.out.println("------------");

            String txt = "Contenido para el archivo.";
            FileOutputStream fileOutputStream = new FileOutputStream("f3.txt", true);
            for (int i = 0; i < txt.length(); i++) {
                fileOutputStream.write(txt.charAt(i));
            }
            fileOutputStream.close();

            System.out.println("--------------");

            FileInputStream fileInputStream_2 = new FileInputStream("f2.txt");
            FileOutputStream fileOutputStream_2 = new FileOutputStream("f4.txt");
            byte[] buffer2 = new byte[30];
            int bytesRead2 = fileInputStream_2.read(buffer2);
            while (bytesRead2 != -1) {
                fileOutputStream_2.write(buffer2, 0, bytesRead2);
                bytesRead2 = fileInputStream_2.read(buffer2);
            }
            fileOutputStream_2.close();
            fileInputStream_2.close();

            System.out.println("---------------");
            FileReader fileReader = new FileReader("f1.txt");
            int charData = fileReader.read();
            while (charData != -1) {
                System.out.println((char) charData);
                charData = fileReader.read();
            }
            fileReader.close();

            System.out.println("---------------");

            FileReader fileReader_2 = new FileReader("f2.txt");
            char[] buffer3 = new char[30];
            int charsRead2 = fileReader_2.read(buffer3);
            while (charsRead2 != -1) {
                System.out.println(new String(buffer3, 0, charsRead2));
                charsRead2 = fileReader_2.read(buffer3);
            }
            fileReader_2.close();

            System.out.println("---------------");

            String txt2 = "Contenido para el archivo con caracteres especiales: ç, á, ñ, ...";
            FileWriter fileWriter = new FileWriter("f5.txt");
            fileWriter.write(txt2);
            fileWriter.close();

            System.out.println("---------------");

            PrintStream printStream = new PrintStream("f6.txt");
            float nro = 5.25f;
            String saludo = "Hola.";
            printStream.print(saludo);
            printStream.println("¿Qué tal?");
            printStream.println(nro + 3);
            printStream.printf("El número %d en hexadecimal es %x", 27, 27);
            printStream.close();

            System.out.println("---------------");

            BufferedReader bufferedReader = new BufferedReader(new FileReader("f7_ent.txt"));
            PrintWriter printWriter = new PrintWriter("f7_eix.txt");
            String line = bufferedReader.readLine();
            int contador = 0;
            while (line != null) {
                contador++;
                printWriter.println(contador + ".-" + line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            printWriter.close();

//            File archivoImagen = new File("landscape.bmp");
//            FicheroImagen ficheroImagen = new FicheroImagen(archivoImagen);
//            ficheroImagen.transformaNegativo();
//            ficheroImagen.transformaOscuro();
//            ficheroImagen.transformaBlancoNegro();

            System.out.println("Fin");

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Ejercicio_2_2_Pantalla().setVisible(true);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Ejercicio_2_2_Pantalla extends JFrame {
        private JLabel etiquetaArchivo = new JLabel("Archivo");
        private JTextField campoArchivo = new JTextField(25);
        private JButton botonAbrir = new JButton("Abrir");
        private JButton botonGuardar = new JButton("Guardar");
        private JTextArea areaTexto = new JTextArea(10, 50);
        private JScrollPane scrollPane = new JScrollPane(areaTexto);

        public Ejercicio_2_2_Pantalla() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(2, 1));
            setTitle("Editor de texto");

            JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
            JPanel panelArchivo = new JPanel(new FlowLayout());
            panelArchivo.add(etiquetaArchivo);
            panelArchivo.add(campoArchivo);
            panelSuperior.add(panelArchivo);

            JPanel panelBotones = new JPanel(new FlowLayout());
            panelBotones.add(botonAbrir);
            panelBotones.add(botonGuardar);
            panelSuperior.add(panelBotones);

            JPanel panelInferior = new JPanel(new BorderLayout());
            panelInferior.add(scrollPane, BorderLayout.CENTER);

            add(panelSuperior);
            add(panelInferior);

            pack();

            botonAbrir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String file = campoArchivo.getText();
                    if (!file.isEmpty())
                        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                            areaTexto.setText("");
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                areaTexto.append(line + "\n");
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
                        }
                    else {
                        JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
                    }
                }
            });

            botonGuardar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String file = campoArchivo.getText();
                    if (!file.isEmpty()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            writer.write(areaTexto.getText());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
                    }
                }
            });
        }
    }

}