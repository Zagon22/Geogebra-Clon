package Modelo;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class GestorObjetos {
    private Map<String, VectorGeo> vectores;
    private Map<String, Punto> puntos;
    private Map<String, Recta> rectas;
    private Map<String, Segmento> segmentos;
    // Contadores
    private AtomicLong contadorPorDefecto;
    private AtomicLong contadorPuntosInt;
    private AtomicLong contadorVectoresProj;
    private AtomicLong contadorVectoresProjE;
    private AtomicLong contadorVectoresCb;
    private AtomicLong contadorVectorDif;
    private AtomicLong contadorDistancia;

    private final List<ModeloListener> listeners = new CopyOnWriteArrayList<>();

    public GestorObjetos() {
        this.vectores = new HashMap<>();
        this.rectas = new HashMap<>();
        this.puntos = new HashMap<>();
        this.segmentos = new HashMap<>();

        this.contadorPorDefecto = new AtomicLong(0);
        this.contadorPuntosInt = new AtomicLong(0);
        this.contadorVectoresProj = new AtomicLong(0);
        this.contadorVectoresProjE = new AtomicLong(0);
        this.contadorVectoresCb = new AtomicLong(0);
        this.contadorVectorDif = new AtomicLong(0);
        this.contadorDistancia = new AtomicLong(0);
    }

    public void addModeloListener(ModeloListener l) { listeners.add(l); }
    public void removeModeloListener(ModeloListener l) { listeners.remove(l); }
    public void notificar() {for (ModeloListener l : listeners) l.modeloCambiado(); }

    public Map<String, Punto> getPuntos() { return Collections.unmodifiableMap(puntos); }
    public Map<String, Recta> getRectas() { return Collections.unmodifiableMap(rectas); }
    public Map<String, VectorGeo> getVectores() { return Collections.unmodifiableMap(vectores); }
    public Map<String, Segmento> getSegmentos() { return Collections.unmodifiableMap(segmentos); }


    public void agregarVector(String label, double x, double y) {
        vectores.put(label.toLowerCase(), new VectorGeo(x, y));
        notificar();
    }

    public void agregarVector(String label, VectorGeo v) {
        vectores.put(label.toLowerCase(), v);
        notificar();
    }

    public void agregarRecta(String label, double x1, double x2, double y1, double y2) {
        rectas.put(label.toLowerCase(), new Recta(x1, x2, y1, y2));
        notificar();
    }

    public void agregarRecta(String label, Recta r) {
        rectas.put(label.toLowerCase(), r);
        notificar();
    }

    public void agregarPunto(String label, double x, double y) {
        puntos.put(label.toLowerCase(), new Punto(x, y));
        notificar();
    }

    public void agregarPunto(String label, Punto p) {
        puntos.put(label.toLowerCase(), p);
        notificar();
    }

    public void agregarSegmento(String label, Segmento s) {
        segmentos.put(label.toLowerCase(), s);
        notificar();
    }

    // Nota: para los vectores y puntos que se generen desde el modelo, la idea es que se les genere una etiqueta

    public String crearLabel(String operacion) {
        switch (operacion) {
            case "interseccion":
                return "i_" + contadorPuntosInt.incrementAndGet();
            case "projv":
                return "projV_" + contadorVectoresProj.incrementAndGet();
            case "cambioB":
                return "nV_" + contadorVectoresCb.incrementAndGet();
            case "difV":
                return "difV_" + contadorVectorDif.incrementAndGet();
            case "dist":
                return "dist_" + contadorDistancia.incrementAndGet(); 
            case "projE":
                return "projE_" + contadorVectoresProjE.incrementAndGet();
            default:
                return "de_" + contadorPorDefecto.incrementAndGet();
        }
    }

    public Recta buscarRecta(String label) {
        if(rectas.containsKey(label.toLowerCase())) {
            Recta r = rectas.get(label.toLowerCase());
            return r;
        }
        return null;
    }

    public Punto buscarPunto(String label) {
        if(puntos.containsKey(label.toLowerCase())) {
            Punto p = puntos.get(label.toLowerCase());
            return p;
        }
        return null;
    }

    public VectorGeo buscarVector(String label) {
        if(vectores.containsKey(label.toLowerCase())) {
            VectorGeo v = vectores.get(label.toLowerCase());
            return v;
        }
        return null;
    }

    public Segmento buscarSegmento(String label) {
        if(segmentos.containsKey(label.toLowerCase())) {
            Segmento s = segmentos.get(label.toLowerCase());
            return s;
        }
        return null;
    }

    public void removerVector(String label) {
        if(vectores.containsKey(label.toLowerCase())) {
            vectores.remove(label.toLowerCase());
            notificar();
        }
    }

    public void removerPunto(String label) {
        if(puntos.containsKey(label.toLowerCase())) {
            puntos.remove(label.toLowerCase());
            notificar();
        }
    }

    public void removerRecta(String label) {
        if(rectas.containsKey(label.toLowerCase())) {
            rectas.remove(label.toLowerCase());
            notificar();
        }
    }

    public void removerSegmento(String label) {
        if(segmentos.containsKey(label.toLowerCase())) {
            segmentos.remove(label.toLowerCase());
            notificar();
        }
    }

    public void limpiar() {
        rectas.clear();
        puntos.clear();
        vectores.clear();
        segmentos.clear();
        
        notificar();
    } // no implementado, por lo pronto.
}