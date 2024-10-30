package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FicheroImagen {
    private File archivo;

    public FicheroImagen(File archivoEntrada) {
        if (archivoEntrada.exists() && archivoEntrada.getName().endsWith(".bmp")) {
            this.archivo = archivoEntrada;
        } else {
            System.out.println("El archivo no existe o no es un archivo BMP válido.");
        }
    }

    public void transformaNegativo() throws IOException {
        transforma("_n", "negative");
    }

    public void transformaOscuro() throws IOException {
        transforma("_o", "dark");
    }

    public void transformaBlancoNegro() throws IOException {
        transforma("_bn", "blackAndWhite");
    }

    private void transforma(String suffix, String transformType) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(archivo)) {
            String newName = archivo.getName().replace(".bmp", suffix + ".bmp");
            File newFile = new File(archivo.getParent(), newName);

            try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
                byte[] buffer = new byte[54];
                fileInputStream.read(buffer);
                fileOutputStream.write(buffer);

                if (transformType.equals("blackAndWhite")) {
                    whiteAndBlackTransformation(fileInputStream, fileOutputStream);
                } else {
                    overallTransformation(fileInputStream, fileOutputStream, transformType);
                }
            }
        }
    }

    private void overallTransformation(FileInputStream fileInputStream, FileOutputStream fileOutputStream, String transformType) throws IOException {
        int byteRead;
        while ((byteRead = fileInputStream.read()) != -1) {
            if (transformType.equals("negative")) {
                fileOutputStream.write(255 - byteRead);
            } else if (transformType.equals("dark")) {
                fileOutputStream.write(byteRead / 2);
            }
        }
    }

    private void whiteAndBlackTransformation(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        int r, g, b;
        while ((r = fileInputStream.read()) != -1 && (g = fileInputStream.read()) != -1 && (b = fileInputStream.read()) != -1) {
            int avg = (r + g + b) / 3;
            fileOutputStream.write(avg);
            fileOutputStream.write(avg);
            fileOutputStream.write(avg);
        }
    }
}