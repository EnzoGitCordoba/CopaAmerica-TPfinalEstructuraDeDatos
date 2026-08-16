package estructuras.conjuntistas;

public class HeapMax {

    @SuppressWarnings("rawtypes")
    private final Comparable[] heap;
    private int ultimo;
    private final int tamaño = 20;

    public HeapMax() {
        this.heap = new Comparable[tamaño];
        this.ultimo = 0;
    }

    @SuppressWarnings("rawtypes")
    public boolean insertar(Comparable elem) {
        boolean exito;
        if (this.ultimo > this.tamaño) { //si salgo del tamaño no lo puedo ingresar (20)
            exito = false;
        } else {
            this.ultimo++; //muevo 1 pos e inserto
            this.heap[ultimo] = elem;
            hacerSubir(this.ultimo); //lo posiciono donde corresponde
            exito = true;
        }
        return exito;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void hacerSubir(int ultPos) {
        int posCima = ultPos / 2; // Marco el máximo (padre)
        int i = ultPos;
        Comparable aux;
        boolean salida = false;

        while (!salida && posCima > 0) {
            if (this.heap[i].compareTo(this.heap[posCima]) > 0) { //comparo si el elemento ingresado a lo último es mayor a su padre
                aux = this.heap[i];  //Si lo es , intercambio sus valores
                this.heap[i] = this.heap[posCima];
                this.heap[posCima] = aux;
                i = posCima; //actualizo la posición del elemento  para la siguiente iteración
                posCima = i / 2;
            } else {
                salida = true;
            }
        }
    }

    public boolean eliminarCima() {
        boolean exito;
        if (esVacio()) {
            exito = false;
        } else {
            this.heap[1] = this.heap[ultimo]; //cambio el valor del maximo por el ultimo hijo
            this.ultimo--;   //controlo la ultima posicion
            hacerBajar(1); //hago bajar al hijo si corresponde
            exito = true;
        }
        return exito;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;
        while (!salir) {
            posH = posPadre * 2; // obtengo la posicion del hijo
            if (posH <= this.ultimo) {// analizo si el hijo existe
                if (posH < this.ultimo) {  // significa que tiene 2 hijos
                    if (this.heap[posH + 1].compareTo(this.heap[posH]) > 0) { //me quedo con el mayor
                        posH++;
                    }
                }

                if (this.heap[posH].compareTo(temp) > 0) {  //es mas grande que el padre?
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp; //los cambio
                    posPadre = posH;//analizo con el sub arbol que se genero al bajar el elemento
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

    public Object recuperarCima() {
        Object cima = null;
        if (!esVacio()) {
            cima = heap[1];
        }
        return cima;
    }

    public boolean esVacio() {
        return (this.ultimo == 0);
    }

    public String toString() {
        String cadena = "";
        if (!esVacio()) {
            cadena += "[";
            for (int i = 1; i <= this.ultimo; i++) {
                cadena += heap[i];
                if (i < this.ultimo) {
                    cadena += ",";
                }
            }
            cadena += "]";
        } else {
            cadena = "cadena vacia";
        }

        return cadena;
    }
}
