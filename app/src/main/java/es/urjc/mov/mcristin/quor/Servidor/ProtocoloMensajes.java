package es.urjc.mov.mcristin.quor.Servidor;

import es.urjc.mov.mcristin.quor.Jugador.JugadorRED;

public class ProtocoloMensajes{
    int id;
    JugadorRED client1;
    JugadorRED client2;
    ProtocoloMensajes(int id, JugadorRED client1,JugadorRED client2){
        this.id=id;
        this.client1=client1;
        this.client2=client2;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public JugadorRED getClient1() {
        return client1;
    }
    public void setClient1(JugadorRED client1) {
        this.client1 = client1;
    }
    public JugadorRED getClient2() {
        return client2;
    }
    public void setClient2(JugadorRED client2) {
        this.client2 = client2;
    }
}
