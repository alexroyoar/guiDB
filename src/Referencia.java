
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author clase
 */
class Referencia {

    //ELEMENTOS ESTÁTICOS
    static private final Map<String, Referencia> ALMACEN = new HashMap<>();

    static class IllegalFieldException extends Exception {
        public IllegalFieldException(String string) {
            super(string);
        }
    }

    //CAMPOS DE OBJETOS
    private String titulo, anio, publicacion, referencias, link = "UNKNOWN";
    private final String refNum;
    private final List<String> autores = new ArrayList<>();

    //MÉTODOS ESTÁTICOS
    static void addLine(String line) throws IllegalFieldException {
        String[] campos = line.replaceFirst("([a-zA-Z])", " $1").split(" ", 3); // He simplificado la expresión regular
        if (ALMACEN.get(campos[0]) == null) {
            ALMACEN.put(campos[0], new Referencia(campos[0]));
        }
        Referencia r = ALMACEN.get(campos[0]);
        switch (campos[1]) {
            case "t":
                r.titulo = campos[2];
                break;
            case "y":
                r.anio = campos[2];
                break;
            case "p":
                r.publicacion = campos[2];
                break;
            case "r":
                r.referencias = campos[2];
                break;
            case "a":
                r.autores.add(campos[2]);
                break;
            case "l":
                r.link = campos[2];
                break;
            default:
                throw new IllegalFieldException("Leida linea con tipo de información " + campos[1]);
        }
    }

    // GETTERS
    static Iterable<Referencia> getAllRefs() {
        return ALMACEN.values();
    }

    String getRefNum() {
        return refNum;
    }

    List<String> getAutores() {
        return autores;
    }

    String getTitulo() {
        return titulo;
    }

    String getAnio() {
        return anio;
    }

    String getPublicacion() {
        return publicacion;
    }

    String getLink() {
        return link;
    }

    String getReferencias() {
        return referencias;
    }

    static Set<String> getAllAutoresFiliaciones() {
        Set<String> todosAutores = new HashSet<>();
        for (Referencia r : ALMACEN.values()) {
            todosAutores.addAll(r.autores);
        }
        return todosAutores;
    }

    static SortedSet<String> getAllPublicaciones() {
        SortedSet<String> TodasPublicaciones = new TreeSet<>();
        for (Referencia r : ALMACEN.values()) {
            TodasPublicaciones.add(r.getPublicacion());
        }
        return TodasPublicaciones;
    }

    //Reescritura de herencia de Object
    @Override
    public String toString() {
        return "Referencia{" + "titulo=" + titulo + ", anio=" + anio + ", publicacion=" + publicacion + ", referencias=" + referencias + ", link=" + link + ", autores=" + autores + '}';
    }

    //ZONA PRIVADA (el constructor usado internamente por addLine(.))
    private Referencia(String refNum) {
        this.refNum = refNum;
    }
}
