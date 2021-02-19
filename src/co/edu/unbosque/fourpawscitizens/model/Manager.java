package co.edu.unbosque.fourpawscitizens.model;

import co.edu.unbosque.fourpawscitizens.model.daos.Pet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    File file = new File("C:\\Users\\Robinson\\WorkShop1\\src\\co\\edu\\unbosque\\fourpawscitizens\\pets-citizens.csv");
    ArrayList<Pet> pet = new ArrayList<Pet>();
    public Manager () {
    pet = leerArchivo(file);
    this.funcionar();

    }
    public void funcionar(){

        for (int i = 0; i < pet.size(); i++) {
            System.out.println(pet.get(i).getSex());
        }
    }

    public ArrayList<Pet> leerArchivo(File fArchivo) {
        try {

            if (fArchivo.exists()) {
                BufferedReader fLectura = new BufferedReader(new FileReader(fArchivo));
                ArrayList<Pet> petsList = new ArrayList<Pet>();
                Scanner scanner = new Scanner(fArchivo);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Scanner delimitar = new Scanner(line);
                    delimitar.useDelimiter("\s;\s");

                    Pet e = new Pet();

                    e.setId(delimitar.next());
                    e.setMicrochip(delimitar.nextLong());
                    e.setSpecies(delimitar.next());
                    e.setSex(delimitar.next());
                    e.setSize(delimitar.next());
                    e.setPotentDangerous(delimitar.nextBoolean());
                    e.setNeighborhood(delimitar.next());

                    petsList.add(e);
                }
                fLectura.close();

                return petsList;

            } else {
                return null;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
