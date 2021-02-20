package co.edu.unbosque.fourpawscitizens.model;

import co.edu.unbosque.fourpawscitizens.model.daos.Pet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Manager {
    ArrayList<Pet> petsList = new ArrayList<Pet>();

    public Manager() {
    this.uploadData();
        for (int i = 0; i < petsList.size(); i++) {
         System.out.println(petsList.get(i).getMicrochip() + "    " + petsList.get(i).getId());
        }
        System.out.println(petsList.size());
    }


    public void uploadData(){
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader("C:\\Users\\Robinson\\WorkShop1\\src\\co\\edu\\unbosque\\fourpawscitizens\\pets-citizens.csv"));

            String linea = bufferLectura.readLine();

            while (linea != null) {
                Pet pet = new Pet();
                String[] campos = linea.split(";");
                if(campos.length == 6 ){
                    try {
                        pet.setMicrochip(Long.parseLong(campos[0]));
                    }catch (NumberFormatException e){

                    }
                    
                pet.setSpecies(campos[1]);
                pet.setSex(campos[2]);
                pet.setSize(campos[3]);
                pet.setPotentDangerous(Boolean.parseBoolean(campos[4]));
                pet.setNeighborhood(campos[5]);
                    petsList.add(pet);
                }

                linea = bufferLectura.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void assignID(){
        for (int i = 0; i < this.petsList.size(); i++) {
            System.out.println(this.petsList.get(i).getSex() + "Hola");
        }
        System.out.println("Hola");

    }
}
