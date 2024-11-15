import java.io.*;
import java.util.*;

class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class ejercicio2 {
    private static final String FILE_NAME = "agenda.dat";
    private List<Contacto> contactos;

    @SuppressWarnings("unchecked")
    public ejercicio2() {
        contactos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contactos = (List<Contacto>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de contactos. Se creará uno nuevo al guardar.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void agregarContacto(String nombre, String telefono) {
        contactos.add(new Contacto(nombre, telefono));
        guardarContactos();
    }

    public void listarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("Contactos:");
            for (Contacto contacto : contactos) {
                System.out.println(contacto);
            }
        }
    }

    public void buscarContacto(String nombre) {
        boolean encontrado = false;
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Contacto encontrado: " + contacto);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un contacto con el nombre: " + nombre);
        }
    }

    private void guardarContactos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contactos);
            System.out.println("Contactos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ejercicio2 agenda = new ejercicio2();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nAgenda de Contactos");
            System.out.println("1. Agregar contacto");
            System.out.println("2. Listar contactos");
            System.out.println("3. Buscar contacto");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingresa un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    agenda.agregarContacto(nombre, telefono);
                }
                case 2 -> agenda.listarContactos();
                case 3 -> {
                    System.out.print("Nombre a buscar: ");
                    String nombre = scanner.nextLine();
                    agenda.buscarContacto(nombre);
                }
                case 4 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcion != 4);

        scanner.close();
    }
}
