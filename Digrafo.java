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

public class Digrafo implements Grafo
{   

    LinkedList<Vertice> vertices;
    LinkedList<Arco> arcos;


    /**
     * Constructor del objeto Digrafo
     *
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public Digrafo() {
        this.vertices = new LinkedList<Vertice>();
        this.arcos = new LinkedList<Arco>();
    }


    /**
     * Carga toda la información de un archivo con el formato @format a 
     * un objeto Digrafo ya existente.
     *
     * @param   dirArchivo    Ruta absoluta o relativa del archivo.
     * @format
     *          cantVertices
     *          cantArcos
     *          idVertice pesoVertice
     *          idArco idVerticeInicial idVerticeFinal pesoArco
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

            agregarArco(idLado, pesoLado, idVerticeInicial, idVerticeFinal);
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
        return this.arcos.size();
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
     * @throw Si no consigue el vértice en el grafo.
     * @preCondition El vértice se encuentra en el grafo.
     * @postCondition Vertice encontrado y devuelto como objeto.
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
     * @param String u  Identificador del extremo inicial del lado.
     * @param String v  Identificador del extremo final del lado.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition Verificar el valor devuelto.
     * @complexity O(n)
     */
    public boolean estaLado(String u, String v){

        if (estaVertice(u) && estaVertice(v) == true) {
            for (int i = 0; i < this.arcos.size(); i++) {

                Arco arco = this.arcos.get(i);
                Vertice extremoInicial = arco.getExtremoInicial();
                Vertice extremoFinal = arco.getExtremoFinal();
                boolean extremoInicialEsU = u.equals(extremoInicial.getId());
                boolean extremoFinalEsV = v.equals(extremoFinal.getId());

                if ( (extremoInicialEsU && extremoFinalEsV) ) {
                    return true;
                }  
            }
            return false;
        }
        return false;
    }

