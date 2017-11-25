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

public class ClienteGrafo {
  public static void main(String [] args) {

  	BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
	int opcion;
	String ruta;
	boolean resultado;

  	System.out.println("Bienvenido al manejador de grafos.");
  	System.out.println("Elija con qué grafo desea trabajar.");
  	System.out.println("1-. Grafo No Dirigido.");
  	System.out.println("2-. Grafo Dirigido.");
  	System.out.println("3-. Salir");

	System.out.print("Introduzca el numero de la opción: ");
	opcion = Integer.parseInt(leerLinea(lector));

	switch (opcion) {
		case 1:
			GrafoNoDirigido grafo = new GrafoNoDirigido();
			System.out.print("Introduzca la ruta del archivo: ");
			ruta = leerLinea(lector);
			resultado = grafo.cargarGrafo(ruta);

			if (resultado) {
				programaNoDirigido(grafo);
			} else {
				System.out.println("Hubo un error cargando el grafo.");
			}
			break;
		case 2:
			Digrafo digrafo = new Digrafo();
			System.out.print("Introduzca la ruta del archivo: ");
			ruta = leerLinea(lector);
			resultado = digrafo.cargarGrafo(ruta);

			if (resultado) {
				programaDirigido(digrafo);
			} else {
				System.out.println("Hubo un error cargando el grafo.");
			}
			break;
		case 3:
			return;
		default:
			System.out.println("Opción equivocada.");
	}

  }

  public static String leerLinea(BufferedReader lector) {
  	
  	String linea = "";
  	try{
		linea = lector.readLine();
	} 
	catch (IOException ex) {
		System.out.println("General I/O exception: " + ex.getMessage());
        ex.printStackTrace();
	}

	return linea;
  }

	public static void programaNoDirigido(GrafoNoDirigido grafo) {

	  	int opcion;
	  	BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

	  	// Inicializa todas las posibles variables usadas en el programa
	  	String linea;
	  	String[] vertice = new String[2];
	  	String[] lado = new String[4];
	  	String id, u, v;
	  	Double peso;

	  	System.out.println("\nEl Grafo no dirigido fue cargado existosamente.\n");

	  	while(true){

			System.out.println("1-. Ver número de vertices del grafo.");
			System.out.println("2-. Ver número de lados del grafo.");
			System.out.println("3-. Agregar un vertice al grafo.");
			System.out.println("4-. Verificar si un vertice está en el grafo,");
			System.out.println("5-. Verificar si un lado está en el grafo.");
			System.out.println("6-. Eliminar un vertice del grafo.");
			System.out.println("7-. Listar todos los vertices del grafo.");
			System.out.println("8-. Listar todos los lados del grafo.");
			System.out.println("9-. Grado de un vertice del grafo.");
			System.out.println("10-. Listar los vertices adyacentes a un vertice.");
			System.out.println("11-. Listar los vertices incidentes a un vertice.");
			System.out.println("12-. Agregar una arista al grafo.");
			System.out.println("13-. Eliminar una arista del grafo.");
			System.out.println("14-. Imprimir el grafo en pantalla.");
			System.out.println("15-. Clonar el grafo.");
			System.out.println("16-. Salir");
			System.out.print("¿Qué deseas hacer?: ");

			opcion = Integer.parseInt(leerLinea(lector));

			switch(opcion) {
				case 1:{
				    System.out.println("El grafo tiene "+grafo.numeroDeVertices()+" vertices");
				    break;
				}
				case 2:{
				    System.out.println("El grafo tiene "+grafo.numeroDeLados()+" lados");
				    break;
				}
				case 3:{
				    System.out.println("¿Qué vertice deseas agregar?: ");
				    System.out.print("Ingrese el vertice: id peso -> ");
				    vertice = grafo.leeVertice(leerLinea(lector));
				    id = vertice[0];
				    peso = Double.parseDouble(vertice[1]);
				    grafo.agregarVertice(id, peso);
				    break;
				}
				case 4:{
				    System.out.print("¿Qué vertice deseas ver si esta en el grafo?: ");
				    id = leerLinea(lector);
				    if(grafo.estaVertice(id)){
				        System.out.println("El vertice "+id+" esta en el grafo.");
				    }
				    else {
				        System.out.println("El vertice "+id+" no esta en el grafo.");
				    }
				    break;
				}
				case 5:{
				    System.out.print("¿Qué lado deseas ver si esta en el grafo?: ");
				    System.out.print("ID Vertice inicial: ");
				    u = leerLinea(lector);
				    System.out.print("ID Vertice final: ");
				    v = leerLinea(lector);
				    if(grafo.estaLado(u, v)) {
				        System.out.println("El lado ("+u+","+v+") esta en el grafo.");
				    }
				    else {
				        System.out.println("El lado ("+u+","+v+") no esta en el grafo.");
				    }
				    break;
				}
				case 6:{
				    System.out.print("¿Qué vertice deseas eliminar del grafo?: ");
				    id = leerLinea(lector);
				    if(grafo.estaVertice(id)) {
				    	grafo.eliminarVertice(id);
				    }
				    else {
				    	System.out.println("El vertice "+ id +" no existe en el grafo.");
				    }
				    break;
				}
				case 7:{
				    System.out.print("Imprimiendo lista de vertices: ");
				    System.out.print(grafo.vertices());
				    System.out.println();
				    break;
				}
				case 8:{
				    System.out.print("Imprimiendo lista de Lados: ");
				    System.out.print(grafo.lados());
				    System.out.println();
				    break;          
				}
				case 9:{
				    System.out.print("¿A Qué vertice deseas calcular su grado?: ");
				    id = leerLinea(lector);
				    System.out.print("El grado del vertice "+id+" es");
				    System.out.println(grafo.grado(id));
				    break;
				}
				case 10:{
				    System.out.print("¿De Qué vertice deseas ver sus adyacencias?: ");
				    id = leerLinea(lector);
				    System.out.print("Lista de Adyacencias de "+id+": ");
				    System.out.println(grafo.adyacentes(id));
				    System.out.println();
				    break;
				}
				case 11:{
				    System.out.print("¿De Qué vertice deseas ver sus incidencias?: ");
				    id = leerLinea(lector);
				    System.out.print("Lista de Incidencias de "+id+": ");
				    System.out.println(grafo.incidentes(id));
				    System.out.println();
				    break;
				}
				case 12:{
				    System.out.println("¿Qué Arista deseas agregar al grafo?.");
				    System.out.print("Nombre del lado: ");
				    id = leerLinea(lector);
				    System.out.print("Vertice 1: ");
				    u = leerLinea(lector);
				    System.out.print("Vertice 2: ");
				    v = leerLinea(lector);
				    System.out.print("Peso: ");
				    peso = Double.parseDouble(leerLinea(lector));
				    grafo.agregarArista(id, peso, u, v);
				    break;
				}
				case 13:{
				    System.out.print("¿Qué Arista deseas eliminar del grafo?: ");
				    id = leerLinea(lector);
				    grafo.eliminarArista(id);
				    break;          
				}
				case 14: {
					System.out.print(grafo.toString());
					break;
				}
				case 15: {
					Object grafoNuevo = grafo.clone();
					System.out.println(grafoNuevo.toString());
					break;
				}
				case 16:{
				    System.out.println("\tHASTA LUEGO.");
				    System.exit(0);
				    break;
				}
				default:{
				    System.out.println("Opcion incorrecta.");
				    break;
				}
			}
		}
	}

