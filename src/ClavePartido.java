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
}
