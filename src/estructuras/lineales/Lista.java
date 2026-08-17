package estructuras.lineales;

public class Lista {

    private Nodo cabecera; //primer elemento de la lista
    private int longitud; // largo de la lista , la primer posicion sera =1

    //constructor vacio
    public Lista() {
        cabecera = null;
        longitud = 0;
    }

    public boolean insertar(Object elem, int pos) { //recibo el elemento que debo agregar y la posicion donde lo debo agregar
        //debo devolver true si fui capaz de insertar un elemento y false caso contrario
        boolean res = false;
        //creo una referencia y un entero auxiliares para recorrer la lista
        Nodo nodoAux = cabecera;
        int aux = 1;
        // analizo la posicion es valida ya que no podria agregar elementos en la posicion longitud +2 ya que habria un lugar vacio y tampoco en una pos<0
        if (pos > 0 && pos < longitud + 2) {
            //primer caso: agrego al principio de la lista
            if (pos == 1) {
                Nodo nodoInsert = new Nodo(elem, null);// creo un nodo vacio
                nodoInsert.setEnlace(cabecera); // este nodo se enlaza a la cabecera quedando primero
                cabecera = nodoInsert;// muevo el puntero a esta nueva cabecera
                longitud = longitud + 1; // agrego 1 a la longitud
                res = true;
            } else {
                //caso 2: que quiera añadirlo al final de la lista
                if (longitud + 1 == pos) {
                    Nodo nodoInsert = new Nodo(elem, null); //creo el nodo que debo insertar
                    while (aux < pos - 1) {  // recorro la lista hasta llegar al ultimo elemento
                        nodoAux = nodoAux.getEnlace(); //nodoAux empezando en la cabecera se mueve atravez de los enlaces
                        aux++;//
                    }//nodoAux es un puntero que marca el final de mi lista por lo que su siguiente enlace es null
                    nodoAux.setEnlace(nodoInsert); // modificico el enlace
                    longitud = longitud + 1; //agrego 1 a la longitud
                    res = true;
                } else { // caso 3: añado de forma generica , no es principio ni final
                    Nodo nodoInsert = new Nodo(elem, null); //creo el nodo a insertar
                    while (aux < pos - 1) { //me muevo hasta la posicion anterior donde quiero añadir el elemento a travez de los enlaces
                        nodoAux = nodoAux.getEnlace();
                        aux++;
                    }
                    nodoInsert.setEnlace(nodoAux.getEnlace()); //engancho el nodo al enlace de del nodo que apunta aux ya que este ocupara el lugar del enlace.
                    //en este momento 2 nodos apuntaran al mismo nodo , por lo que solo queda modificar el enlace del nodo que apunta aux al que debo agregar
                    nodoAux.setEnlace(nodoInsert);

                    res = true;
                    longitud++; //agrego 1 a la longitud
                }

            }

        }
        return res;
    }

    public void vaciar() { //pongo el puntero cabecera en null , la posicion en 0 y el gabaje collector se llevara lo que habia antes
        cabecera = null;
        longitud = 0;
    }


    public boolean esVacia() { // compruebo si la lista esta vacia- si esta vacia cabecera sera null
        boolean res = false;
        if (cabecera == null) {
            res = true;
        }
        return res;
    }

    public Object recuperar(int pos) { //obtengo el elemento almacenado en la posicion pos
        Object recuperado;
        //auxiliares que me ayudaran a recorrer la lista
        int aux = 1;
        Nodo nodoAux = cabecera;
        //analizo si la posicion es valida
        if (pos > 0 && pos <= longitud) {
            while (aux < pos) { // recorro hasta llegar a el nodo donde recuperar el elemento a travez de los enlaces
                nodoAux = nodoAux.getEnlace(); //primero me muevo , luego aumento en 1 . por eso "<"
                aux++;
            }
            // aqui estoy parado justo en el elemento a recuperar
            recuperado = nodoAux.getElem(); // pido el elemento
        } else {
            recuperado = null; //la posicion ingresada fue invalida recuperado no tiene valor
        }
        return recuperado;
    }

