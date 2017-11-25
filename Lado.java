/**
 * @author Jonathan Bandes <jonathanbandes@gmail.com>
 * @author Mariano Rodríguez <12-10892@usb.ve>
 * @since 1.0
 * @version 1.0
 */

public abstract class Lado
{
  private String id;
  private double peso;

  

  public Lado(String id, double peso) {
  	this.id = id;
  	this. peso = peso;
  }

  

  public String getId() {
  	return this.id;
  }

  

  public double getPeso() {
  	return this.peso;
  }

  

  public abstract String toString();

}