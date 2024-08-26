package org.om2324.heroesmarvel;

public class HeroeMarvel {
    String nombre;
    String poder;
    String alias;

    public HeroeMarvel(String nombre, String poder, String alias) {
        this.nombre = nombre;
        this.poder = poder;
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    @Override
    public String toString(){
        return nombre + " -- " + alias;
    }
}
