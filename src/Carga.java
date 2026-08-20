import estructuras.conjuntistas.ArbolAVL;
import estructuras.grafos.GrafoEtiquetado;
import estructuras.lineales.Lista;

import java.io.BufferedReader;        // agrega metodos para leer con mayor comodidad readLine()
import java.io.FileNotFoundException; //excepción que salta si el archivo no existe en la ruta indicada.
import java.io.FileReader;  // nos permite leer un archivo. lee caracter a caracter.
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;// clase  de Java para partir un String en pedazos ("tokens") usando un separador

public class Carga {  private GrafoEtiquetado ciudades = new GrafoEtiquetado();
    private ArbolAVL equipos = new ArbolAVL();
    private HashMap<ClavePartido, Lista> mapa = new HashMap<ClavePartido, Lista>();
    private FileReader archivoLectura;
    private BufferedReader lector;
    private StringTokenizer split;

    public GrafoEtiquetado cargaCiudades() {
        try {
            archivoLectura = new FileReader(
                    "src/ListaCiudades.txt");   // leo el archivo
            String linea, valor, nombreCiudad = "";
            boolean alojamiento, sedeCopa;
            if (archivoLectura.ready()) {   //si el archivo esta listo para ser leido
                lector = new BufferedReader(archivoLectura);
                while ((linea = lector.readLine()) != null) {  //leo una linea
                    split = new StringTokenizer(linea, ";");  //separo por ";"
                    alojamiento = false;
                    sedeCopa = false;
                    for (int j = 0; j < 3; j++) {
                        valor = (String) split.nextElement(); //me muevo entre tokens
                        if (split.hasMoreElements()) { // contabilizo token
                            switch (j) {
                                case 0:
                                    nombreCiudad = valor;
                                    break;
                                case 1:
                                    if (valor.equalsIgnoreCase("true")) {
                                        alojamiento = true;
                                    }
                                    break;
                            }
                        } else {
                            if (valor.equalsIgnoreCase("true")) {
                                sedeCopa = true;
                            }
                        }
                    }

                    Ciudad ciudad = new Ciudad(nombreCiudad.toLowerCase(), alojamiento, sedeCopa);
                    ciudades.insertarVertice(ciudad);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("el archivo no existe");
        } catch (IOException e) {
            System.out.println("error leyendo el archivo");
        }
        return ciudades;

    }

    public ArbolAVL cargaEquipos() {
        try {
            archivoLectura = new FileReader(
                    "src/ListaEquipos.txt");
            String linea, valor, nombrePais = "", director = "", grupo = "";
            if (archivoLectura.ready()) {
                lector = new BufferedReader(archivoLectura);
                while ((linea = lector.readLine()) != null) {
                    split = new StringTokenizer(linea, ";");
                    for (int j = 0; j < 3; j++) {
                        valor = (String) split.nextElement();
                        if (split.hasMoreElements()) {
                            switch (j) {
                                case 0:
                                    nombrePais = valor;
                                    break;
                                case 1:
                                    director = valor;
                                    break;
                            }
                        } else {
                            grupo = valor;
                        }
                    }

                    Equipo equipo = new Equipo(nombrePais.toUpperCase(), director.toLowerCase(), grupo.toUpperCase());
                    equipos.insertar(equipo);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("el archivo no existe");
        } catch (IOException e) {
            System.out.println("error leyendo el archivo");
        }

        return equipos;

    }

    public HashMap<ClavePartido, Lista> cargaPartidos() {
        try {
            archivoLectura = new FileReader(
                    "src/listaPartidos.txt");
            String linea, eq1 = "", eq2 = "", instancia = "", ciudad = "", estadio = "", valor = "";
            int golE1 = 0, golE2 = 0;
            if (archivoLectura.ready()) {
                lector = new BufferedReader(archivoLectura);
                while ((linea = lector.readLine()) != null) {
                    split = new StringTokenizer(linea, ";");
                    for (int j = 0; j < 7; j++) {
                        valor = (String) split.nextElement();
                        if (split.hasMoreElements()) {
                            switch (j) {
                                case 0:
                                    eq1 = valor;
                                    break;
                                case 1:
                                    eq2 = valor;
                                    break;
                                case 2:
                                    instancia = valor;
                                    break;
                                case 3:
                                    ciudad = valor;
                                    break;
                                case 4:
                                    estadio = valor;
                                    break;
                                case 5:
                                    golE1 = Integer.parseInt(valor);
                                    break;
                            }
                        } else {
                            golE2 = Integer.parseInt(valor);
                        }
                    }
                    Partido partido = new Partido(eq1.toUpperCase(), eq2.toUpperCase(), instancia, ciudad, estadio,
                            golE1,
                            golE2); //tal vez deberia poner touppercase a cuidad, estadio e instancia , pero como controlo la carga desde el txt , no lo considero necesario en un primer momento. no encontrare JAmaiCA
                    ClavePartido clave= partido.getClavePartido();

                    if (mapa.containsKey(clave)) {
                        Lista listaPartidos = mapa.get(clave);
                        listaPartidos.insertar(partido, listaPartidos.longitud() + 1);
                    } else {
                        Lista listaPartidos = new Lista();
                        listaPartidos.insertar(partido, 1);
                        mapa.put(clave, listaPartidos);
                    }

                    Equipo equipo1 = (Equipo) equipos.recuperar(new Equipo(eq1.toUpperCase()));
                    Equipo equipo2 = (Equipo) equipos.recuperar(new Equipo(eq2.toUpperCase()));
                    equipo1.actualizarEquipo(golE1, golE2);
                    equipo2.actualizarEquipo(golE2, golE1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("el archivo no existe");
        } catch (IOException e) {
            System.out.println("error leyendo el archivo");
        }

        return mapa;

    }

    public void cargaRutas(GrafoEtiquetado ciudades) {
        try {
            archivoLectura = new FileReader(
                    "src/ListaRutas.txt");
            String linea, valor, origen = "", destino = "";
            int tiempoEstimado = 0;
            if (archivoLectura.ready()) {
                lector = new BufferedReader(archivoLectura);
                while ((linea = lector.readLine()) != null) {
                    split = new StringTokenizer(linea, ";");
                    for (int j = 0; j < 3; j++) {
                        valor = (String) split.nextElement();
                        if (split.hasMoreElements()) {
                            switch (j) {
                                case 0:
                                    origen = valor;
                                    break;
                                case 1:
                                    destino = valor;
                            }
                        } else {
                            tiempoEstimado = Integer.parseInt(valor);
                        }
                    }
                    Ciudad ciudadOrigen = new Ciudad(origen.toLowerCase());
                    Ciudad ciudadDestino = new Ciudad(destino.toLowerCase());
                    ciudades.insertarArco(ciudadOrigen, ciudadDestino, tiempoEstimado);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("el archivo no existe");
        } catch (IOException e) {
            System.out.println("error leyendo el archivo");
        }
    }
}
// usar el fileWritter para cargar el archivo LOG.txt

