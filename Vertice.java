/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

import java.util.LinkedList;

public class Vertice
{
  private String id;
  private double peso;
  
  
  /**
   * Constructor del objeto Vertice.
   *
   * @param id  Identificador del Vertice en el grafo.
   * @param peso Peso del Vertice.
   * @preCondition true
   * @postCondition true
   * @complexity O(1)
   */
  public Vertice(String id, double peso) {
    this.id = id;
    this.peso = peso;
  }

  

  public double getPeso() {
    return this.peso;
  }

  

  public String getId() {
    return this.id;
  }

  
    /**
     * Convierte el objeto Vertice en un String, de tal manera que los datos 
     * del objeto están separados por espacios.
     *
     * @return  Un string con toda la información del objeto.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
  public String toString() { 
    String pesoString = String.valueOf(this.peso);
    String vertice = this.id + " " + pesoString;

    return vertice;
  }
}