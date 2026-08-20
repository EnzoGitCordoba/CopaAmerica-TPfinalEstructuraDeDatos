package estructuras.grafos;

import estructuras.lineales.*;

public class GrafoEtiquetado {

    private NodoVert inicio;

    // constructor
    public GrafoEtiquetado() {
        inicio = null;
    }

    //
    public boolean insertarVertice(Object nuevoVert) {
        boolean encontrado = false; // suponemos que el elemento ya esta en el grafo
        NodoVert aux = this.ubicarVertice(nuevoVert);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVert, this.inicio, null); // insertamos nodos en el grafo
            encontrado = true;
        }
        return encontrado;
    }

    private NodoVert ubicarVertice(Object buscado) { //recorre los nodos a travez del enlase "siguinte" para ver si encuentro el buscado
        NodoVert aux = this.inicio;
        while (aux != null && !(aux.getElem().equals(buscado))) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public Lista profundidad() {
        Lista listaVisitados = new Lista();
        NodoVert aux = this.inicio;

        while (aux != null) {
            if (listaVisitados.localizar(aux.getElem()) < 0) { //si el nodo no esta en la lista de visitados
                listaVisitados = ProfundidadDesde(aux, listaVisitados); //llamo a profundidadDesde ese nodo
            }
            aux = aux.getSigVertice();
        }

        return listaVisitados;
    }

    private Lista ProfundidadDesde(NodoVert vertActual, Lista visitados) {
        if (vertActual != null) {
            visitados.insertar(vertActual.getElem(), visitados.longitud() + 1);
            NodoAdy siguiente = vertActual.getPrimerAdy();
            while (siguiente != null) {
                if (visitados.localizar(siguiente.getVertice().getElem()) < 0) {
                    visitados = ProfundidadDesde(siguiente.getVertice(), visitados);
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return visitados;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean encontrado = false;
        Lista listaVisitados = new Lista();
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        NodoVert nodoDestino = buscarVertice(this.inicio, destino);
        if (nodoOrigen != null && nodoDestino != null) {
            encontrado = buscarCamino(nodoOrigen, nodoDestino, listaVisitados);
        }
        return encontrado;
    }

    private boolean buscarCamino(NodoVert nodoOrigen, NodoVert nodoDestino, Lista visitados) {
        boolean encontrado = false;
        NodoAdy siguiente = nodoOrigen.getPrimerAdy();
        if (nodoOrigen != null) {
            if (siguiente.getVertice().getElem().equals(nodoDestino.getElem())) {
                encontrado = true;
            } else {
                visitados.insertar(nodoOrigen.getElem(), visitados.longitud() + 1);
                while (!encontrado && siguiente != null) {
                    if (visitados.localizar(siguiente.getVertice().getElem()) < 0) {
                        encontrado = buscarCamino(siguiente.getVertice(), nodoDestino, visitados);
                    }
                    siguiente = siguiente.getSigAdyacente();
                }
            }
        }
        return encontrado;
    }

    public boolean insertarArco(Object origen, Object dest, Object etiqueta) {
        boolean encontrado = false;
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        NodoVert nodoDestino = buscarVertice(this.inicio, dest);
        if (nodoOrigen != null && nodoDestino != null) {
            encontrado = true;
            if (origen.equals(dest)) {
                crearEnlace(nodoOrigen, nodoDestino, etiqueta);
            } else {
                crearEnlace(nodoOrigen, nodoDestino, etiqueta);
                crearEnlace(nodoDestino, nodoOrigen, etiqueta);
            }

        }
        return encontrado;
    }

    private NodoVert buscarVertice(NodoVert vertActual, Object buscado) {
        NodoVert nodoBuscado = null;
        if (vertActual != null) {
            if (vertActual.getElem().equals(buscado)) {
                nodoBuscado = vertActual;
            } else {
                nodoBuscado = buscarVertice(vertActual.getSigVertice(), buscado);
            }
        }
        return nodoBuscado;
    }

    private void crearEnlace(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        NodoAdy siguiente = new NodoAdy(nodoDestino, null, etiqueta);
        if (nodoOrigen.getPrimerAdy() == null) {
            nodoOrigen.setPrimerAdy(siguiente);
        } else {
            NodoAdy siguienteAdy = nodoOrigen.getPrimerAdy();
            while (siguienteAdy.getSigAdyacente() != null) {
                siguienteAdy = siguienteAdy.getSigAdyacente();
            }
            siguienteAdy.setSigAdyacente(siguiente);
        }

        // otra opcion seria
        // nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, nodoOrigen.getPrimerAdy(),
        // etiqueta));
        // nodoDestino.setPrimerAdy(new NodoAdy(nodoOrigen, nodoDestino.getPrimerAdy(),
        // etiqueta));
        // esto agregaria el nuevo enlace al principio de la lista de adyacentes, en
        // lugar de al final
    }

    public String toString() {
        String cad = "";
        NodoVert recorrercolaVertices = this.inicio;
        NodoAdy recorrerAdyacentes;
        if (!esVacio()) {
            recorrercolaVertices = this.inicio;
            while (recorrercolaVertices != null) {
                cad = cad + "Vertice: " + recorrercolaVertices.getElem().toString() + " arcos: ";
                recorrerAdyacentes = recorrercolaVertices.getPrimerAdy();
                while (recorrerAdyacentes != null) {
                    cad = cad + "[ etiqueta: " + recorrerAdyacentes.getEtiqueta() + " enlace: "
                            + recorrerAdyacentes.getVertice().getElem() + " ]";
                    if (recorrerAdyacentes.getSigAdyacente() != null) {
                        cad += ", ";
                    }
                    recorrerAdyacentes = recorrerAdyacentes.getSigAdyacente();
                }
                cad = cad + "\n";
                recorrercolaVertices = recorrercolaVertices.getSigVertice();
            }

        } else {
            cad = "el grafo no tiene vertices";
        }

        return cad;
    }

    public boolean eliminarVertice(Object eliminado) {
        boolean encontrado = false;
        NodoVert nodoBuscado = buscarVertice(this.inicio, eliminado), nodoSiguiente = this.inicio.getSigVertice(),
                anterior = this.inicio;
        NodoAdy siguiente;
        if (!esVacio() && nodoBuscado != null) {
            encontrado = true;
            siguiente = nodoBuscado.getPrimerAdy();
            // elimino primero los enlaces
            while (siguiente != null) {
                eliminarEnlace(siguiente.getVertice(), nodoBuscado, siguiente.getEtiqueta());
                siguiente = siguiente.getSigAdyacente();
            }
            // elimino el vertice
            if (this.inicio.getElem().equals(eliminado)) {
                if (this.inicio.getSigVertice() == null) {
                    vaciar();
                } else {
                    this.inicio = this.inicio.getSigVertice();
                }
            } else {
                if (nodoSiguiente.getElem().equals(eliminado)) {
                    nodoSiguiente = nodoSiguiente.getSigVertice();
                    anterior.setSigVertice(nodoSiguiente);
                } else {
                    while (nodoSiguiente != null && !nodoSiguiente.getElem().equals(eliminado)) {
                        anterior = nodoSiguiente;
                        nodoSiguiente = nodoSiguiente.getSigVertice();
                    }
                    anterior.setSigVertice(nodoSiguiente.getSigVertice());
                }
            }

        }
        return encontrado;
    }

    public boolean eliminarArco(Object origen, Object destino, Object etiqueta) {
        boolean encontrado = false;
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        NodoVert nodoDestino = buscarVertice(this.inicio, destino);
        if (nodoOrigen != null && nodoDestino != null && existeArcoConEtiqueta(nodoOrigen, nodoDestino, etiqueta)) {
            encontrado = true;
            if (origen.equals(destino)) {
                eliminarEnlace(nodoOrigen, nodoDestino, etiqueta);
            } else {
                eliminarEnlace(nodoOrigen, nodoDestino, etiqueta);
                eliminarEnlace(nodoDestino, nodoOrigen, etiqueta);
            }

        }
        return encontrado;
    }

    private boolean existeArcoConEtiqueta(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        boolean encontrado = false;
        NodoAdy siguiente = nodoOrigen.getPrimerAdy();
        if (nodoOrigen != null && nodoDestino != null) {
            while (siguiente != null && !encontrado) {
                if (siguiente.getVertice().getElem().equals(nodoDestino.getElem())
                        && siguiente.getEtiqueta().equals(etiqueta)) {
                    encontrado = true;
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return encontrado;
    }

    private void eliminarEnlace(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        NodoAdy siguiente = nodoOrigen.getPrimerAdy();
        NodoAdy anterior = null;
        boolean encontrado = false;
        if (siguiente.getSigAdyacente() == null) {
            nodoOrigen.setPrimerAdy(null);
        } else {
            while (!encontrado && siguiente != null) {
                if (siguiente.getVertice().getElem().equals(nodoDestino.getElem())
                        && siguiente.getEtiqueta().equals(etiqueta)) {
                    encontrado = true;
                    if (anterior == null) {
                        nodoOrigen.setPrimerAdy(siguiente.getSigAdyacente());
                    } else {
                        anterior.setSigAdyacente(siguiente.getSigAdyacente());
                    }
                }
                anterior = siguiente;
                siguiente = siguiente.getSigAdyacente();
            }
        }

    }

    public boolean existeVertice(Object buscado) {
        boolean encontrado = false;
        if (this.inicio != null && buscarVertice(this.inicio, buscado) != null) {
            encontrado = true;
        }
        return encontrado;
    }

    public boolean existeArco(Object origen, Object destino) {
        boolean encontrado = false;
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        NodoVert nodoDestino = buscarVertice(this.inicio, destino);
        if (nodoOrigen != null && nodoDestino != null) {
            encontrado = buscarArco(nodoOrigen.getPrimerAdy(), nodoDestino);
        }
        return encontrado;
    }

    private boolean buscarArco(NodoAdy nodoSiguiente, NodoVert nodoDestino) {
        boolean encontrado = false;
        while (nodoSiguiente != null && !encontrado) {
            if (nodoSiguiente.getVertice().getElem().equals(nodoDestino.getElem())) {
                encontrado = true;
            }
            nodoSiguiente = nodoSiguiente.getSigAdyacente();
        }
        return encontrado;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public void vaciar() {
        this.inicio = null;
    }

    public Lista anchura(Object origen) {
        Lista lista = new Lista();
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        if (!esVacio() && nodoOrigen != null) {
            while (nodoOrigen != null) {
                if (lista.localizar(nodoOrigen.getElem()) < 0) {
                    lista = anchuraDesde(nodoOrigen, lista);
                }
                nodoOrigen = nodoOrigen.getSigVertice();
            }
        }
        return lista;
    }

    private Lista anchuraDesde(NodoVert nodoOrigen, Lista visitados) {
        Cola verticesFaltantes = new Cola();
        Object objetoVert;
        NodoVert vertActual;
        NodoAdy siguiente;
        visitados.insertar(nodoOrigen.getElem(), visitados.longitud() + 1);
        verticesFaltantes.poner(nodoOrigen.getElem());
        while (!verticesFaltantes.esVacia()) {
            objetoVert = verticesFaltantes.obtenerFrente();
            verticesFaltantes.sacar();
            vertActual = buscarVertice(this.inicio, objetoVert);
            siguiente = vertActual.getPrimerAdy();
            while (siguiente != null) {
                if (visitados.localizar(siguiente.getVertice().getElem()) < 0) {
                    visitados.insertar(siguiente.getVertice().getElem(), visitados.longitud() + 1);
                    verticesFaltantes.poner(siguiente.getVertice().getElem());
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return visitados;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista listaVisitados = new Lista();
        Lista caminoCorto = new Lista();

        NodoVert nodoOrigen = buscarVertice(this.inicio, origen);
        NodoVert nodoDestino = buscarVertice(this.inicio, destino);

        if (!esVacio() && nodoOrigen != null && nodoDestino != null) {
            if (origen.equals(destino)) {  //caso particular de si me encuentro con origen y destino igual
                caminoCorto.insertar(destino, caminoCorto.longitud() + 1);
            } else {
                caminoCorto = caminoCortoDesde(nodoOrigen, nodoDestino, listaVisitados, caminoCorto);
            }
        }
        return caminoCorto;
    }

    // busco en profundidad
    private Lista caminoCortoDesde(NodoVert n, NodoVert destino, Lista visitado, Lista caminoCorto) {
        if (n != null) {
            boolean encontrado = false;
            visitado.insertar(n.getElem(), visitado.longitud() + 1); // inserto el nodo en la lista
            NodoAdy siguiente = n.getPrimerAdy(); // me muevo al siguiente ady
            while (siguiente != null && !encontrado) { // mientras tenga ady y no lo encuentre hacer
                if (siguiente.getVertice().getElem().equals(destino.getElem())) { // si lo encuentro rompo el while y
                                                                                  // pregunto
                    encontrado = true;
                    if (caminoCorto.longitud() == 0 || visitado.longitud() < caminoCorto.longitud() - 1) { // si este es el camino  mas corto encontrado hasta el momento

                        caminoCorto = visitado.clone(); // clono la lista de las visitas hasta el momento
                        caminoCorto.insertar(destino.getElem(), visitado.longitud() + 1); // agrego mi ultimo elemento
                                                                                          // (destino)
                    }
                } else { // sino
                    if (visitado.localizar(siguiente.getVertice().getElem()) < 0) { // analizo si el nodo donde estoy
                                                                                    // parado ya fue visitado (para no
                                                                                    // dar vueltas en circulo)
                        if (visitado.longitud() < caminoCorto.longitud() - 1 || caminoCorto.longitud() == 0) { // Si el camino de  visitado sigue siendo mas corto que caminoCorto o no tengo un camino ya sigo buscando

                            caminoCorto = caminoCortoDesde(siguiente.getVertice(), destino, visitado, caminoCorto); // avanzo en el grafo

                            visitado.eliminar(visitado.longitud()); // saco el nodo visitado para limpiar esta lista
                                                                    // auxiliar
                        }

                    }
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return caminoCorto;
    }

    public Lista caminoMenorTiempo(Object origen, Object destino) {
        Lista caminoCorto = new Lista(), listaVisitados = new Lista();
        Double tiempo = 0.0;
        Double[] menorTiempo = new Double[1];
        menorTiempo[0] = 0.0;
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen), nodoDestino = buscarVertice(this.inicio, destino);
        if (!esVacio() && nodoOrigen != null && nodoDestino != null) {
            if (origen.equals(destino)) {
                caminoCorto.insertar(destino, caminoCorto.longitud() + 1);
            } else {
                caminoCorto = caminoMenorTiempoDesde(nodoOrigen, nodoDestino, tiempo, menorTiempo, listaVisitados,
                        caminoCorto);
            }
        }
        System.out.println("tiempo del camino: " + menorTiempo[0]);
        return caminoCorto;
        //este sout esta por que necesito devolver tanto la lista como el menor tiempo . podria devolver un arreglo de 2 posiciones en vez de la lista
    }

    private Lista caminoMenorTiempoDesde(NodoVert nodoOrigen, NodoVert nodoDestino, Double tiempo,
            Double[] menorTiempo, Lista visitado, Lista caminoCorto) {
        if (nodoOrigen != null) {
            visitado.insertar(nodoOrigen.getElem(), visitado.longitud() + 1); // agrego el nodo que estoy parado en la lista de visitado
            NodoAdy siguiente = nodoOrigen.getPrimerAdy(); // me muevo al primer ady
            while (siguiente != null) { //mientras tenga ady
                double tiempoArco = tiempo + (int) siguiente.getEtiqueta(); // valor propio de ESTE arco (el que conecta origen y siguiente)

                if (siguiente.getVertice().getElem().equals(nodoDestino.getElem())) {  //si siguiente es el buscado
                    if (menorTiempo[0] == 0 || tiempoArco < menorTiempo[0]) { // analizo si tengo un tiempo guardado y es menor o no tengo
                        caminoCorto = visitado.clone(); //guardo la lista para este camino
                        menorTiempo[0] = tiempoArco;// guardo el tiempo
                        caminoCorto.insertar(nodoDestino.getElem(), caminoCorto.longitud() + 1);
                    }
                } else { //si no
                    if (visitado.localizar(siguiente.getVertice().getElem()) < 0) { // veo si ya pise el siguiente dentro de la lista de visitados
                        if (menorTiempo[0] == 0 || tiempoArco < menorTiempo[0]) { // si todavia no encontre un camino o si mi tiempo recorrido hasta el momento es menor , sigo recorriendo
                            caminoCorto = caminoMenorTiempoDesde(siguiente.getVertice(), nodoDestino, tiempoArco,
                                    menorTiempo, visitado, caminoCorto);
                            visitado.eliminar(visitado.longitud()); // al volver , saco de la lista de visitados
                        }
                    }
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return caminoCorto;
    }

    public Lista caminoMasCortoSinCiudad(Object origen, Object destino, Object evitada) {
        Lista listaVisitados = new Lista(), caminoCorto = new Lista();
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen), nodoDestino = buscarVertice(this.inicio, destino),
                nodoEvitar = buscarVertice(this.inicio, evitada);
        if (!esVacio() && nodoOrigen != null && nodoDestino != null && !evitada.equals(origen)
                && !evitada.equals(destino)) {
            if (origen.equals(destino)) {
                caminoCorto.insertar(destino, caminoCorto.longitud() + 1);
            } else {
                if (nodoEvitar == null) {
                    caminoCorto = caminoCortoDesde(nodoOrigen, nodoDestino, listaVisitados, caminoCorto);
                } else {
                    caminoCorto = caminoCortoAux(nodoOrigen, nodoDestino, nodoEvitar, listaVisitados, caminoCorto);
                }
            }
        }
        return caminoCorto;
    }
// mismo recorrido que antes , pero con la condicional de no pisar la cuidad evitada
    private Lista caminoCortoAux(NodoVert nodoOrigen, NodoVert nodoDestino, NodoVert ciudadEvitada, Lista camino,
            Lista caminoCorto) {
        if (nodoOrigen != null) {
            boolean encontrado = false;
            camino.insertar(nodoOrigen.getElem(), camino.longitud() + 1);
            NodoAdy siguiente = nodoOrigen.getPrimerAdy();
            while (siguiente != null && !encontrado) {
                if (!siguiente.getVertice().getElem().equals(ciudadEvitada.getElem())) {
                    if (siguiente.getVertice().getElem().equals(nodoDestino.getElem())) {
                        encontrado = true;
                        if (caminoCorto.longitud() == 0 || camino.longitud() < caminoCorto.longitud() - 1) {
                            caminoCorto = camino.clone();
                            caminoCorto.insertar(nodoDestino.getElem(), caminoCorto.longitud() + 1);
                        }
                    } else {
                        if (camino.localizar(siguiente.getVertice().getElem()) < 0) {
                            if (camino.longitud() < caminoCorto.longitud() - 1 || caminoCorto.longitud() == 0) {
                                caminoCorto = caminoCortoAux(siguiente.getVertice(), nodoDestino, ciudadEvitada, camino,
                                        caminoCorto);
                                camino.eliminar(camino.longitud());
                            }

                        }
                    }
                }
                siguiente = siguiente.getSigAdyacente();
            }
        }
        return caminoCorto;
    }

    public Lista todosLosCaminos(Object origen, Object destino) {
        Lista caminos = new Lista(), listadoCaminos = new Lista(); //lista de caminos y lista de listas
        NodoVert nodoOrigen = buscarVertice(this.inicio, origen), nodoDestino = buscarVertice(this.inicio, destino);
        if (!esVacio() && nodoOrigen != null && nodoDestino != null) { //si tengo grafo , origen y destino
            todosCaminos(nodoOrigen, nodoDestino, caminos, listadoCaminos); //busco todos los caminos
        }
        return listadoCaminos;

    }

    private void todosCaminos(NodoVert nodoOrigen, NodoVert nodoDestino, Lista camino, Lista listadoCaminos) {
        if (nodoOrigen != null) {
            camino.insertar(nodoOrigen.getElem(), camino.longitud() + 1); //me paro en un nodo y lo listo
            if (nodoOrigen.getElem().equals(nodoDestino.getElem())) {  //si el nodo donde estoy parado es  igual al destino
                Lista lista = camino.clone(); // clono el camino y lo guardo en la lista de lista
                listadoCaminos.insertar(lista, listadoCaminos.longitud() + 1);
            } else {
                NodoAdy siguiente = nodoOrigen.getPrimerAdy(); // me muevo al ady
                while (siguiente != null) { // recorro todos los ady
                    if (camino.localizar(siguiente.getVertice().getElem()) < 0) { // si aun no pise este nodo (evito vueltas en circulos)
                        todosCaminos(siguiente.getVertice(), nodoDestino, camino, listadoCaminos); //llamo recursivamente para listarlo posteriormente y encontrar un camino con el
                        camino.eliminar(camino.longitud()); // al volver lo saco de la lista
                    }
                    siguiente = siguiente.getSigAdyacente();
                }
            }
        }
    }

    public Object obtenerVertice(Object nombrePais) {
        // creo este metodo publico para acceder desde el menu , ya que la busqueda se
        // hace en privado
        Object retorno = null;
        if ((buscarVertice(inicio, nombrePais) != null)) {
            retorno = buscarVertice(inicio, nombrePais).getElem();
        }

        return retorno;
    }
}
