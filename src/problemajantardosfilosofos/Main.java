
package problemajantardosfilosofos;

public class Main {//declara classe Main
    
    public static void main(String[] args) {//declara método main

        final Filosofo[] filosofo = new Filosofo[5];//declara uma array com 5 elementos Filósofo
        
        for(int i=0; i<5; i++){//declara um loop de 5 ciclos para inicialização do vetor de filósofos
            
            filosofo[i] = new Filosofo(i, i, (i+1)%5 );//atribui a cada elemento do vetor um novo objeto Filosofo passando seu nome e seus garfos

        }
        
        Mesa.sentarMesa();//executa o método sentarMesa() para que os garfos sejam inicializados
        
        //cria um loop para iniciar cada uma das Threads
        for(int i=0; i<5; i++){
            filosofo[i].start();//inicia cada Thread
        }
           
    }
}
