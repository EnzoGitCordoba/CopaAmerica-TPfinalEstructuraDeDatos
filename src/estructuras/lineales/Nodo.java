package estructuras.lineales;

public class Nodo {

    private Object elem;
    private Nodo enlace;

    public Nodo(Object el, Nodo en) {
        elem = el; //contenido
        enlace = en; // elemento con el que se enlaza
    }

    public Object getElem() {   //obtener el contenido
        return this.elem;
    }

    public void setElem(Object el) { // cambiar el contenido
        elem = el;
    }

    public Nodo getEnlace() { // obtener el enlace
        return this.enlace;
    }

    public void setEnlace(Nodo en) { //cambiar el enlace
        enlace = en;
    }
}