    public int localizar(Object busca) { //devueldo la posicion de la primera coincidencia con el elemento a buscar (en la lista puedo tener elementos repetidos)
        int pos = -1; //entero que me marcara la posicion a devolver . inicia en -1 por si no encuentro el elemento remarcar que no esta en la lista
        boolean encontrado = false;//bandera de corte
        //auxiliares para moverme en la lista
        int aux = 1;
        Nodo nodoAux = cabecera;
        while ((!encontrado) && (aux <= longitud)) { // mientras no lo encuentre y no llegue al final de la lista , recorro

            if (nodoAux.getElem() == busca) { //pregunto si el elemento del nodo donde estoy , es el que busco
                pos = aux;  //cambio pos para retornar el lugar donde estoy parado
                encontrado = true;// bandera en true para cortar el while
            }
            nodoAux = nodoAux.getEnlace(); //muevo el nodo , independientemente si lo encuentro al elemento o no ya que lo analizare en la siguiente vuelta
            aux++;
        }
        return pos;
    }

    public boolean eliminar(int pos) { //elimino posiciones de la lista
        boolean res = false; //devolvere true si puedo eliminar

        //auxiliares para recorrer la lista
        int aux = 1;
        Nodo nodoAux = cabecera;

        if (pos > 0 && pos <= longitud) { // si la posicion ingresada es valida , analizo los diferentes casos para eliminar
            // caso1:  si tengo 1 solo elemento
            if (pos == 1 && longitud == 1) {
                //la lista quedara vacia
                longitud = 0;
                cabecera = null;
                res = true;
            } else {
                //si no tengo 1 solo elemento pero quiero eliminar el primero
                if (pos == 1) {
                    //muevo la cabecera y resto 1 a la longitud
                    cabecera = cabecera.getEnlace();
                    longitud--;
                    res = true;
                } else {
                    //si no es el primero elemento , recorro hasta llegar al anterior nodo del que quiero eliminar
                    while (aux < pos - 1) {
                        aux++;
                        nodoAux = nodoAux.getEnlace();
                    }
                    // conecto el nodo anterior al que quiero eliminar con el siguiente del siguiente , "salteando" el que eliminaria y que el garbaje collector se encargue de borrarlo
                    res = true;
                    longitud--;
                    nodoAux.setEnlace((nodoAux.getEnlace().getEnlace()));
                }
            }

        }
        return res;

    }

    public int longitud() { //retorno la longitud de la lista
        return longitud;
    }

    public Lista clone() { //clono la lista

        Lista clonada = new Lista(); //creo una lista vacia

        if (cabecera != null) {// analizo si hay elementos en la lista , si los hay , tengo que clonarlos uno a uno, sino , ya cumpli
            Nodo cabe = new Nodo(cabecera.getElem(), null);//creo un nodo "cabe" el cual me ayudara a recorrer mi lista clonada. este tendra el primer elemento de mi lista original y me marcara la ultima posicion
            //pongo la cabecera y la longitud correspondientes de mi nueva lista
            clonada.cabecera = cabe;
            clonada.longitud = 1;
            //creo un nodo que tomara el valor del siguiente elemento de mi lista original
            Nodo recorrido = cabecera.getEnlace();
            while (recorrido != null) {// si el siguiente elemento , es distinto de nulo en mi lista original , significa que me quedan elementos por copiar
                Nodo aux3 = new Nodo(recorrido.getElem(), null);//creo un nodo auxiliar con este valor que sera anexado a mi lista clonada, este tendra el contenido de recorrido(siguiente nodo a clonar)
                cabe.setEnlace(aux3); //cambio el enlace del ultimo elemento de mi lista clonada asi añado este nuevo nodo
                cabe = cabe.getEnlace(); // muevo el puntero que me marca la ultima posicion de mi lista clonada
                recorrido = recorrido.getEnlace(); //muevo el puntero que me marca la ultima posicion de mi lista original
                clonada.longitud++;// aumento la longitud  de la clonada
            }
        }
        return clonada;
    }

