/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

import java.util.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

public class GrafoNoDirigido implements Grafo
{
    LinkedList<Vertice> vertices;
    LinkedList<Arista> aristas;

    
    /**
     * Constructor del objeto GrafoNoDirigido
     *
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public GrafoNoDirigido() {
        this.vertices = new LinkedList<Vertice>();
        this.aristas = new LinkedList<Arista>();
    }

    
    /**
     * Carga toda la información de un archivo con el formato @format a 
     * un objeto GrafoNoDirigido ya existente.
     *
     * @param   dirArchivo    Ruta absoluta o relativa del archivo.
     * @format
     *          cantVertices
     *          cantAristas
     *          idVertice pesoVertice
     *          idArista idVerticeInicial idVerticeFinal pesoArista
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition Existencia del archivo dirArchivo.
     * @postCondition Carga exisota de toda la inforamción a un grafo.
     * @complexity O(n)
     */
    public boolean cargarGrafo(String dirArchivo) {
        
        FileReader archivo;
        BufferedReader lector;
        int numeroDeLados;
        int numeroDeVertices;

        // Abre el archivo
        try {
            archivo = new FileReader(dirArchivo);
            lector = new BufferedReader(archivo);
        }
        catch (IOException ex) {
            System.out.println("Hubo un error abriendo el archivo " + 
                                dirArchivo);
            return false;
        }

        // Lee numeroDeVertices y numeroDeLados
        try {
            numeroDeVertices = Integer.parseInt(lector.readLine());
            numeroDeLados = Integer.parseInt(lector.readLine());
        }
        catch (Exception e){
            System.out.println("La cantidad de vertices y/o lados deben ser" +
                                " números enteros.");
            return false;
        }

        // Lee todos los vertices
        for (int i = 0; i < numeroDeVertices; i++) {

            String linea = leeLinea(lector);
            String[] vertice = leeVertice(linea);

            double pesoVertice;
            try {
                pesoVertice = Double.parseDouble(vertice[1]);
            }
            catch (Exception e) {
                System.out.println("El peso del vértice debe ser un número.");
                pesoVertice = 0;
            }

            String idVertice = vertice[0];

            if (agregarVertice(idVertice, pesoVertice)) {
                continue;
            } else {
                System.out.println("Hubo un error agregando el vertice " + 
                                    idVertice);
            }
        }

        // Lee todos los lados
        for (int i = 0; i < numeroDeLados; i++) {

            String linea = leeLinea(lector);
            String[] lado = leeLado(linea);

            String idLado = lado[0];
            String idVerticeInicial = lado[1];
            String idVerticeFinal = lado[2];
            double pesoLado;
            try {
                pesoLado = Double.parseDouble(lado[3]);
            }
            catch (Exception e) {
                System.out.println("El peso del lado debe ser un número.");
                pesoLado = 0;
            }

            agregarArista(idLado, pesoLado, idVerticeInicial, idVerticeFinal);
        } 

        // Cierra los archivos
        try {
            archivo.close();
            lector.close();
        } 
        catch(IOException ex) {
            System.out.println("Hubo un error cerrando los archivos.");
            return false;
        }

        return true;
    }
    
    
    /**
     * Getter del numero de vértices del grafo.
     *
     * @return     Cantidad de vértices que tiene el grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public int numeroDeVertices() {
        return this.vertices.size();
    }

    
    /**
     * Getter del numero de lados del grafo.
     *
     * @return     Cantidad de lados que tiene el grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public int numeroDeLados() {
        return this.aristas.size();
    }
   
    
    /**
     * Agrega un objeto Vertice al grafo.
     *
     * @param Vertice v     Vertice a ser agregado en el grafo.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition Objeto Vertice inicializado.
     * @postCondition Vertice agregado correctamente al grafo.
     * @complexity O(1)
     */
    public boolean agregarVertice(Vertice v) {

        if (vertices.size() == 0) {
            vertices.addFirst(v);
        } else {
            if (estaVertice(v.getId())) {
                return false;
            }
            vertices.add(v);
        }
        return true;
    }

    
    /**
     * Agrega un vertice al grafo.
     *
     * @param String id     Identificador del vértice a agregar.
     * @param Double peso   Peso del vértice a agregar.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition id y peso válidos.
     * @postCondition Vertice agregado correctamente al grafo.
     * @complexity O(1)
     */
    public boolean agregarVertice(String id, double peso) {

        Vertice vertice = new Vertice(id, peso);

        if (this.agregarVertice(vertice)) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Busca un vértice por su ID en el grafo y lo devuelve.
     *
     * @param String   id   Identificador del vértice.
     * @return      Retorna el vértice que coincida con el id.
     * @preCondition El vértice se encuentra en el grafo.
     * @postCondition Vertice encontrado y devuelto como objeto.
     * @throw En caso de no conseguir el vertice.
     * @complexity O(n)
     */
    public Vertice obtenerVertice(String id) {

        Vertice vertice;

        for(int i = 0; i < this.vertices.size(); i++) {
            vertice = this.vertices.get(i);

            if (id.equals(vertice.getId())) {
                return vertice;
            }
        }
        throw new NoSuchElementException();
    }

    
    /**
     * Busca un vértice en el grafo e informa si está o no.
     *
     * @param String   id   Identificador del vértice.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition El id tiene un formato válido.
     * @postCondition Verificar el valor devuelto.
     * @complexity O(n)
     */
    public boolean estaVertice(String id) {

        Vertice vertice;

        for (int i = 0; i < this.vertices.size(); i++) {
            vertice = this.vertices.get(i);

            if(id.equals(vertice.getId())) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Busca un lado en el grafo e informa si está o no.
     *
     * @param String u  Identificador de uno de los vértices del lado.
     * @param String v  Identificador del otro vértice del lado.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition Verificar el valor devuelto.
     * @complexity O(n)
     */
    public boolean estaLado(String u, String v){

        if ( estaVertice(u) && estaVertice(v) ) {
            for (int i = 0; i < this.aristas.size(); i++) {

                Arista arista = this.aristas.get(i);
                Vertice extremoUno = arista.getExtremo1();
                Vertice extremoDos = arista.getExtremo2();
                boolean extremoUnoEsU = u.equals(extremoUno.getId());
                boolean extremoUnoEsV = v.equals(extremoUno.getId());
                boolean extremoDosEsU = u.equals(extremoDos.getId());
                boolean extremoDosEsV = v.equals(extremoDos.getId());

                if ( (extremoUnoEsU || extremoUnoEsV) && 
                     (extremoDosEsU || extremoDosEsV) ) {
                    return true;
                }  
            }
            return false;
        }
        return false;
    }

    
    /**
     * Busca una arista por su ID en el grafo e informa si está o no.
     *
     * @param String id     Identificador de la Arista a buscar.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition Verificar el valor devuelto.
     * @complexity O(n)
     */
    public boolean estaArista(String id){

        Arista arista;

        for (int i = 0; i < this.aristas.size(); i++) {
            arista = this.aristas.get(i);
            if ( id.equals(arista.getId()) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Elimina un vértice del grafo. Elimina a su vez todos los lados 
     * incidentes al lado eliminado, para evitar inconsistencias en la 
     * inforamción del grafo.
     *
     * @param String id     Identificador del vértice a eliminar
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition Verificar resultado, para asegurar correcta eliminación.
     * @complexity O(n)
     */
    public boolean eliminarVertice(String id) {

        if (this.estaVertice(id)) {
            List<Lado> incidentes = this.incidentes(id);

            for (int i = 0; i < incidentes.size(); i++) {
                Lado lado = incidentes.get(i);
                this.eliminarArista(lado.getId());
            }

            this.vertices.remove(this.obtenerVertice(id));
        }
        else {
            return false;
        }

        return true;
    }

    
    /**
     * Devuelve una lista con todos los vértices del grafo.
     *
     * @return   Lista con todos los vertices del grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(1) 
     */
    public List<Vertice> vertices() {
        return this.vertices;
    }

    
    /**
     * Devuelve una lista con todos los lados del grafo.
     *
     * @return   Lista con todos los lados del grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public List<Lado> lados() {
        List<Lado> lados = new ArrayList<Lado>(this.aristas.size());

        for (int i = 0; i < this.aristas.size(); i++) {
            lados.add(this.aristas.get(i));
        }

        return lados;
    }

    
    /**
     * Calcula el grado de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Grado del vértice en el grafo.
     * @throw   En caso de que el vértice no se encuentre en el grafo
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public int grado(String id) {
        int grado = 0;

        if (this.estaVertice(id)) {
            for (int i = 0; i < aristas.size(); i++) {
                Arista arista = aristas.get(i);
                if (id.equals( arista.getExtremo1().getId() )) {
                    grado++;
                }
                if (id.equals( arista.getExtremo2().getId() )) {
                    grado++;
                }
            }
            return grado;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    
    /**
     * Calcula la lista de vértices adyacentes a un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo
     * @return  Lista de vértices adyacentes.
     * @throw   En caso de que el vértice no se encuentre.
     * @preCondition Existencia del vértice en el grafo.
     * @postCondition true
     * @complexity O(n)
     */
    public List<Vertice> adyacentes(String id) {

        LinkedList<Vertice> adyacentes = new LinkedList<Vertice>();

        if(estaVertice(id)) {
            for (int i = 0; i < this.aristas.size(); i++) {
            Arista arista = this.aristas.get(i);
            if (id.equals( arista.getExtremo1().getId() )) {
                adyacentes.add( arista.getExtremo2() );
            }
            if (id.equals( arista.getExtremo2().getId() )) {
                adyacentes.add( arista.getExtremo1() );
            }
        }
        } 
        else {
            throw new NoSuchElementException();
        }

        return adyacentes;
    }
 
    
    /**
     * Calcula la lista de lados incidentes a un vértice en el grafo.
     *
     * @param id     Identificador del vértice en el grafo.
     * @return      Lista de lados incidentes al vértice.
     * @throw   En caso de que el vértice no se encuentre en el grafo.
     * @preCondition Existencia del vértice en el grafo.
     * @postCondition true
     * @complexity O(n)
     */
    public List<Lado> incidentes(String id) {

        LinkedList<Lado> incidentes = new LinkedList<Lado>();

        if (estaVertice(id)) {
            for (int i = 0; i < this.aristas.size(); i++) {
                Arista arista = this.aristas.get(i);
                boolean verticeEsExtremo1 = id.equals(arista.getExtremo1().getId());
                boolean verticeEsExtremo2 = id.equals(arista.getExtremo2().getId());

                if (verticeEsExtremo1 || verticeEsExtremo2) {
                    incidentes.add( arista );
                }
            }
        }
        else {
            throw new NoSuchElementException();
        }

        return incidentes;
    }

    
    /**
     * Clona el objeto GrafoNoDirigdo a un nuevo objeto.
     *
     * @return  Un nuevo objeto con una copia de la información del grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public Object clone() {
        
        GrafoNoDirigido grafoNuevo = new GrafoNoDirigido();

        for (int i = 0; i < this.vertices.size(); i++) {
            Vertice vertice = this.vertices.get(i);
            String id = vertice.getId();
            Double peso = vertice.getPeso();
            grafoNuevo.agregarVertice(id, peso);
        }

        for (int i = 0; i < this.aristas.size(); i++) {
            Arista arista = this.aristas.get(i);
            String id = arista.getId();
            Double peso = arista.getPeso();
            Vertice extremoUno = arista.getExtremo1();
            Vertice extremoDos = arista.getExtremo2();

            Arista aristaCopia = new Arista(id, peso, extremoUno, extremoDos);
            grafoNuevo.agregarArista(aristaCopia);
        }

        return grafoNuevo;
    }

    
    /**
     * Convierte toda la representación en un String que cumple con el 
     * formato del archivo requerido para la función cargarGrafo().
     *
     * @return      Toda la información del grafo en un String.
     * @preCondition true
     * @postCondition true
     * @complexity O(n) 
     */
    public String toString() {

        String grafoString = "";

        grafoString += this.numeroDeVertices() + "\n";
        grafoString += this.numeroDeLados() + "\n";

        // Impresión de Vertices
        for (int i = 0; i < vertices.size(); i++) {
            Vertice vertice = vertices.get(i);
            grafoString += vertice.toString() + "\n";
        }
        // Impresión de Lados
        for (int i = 0; i < aristas.size(); i++) {
            Arista arista = aristas.get(i);
            grafoString += arista.toString() + "\n";
        }

        return grafoString;
    }

    
    /**
     * Agrega un objeto arista al grafo.
     *
     * @param a     La nueva arista a agregar
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1) 
     */
    public boolean agregarArista(Arista a) {

        if (aristas.size() == 0) {
            aristas.addFirst(a);
        } else {
            if (estaArista(a.getId())) {
                return false;
            }
            aristas.add(a);
        }
        return true;
    }

    
    /**
     * Agrega una arista al grafo.
     *
     * @param id    id de la Arista a agregar.
     * @param peso  peso de la Arista a agregar.
     * @param u     id de uno de los vértices de la arista.
     * @param v     id del otro vértice de la arista.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public boolean agregarArista(String id, double peso, String u, String v) {

        Vertice uVertice, vVertice;

        try {
            uVertice = obtenerVertice(u);
        }
        catch(NoSuchElementException e) {
            return false;
        }
        try {
            vVertice = obtenerVertice(v);
        }
        catch(NoSuchElementException e) {
            return false;
        }        

        Arista arista = new Arista(id, peso, uVertice, vVertice);

        if (this.agregarArista(arista)) {
            return true;
        }

        return false;
    }

    
    /**
     * Elimina una arista del grafo.
     *
     * @param id    Identificador de la arista a eliminar
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1) 
     */
    public boolean eliminarArista(String id) {
        
        if (this.estaArista(id)) {
            boolean aristaEliminada;
            aristaEliminada = this.aristas.remove(this.obtenerArista(id));
            if (aristaEliminada) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Busca la arista deseada en el grafo y la devuelve.
     *
     * @param id    Identificador de la arista deseada en el grafo.
     * @return      Arista deseada.
     * @throw En caso de que el vértice no se encuentre en el grafo.
     * @preCondition true
     * @postCondition Verificar que no devuelva null para asegurar haber 
     *                  obtenido la arista del grafo.
     * @complexity O(n) 
     */
    public Arista obtenerArista(String id) {

        if (this.estaArista(id)) {
            for (int i = 0; i < this.aristas.size(); i++) {
                Arista arista = aristas.get(i);
                if (id.equals(arista.getId())) {
                    System.out.println("Encontré " + arista.getId());
                    return arista;
                }
            }
        } else {
            throw new NoSuchElementException();
        }
        return null;
    }

    
    /**
     * Lee una línea de un archivo de texto previamente abierto.
     *
     * @param lector    Apuntador al archivo a leer.
     * @return      Linea leída del archivo.
     * @preCondition Objeto BufferedReader de un archivo abierto
     * @postCondition true
     * @complexity O(1)
     */
    public String leeLinea(BufferedReader lector) {
        String linea = null;
        try {
            linea = lector.readLine();
        }
        catch (Exception e) {
            System.out.println("Hubo un error leyendo la linea.");
            linea = "";
        }
        return linea;
    }


    /**
     * Divide una línea de un archivo de texto con el formato 
     * especificado en cargarGrafo() de tal forma que el programa 
     * pueda hacer uso de la información contenida para crear un vértice.
     *
     * @param linea     String de texto a ser dividido.
     * @return  Arreglo con toda la información contenido en linea.
     * @preCondition true
     * @postCondition Tamaño del arreglo correspondiente a la cantidad de datos
     *                  necesarios para inicializar un vértice.
     * @complexity O(1)
     */
    public String[] leeVertice(String linea) {

        String[] vertice = new String[2];
        try {
            vertice = linea.split(" ");
        }
        catch (Exception e) {
            System.out.println("Hay un error con el formato del vértice");
            System.out.println(linea);
        }
        return vertice;
    }


    /**
     * Divide una línea de un archivo de texto con el formato 
     * especificado en cargarGrafo() de tal forma que el programa 
     * pueda hacer uso de la información contenida para crear una arista.
     *
     * @param linea     String de texto a ser dividido.
     * @return  Arreglo con toda la información contenido en linea.
     * @preCondition true
     * @postCondition Tamaño del arreglo correspondiente a la cantidad de datos
     *                  necesarios para inicializar una arista.
     * @complexity O(1)
     */
    public String[] leeLado(String linea) {

        String[] lado = new String[4];
        
        try {
            lado = linea.split(" ");
        }
        catch (Exception e) {
            System.out.println("Hay un error con el formato del lado");
            System.out.println(linea);
        }
        return lado;
    }
}