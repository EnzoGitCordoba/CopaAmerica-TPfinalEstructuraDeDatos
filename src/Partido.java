public class Partido {
    private String eq1;
    private String eq2;
    private String instancia;
    private String ciudad;
    private String estadio;
    private int golesE1;
    private int golesE2;
    private ClavePartido clave;

    public Partido(String e1, String e2, String ins, String ci, String es, int gol1, int gol2) {
        if (e1.compareTo(e2) < 0) {
            clave = new ClavePartido(e1, e2);
            eq1 = e1;
            eq2 = e2;
            golesE1 = gol1;
            golesE2 = gol2;
        } else {
            clave = new ClavePartido(e2, e1);
            eq1 = e2;
            eq2 = e1;
            golesE1 = gol2;
            golesE2 = gol1;
        }
        instancia = ins;
        ciudad = ci;
        estadio = es;

    }

    public Partido(String e1, String e2) {
        if (e1.compareTo(e2) < 0) {
            clave = new ClavePartido(e1, e2);
        } else {
            clave = new ClavePartido(e2, e1);
        }
    }

    public String getEquipo1() {
        return this.eq1;
    }

    public String getEquipo2() {
        return this.eq2;
    }

    public String getInstancia() {
        return this.instancia;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public String getEstadio() {
        return this.estadio;
    }

    public int golesEquipo1() {
        return this.golesE1;
    }

    public int golesEquipo2() {
        return this.golesE2;
    }

    public void setEquipo1(String e1) {
        eq1 = e1;
    }

    public void setEquipo2(String e2) {
        eq2 = e2;
    }

    public void setInstancia(String ins) {
        instancia = ins;
    }

    public void setCiudad(String ci) {
        ciudad = ci;
    }

    public void setEstadio(String es) {
        estadio = es;
    }

    public void setGolesEquipo1(int gol) {
        golesE1 = gol;
    }

    public void setGolesEquipo2(int gol) {
        golesE2 = gol;
    }

    public String toString() {
        return eq1 + " [" + golesE1 + " - " + golesE2 + "] " + eq2
                + " INSTANCIA: " + instancia + ", ciudad: " + ciudad + ", estadio: " + estadio + "";
    }

    public String resultado() {
        String cad;

        cad = eq1 + " [" + golesE1 + " - " + golesE2 + "] " + eq2
                + " INSTANCIA: " + instancia;

        return cad;
    }

    public ClavePartido getClavePartido() {

        return clave;
    }

    public boolean compararEquipos(String e1, String e2) {
        ClavePartido nuClave;
        if (e1.compareTo(e2) < 0) {
            nuClave = new ClavePartido(e1, e2);
        } else {
            nuClave = new ClavePartido(e2, e1);
        }
        return ((clave.getEquipoUno().equals(nuClave.getEquipoUno()))
                && (clave.getEquipoDos().equals(nuClave.getEquipoDos())));
    }

}
