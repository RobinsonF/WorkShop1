/**
 * model Package
 */
package co.edu.unbosque.fourpawscitizens.model;

import co.edu.unbosque.fourpawscitizens.model.dtos.Pet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Manager class
 */
public class Manager {

    ArrayList<Pet> petsList = new ArrayList<Pet>();

    /**
     * Constructor Manager class
     */
    public Manager() {
        this.function();
    }

    /**
     * This method is responsible for uploading all the information in the file to a pet type arrayList
     */
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

    /**
     * This method is responsible for assigning the id to each pet in the list, taking into account
     * all the parameters of their respective data
     */
    public void assignID() {
        int q = 0;
        int s = 0;
        String subCadenaSize = "";
        String subCadenapotentDangerous = "";
        String subCadenaMicroChip = "";
        for (int i = 0; i < this.petsList.size(); i++) {
            String microChip = String.valueOf(this.petsList.get(i).getMicrochip());
            subCadenaMicroChip = microChip.substring(microChip.length() - 3, microChip.length());
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
            for (int j = 0; j < this.petsList.size(); j++) {
                String microChip3 = String.valueOf(this.petsList.get(j).getMicrochip());
                if (this.petsList.get(j).getId().equals(id)) {
                    subCadenaMicroChip = microChip.substring(microChip3.length() - (subCadenaMicroChip.length() + 1), microChip3.length());
                    id = subCadenaMicroChip + "-" + subCadenaSpecies + subCadenaSex + subCadenaSize + subCadenapotentDangerous + "-" + this.petsList.get(i).getNeighborhood();
                    ;
                }
            }
            this.petsList.get(i).setId(id);
        }
    }

    /**
     * This method is responsible for searching for a pet within the entire
     * list of pets, depending on the microchip parameter
     *
     * @param microChip, it's the pet's microchip
     * @return, returns the pet with all its attributes
     */
    public String findByMicrochip(long microChip) {
        String mensaje = "";
        for (int i = 0; i < this.petsList.size(); i++) {
            if (this.petsList.get(i).getMicrochip() == microChip) {
                mensaje = "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood();
                break;
            } else {
                mensaje = "Pet not found";
            }
        }
        return mensaje;
    }

    /**
     * This method is responsible for counting the number of pets per species
     *
     * @param species, It is the type of species of the pet
     * @return, returns the number of pets of the species that the user has entered
     */
    public String countBySpecies(String species) {
        int j = 0;
        for (int i = 0; i < this.petsList.size(); i++) {
            if (this.petsList.get(i).getSpecies().equals(species)) {
                j++;
            } else {

            }
        }
        return "El número de animales de la especie " + species + " es: " + j;
    }

    /**
     * This method is responsible for displaying a number of pets, potentially dangerous from a neighborhood
     *
     * @param n,number      of pets to display
     * @param position,     It is in charge of renococer if the search goes from top to last or last to top
     * @param neighborhood, the pet's neighborhood
     * @return, returns all pets that have met the given characteristics
     */
    public String findBypotentDangerousInNeighborhood(int n, String position, String neighborhood) {
        String mensaje = "";
        int k = 0;
        int l = 0;
        for (int j = 0; j < this.petsList.size(); j++) {
            if (!this.petsList.get(j).getNeighborhood().equals(neighborhood)) {
                System.out.println("No hay mascotas que se encuentren en esa localidad");
                break;
            } else {
                if (position.equals("TOP")) {
                    for (int i = 0; i < this.petsList.size(); i++) {
                        if (this.petsList.get(i).isPotentDangerous() && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                            k++;
                            if (k <= n) {
                                mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                            }
                        }
                    }
                    if (n > k) {
                        mensaje = "No hay tal cantidad de mascotas con esas caracteristicas, le mostramos las disponibles\n";
                        for (int i = 0; i < this.petsList.size(); i++) {
                            if (this.petsList.get(i).isPotentDangerous() && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                                mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                            }
                        }
                    }
                } else if (position.equals("LAST")) {
                    for (int i = this.petsList.size() - 1; i >= 0; i--) {
                        if (this.petsList.get(i).isPotentDangerous() && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                            l++;
                            if (l <= n) {
                                mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                            }
                        }
                    }
                    if (n > l) {
                        mensaje = "No hay tal cantidad de mascotas con esas caracteristicas, le mostramos las disponibles\n";
                        for (int i = 0; i < this.petsList.size(); i++) {
                            if (this.petsList.get(i).isPotentDangerous() && this.petsList.get(i).getNeighborhood().equals(neighborhood)) {
                                mensaje += "ID: " + this.petsList.get(i).getId() + "\nSpecies: " + this.petsList.get(i).getSpecies() + "\nGender: " + this.petsList.get(i).getSex() + "\nSize: " + this.petsList.get(i).getSize() + "\nPotentially Dangerous: " + this.petsList.get(i).isPotentDangerous() + "\nNeighborhood: " + this.petsList.get(i).getNeighborhood() + "\n\n";
                            }
                        }
                    }
                }
                break;
            }
        }

        return mensaje;
    }

