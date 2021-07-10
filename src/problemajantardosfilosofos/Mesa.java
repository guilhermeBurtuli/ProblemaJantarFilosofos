
package problemajantardosfilosofos;

import java.util.concurrent.Semaphore;//importa a classe Semaphore do pacote java.util

public class Mesa {//declara a classe Mesa

    public static Semaphore[] semaforo = new Semaphore[5];//declara um vetor com 5 semáforos responsáveis por cada um dos talheres
   
    public static void sentarMesa(){//declara o método estático sentarMesa() para inicializar os semáforos
        
        for(int i=0; i<5; i++){//declara um loop de 5 ciclos
            semaforo[i] = new Semaphore(1);//atribui a cada elemento do vetor um objeto Semaphore
        }
        System.out.println("OS FILÓSOFOS SENTARAM-SE A MESA");//exibe uma mensagem informando que os filósofos sentaram-se  a mesa
        
    }
          
}