    public String toString() { //debo devolver una cadena
        String s = "";  //creo una cadena vacia
        if (this.cabecera == null) { //si no hay elementos , lo remarco y devuelvo
            s = "Lista vacia";
        } else {
            // creo un puntero que apunte a la cabecera y empiezo a recorrer para devolver la cadena
            Nodo aux = this.cabecera;
            s = "["; // inicio la cadena
            while (aux != null) { //mientra el elemento no sea nulo
                // agrego texto a la cadena y avanzo
                s += aux.getElem().toString();  // obtengo el elemento y lo convierto en string  ya que me devuelve un object
                aux = aux.getEnlace(); //paso al siguiente elemento
                if (aux != null) { //si hay elemento siguiente , punto y sigo
                    s += ",";
                }
            }
            s += "] \n "; //fin de la cadena
        }
        return s; // return the string here
    }

    public void invertir() {
        //  invierto la lista sin crear nodos auxiliares
        //si la lista tiene 1 o 0 elementos no necesito modificar la lista
        // si la longitud es 2 simplemente muevo los nodos de lugar intercambiando los enlaces y la cabecera
        if (longitud == 2) {
            //creo un puntero para cada nodo
            Nodo auxInt1 = cabecera;
            Nodo auxInt2 = cabecera.getEnlace();
            //conecto el nodo 2 al nodo 1
            auxInt2.setEnlace(auxInt1);
            //seteo el enlace del nodo cabecera en null
            auxInt1.setEnlace(null);
            //muevo la cabecera
            cabecera = auxInt2;
        } else {
            //si tengo mas elementos
            if (longitud > 2) {
                Nodo a = cabecera; //puntero a cabecera
                Nodo b = cabecera.getEnlace();//puntero al siguiente elemento
                Nodo d = cabecera; //puntero a cabecera

                while (a.getEnlace() != null) { //mientras el puntero a no tenga enlace nulo los invertire la cadena , para ello necesitare 3 punteros. parandome en un primer nodo
                    // cambiando los enlaces , moviendo la cabecera y continuando , ya que la cabecera tiene que quedar al final de mi cola oroginal

                    d = cabecera; //posiciono otro puntero en la cabecera
                    cabecera = b; //muevo la cabecera de lugar  al siguiente elemento esto es para que la cabecera vaya quedando al principio de mi nueva lista modificados los enlaces
                    a.setEnlace(b.getEnlace()); //cambio el enlace del primer nodo apuntado por siguiente del siguiente, sin perder el del medio ya que queda apuntado por la cabecera
                    b.setEnlace(d);// muevo el segundo nodo de esta vuelta 2posiciones atras

                    b = a.getEnlace(); // me posiciono en el siguiente elemento a mover .

                }

            }
        }

    }

    public void eliminarApariciones(Object x) {//elimino todas las apariciones de un elemento en la lista

        while (cabecera != null && cabecera.getElem() == x) {//mientras tenga elementos y la cabecera sea un elemento a eliminar
            cabecera = cabecera.getEnlace(); //muevo la cabecera
            longitud--;//bajo la longitud .
            //como los nodos que dejan de ser cabecera no pueden
        }
        Nodo aux = cabecera;
        if (cabecera != null) {
            while (aux.getEnlace() != null) {
                if (aux.getEnlace().getElem() == x) {
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    longitud--;
                } else {
                    aux = aux.getEnlace();
                }
            }
        }

    }

    // metodos del simulacro parcial 1
    public Lista obtenerMultiplos(int n) {
        Lista multiplos = new Lista();
        int aux = n;
        int i = 1;
        Nodo nodito = cabecera;
        if (n > 0) {

            while (aux <= longitud) {
                while (i != n) {
                    if (nodito != null) {
                        nodito = nodito.getEnlace();

                    }
                    i++;
                }
                System.out.println(nodito.getElem());
                multiplos.insertar(nodito.getElem(), multiplos.longitud + 1);
                aux = aux + n;
                i = 1;
                nodito = nodito.getEnlace();
            }
        }
        return multiplos;
    }
}
