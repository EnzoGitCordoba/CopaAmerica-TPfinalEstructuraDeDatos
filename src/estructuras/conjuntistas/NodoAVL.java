package estructuras.conjuntistas;

public class NodoAVL {

    @SuppressWarnings("rawtypes")
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    @SuppressWarnings("rawtypes")
    //constructor con hijos
    public NodoAVL(Comparable el, NodoAVL izq, NodoAVL der) {
        this.elem = el;
        this.izquierdo = izq;
        this.derecho = der;
    }

    @SuppressWarnings("rawtypes")
    //constructor vacio
    public NodoAVL(Comparable el) {
        this.elem = el;
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura() {
        int alturaDerecho = -1, alturaIzquierdo = -1;
        if (derecho != null) {
            alturaDerecho = derecho.getAltura();
        }
        if (izquierdo != null) {
            alturaIzquierdo = izquierdo.getAltura();
        }
        altura = Math.max(alturaIzquierdo, alturaDerecho) + 1;// la altura de un nodo es la altura de su hijo mas alto+1 . 0 si es hoja-
    }

    @SuppressWarnings("rawtypes")
    public Comparable getElem() {
        return this.elem;
    }

    @SuppressWarnings("rawtypes")
    public void setElem(Comparable el) {
        elem = el;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public void setIzquierdo(NodoAVL izq) {
        izquierdo = izq;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setDerecho(NodoAVL der) {
        derecho = der;
    }
}