    /**
     * Busca una arco por su ID en el grafo e informa si está o no.
     *
     * @param String id     Identificador de la Arco a buscar.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition Verificar el valor devuelto.
     * @complexity O(n)
     */
    public boolean estaArco(String id){

        Arco arco;

        for (int i = 0; i < this.arcos.size(); i++) {
            arco = this.arcos.get(i);
            if ( id.equals(arco.getId()) ) {
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
                this.eliminarArco(lado.getId());
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
        List<Lado> lados = new ArrayList<Lado>(this.arcos.size());

        for (int i = 0; i < this.arcos.size(); i++) {
            lados.add(this.arcos.get(i));
        }

        return lados;
    }

    
    /**
     * Calcula el grado de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Grado del vértice en el grafo o -1 si no se encuentra.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public int grado(String id) {
        int grado;
        if (estaVertice(id)) {
            int gradoExterior = this.gradoExterior(id);
            int gradoInterior = this.gradoInterior(id);
            grado = gradoInterior + gradoExterior;
        }
        else {
            throw new NoSuchElementException();
        }
        return grado;
    }

    
    /**
     * Calcula la lista de vértices adyacentes a un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo
     * @return  Lista de vértices adyacentes.
     * @preCondition Existencia del vértice en el grafo.
     * @postCondition true
     * @complexity O(n)
     */
    public List<Vertice> adyacentes(String id) {

        LinkedList<Vertice> adyacentes = new LinkedList<Vertice>();

        if (!estaVertice(id)) {
            throw new NoSuchElementException();
        }

        for (int i = 0; i < this.arcos.size(); i++) {
            Arco arco = this.arcos.get(i);
            if (id.equals( arco.getExtremoInicial().getId() )) {
                adyacentes.add( arco.getExtremoFinal() );
            }
            if (id.equals( arco.getExtremoFinal().getId() )) {
                adyacentes.add( arco.getExtremoInicial() );
            }
        }
        return adyacentes;
    }
 
    
    /**
     * Calcula la lista de lados incidentes a un vértice en el grafo.
     *
     * @param id     Identificador del vértice en el grafo.
     * @return      Lista de lados incidentes al vértice.
     * @preCondition Existencia del vértice en el grafo.
     * @postCondition true
     * @complexity O(n)
     */
    public List<Lado> incidentes(String id) {

        LinkedList<Lado> incidentes = new LinkedList<Lado>();

        if(!estaVertice(id)) {
            throw new NoSuchElementException();
        }

        for (int i = 0; i < this.arcos.size(); i++) {
            Arco arco = this.arcos.get(i);
            boolean verticeEsExtremoInicial;
            boolean verticeEsExtremoFinal;

            verticeEsExtremoInicial = id.equals(arco.getExtremoInicial().getId());
            verticeEsExtremoFinal = id.equals(arco.getExtremoFinal().getId());

            if (verticeEsExtremoInicial || verticeEsExtremoFinal) {
                incidentes.add( arco );
            }
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
        
        Digrafo grafoNuevo = new Digrafo();

        for (int i = 0; i < this.vertices.size(); i++) {
            Vertice vertice = this.vertices.get(i);
            String id = vertice.getId();
            Double peso = vertice.getPeso();
            grafoNuevo.agregarVertice(id, peso);
        }

        for (int i = 0; i < this.arcos.size(); i++) {
            Arco arco = this.arcos.get(i);
            String id = arco.getId();
            Double peso = arco.getPeso();
            Vertice extremoUno = arco.getExtremoInicial();
            Vertice extremoDos = arco.getExtremoFinal();

            Arco arcoCopia = new Arco(id, peso, extremoUno, extremoDos);
            grafoNuevo.agregarArco(arcoCopia);
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
        for (int i = 0; i < arcos.size(); i++) {
            Arco arco = arcos.get(i);
            grafoString += arco.toString() + "\n";
        }
        return grafoString;
    }

    

    /**
     * Agrega un objeto arco al grafo.
     *
     * @param a     La nueva arco a agregar
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1) 
     */
    public boolean agregarArco(Arco a) {

        if (arcos.size() == 0) {
            arcos.addFirst(a);
        } else {
            if (estaArco(a.getId())) {
                return false;
            }
            arcos.add(a);
        }
        return true;
    }

    /**
     * Agrega una arco al grafo.
     *
     * @param id    id de la Arco a agregar.
     * @param peso  peso de la Arco a agregar.
     * @param u     id de uno de los vértices de la arco.
     * @param v     id del otro vértice de la arco.
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
    public boolean agregarArco(String id, double peso, String u, String v) {

        Vertice uVertice = obtenerVertice(u);
        Vertice vVertice = obtenerVertice(v);

        Arco arco = new Arco(id, peso, uVertice, vVertice);

        if (this.agregarArco(arco)) {
            return true;
        }

        return false;
    }


    /**
     * Calcula el grado interior de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Grado del vértice en el grafo o -1 si no se encuentra.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public int gradoInterior(String id) {

        int gradoInterior = 0;

        if (estaVertice(id)) {
            for (int i = 0; i < this.arcos.size(); i++) {

                Arco arco = this.arcos.get(i);
                Vertice extremoFinal = arco.getExtremoFinal();
                boolean extremoFinalEsId = id.equals(extremoFinal.getId());

                if ( extremoFinalEsId ) {
                    gradoInterior++;
                }  
            }
        } 
        else {
            throw new NoSuchElementException();
        }

        return gradoInterior;
    }


    /**
     * Calcula el grado exterior de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Grado del vértice en el grafo o -1 si no se encuentra.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public int gradoExterior(String id) {

        int gradoExterior = 0;

        if (estaVertice(id)) {
            for (int i = 0; i < this.arcos.size(); i++) {

                Arco arco = this.arcos.get(i);
                Vertice extremoInicial = arco.getExtremoInicial();
                boolean extremoInicialEsId = id.equals(extremoInicial.getId());

                if ( extremoInicialEsId ) {
                    gradoExterior++;
                }  
            }
        } 
        else {
            throw new NoSuchElementException();
        }

        return gradoExterior;
    }


    /**
     * Calcula la lista de sucesores de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Lista de sucesores del vértice, null si no está en el grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public List<Vertice> sucesores(String id) {

        int cantSucesores = 0;
        ArrayList<Vertice> sucesores = new ArrayList<Vertice>(cantSucesores);

        try {
            cantSucesores = gradoExterior(id);
        }
        catch (NoSuchElementException e) {
            return sucesores;
        }

        for (int i = 0; i < this.arcos.size(); i++) {

            Arco arco = this.arcos.get(i);
            Vertice extremoInicial = arco.getExtremoInicial();
            boolean extremoInicialEsId = id.equals(extremoInicial.getId());

            if ( extremoInicialEsId ) {
                sucesores.add( arco.getExtremoFinal() );
                cantSucesores--;
            }

            if (cantSucesores == 0) {
                break;
            } 
        }
        return sucesores;
    }


    /**
     * Calcula la lista de predecesores de un vértice dado.
     *
     * @param String id     Identificador del vértice en el grafo.
     * @return    Lista de predecesores del vértice, null si no está en el grafo.
     * @preCondition true
     * @postCondition true
     * @complexity O(n)
     */
    public List<Vertice> predecesores(String id) {
    
        int cantPredecesores = 0;
        ArrayList<Vertice> predecesores;
        predecesores = new ArrayList<Vertice>(cantPredecesores);

        try {
            cantPredecesores = gradoExterior(id);
        }
        catch (NoSuchElementException e) {
            return predecesores;
        }

        for (int i = 0; i < this.arcos.size(); i++) {

            Arco arco = this.arcos.get(i);
            Vertice extremoFinal = arco.getExtremoFinal();
            boolean extremoFinalEsId = id.equals(extremoFinal.getId());

            if ( extremoFinalEsId ) {
                predecesores.add( arco.getExtremoInicial() );
                cantPredecesores--;
            }

            if (cantPredecesores == 0) {
                break;
            } 
        }
        return predecesores;
    }

    /**
     * Elimina una arco del grafo.
     *
     * @param id    Identificador de la arco a eliminar
     * @return    true si se ejecuta correctamente, false en caso contrario.
     * @preCondition true
     * @postCondition true
     * @complexity O(1) 
     */
    public boolean eliminarArco(String id) {
        
        if (this.estaArco(id)) {
            boolean arcoEliminada;
            arcoEliminada = this.arcos.remove(this.obtenerArco(id));
            if (arcoEliminada) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca la arco deseado en el grafo y la devuelve.
     *
     * @param id    Identificador de la arco deseada en el grafo.
     * @return      Arco deseada.
     * @preCondition true
     * @postCondition Verificar que no devuelva null para asegurar haber 
     *                  obtenido la arco del grafo.
     * @complexity O(n) 
     */
    public Arco obtenerArco(String id) {

        if (this.estaArco(id)) {
            for (int i = 0; i < this.arcos.size(); i++) {
                Arco arco = arcos.get(i);
                if (id.equals(arco.getId())) {
                    return arco;
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