import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;
import java.util.*;

import estructuras.conjuntistas.*;
import estructuras.grafos.*;
import estructuras.lineales.*;

public class MenuCopaAmerica {
    private static GrafoEtiquetado ciudades;
    private static ArbolAVL equipos;
    private static HashMap<Integer, Partido> partidos;
    private static Carga cargaDatos = new Carga();
    private static Scanner dato = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        ciudades = cargaDatos.cargaCiudades();
        equipos = cargaDatos.cargaEquipos();
        partidos = cargaDatos.cargaPartidos();
        cargaDatos.cargaRutas(ciudades);
        limpiarLog();
        mostrarMenu();
    }

    public static void mostrarMenu() {
        String opcion = "";
        boolean stop = false;
        do {
            System.out.println("\nIngrese una opcion.");
            mostrarOpciones();
            opcion = dato.next();
            switch (opcion) {
                case "1":
                    verCiudades();// agregar, eliminar, modificar una ciudad
                    break;
                case "2":
                    verEquipos();// agregar, eliminar, modificar un equipo
                    break;
                case "3":
                    verPartidos();// agregar un partido
                    break;
                case "4":
                    consultaEquipos();// mostrar los datos de un pais o una lista de paises
                    break;
                case "5":
                    consultaPartidos();// buscar si un partido se jugo entre 2 equipos
                    break;
                case "6":
                    consultaViajes();// buscar caminos segun una ciudad origen y una ciudad destino
                    break;
                case "7":
                    listadoEquipos();// listar los equipos de mayor a menor segun goles a favor
                    break;
                case "8":
                    mostrarSistema();// mostrar toString de las distintas estructuras usadas
                    break;
                case "0":// guardar en un txt los resultados de las estructuras
                    stop = true;
                    String log = "ESTRUCTURAS A LA HORA DE FINALIZAR EL SISTEMA\n";
                    actualizarLog(log);
                    log = "-------------------------------------------------------------//EQUIPOS//-----------------------------------------------------------------\n";
                    actualizarLog(log);
                    Lista listaAux = equipos.listar();
                    while (!listaAux.esVacia()) {
                        Equipo aux = (Equipo) listaAux.recuperar(1);
                        log = aux.datosEquipo();
                        actualizarLog(log);
                        listaAux.eliminar(1);
                    }
                    log = "-------------------------------------------------------------//MAPA//--------------------------------------------------------------------\n";
                    actualizarLog(log);
                    log = ciudades.toString();
                    actualizarLog(log);
                    log = "-------------------------------------------------------------//PARTIDOS//----------------------------------------------------------------\n";
                    actualizarLog(log);
                    for (Integer indice : partidos.keySet()) {
                        log = indice + ": " + partidos.get(indice).toString();
                        actualizarLog(log);
                    }
                    System.out.println("SE CIERRA EL SISTEMA");
                    break;
                default:
                    System.out.println("opcion invalida. \ningrese nuevamente.");
                    break;
            }
        } while (!stop);
    }

    public static void mostrarOpciones() {
        System.out.println("1. si desea modificar ciudades");
        System.out.println("2. si desea modificar equipos");
        System.out.println("3. si desea dar de alta partidos");
        System.out.println("4. si desea realizar consultas sobre los equipos");
        System.out.println("5. si desea realizar consultas sobre los partidos");
        System.out.println("6. si desea realizar consultas sobre viajes");
        System.out.println("7. si desea ver los equipos ordenados por cantidad de goles a favor");
        System.out.println("8. si desea ver las estructuras del sistema");
        System.out.println("0. si desea finalizar");
    }

    public static void verCiudades() {
        String opcion = "", nombreCiudad = "", valorSede = "", valorAlojamiento = "", letra = "", textoLog = "";
        boolean sede = false, alojamiento = false, stop = false;
        Ciudad laCiudad;
        do {
            System.out.println("1. si desea agregar una ciudad");
            System.out.println("2. si desea eliminar una ciudad");
            System.out.println("3. si desea modificar una ciudad");
            System.out.println("4. para salir");
            opcion = dato.next();
            switch (opcion) {
                case "1": // agregar una ciudad
                    System.out.println("ingrese el nombre de la ciudad:");
                    nombreCiudad = dato.nextLine();
                    nombreCiudad = dato.nextLine().toLowerCase();
                    if (ciudades.existeVertice(new Ciudad(nombreCiudad))) {
                        System.out.println("la ciudad " + nombreCiudad + " ya se encuentra cargada");
                    } else {
                        System.out.println("la ciudad " + nombreCiudad + " tiene alojamiento? ingrese S/N");
                        valorAlojamiento = dato.next();
                        if (valorAlojamiento.equalsIgnoreCase("s")) {
                            alojamiento = true;
                        }
                        System.out.println("la ciudad " + nombreCiudad + " es sede de la copa? ingrese S/N");
                        valorSede = dato.next();
                        if (valorSede.equalsIgnoreCase("s")) {
                            sede = true;
                        }
                        Ciudad nuCiudad = new Ciudad(nombreCiudad, alojamiento, sede);
                        ciudades.insertarVertice(nuCiudad);
                        System.out.println("la ciudad " + nombreCiudad + " se ha agregado con exito.");
                        textoLog = "se inserto la ciudad " + nombreCiudad;
                        actualizarLog(textoLog);
                    }
                    break;
                case "2": // eliminar una ciudad
                    System.out.println("ingrese el nombre de la ciudad:");
                    nombreCiudad = dato.nextLine();
                    nombreCiudad = dato.nextLine().toLowerCase();
                    if (ciudades.existeVertice(new Ciudad(nombreCiudad))) {
                        ciudades.eliminarVertice(new Ciudad(nombreCiudad));
                        System.out.println("la ciudad " + nombreCiudad + " se ha eliminado con exito.");
                        textoLog = "se elimino la ciudad " + nombreCiudad;
                        actualizarLog(textoLog);
                    } else {
                        System.out.println("la ciudad " + nombreCiudad + " NO se encuentra cargada");
                    }
                    break;
                case "3": // modificar una ciudad
                    System.out.println("ingrese el nombre de la ciudad:");
                    nombreCiudad = dato.nextLine();
                    nombreCiudad = dato.nextLine().toLowerCase();
                    laCiudad = (Ciudad) ciudades.obtenerVertice(new Ciudad(nombreCiudad));
                    if (laCiudad != null) {
                        System.out.println("que desea modificar de la ciudad " + nombreCiudad + "?\n");
                        System.out.println(laCiudad.toString());
                        System.out.println(
                                "ingrese 1 para modificar la sede, 2 para el alojamiento o 3 para modificar ambos");
                        letra = dato.next();
                        switch (letra) {
                            case "1":
                                laCiudad.cambiarAlojamiento();
                                break;
                            case "2":
                                laCiudad.cambiarSede();
                                break;
                            case "3":
                                laCiudad.cambiarSede();
                                laCiudad.cambiarAlojamiento();
                                break;
                            default:
                                System.out.println("opcion invalida");
                                break;
                        }
                        textoLog = "se modifico de la ciudad " + nombreCiudad + "la opcion " + letra + "\n";
                        System.out.println(laCiudad.toString());
                        actualizarLog(textoLog);
                        System.out.println("se modifico de la ciudad " + nombreCiudad + " la opcion " + letra);
                    } else {
                        System.out.println("la ciudad " + nombreCiudad + " NO se encuentra cargada");
                    }
                    break;
                case "4":
                    stop = true;
                    break;
                default:
                    System.out.println("opcion invalida. \ningrese nuevamente.");
                    break;
            }
        } while (!stop);
    }

    public static void verEquipos() {

        String opcion = "", nombrePais = "", apellido = "", grupo = "", letra = "", textoLog = "";
        boolean stop = false;
        do {
            System.out.println("1. si desea agregar un equipo");
            System.out.println("2. si desea eliminar un equipo");
            System.out.println("3. si desea modificar un equipo");
            System.out.println("4. para salir");
            opcion = dato.next();
            switch (opcion) {
                case "1": // agregar un equipo
                    System.out.println("ingrese el nombre del pais:");
                    nombrePais = dato.nextLine();
                    nombrePais = dato.nextLine().toUpperCase();
                    if (equipos.pertenece(new Equipo(nombrePais))) {
                        System.out.println("el pais " + nombrePais + " ya se encuentra cargada");
                    } else {
                        System.out.println("ingrese el apellido del tecnico del pais " + nombrePais);
                        apellido = dato.next().toLowerCase();
                        System.out.println("ingrese el grupo A/B/C/D del pais " + nombrePais);
                        grupo = dato.next().toUpperCase();
                        Equipo nuEquipo = new Equipo(nombrePais, apellido, grupo);
                        equipos.insertar(nuEquipo);
                        textoLog = "se inserto el equipo " + nombrePais;
                        actualizarLog(textoLog);
                        System.out.println("el pais " + nombrePais + " se ha agregado con exito.");
                    }
                    break;
                case "2": // eliminar un equipo
                    System.out.println("ingrese el nombre del pais:");
                    nombrePais = dato.nextLine();
                    nombrePais = dato.nextLine().toUpperCase();
                    if (equipos.pertenece(new Equipo(nombrePais))) {
                        equipos.eliminar(new Equipo(nombrePais));
                        System.out.println("el pais " + nombrePais + " ha sido eliminado con exito");
                        textoLog = "se elimino el equipo " + nombrePais;
                        actualizarLog(textoLog);
                    } else {
                        System.out.println("el pais " + nombrePais + " NO se encuentra cargado");
                    }
                    break;
                case "3": // modificar un equipo
                    System.out.println("ingrese el nombre del pais:");
                    nombrePais = dato.nextLine();
                    nombrePais = dato.nextLine().toUpperCase();
                    Equipo elEquipo = (Equipo) equipos.recuperar(new Equipo(nombrePais));
                    if (elEquipo != null) {
                        System.out.println("que desea modificar del pais " + nombrePais + "?");
                        System.out.println(
                                "ingrese 1 para modificar al tecnico, 2 para el grupo");
                        letra = dato.next();
                        switch (letra) {
                            case "1":
                                System.out.println("ingrese el apellido del tecnico del pais " + nombrePais);
                                apellido = dato.next().toLowerCase();
                                elEquipo.setTecnico(apellido);
                                break;
                            case "2":
                                System.out.println("ingrese el grupo A/B/C/D del pais " + nombrePais);
                                grupo = dato.next().toUpperCase();
                                elEquipo.setGrupo(grupo);
                                break;
                            default:
                                System.out.println("opcion invalida");
                                break;
                        }
                        textoLog = "se modifico del equipo " + nombrePais + " la opcion " + opcion;
                        actualizarLog(textoLog);
                        System.out.println("se modifico del equipo " + nombrePais + " la opcion " + opcion);
                    } else {
                        System.out.println("el pais " + nombrePais + " NO se encuentra cargado");
                    }
                    break;
                case "4":
                    stop = true;
                    break;
                default:
                    System.out.println("opcion invalida. \ningrese nuevamente.");
                    break;
            }
        } while (!stop);
    }

    public static void verPartidos() {
        String eq1 = "", eq2 = "", instancia = "", ciudad = "", estadio = "", textoLog = "";
        int golesE1 = 0, golesE2 = 0;
        Ciudad sede;
        Equipo equipo1 = null, equipo2 = null;
        System.out.println("ingrese el nombre del equipo 1:");
        eq1 = dato.nextLine();
        eq1 = dato.nextLine().toUpperCase();
        if (equipos.pertenece(new Equipo(eq1))) {
            System.out.println("ingrese el nombre del equipo 2:");
            eq2 = dato.nextLine().toUpperCase();
            if (equipos.pertenece(new Equipo(eq2))) {
                Partido unPartido = new Partido(eq1, eq2);
                if (partidos.containsKey(unPartido.getClavePartido())) {
                    System.out.println("el partido ya se encuentra cargado");
                } else {
                    System.out.println("partido entre " + eq1 + " y " + eq2);
                    System.out.println("ingrese la instancia del partido");
                    instancia = dato.nextLine().toUpperCase();
                    System.out.println("ingrese la ciudad donde se jugo el partido");
                    ciudad = dato.nextLine().toLowerCase();
                    if (ciudades.existeVertice(new Ciudad(ciudad))) {
                        sede = (Ciudad) ciudades.obtenerVertice(new Ciudad(ciudad));
                        if (sede.sedeCopa()) {
                            System.out.println("ingrese el estadio donde se jugo el partido");
                            estadio = dato.nextLine().toLowerCase();
                            System.out.println("Ingrese la cantidad de goles del equipo 1: " + eq1);
                            golesE1 = dato.nextInt();
                            System.out.println("Ingrese la cantidad de goles del equipo 2: " + eq2);
                            golesE2 = dato.nextInt();
                            equipo1 = (Equipo) equipos.recuperar(new Equipo(eq1));
                            equipo2 = (Equipo) equipos.recuperar(new Equipo(eq2));
                            equipo1.actualizarEquipo(golesE1, golesE2);
                            equipo2.actualizarEquipo(golesE2, golesE1);
                            Partido nuPartido = new Partido(eq1, eq2, instancia, ciudad, estadio, golesE1, golesE2);
                            partidos.put(nuPartido.getClavePartido(), nuPartido);
                            System.out.println("El partido fue cargado correctamente");
                            textoLog = "se cargaron los datos del partido " + eq1 + "-" + eq2;
                            actualizarLog(textoLog);
                        } else {
                            System.out.println("la ciudad " + ciudad + " NO es sede de la copa");
                        }
                    } else {
                        System.out.println("la ciudad " + ciudad + " NO se encuentra cargada");
                    }
                }
            } else {
                System.out.println(" el equipo 2 NO se encuentra cargado");
            }
        } else {
            System.out.println(" el equipo 1 NO se encuentra cargado");
        }
    }

    public static void consultaEquipos() {
        String pais = "", opcion = "", min = "", max = "";
        boolean stop = false;
        do {
            System.out.println("1. buscar los datos de un pais");
            System.out.println("2. mostrar una lista de equipos dentro de un rango alfabeticamente");
            System.out.println("3. para salir");
            opcion = dato.next();
            switch (opcion) {
                case "1":
                    System.out.println("ingrese el nombre de un pais:");
                    pais = dato.nextLine();
                    pais = dato.nextLine().toUpperCase();
                    if (equipos.pertenece(new Equipo(pais))) {
                        Equipo elEquipo = (Equipo) equipos.recuperar(new Equipo(pais));
                        System.out.println(elEquipo.datosEquipo());
                    } else {
                        System.out.println("el pais " + pais + " NO se encuentra cargado");
                    }
                    break;
                case "2":
                    System.out.println("ingrese la cadena minima de la lista:");
                    min = dato.next().toUpperCase();
                    System.out.println("ingrese la cadena maxima de la lista:");
                    max = dato.next().toUpperCase();
                    System.out.println(equipos.listarPorRango(min, max));
                    break;
                case "3":
                    stop = true;
                    break;
                default:
                    System.out.println("opcion invalida. \ningrese nuevamente.");
                    break;
            }
        } while (!stop);
    }

    public static void consultaPartidos() {
        String eq1 = "", eq2 = "";
        Iterator<Integer> indice = partidos.keySet().iterator();
        boolean encontrado = false;
        System.out.println("ingrese el nombre del equipo 1:");
        eq1 = dato.nextLine();
        eq1 = dato.nextLine().toUpperCase();
        if (equipos.pertenece(new Equipo(eq1))) {
            System.out.println("ingrese el nombre del equipo 2:");
            eq2 = dato.nextLine().toUpperCase();
            if (equipos.pertenece(new Equipo(eq2))) {
                while (indice.hasNext() && !encontrado) {
                    Integer clave = indice.next();
                    Partido elPartido = partidos.get(clave);
                    if (elPartido.compararEquipos(eq1, eq2)) {
                        encontrado = true;
                        System.out.println(elPartido.resultado());
                    }
                }
                if (!encontrado) {
                    System.out.println("no se ha jugado ningun partido entre " + eq1 + " y " + eq2);
                }
            } else {
                System.out.println(" el equipo 2 NO se encuentra cargado");
            }
        } else {
            System.out.println(" el equipo 1 NO se encuentra cargado");
        }
    }

    public static void consultaViajes() {
        String origen = "", destino = "", opcion = "", evitada = "", ciudad = "", filtro = "";
        boolean stop = false;
        Lista caminos;
        Ciudad ciudadOrigen, ciudadDestino, ciudadEvitada, ciudadIncluida;
        System.out.println("ingrese la ciudad de origen");
        origen = dato.nextLine();
        origen = dato.nextLine().toLowerCase();
        System.out.println("ingrese la ciudad de destino");
        destino = dato.nextLine().toLowerCase();
        ciudadOrigen = new Ciudad(origen);
        ciudadDestino = new Ciudad(destino);
        if (ciudades.existeVertice(ciudadOrigen) && ciudades.existeVertice(ciudadDestino)) {
            do {
                System.out.println("1. obtiene el camino de menor tiempo");
                System.out.println("2. obtiene el camino mas corto");
                System.out.println("3. obtiene el camino mas corto si pasar por una ciudad");
                System.out.println("4. todos los posibles caminos");
                System.out.println("5. para salir");
                opcion = dato.next();
                switch (opcion) {
                    case "1":
                        System.out.println("camino desde " + origen + " hasta " + destino + " en menor tiempo:");
                        System.out.println(ciudades.caminoMenorTiempo(ciudadOrigen, ciudadDestino));
                        break;
                    case "2":
                        System.out.println("camino mas corto desde " + origen + " hasta " + destino + ":");
                        System.out.println(ciudades.caminoMasCorto(ciudadOrigen, ciudadDestino));
                        break;
                    case "3":
                        System.out.println("ingrese la ciudad que desea evitar:");
                        evitada = dato.nextLine();
                        evitada = dato.nextLine().toLowerCase();
                        System.out.println("camino mas corto desde " + origen + " hasta " + destino + " sin pasar por "
                                + evitada + ":");
                        ciudadEvitada = new Ciudad(evitada);
                        System.out
                                .println(ciudades.caminoMasCortoSinCiudad(ciudadOrigen, ciudadDestino, ciudadEvitada));
                        break;
                    case "4":
                        System.out.println("todos los posibles caminos desde " + origen + " hasta " + destino + ":");
                        caminos = ciudades.todosLosCaminos(ciudadOrigen, ciudadDestino);
                        for (int i = 1; i <= caminos.longitud(); i++) {
                            System.out.println("camino " + i + ") " + caminos.recuperar(i).toString());
                        }
                        System.out.println("desea filtrar los caminos? S/N");
                        filtro = dato.next();
                        if (filtro.equalsIgnoreCase("S")) {
                            System.out.println("desea filtrar por alojamiento(A) o ciudad donde quiera pasar(C)?");
                            filtro = dato.next().toUpperCase();
                            switch (filtro) {
                                case "A":
                                    System.out.println("caminos filtrados segun alojamiento:");
                                    todosCaminosConAlojamiento(caminos);
                                    for (int i = 1; i <= caminos.longitud(); i++) {
                                        System.out.println("camino " + i + ") " + caminos.recuperar(i).toString());
                                    }
                                    break;
                                case "C":
                                    System.out.println("ingrese ciudad por donde desea pasar?");
                                    ciudad = dato.nextLine();
                                    ciudad = dato.nextLine().toLowerCase();
                                    ciudadIncluida = new Ciudad(ciudad);
                                    if (ciudades.existeVertice(ciudadIncluida)) {
                                        System.out.println("caminos filtrados que pasen por " + ciudad + ":");
                                        todosCaminosPorCiudad(caminos, ciudadIncluida);
                                        for (int i = 1; i <= caminos.longitud(); i++) {
                                            System.out.println("camino " + i + ") " + caminos.recuperar(i).toString());
                                        }
                                    } else {
                                        System.out.println("la ciudad " + ciudad + " NO se encuentra cargada");
                                    }

                                    break;

                                default:
                                    System.out.println("opcion invalida");
                                    break;
                            }
                        }
                        break;
                    case "5":
                        stop = true;
                        break;

                    default:
                        System.out.println("opcion invalida. \ningrese nuevamente.");
                        break;
                }
            } while (!stop);
        } else {
            System.out.println("error. una de las ciudades NO se encuentra cargada");
        }
    }

    public static void listadoEquipos() {
        HeapMax arbolGoles = new HeapMax();
        Lista listadoEquipos = equipos.listar();
        int indice = 1;
        while (!listadoEquipos.esVacia()) {
            EquipoGoles elEquipo = new EquipoGoles((Equipo) listadoEquipos.recuperar(1));
            arbolGoles.insertar(elEquipo);
            listadoEquipos.eliminar(1);
        }
        System.out.println("lista de equipos ordenados de mayor a menor segun goles a favor:");
        while (!arbolGoles.esVacio()) {
            System.out.println(indice + ") " + arbolGoles.recuperarCima().toString());
            arbolGoles.eliminarCima();
            indice++;
        }
    }

    public static void mostrarSistema() {
        System.out.println(
                "//////////////////////////////////////////////////////////estructura de ciudades//////////////////////////////////////////////////////////");
        System.out.println(ciudades.toString());
        System.out.println(
                "//////////////////////////////////////////////////////////estructura de equipos//////////////////////////////////////////////////////////");
        System.out.println(equipos.toString());
        System.out.println(
                "//////////////////////////////////////////////////////////estructura de partidos//////////////////////////////////////////////////////////");
        for (Integer indice : partidos.keySet()) {
            System.out.println(indice + ") " + partidos.get(indice).toString());
        }
    }

    public static void actualizarLog(String cad) {
        try (Writer w = new FileWriter("log.txt", true)) {
            w.write(cad + "\r\n");
            w.close();

        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void limpiarLog() {
        try (Writer w = new FileWriter("log.txt")) {
            w.write("INICIO DEL SISTEMA\r\n");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    // metodos para filtrar caminos

    public static void todosCaminosPorCiudad(Lista todosCaminos, Ciudad ciudad) {
        for (int i = 1; i <= todosCaminos.longitud(); i++) {
            Lista camino = (Lista) todosCaminos.recuperar(i);
            if (camino.localizar(ciudad) < 0) {
                todosCaminos.eliminar(i);
            }
        }
    }

    public static void todosCaminosConAlojamiento(Lista listadoCaminos) {
        int j;
        boolean encontrado;
        for (int i = 1; i <= listadoCaminos.longitud(); i++) {
            Lista camino = (Lista) listadoCaminos.recuperar(i);
            encontrado = false;
            j = 2;
            while (j < camino.longitud() && !encontrado) {
                Ciudad ciudadActual = (Ciudad) camino.recuperar(j);
                if (ciudadActual.tieneAlojamiento()) {
                    encontrado = true;
                }
                j++;
            }
            if (!encontrado) {
                listadoCaminos.eliminar(i);
            }
        }
    }
}