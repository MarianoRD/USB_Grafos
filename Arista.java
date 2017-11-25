/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

public class Arista extends Lado
{
  private Vertice u;
  private Vertice v;
  
  
  /**
   * Constructor del objeto Arista.
   *
   * @param id  Identificador de la Arista en el grafo.
   * @param peso Peso de la Arista.
   * @param u   Uno de los vértices que compone la Arista
   * @param v   El otro vértice que compone la Arista
   * @preCondition true
   * @postCondition true
   * @complexity O(1)
   */
  public Arista(String id, double peso, Vertice u, Vertice v) {
    super(id, peso);
    this.u = u;
    this.v = v;
  }

  

  public Vertice getExtremo1() {
    return this.u;
  }

  

  public Vertice getExtremo2() {
    return this.v;
  }

  
    /**
     * Convierte el objeto Arista en un String, de tal manera que los datos 
     * del objeto están separados por espacios.
     *
     * @return  Un string con toda la información del objeto.
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
  public String toString() {
    String arista = super.getId() + " " + u.getId() + " " + v.getId() + " " 
                  + String.valueOf(super.getPeso());

    return arista;
  }
}