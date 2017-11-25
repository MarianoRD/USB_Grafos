/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

public class Arco extends Lado
{
  private Vertice extremoInicial;
  private Vertice extremoFinal;
  
  
    /**
     * Constructor del objeto Arco
     *
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
  public Arco(String id, double peso, Vertice extremoInicial, 
              Vertice extremoFinal) {

    super(id, peso);
    this.extremoInicial = extremoInicial;
    this.extremoFinal = extremoFinal;
  }

  

  public Vertice getExtremoInicial() {
    return this.extremoInicial;
  }

  

  public Vertice getExtremoFinal() {
    return this.extremoFinal;
  }

  
    /**
     * Convierte el objeto Arco en un String, de tal manera que los datos 
     * del objeto están separados por espacios.
     *
     * @preCondition true
     * @postCondition true
     * @complexity O(1)
     */
  public String toString() {
    String arco = super.getId() + " " + extremoInicial.getId() + " " + extremoFinal.getId() + " " 
                  + String.valueOf(super.getPeso());


    return arco;
  }
}