    /**
     * This method is in charge of displaying the pet ids depending on the given parameters
     *
     * @param species,         It is the species of the pet
     * @param sex,             It is the sex of the pet
     * @param size,            It is the size of the pet
     * @param potentDangerous, It is the status of the pet, if it is potentially dangerous or not
     * @return, returns all pets that meet the given parameters
     */
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

    /**
     * This method is in charge of asking the user for all the necessary data in order to make the program work
     */
    public void function() {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        String option = "";
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
            System.out.println("¿Qué desea hacer?");
            System.out.println("1.Cargar el archivo");
            System.out.println("2.Adignar el id a las mascotas");
            System.out.println("3.Buscar mascotas por el parametro de microchip");
            System.out.println("4.El número de mascotas por especie");
            System.out.println("5.Buscar mascotas dependiendo si es potencialmente peligroso o no");
            System.out.println("6.Buscar mascotas por especie, sexo, tamaño, y potencialmente peligroso");
            option = sc2.nextLine();
            int h = 0;
            do {
                if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4") || option.equals("5") || option.equals("6")) {
                    h = 1;
                } else {
                    System.out.println("Ingrese un dato valido");
                    option = sc2.nextLine();
                    h = 0;
                }
            } while (h == 0);
            switch (option) {
                case "1":
                    this.uploadData();
                    System.out.println("Proceso finalizado");
                    break;
                case "2":
                    this.assignID();
                    System.out.println("Proceso finalizado");
                    break;
                case "3":
                    if (this.petsList.size() == 0) {
                        System.out.println("No ha cargado la información de las mascotas");
                    } else {
                        System.out.println("Ingrese el microchip de la mascota que desea buscar");
                        try {
                            microChip = sc2.nextLine();
                            System.out.println(this.findByMicrochip(Long.parseLong(microChip)));
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                case "4":
                    if (this.petsList.size() == 0) {
                        System.out.println("No ha cargado la información de las mascotas");
                    } else {
                        System.out.println("Ingrese el número correspondiente a la especie\n1.CANINO\2.FELINO");
                        species = sc2.nextLine();
                        int j = 0;
                        do {
                            if (species.equals("1") || species.equals("2")) {
                                j = 1;
                            } else {
                                System.out.println("Ingrese un dato valido\n1.CANINO\2.FELINO");
                                species = sc2.nextLine();
                                j = 0;
                            }
                        } while (j == 0);
                        System.out.println(this.countBySpecies(species.toUpperCase()));
                    }
                    break;
                case "5":
                    if (this.petsList.size() == 0) {
                        System.out.println("No ha cargado la información de las mascotas");
                    } else {
                        try {
                            System.out.println("Ingrese la cantidad de mascotas que desea ver");
                            numberPets = sc.nextInt();
                            System.out.println("Ingrese la posición (TOP / LAST) de acuerdo al número\n1.TOP\n2.LAST");
                            position = sc.nextInt();
                            int j = 0;
                            do {
                                if (position == 1) {
                                    j = 0;
                                } else if (position == 2) {
                                    j = 0;
                                } else {
                                    System.out.println("Ingrese un dato valido<1/2>");
                                    position = sc.nextInt();
                                    j = 1;
                                }
                            } while (j == 1);

                            if (position == 1) {
                                position2 = "TOP";
                            } else if (position == 2) {
                                position2 = "LAST";
                            }
                            System.out.println("Ingrese la localidad");
                            neighborhood = sc2.nextLine().toUpperCase();
                            System.out.println(this.findBypotentDangerousInNeighborhood(numberPets, position2, neighborhood));
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                case "6":
                    if (this.petsList.size() == 0) {
                        System.out.println("No ha cargado la información de las mascotas");
                    } else if (this.petsList.get(0).getId().equals("NO-ID")) {
                        System.out.println("No ha asignado los ids a las mascotas");
                    } else {
                        try {
                            System.out.println("Ingrese el número correspondiente a la especie\n1.CANINO\n2.FELINO");
                            species2 = sc.nextInt();
                            int j = 0;
                            do {
                                if (species2 == 1 || species2 == 2) {
                                    j = 0;
                                } else {
                                    System.out.println("Ingrese un dato valido<1/2>");
                                    species2 = sc.nextInt();
                                    j = 1;
                                }
                            } while (j == 1);

                            if (species2 == 1) {
                                species3 = "CANINO";
                            } else if (species2 == 2) {
                                species3 = "FELINO";
                            }
                            j = 0;
                            System.out.println("Ingrese el número correspondiente al sexo\n1.MACHO\n2.HEMBRA");
                            sex = sc.nextInt();
                            do {
                                if (sex == 1 || sex == 2) {
                                    j = 0;
                                } else {
                                    System.out.println("Ingrese un dato valido<1/2>");
                                    sex = sc.nextInt();
                                    j = 1;
                                }
                            } while (j == 1);
                            if (sex == 1) {
                                sex2 = "MACHO";
                            } else if (sex == 2) {
                                sex2 = "HEMBRA";
                            }
                            System.out.println("Ingrese el número correspondiente al tamaño de la mascota\n1.GRANDE\n2.MEDIANO\n3.PEQUEÑO\n4.MINIATURA");
                            size = sc.nextInt();
                            do {
                                if (size == 1 || size == 2 || size == 3 || size == 4) {
                                    j = 0;
                                } else {
                                    System.out.println("Ingrese un dato valido<1/2/3/4>");
                                    size = sc.nextInt();
                                    j = 1;
                                }
                            } while (j == 1);
                            if (size == 1) {
                                size2 = "GRANDE";
                            } else if (size == 2) {
                                size2 = "MEDIANO";
                            } else if (size == 3) {
                                size2 = "PEQUEÑO";
                            } else if (size == 4) {
                                size2 = "MINIATURA";
                            }
                            System.out.println("Ingrese el número correspondiente, si la mascota es potencialmente peligrosa\n1.SI\n2.NO");
                            potentDangerous = sc.nextInt();
                            do {
                                if (potentDangerous == 1 || potentDangerous == 2) {
                                    j = 0;
                                } else {
                                    System.out.println("Ingrese un dato valido<1/2>");
                                    potentDangerous = sc.nextInt();
                                    j = 1;
                                }
                            } while (j == 1);
                            if (potentDangerous == 1) {
                                potentDangerous2 = "SI";
                            } else if (potentDangerous == 2) {
                                potentDangerous2 = "NO";
                            }
                            System.out.println(this.findByMultipleFields(species3, sex2, size2, potentDangerous2));
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                default:
                    System.out.println("Opción invalida");
            }
            System.out.println("¿Desea continuar? <S/N>");
            option2 = sc2.nextLine().toUpperCase();
            int j = 0;
            do {
                if (option2.equals("N")) {
                    j = 0;
                } else if (option2.equals("S")) {
                    j = 0;
                } else {
                    System.out.println("Ingrese un dato valido <S/N>");
                    option2 = sc2.nextLine().toUpperCase();
                    j = 1;
                }
            } while (j == 1);

        } while (option2.equals("S"));
    }
}
