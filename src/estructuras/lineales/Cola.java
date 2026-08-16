package estructuras.lineales;

import java.util.Objects;

//una cola es una estructura FIFO
public class Cola {

    private Nodo frente;
    private Nodo fin;

    // constructor vacio
    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    // metodos

    public boolean poner(Object nuevoElemento) {
        //creo el nodo nuevo
        Nodo nuevoNodo = new Nodo(nuevoElemento, null);
        // ¿  tengo un nodo ? => frente != null
        if (this.frente != null) {
            this.fin.setEnlace(nuevoNodo); // conecto el antiguo fin con este nuevo nodo
            this.fin = nuevoNodo;// muevo el puntero fin a este nuevo nodo
        } else {
            this.frente = this.fin = nuevoNodo; // significa que la cola estaba vacia y este es el primer elemento entonces fin=frente
        }
        // nunca hay error de cola llena ya que es dinamica, entonces devuelve true
        return true;
    }

    public boolean sacar() {
        boolean exito = true; // devuelve true si pudo sacar un elemento , false en caso de que la cola haya estado vacia
        if (this.frente == null) {
            // la cola esta vacia, reporta error
            exito = false;
        } else {
            // hay al menos un elemento actualizo el frente
            this.frente = this.frente.getEnlace();// muevo el puntero de frente al siguiente elemento (el antiguo frente se "borrara" con el garbage collector)

            //si solo habia 1 elemento al mover el frente con el enlace nos encontramos un null
            if (this.frente == null) {
                this.fin = null; // me aseguro que fin no quede apuntando a algo
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        //devuelve el elemento del frente , si la cola no esta vacia
        Object frenteRetorno = null;
        frenteRetorno = this.frente.getElem();
        return frenteRetorno; //si la cola esta vacia devolvera null
    }

    public Boolean esVacia() {
//devuelvo verdadero si no hay elementos en la cola , es decir si frente y fin son nulos.
        boolean res;
        if ((this.frente == null) && (this.fin == null)) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    public void vaciar() {
        //saco todos los elementos de la estructura llevando frente y fin a nulos y dejando que el garbaje collector elimine lo que haya dejado atras
        if (!esVacia()) {
            this.frente = null;
            this.fin = null;
        }
    }
}
