package co.edu.unbosque.fourpawscitizens.model;

import co.edu.unbosque.fourpawscitizens.model.dtos.Pet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Manager {

    ArrayList<Pet> petsList = new ArrayList<Pet>();

    public Manager() {
        this.function();
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
                        pet.setId("NO-ID");
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
            for (int j = 0; j < this.petsList.size(); j++) {
                if (i != j && this.petsList.get(i).getId().equals(this.petsList.get(j).getId())) {
                    String microChip = String.valueOf(this.petsList.get(j).getMicrochip());
                    String subCadenaMicroChip = microChip.substring(microChip.length() - 4, microChip.length() - 3);
                    String id = subCadenaMicroChip + this.petsList.get(j).getId();
                    this.petsList.get(i).setId(id);
                }
            }
        }

        for (int i = 0; i < this.petsList.size(); i++) {
            for (int j = 0; j < this.petsList.size(); j++) {
                if (i != j && this.petsList.get(i).getId().equals(this.petsList.get(j).getId())) {
                    String microChip = String.valueOf(this.petsList.get(j).getMicrochip());
                    String subCadenaMicroChip = microChip.substring(microChip.length() - 5, microChip.length() - 4);
                    String id = subCadenaMicroChip + this.petsList.get(j).getId();
                    this.petsList.get(i).setId(id);
                }
            }
        }

        for (int i = 0; i < this.petsList.size(); i++) {
            for (int j = 0; j < this.petsList.size(); j++) {
                if (i != j && this.petsList.get(i).getId().equals(this.petsList.get(j).getId())) {
                    String microChip = String.valueOf(this.petsList.get(j).getMicrochip());
                    String subCadenaMicroChip = microChip.substring(microChip.length() - 6, microChip.length() - 5);
                    String id = subCadenaMicroChip + this.petsList.get(j).getId();
                    this.petsList.get(i).setId(id);
                }
            }
        }
    }

    public String findByMicrochip(long microChip) {
        String mensaje = "";
        for (int i = 0; i < this.petsList.size(); i++) {
            if (this.petsList.get(i).getMicrochip() == microChip) {
                mensaje = "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood();
                break;
            }else{
                mensaje = "Pet not found";
            }
        }
        return mensaje;
    }

    public String countBySpecies(String species) {
        int j = 0;
        for (int i = 0; i < this.petsList.size(); i++) {
            if (this.petsList.get(i).getSpecies().equals(species)) {
                j++;
            }
        }
        return "El número de animales de la especie " + species + " es: " + j;
    }

    public String findBypotentDangerousInNeighborhood(int n, String position, String neighborhood) {
        String mensaje = "";
        int k = 0;
        int l = 0;
        if (position.equals("TOP")) {
            for (int i = 0; i < this.petsList.size(); i++) {
                if (this.petsList.get(i).isPotentDangerous() == true && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                    k++;
                    if (k <= n) {
                        mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                    }
                }
            }
        } else if (position.equals("LAST")) {
            for (int i = this.petsList.size() - 1; i >= 0; i--) {
                if (this.petsList.get(i).isPotentDangerous() == true && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                    l++;
                    if (l <= n) {
                        mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                    }
                }
            }
        }
        return mensaje;
    }

    public String findByMultipleFields(String species, String sex, String size, String potentDangerous) {
        String mensaje = "";
        String sex2 = sex.substring(0, 1);
        String species2 = species.substring(0, 1);
        String size2 = "";
        String potentDangerous2 = "";
        if (size.equals("MINIATURA")) {
            size2 = size.substring(0, 2);
        } else {
            size2 = size.substring(0, 1);
        }
        if (potentDangerous.equals("SI")) {
            potentDangerous2 = "T";
        } else if (potentDangerous.equals("NO")) {
            potentDangerous2 = "F";
        }
        String id = species2 + sex2 + size2 + potentDangerous2;
        for (int i = 0; i < this.petsList.size(); i++) {
            String id2 = this.petsList.get(i).getId();
            String id3 = "";
            String[] cadena = id2.split("-");
            id3 = cadena[1];
            if (id3.equals(id)) {
                mensaje += this.petsList.get(i).getId() + "\n";
            }
        }
        return mensaje;
    }

    public void function() {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int option;
        String option2 = "";
        String microChip = "";
        String species = "";
        int numberPets;
        int position;
        String position2 = "";
        String neighborhood = "";
        int sex;
        String sex2 = "";
        int size;
        String size2 = "";
        int potentDangerous;
        String potentDangerous2 = "";
        int species2;
        String species3 = "";
        do {
            System.out.println("What would you like to do?");
            System.out.println("1.Upload Data");
            System.out.println("2.Assign ID");
            System.out.println("3.Find by microchip");
            System.out.println("4.The number of animals per species");
            System.out.println("5.Search for potentially dangerous animals by neighborhood");
            System.out.println("6.Search by species, sex, size, and potentially dangerous");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    this.uploadData();
                    System.out.println("Ended process");
                    break;
                case 2:
                    this.assignID();
                    System.out.println("Ended process");
                    break;
                case 3:
                System.out.println("Enter the microchip");
                    microChip = sc2.nextLine();
                    if(this.petsList.size() == 0){
                        System.out.println("You have not uploaded the file, there is no information about the pets");
                    }else {
                        System.out.println( this.findByMicrochip(Long.parseLong(microChip)));
                    }
                    break;
                case 4:
                System.out.println("Enter the species");
                species = sc2.nextLine();
                    if(this.petsList.size() == 0){
                        System.out.println("You have not uploaded the file, there is no information about the pets");
                    }else {
                        System.out.println(this.countBySpecies(species.toUpperCase()));
                    }
                break;
                case 5:
                    System.out.println("Enter the number of pets you want to see");
                    numberPets = sc.nextInt();
                    System.out.println("Enter the position (TOP / LAST) according to the corresponding number\n1.TOP\n2.LAST");
                    position = sc.nextInt();
                    if(position == 1){
                        position2 = "TOP";
                    } else if(position == 2){
                        position2 = "LAST";
                    }
                    System.out.println("Enter the neighborhood");
                    neighborhood = sc2.nextLine();
                    System.out.println(this.findBypotentDangerousInNeighborhood(numberPets, position2, neighborhood));
                    break;
                case 6:
                    System.out.println("Enter the species\n1.CANINO\n2.FELINO");
                    species2 = sc.nextInt();
                    if(species2 == 1){
                        species3 = "CANINO";
                    }else if(species2 == 2){
                        species3 = "FELINO";
                    }
                    System.out.println("Enter the sex\n1.MACHO\n2.HEMBRA");
                    sex = sc.nextInt();
                    if(sex == 1){
                        sex2 = "MACHO";
                    }else if (sex == 2){
                        sex2 = "HEMBRA";
                    }
                    System.out.println("Enter the size\n1.GRANDE\n2.MEDIANO\n3.PEQUEÑO\n4.MINIATURA");
                    size = sc.nextInt();
                    if(size == 1){
                        size2 = "GRANDE";
                    }else if(size == 2){
                        size2 = "MEDIANO";
                    }else if(size == 3){
                        size2 = "PEQUEÑO";
                    }else if(size == 4){
                        size2 = "MINIATURA";
                    }
                    System.out.println("Enter if it is potentially dangerous\n1.SI\n2.NO");
                    potentDangerous = sc.nextInt();
                    if(potentDangerous == 1){
                        potentDangerous2 = "SI";
                    }else if (potentDangerous == 2){
                        potentDangerous2 = "NO";
                    }
                    System.out.println(this.findByMultipleFields(species3, sex2, size2, potentDangerous2));
                    break;
                default:
                    System.out.println("Option not valid");
            }
            System.out.println("Do you wish to continue? <y/n>");
            option2 = sc2.nextLine().toUpperCase();
            int j = 0;
            do {
                if(option2.equals("N")){
                    j=0;
                } else if(option2.equals("Y")){
                    j=0;
                }else{
                    System.out.println("Enter a valid data<y/n");
                    option2 = sc2.nextLine().toUpperCase();
                    j = 1;
                }
            }while(j==1);
        } while (option2.equals("Y"));
    }
}
