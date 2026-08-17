public class ClavePartido {
    private String equipoUno;
    private String equipoDos;

    // -------------------------------------- CONSTRUCTOR
    // -------------------------------------------------------------------------------------------------------------------------------------------------//

    public ClavePartido(String eq1, String eq2) {
        equipoUno = eq1;
        equipoDos = eq2;
    }

    // -------------------------------------- CLAVE HASH
    // -------------------------------------------------------------------------------------------------------------------------------------------------//

    public int hashCode() {
        int hash;
        hash = equipoUno.hashCode();
        hash += equipoDos.hashCode();
        return hash;
    }

    // -------------------------------------- PROPIOS
    // -------------------------------------------------------------------------------------------------------------------------------------------------//

    public String toString() {
        String cad;
        cad = "equipo uno: " + equipoUno + " equipo dos: " + equipoDos + "\n";
        return cad;
    }

    // -------------------------------------- GET
    // -------------------------------------------------------------------------------------------------------------------------------------------------//

    public String getEquipoUno() {
        return equipoUno;
    }

    public String getEquipoDos() {
        return equipoDos;
    }


    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof ClavePartido) { // sirve para saber si podemos realizar un casting de obj a ClavePartido
            ClavePartido clave = (ClavePartido) obj;
            if (this.equipoUno.equals(clave.getEquipoUno()) && this.equipoDos.equals(clave.getEquipoDos())) {
                res = true;
            }
        }
        return res;
    }


}
