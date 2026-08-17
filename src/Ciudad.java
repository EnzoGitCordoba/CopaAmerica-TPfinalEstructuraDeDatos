public class Ciudad {
    private String nombre;
    private boolean alojamiento;
    private boolean sede;

    public Ciudad(String nn, boolean al, boolean se) {
        nombre = nn;
        alojamiento = al;
        sede = se;
    }

    public Ciudad(String nn) {
        nombre = nn;
        alojamiento = false;
        sede = false;
    }

    public String getNombre() {
        return this.nombre;
    }

    public boolean tieneAlojamiento() {
        return this.alojamiento;
    }

    public boolean sedeCopa() {
        return this.sede;
    }

    public void setNombre(String nn) {
        nombre = nn;
    }

    public void setAlojamiento(boolean al) {
        alojamiento = al;
    }

    public void setSede(boolean se) {
        sede = se;
    }

    public String toString() {
        return "NOMBRE:" + nombre + ", ALOJAMIENTO: " + alojamiento + ", SEDE DE LA COPA: " + sede + "\n";
    }

    public void cambiarSede() {
        sede = !sede;
    }

    public void cambiarAlojamiento() {
        alojamiento = !alojamiento;
    }

    public boolean equals(Object otroPais) {
        Ciudad laCiudad = (Ciudad) otroPais;
        return nombre.equals(laCiudad.nombre);
    }
}
