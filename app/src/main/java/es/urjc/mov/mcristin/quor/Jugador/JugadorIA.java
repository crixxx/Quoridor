package es.urjc.mov.mcristin.quor.Jugador;

import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.JuegoAleatorio;
import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.ModoDeJuegoIA;

/**
 * Created by crixx on 7/04/18.
 */

// Matar un hilo cada vez que se sale de la activity(girando la pantalla, cambiando de pantalla)
// , con una señal una jugada venenosa, con una variable bool para
// despertarlo y que cuando lo lea se mate (con un metodo syncronize)

// en el sleep(cuando dos maquinas juegan) con un time out
// Para pedir jugadas en un bucle infinito

public class JugadorIA extends Jugador {
    public JugadorIA() {
        super();
        tipoJugador tipo = tipoJugador.IA;
    }

    ModoDeJuegoIA modoJuego = new JuegoAleatorio();
    
}
