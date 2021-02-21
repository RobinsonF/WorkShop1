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
        this.assignID();
        //for (int i = 0; i < this.petsList.size(); i++) {
         //   System.out.println(this.petsList.get(i).getId());
        //}
    }


    public void uploadData() {
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader("C:\\Users\\Robinson\\WorkShop1\\src\\co\\edu\\unbosque\\fourpawscitizens\\pets-citizens.csv"));

            String linea = bufferLectura.readLine();

            while (linea != null) {
                Pet pet = new Pet();
                String[] campos = linea.split(";");
                if (campos.length == 6) {
                    try {
                        pet.setMicrochip(Long.parseLong(campos[0]));
                        pet.setSpecies(campos[1]);
                        pet.setSex(campos[2]);
                        pet.setSize(campos[3]);
                        if (campos[4].equals("SI")) {
                            campos[4] = "true";
                        } else if (campos[4].equals("NO")) {
                            campos[4] = "false";
                        }
                        pet.setPotentDangerous(Boolean.parseBoolean(campos[4]));
                        pet.setNeighborhood(campos[5]);
                        petsList.add(pet);
                    } catch (NumberFormatException e) {

                    }
                }
                linea = bufferLectura.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void assignID() {
        int q = 0;
        int s = 0;
        String subCadenaSize = "";
        String subCadenapotentDangerous = "";
        for (int i = 0; i < this.petsList.size(); i++) {
            String microChip = String.valueOf(this.petsList.get(i).getMicrochip());
            String subCadenaMicroChip = microChip.substring(microChip.length() - 3, microChip.length());
            String species = this.petsList.get(i).getSpecies();
            String subCadenaSpecies = species.substring(0, 1);
            String sex = this.petsList.get(i).getSex();
            String subCadenaSex = sex.substring(0, 1);
            String size = this.petsList.get(i).getSize();
            if (size.equals("MINIATURA")) {
                subCadenaSize = size.substring(0, 2);
            } else {
                subCadenaSize = size.substring(0, 1);
            }
            boolean potentDangerous = this.petsList.get(i).isPotentDangerous();
            if (potentDangerous == true) {
                subCadenapotentDangerous = "T";
            } else if (potentDangerous == false) {
                subCadenapotentDangerous = "F";
            }
            String id = subCadenaMicroChip + "-" + subCadenaSpecies + subCadenaSex + subCadenaSize + subCadenapotentDangerous + "-" + this.petsList.get(i).getNeighborhood();
            this.petsList.get(i).setId(id);


        }

        for (int i = 0; i < this.petsList.size(); i++) {
            q = 0;
            for (int j = 0; j < this.petsList.size(); j++) {
                if ( i != j && this.petsList.get(i).getId().equals(this.petsList.get(j).getId())) {
                    String microChip = String.valueOf(this.petsList.get(i).getMicrochip());
                    String subCadenaMicroChip = microChip.substring(microChip.length() - 4, microChip.length() - 3);
                    String id = subCadenaMicroChip + this.petsList.get(j).getId();
                    this.petsList.get(i).setId(id);
                    break;
                }
            }
        }

        for (int i = 0; i < this.petsList.size(); i++) {
            s = 0;
            for (int j = 0; j < this.petsList.size(); j++) {
                if ( i != j && this.petsList.get(i).getId().equals(this.petsList.get(j).getId())) {
                s = s + 1;
                System.out.println(s);
                }
                System.out.println(s);
            }
        }

    }


}
