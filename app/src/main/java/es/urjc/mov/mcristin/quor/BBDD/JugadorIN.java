package es.urjc.mov.mcristin.quor.BBDD;

public class JugadorIN {
    private int id;
    private String name;
    private int jugadas;
    private int ganadas;

    JugadorIN(int id, String name, int jugadas, int ganadas) {
        this.id = id;
        this.name = name;
        this.jugadas = jugadas;
        this.ganadas = ganadas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}