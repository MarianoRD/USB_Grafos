/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

import java.util.*;
import java.io.BufferedReader;

public interface Grafo
{
    public boolean cargarGrafo(String dirArchivo);
    
    public int numeroDeVertices();

    public int numeroDeLados();
    
    public boolean agregarVertice(Vertice v);

    public boolean agregarVertice(String id, double peso);
    
    public Vertice obtenerVertice(String id);

    public boolean estaVertice(String id);

    public boolean estaLado(String u, String v);

    public boolean eliminarVertice(String id);

    public List<Vertice> vertices();

    public List<Lado> lados();

    public int grado(String id);

    public List<Vertice> adyacentes(String id);
 
    public List<Lado> incidentes(String id);

    public Object clone();

    public String toString();

    public String leeLinea(BufferedReader lector);
}