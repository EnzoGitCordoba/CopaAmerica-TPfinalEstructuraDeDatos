package estructuras.conjuntistas;

import estructuras.conjuntistas.*;
import estructuras.lineales.*;

public class ArbolAVL {

    private NodoAVL raiz;

    //constructor vacio
    public ArbolAVL() {
        raiz = null;
    }

    @SuppressWarnings("rawtypes")
    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) { //si no hay elementos, añado la raiz
            this.raiz = new NodoAVL(elem, null, null);
        } else {
            exito = insertarAux(this.raiz, elem);  // envio la raiz justo con el elemento a insertar
            if (exito) {// si se pudo insertar el elemento, analizo si el arbol quedo balanceado
                this.raiz = analizarBalance(this.raiz);
            }
        }
        return exito;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean insertarAux(NodoAVL nodo, Comparable elem) { // recibo el arbol junto con el elemento a insertar
        boolean exito;
        if ((elem.compareTo(nodo.getElem()) == 0)) { //si es el mismo elemento , no puede ingresar al arbol
            exito = false;
        } else {// si no es el mismo elemento analizo donde ingresarlo
            if (elem.compareTo(nodo.getElem()) < 0) { //si el elemento es mas chico
                if (nodo.getIzquierdo() != null) {// me fijo si tengo hijo izq , si tengo
                    exito = insertarAux(nodo.getIzquierdo(), elem);// llamo recursivamente con el hijo izq para que encuentre el lugar correspondiente
                    if (exito) {
                        nodo.setIzquierdo(analizarBalance(nodo.getIzquierdo())); // cada vez que vuelve analizo si el subarbol esta balanceado y seto el hijo con la raiz de este sub arbol
                    }
                } else { // agrego el hijo
                    nodo.setIzquierdo(new NodoAVL(elem, null, null));
                    nodo.recalcularAltura(); //recalculo la altura
                    exito = true;
                }
            } else {//si el elemento es mas grande
                if (nodo.getDerecho() != null) {   // me fijo si tengo hijo der , si tengo
                    exito = insertarAux(nodo.getDerecho(), elem);// llamo recursivamente con el hijo der para que encuentre el lugar correspondiente
                    if (exito) {
                        nodo.setDerecho(analizarBalance(nodo.getDerecho()));// cada vez que vuelve analizo si el subarbol esta balanceado y seto el hijo con la raiz de este sub arbol
                    }
                } else {
                    nodo.setDerecho(new NodoAVL(elem, null, null));
                    nodo.recalcularAltura(); //recalculo la altura
                    exito = true;
                }
            }
        }
        return exito;
    }

    private NodoAVL analizarBalance(NodoAVL nodo) {
        nodo.recalcularAltura();
        NodoAVL nuevaRaiz = nodo;

        if (obtenerBalance(nodo) == 2) {  //esta desbalanceado a la izq entonces analizo el signo del hijo para determinar la rotacion
            if (obtenerBalance(nodo.getIzquierdo()) >= 0) { //si el hijo tiene balance del mismo signo o 0
                // rotacion simple derecha
                nuevaRaiz = rotacionSimpleDerecha(nodo);
            } else { //si no
                // rotacion doble izquierda-derecha
                nodo.setIzquierdo(rotacionSimpleIzquierda(nodo.getIzquierdo()));
                nuevaRaiz = rotacionSimpleDerecha(nodo);
            }
        }
        if (obtenerBalance(nodo) == -2) {//esta desbalanceado a la der entonces analizo el signo del hijo para determinar la rotacion
            if (obtenerBalance(nodo.getDerecho()) <= 0) {//si el hijo tiene balance del mismo signo o 0
                // rotacion simple izquierda
                nuevaRaiz = rotacionSimpleIzquierda(nodo);
            } else {//si no
                // rotacion doble derecha-izquierda
                nodo.setDerecho(rotacionSimpleDerecha(nodo.getDerecho()));
                nuevaRaiz = rotacionSimpleIzquierda(nodo);
            }
        }
        nodo.recalcularAltura();
        return nuevaRaiz;
    }

    private int obtenerBalance(NodoAVL n) {
        int balance;
        if (n.getIzquierdo() == null) {
            balance = -1; //si no tengo hijo izq , -1
        } else {
            balance = n.getIzquierdo().getAltura();// si tengo hijo izq  obtengo su altura
        }
        //una vez obtenido el valor del balance dele hijo izq , calculo el balance de la raiz  con el derecho
        if (n.getDerecho() == null) {
            balance = balance - (-1); //si no tengo hijo der , -1 y se lo resto al balance del izq
        } else {
            balance = balance - (n.getDerecho().getAltura());// si tengo hijo der obtengo su altura y se lo resto al balance del izq
        }
        return balance;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean eliminar(Comparable elemento) {
        boolean exito = false;  // bandera que comprueba si pude eliminar el  nodo con el elemento
        if (this.raiz != null) {// si tengo un arbol analizo.
            if (this.raiz.getElem().compareTo(elemento) == 0) { // si el elemento a eliminar esta en la raiz
                exito = true;
                if (this.raiz.getIzquierdo() == null && this.raiz.getDerecho() == null) { //pregunto si tiene hijos , de no tener la raiz es nula  y exito true
                    this.raiz = null;
                } else { //si tiene hijos , analizo como eliminar
                    borrarNodo(this.raiz);
                    this.raiz = analizarBalance(this.raiz);
                }
            } else {
                exito = eliminarAux(this.raiz, elemento);  //en el caso de que el elemento a eliminar no sea la raiz analizo si esta y elimino
                this.raiz = analizarBalance(this.raiz);
            }
        }
        return exito;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean eliminarAux(NodoAVL n, Comparable elemento) {
        boolean exito = false;// presupongo que el elemento a eliminar no se encuentra en el arbol , si lo encuento , lo elimino y la bandera es true .
        if (n != null) {
            if (elemento.compareTo(n.getElem()) < 0) { //si el elemento que busco eliminar es más chico que el nodo donde estoy parado
                if (n.getIzquierdo() != null) { //analizo si tiene hijo izquierdo
                    if (n.getIzquierdo().getElem().compareTo(elemento) == 0) { //si el hijo izquierdo es el elemento a borrar
                        exito = true;
                        NodoAVL hijo = n.getIzquierdo(); // pongo un puntero en el hijo izquierdo
                        if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {  //si el hijo no tiene hijos , simplemente elimino este nodo  desconectando el nodo n del su hijo
                            n.setIzquierdo(null);
                            n.recalcularAltura(); //recalculo la altura
                        } else {

                              n.setIzquierdo(borrarNodo(hijo));
                        }
                    } else {
                        exito = eliminarAux(n.getIzquierdo(), elemento);
                        n.setIzquierdo(analizarBalance(n.getIzquierdo()));

                    }

                }
            } else { //hago el mismo análisis para el lado derecho
                if (n.getDerecho() != null) {
                    if (n.getDerecho().getElem().compareTo(elemento) == 0) {
                        exito = true;
                        NodoAVL hijo = n.getDerecho();
                        if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                            n.setDerecho(null);
                            n.recalcularAltura();
                        } else {
                            borrarNodo(hijo);
                            n.setDerecho(analizarBalance(hijo));
                        }
                    } else {
                        exito = eliminarAux(n.getDerecho(), elemento);
                        n.setDerecho(analizarBalance(n.getDerecho()));

                    }

                }
            }
        }
        return exito;
    }

    @SuppressWarnings({ "rawtypes" })
    private NodoAVL borrarNodo(NodoAVL n) {
        // si tiene 2 hijos  bbuscamos candidato
        if (n.getIzquierdo() != null && n.getDerecho() != null) {
            if (n.getIzquierdo().getDerecho() != null) {
                //buscamos el candidato y desenganchamos
                n.setElem(buscarCandidato(n.getIzquierdo(), n));
            } else {
                // el candidato directo es su hijo izquierdo
                n.setElem(n.getIzquierdo().getElem());
                n.setIzquierdo(n.getIzquierdo().getIzquierdo()); // desenganchamos el hijo izq guardando sus subarboles
            }
            n.recalcularAltura();
            return analizarBalance(n);
        }

        // tiene 1 solo hijo o es hoja
        NodoAVL sobreviviente = (n.getIzquierdo() != null) ? n.getIzquierdo() : n.getDerecho();
        return sobreviviente;
    }


    @SuppressWarnings({ "rawtypes" })
    private Comparable buscarCandidato(NodoAVL n, NodoAVL padre) {
        Comparable candidato;
        if (n.getDerecho().getDerecho() == null) {
            // encontramos el mayor
            candidato = n.getDerecho().getElem();
            // Le asignamos a n.derecho los hijos que traía el candidato a su izquierda
            n.setDerecho(n.getDerecho().getIzquierdo());
        } else {
            candidato = buscarCandidato(n.getDerecho(), n);
        }

        n.recalcularAltura();
        if (padre.getIzquierdo() == n) {
            padre.setIzquierdo(analizarBalance(n));
        } else {
            padre.setDerecho(analizarBalance(n));
        }

        return candidato;
    }


    private NodoAVL rotacionSimpleDerecha(NodoAVL pivote) {
        //nos valemos de un temporal para rotar los nodos
        NodoAVL h = pivote.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(pivote);
        pivote.setIzquierdo(temp);
        pivote.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVL rotacionSimpleIzquierda(NodoAVL pivote) {
        //nos valemos de un temporal para rotar los nodos
        NodoAVL h = pivote.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(pivote);
        pivote.setDerecho(temp);
        pivote.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    public String toString() {
        String cad;
        if (raiz != null) {
            cad = privateToString(raiz); //creamos el metodo privateToString para crear la cadena de caracteres
        } else {
            cad = "el arbol esta vacio";
        }

        return cad;
    }

    private String privateToString(NodoAVL n) { // toString en preorden (escribo donde estoy parado , me desplazo a la izq , y luego a la derecha al no poder seguir por la izq
        String cad = "";
        if (n != null) {
            cad += "(" + n.getElem().toString() + ")";
            if (n.getIzquierdo() != null) {
                cad = cad + " HI: " + n.getIzquierdo().getElem().toString();
            }
            if (n.getDerecho() != null) {
                cad = cad + " HD : " + n.getDerecho().getElem().toString();
            }
            cad = cad + " balance: " + obtenerBalance(n) + " altura: " + n.getAltura() + "\n";
            cad += privateToString(n.getIzquierdo());
            cad += privateToString(n.getDerecho());
        }
        return cad;
    }

    @SuppressWarnings({ "rawtypes" })
    public boolean pertenece(Comparable elem) {
        boolean res = false;
        if (raiz != null) { //si tengo arbol busco el elemento dentro de el
            res = privatePertenece(raiz, elem);
        }

        return res;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean privatePertenece(NodoAVL n, Comparable elem) {
        boolean res = false; //presuponemos que el elemento no esta
        //recorro el arbol preguntando siempre si estoy parado sobre el nodo que contiene al elemento y en caso de no serlo , me desplazo llamando nuevamente a este metodo
        //segun corresponda con la comparativa > <
        if (elem.compareTo(n.getElem()) == 0) {
            res = true;
        } else {
            if (elem.compareTo(n.getElem()) < 0) {
                if (n.getIzquierdo() != null) {
                    res = privatePertenece(n.getIzquierdo(), elem);
                }
            } else {
                if (n.getDerecho() != null) {
                    res = privatePertenece(n.getDerecho(), elem);
                }
            }
        }

        return res;
    }

    public void vaciar() {
        raiz = null;
    }

    public boolean esVacio() {
        boolean res =false;
        if(raiz== null){
            res=true;
        }
        return res;
    }

    public Lista listar() {
        Lista listita = new Lista();
        if (raiz != null) {
            privateListar(raiz, listita);
        }

        return listita;

    }

    private void privateListar(NodoAVL n, Lista listita) {
        //lista inorden  entonces lista todos los elementos de menor a mayor
        if (n.getIzquierdo() != null) {
            privateListar(n.getIzquierdo(), listita);
        }
        listita.insertar(n.getElem(), listita.longitud() + 1);

        if (n.getDerecho() != null) {
            privateListar(n.getDerecho(), listita);
        }
    }

    @SuppressWarnings({ "rawtypes" })
    public Comparable minimoElem() {
        Comparable min = null;
        if (raiz != null) {
            min = privateMinimo(raiz);
        }

        return min;
    }

    @SuppressWarnings({ "rawtypes" })
    private Comparable privateMinimo(NodoAVL n) {
        Comparable min = null;
        if (n.getIzquierdo() != null) {
            min = privateMinimo(n.getIzquierdo());
        } else {
            min = n.getElem();
        }
        return min;
    }

    @SuppressWarnings({ "rawtypes" })
    public Comparable maximoElem() {
        Comparable max = null;
        if (raiz != null) {
            max = privateMaximo(raiz);
        }

        return max;
    }

    @SuppressWarnings({ "rawtypes" })
    private Comparable privateMaximo(NodoAVL n) {
        Comparable max = null;
        if (n.getDerecho() != null) {
            max = privateMaximo(n.getDerecho());
        } else {
            max = n.getElem();
        }
        return max;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Lista listarPorRango(Comparable min, Comparable max) {
        Lista lis = new Lista();
        if (min.compareTo(max) > 0) {// compruebo que el minimo sea < a max , si no , los intercambio
            Comparable aux = max;
            max = min;
            min = aux;
        }
        if (this.raiz != null) {
            listarPorRangoAux(this.raiz, min, max, lis);
        }
        return lis;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void listarPorRangoAux(NodoAVL n, Comparable min, Comparable max, Lista lis) {
        if (n != null) {


            if (min.compareTo(n.getElem().toString()) <= 0) { //la rama izq , debe ser listada? llamo con la rama izq
                listarPorRangoAux(n.getIzquierdo(), min, max, lis);
            }
            if (max.compareTo(n.getElem().toString()) >= 0) {//la rama der , debe ser listada? llamo con la rama der
                listarPorRangoAux(n.getDerecho(), min, max, lis);
            }
            //lo escribimos al final para que quede inorden
            if (min.compareTo(n.getElem().toString()) <= 0 && max.compareTo(n.getElem().toString()) >= 0) { //donde estoy parado , esta dentro del rango?  si es asi , lo listo
                lis.insertar(n.getElem(), lis.longitud() + 1);
            }
        }
    }

    public ArbolAVL clone() {
        ArbolAVL arbolClone = new ArbolAVL();

        if (raiz != null) { //en el caso de tener arbol
            NodoAVL aux = new NodoAVL(raiz.getElem(), null, null); //creo un nodo con el elemento de la raiz
            arbolClone.raiz = aux; //y asigno el puntero raiz a ese nodo
            privateClone(raiz, arbolClone.raiz); //llamo a privateClone para copiar el resto del arbol
        }

        return arbolClone;
    }

    private void privateClone(NodoAVL n, NodoAVL c) {    //llegan los 2 arboles , el original y el clonado

        if (n.getIzquierdo() != null) { //si el original tiene hijo izq
            NodoAVL Izquierdo = new NodoAVL(n.getIzquierdo().getElem(), null, null); //creo un nodo aux izq
            c.setIzquierdo(Izquierdo); // conecto la al padre con este hijo izq dentro del arbol clonado
            privateClone(n.getIzquierdo(), c.getIzquierdo());  // repito el metodo con el hijo izq y el sub arbol correspondiente del arbol original
        }
        //repito procedimiento con el lado derecho
        if (n.getDerecho() != null) {
            NodoAVL Derecho = new NodoAVL(n.getDerecho().getElem(), null, null);
            c.setDerecho(Derecho);
            privateClone(n.getDerecho(), c.getDerecho());
        }

    }

    @SuppressWarnings({ "rawtypes" })
    public Comparable recuperar(Comparable buscado) {

        Comparable elem = null;
        if (raiz != null) {
            elem = privateRecuperar(raiz, buscado);
        }
        return elem;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Comparable privateRecuperar(NodoAVL n, Comparable buscado) {
        Comparable elem = null;
        Comparable aux = n.getElem();
        if (aux.compareTo(buscado) == 0) {
            elem = aux;
        } else {
            if (buscado.compareTo(aux) < 0) {
                if (n.getIzquierdo() != null) {
                    elem = privateRecuperar(n.getIzquierdo(), buscado);
                }
            } else {
                if (buscado.compareTo(aux) > 0) {
                    if (n.getDerecho() != null) {
                        elem = privateRecuperar(n.getDerecho(), buscado);
                    }
                }
            }
        }
        return elem;
    }

}