	public static void programaDirigido(Digrafo digrafo){

		int opcion;
	  	BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

	  	// Inicializa todas las posibles variables usadas en el programa
	  	String linea;
	  	String[] vertice = new String[2];
	  	String[] lado = new String[4];
	  	String id, u, v;
	  	Double peso;

	  	System.out.println("\nEl Digrafo fue cargado existosamente.\n");

		while(true) {

			System.out.println("1-. Ver número de vertices del grafo.");
			System.out.println("2-. Ver número de lados del grafo.");
			System.out.println("3-. Agregar un vertice al grafo.");
			System.out.println("4-. Verificar si un vertice está en el grafo,");
			System.out.println("5-. Verificar si un lado está en el grafo.");
			System.out.println("6-. Eliminar un vertice del grafo.");
			System.out.println("7-. Listar todos los vertices del grafo.");
			System.out.println("8-. Listar todos los lados del grafo.");
			System.out.println("9-. Grado de un vertice del grafo.");
			System.out.println("10-. Listar los vertices adyacentes a un vertice.");
			System.out.println("11-. Listar los vertices incidentes a un vertice.");
			System.out.println("12-. Agregar un Arco al grafo.");
			System.out.println("13-. Calcular el grado interior de un vertice.");
			System.out.println("14-. Calcular el grado exterior de un vertice.");
			System.out.println("15-. Listar los vertices sucesores de un vertice.");
			System.out.println("16-. Listar los vertices predecesores de un vertice.");
			System.out.println("17-. Eliminar un arco del grafo.");
			System.out.println("18-. Imprimir el grafo.");
			System.out.println("19-. Clonar el grafo."); 
			System.out.println("20-. Salir");
			System.out.print("¿Qué deseas hacer?: ");

			opcion = Integer.parseInt(leerLinea(lector));
			// Casos
			        switch(opcion){
            case 1:{
                System.out.println("El grafo tiene "+digrafo.numeroDeVertices()+" vertices");
                break;
            }
            case 2:{
                System.out.println("El grafo tiene "+digrafo.numeroDeLados()+" lados");
                break;
            }
            case 3:{
                System.out.println("¿Qué vertice deseas agregar?: ");
			    System.out.print("Ingrese el vertice: id peso -> ");
			    vertice = digrafo.leeVertice(leerLinea(lector));
			    id = vertice[0];
			    peso = Double.parseDouble(vertice[1]);
			    digrafo.agregarVertice(id, peso);
			    break;
            }
            case 4:{
                System.out.print("¿Qué vertice deseas ver si esta en el grafo?: ");
                id = leerLinea(lector);
                if(digrafo.estaVertice(id)){
                    System.out.println("El vertice "+id+" esta en el grafo.");
                }
                else
                    System.out.println("El vertice "+id+" no esta en el grafo.");
                break;
            }
            case 5:{
                System.out.println("¿Qué lado deseas ver si esta en el grafo?: ");
                System.out.print("Vertice inicial: ");
                u = leerLinea(lector);
                System.out.print("Vertice final: ");
                v = leerLinea(lector);
                if(digrafo.estaLado(u, v)){
                    System.out.println("El lado ("+u+","+v+") esta en el grafo.");
                }
                else
                    System.out.println("El lado ("+u+","+v+") no esta en el grafo.");
                break;
            }
            case 6:{
                System.out.print("¿Qué vertice deseas eliminar del grafo?: ");
                id = leerLinea(lector);
                boolean b = digrafo.eliminarVertice(id);
                if (b){
                    System.out.println("Vertice "+id+" eliminado satisfactoriamente.");
                }
                else{
                    System.out.println("El Vertice "+id+" no existe.");
                }
                break;
            }
            case 7:{
                System.out.print("Imprimiendo lista de vertices: ");
                System.out.println(digrafo.vertices());
                System.out.println();
                break;
            }
            case 8:{
                System.out.print("Imprimiendo lista de Lados: ");
                System.out.print(digrafo.lados());
                System.out.println();
                break;          
            }
            case 9:{
                System.out.print("¿A Qué vertice deseas calcular su grado?: ");
                id = leerLinea(lector);
                System.out.print("El grado del vertice "+id+" es");
                System.out.println(digrafo.grado(id));
                break;
            }
            case 10:{
                System.out.print("¿De Qué vertice deseas ver sus adyacencias?: ");
                id = leerLinea(lector);
                System.out.print("Lista de Adyacencias de "+id+": ");
                System.out.println(digrafo.adyacentes(id));
                System.out.println();
                break;
            }
            case 11:{
                System.out.print("¿De Qué vertice deseas ver sus incidencias?: ");
                id = leerLinea(lector);
                System.out.print("Lista de Incidencias de "+id+": ");
                System.out.println(digrafo.incidentes(id));
                System.out.println();
                break;
            }
            case 12:{
                System.out.println("¿Qué arco deseas agregar al grafo?.");
                System.out.print("Nombre del lado: ");
                id = leerLinea(lector);
                System.out.print("Vertice inicial: ");
                u = leerLinea(lector);
                System.out.print("Vertice final: ");
                v = leerLinea(lector);
                System.out.print("Peso: ");
                peso = Double.parseDouble(leerLinea(lector));
                digrafo.agregarArco(id, peso, u, v);
                break;

            }
            case 13:{
                System.out.print("¿A Qué vertice deseas calcular su grado interior?: ");
                id = leerLinea(lector);
                System.out.print("El grado del vertice "+id+" es");
                System.out.println(digrafo.gradoInterior(id));
                break;          
            }
            case 14:{
                System.out.print("¿A Qué vertice deseas calcular su grado exterior?: ");
                id = leerLinea(lector);
                System.out.print("El grado del vertice "+id+" es");
                System.out.println(digrafo.gradoExterior(id));
                break;          
            }
            case 15:{
                System.out.print("¿A Qué vertice deseas calcular sus sucesores?: ");
                id = leerLinea(lector);              
                System.out.print("Imprimiendo lista de vertices sucesores: ");
                System.out.print(digrafo.sucesores(id));
                System.out.println();
                break;          
            }
            case 16:{
                System.out.print("¿A Qué vertice deseas calcular sus predecesores?: ");
                id = leerLinea(lector);              
                System.out.print("Imprimiendo lista de vertices predecesores: ");
                System.out.print(digrafo.predecesores(id));
                System.out.println();
                break;          
            }
            case 17:{
                System.out.println("¿Qué Arco deseas eliminar del grafo?: ");
                id = leerLinea(lector);
                boolean b = digrafo.eliminarArco(id);
                if (b){
                    System.out.println("Lado "+id+" eliminado satisfactoriamente.");
                }
                else{
                    System.out.println("El lado "+id+" no existe.");
                }
                break;          
            }
			case 18: {
				System.out.print(digrafo.toString());
				break;
			}
			case 19: {
				Object grafoNuevo = digrafo.clone();
				System.out.println(grafoNuevo.toString());
				break;
			}
            case 20:{
                System.out.println("\tHASTA LUEGO.");
                System.exit(0);
                break;
            }
            default:{
                System.out.println("Opcion incorrecta.");
                break;
            }         
        }
		}
	};
}