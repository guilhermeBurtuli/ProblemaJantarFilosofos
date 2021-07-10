
package problemajantardosfilosofos;

import java.util.concurrent.Semaphore;//importa a classe Semaphore do pacote java.util

public class Filosofo extends Thread{//declara a Thread Filosofo
    
    private String status;//declara o atributo que define o status de cada Filosofo (comendo ou pensando)
    final int talherEsq;//declara o atributo que define qual o talher que fica a esquerda do filósofo(0; 1; 2; 3; 4)
    final int talherDir;//declara o atributo que define qual o talher que fica a direita do filósofo(0; 1; 2; 3; 4)
    final int nome;//declara o atributo que define qual o nome de cada filosofo (0; 1; 2; 3; 4)
    
    //sobreescreve o construtor 
    public Filosofo(int nome, int talherEsq, int talherDir){//recebe como parâmetros o nome e talheres de cada filosofo
        this.nome = nome;//atribui ao atributo nome do objeto o nome passado pelo parametro
        this.talherEsq = talherEsq;//atribui ao atributo talherEsq do objeto o talher passado pelo parametro
        this.talherDir = talherDir;//atribui ao atributo talherDir do objeto o nome talher pelo parametro
    }
    
    //declara método existe(), responsavel pelo filosofo pensar ou comer
    public void existe(){
       
        this.pensar();//executa o método pensar()                   
        this.comer();//executa o método comer()                       
        
    }
    
    //declara o método pensar()
    public void pensar(){
        
        try {//monitora bloco de código que pode gerar exceções
            
            this.status = "pensando";// atribui o status do filósofo como pensando
            System.out.println("Filósofo "+this.nome+" está "+this.status);//mostra na tela uma mensagem informando que o filósofo está pensando
            Thread.sleep((int) (Math.random() * 5000));// pausa a Thread por um tempo aleatório que varia de 0 a 5 segundos;
            
        } catch (InterruptedException ex) {// captura e trata de exceções 
            
            //exibe uma mensagem informando qual a exceção ocorrida quando o filosofo estava pensando
            System.out.println("OPS! ERRO: "+ex+" NA HORA DO FILÓSOFO "+this.nome+" PENSANDO");
            
        }
        
    }
    
    //declara o metodo comer()
    public void comer (){
        
        if(Mesa.semaforo[this.talherEsq].availablePermits()>0){//testa se o semáforo responsável pelo garfo esquerdo está desligado
            
            this.pegaTalher(Mesa.semaforo[this.talherEsq], this.talherEsq);//executa o método pegaTalher() passando o talher da esquerda
            
            if(Mesa.semaforo[this.talherDir].availablePermits()>0){//testa se o semáforo responsável pelo garfo direito está desligado
                
                //monitora bloco de código que pode gerar exceções
                try {                   
                    this.pegaTalher(Mesa.semaforo[this.talherDir], this.talherDir);//executa o método pegaTalher() passando o talher da direita
                    this.status = "comendo";// define status como comendo
                    System.out.println("Filósofo "+this.nome+" está "+this.status);//exibe mensagem informando que o filosofo está comendo
                    Thread.sleep((int) (Math.random() * 5000));// pausa a Thread por um tempo aleatório que varia de 0 a 5 segundos;
                    this.largaTalher(Mesa.semaforo[this.talherEsq], this.talherEsq);//executa o método largaTalher() passando o talher da esquerda
                    this.largaTalher(Mesa.semaforo[this.talherDir], this.talherDir);//executa o método largaTalher() passando o talher da direita
                    
                } catch (InterruptedException ex) {// captura e trata de exceções
                    //exibe uma mensagem informando qual a exceção ocorrida quando o filosofo estava comendo
                    System.out.println("OPS! ERRO: "+ex+" NA HORA DO FILÓSOFO "+this.nome+" COMER");                    
                }
                
            //executa caso o talher da direita esteja ocupado (semáforo esteja ligado)    
            }else{               
                this.talherOcupado(this.talherDir);//executa o método talherOcupado() passando o talher da direita 
                this.largaTalher(Mesa.semaforo[this.talherEsq], this.talherEsq);//executa o método largaTalher() passando o talher da direita
            }
            
        //executa caso o talher da esquerda esteja ocupado (semáforo esteja ligado)    
        }else{            
            this.talherOcupado(this.talherEsq);//executa o método talherOcupado() passando o talher da esquerda            
        }       
        
    }
    
    //declara o método pegaTalher()
    public void pegaTalher(Semaphore talher, int posicaoTalher){//recebe como parâmetro o talher e seu respectivo semáforo
        
        try {//monitora bloco de código que pode gerar exceções        
            talher.acquire();//liga (bloqueia) o semáforo do talher 
            System.out.println("Filósofo "+this.nome+" pegou o talher "+posicaoTalher);//mostra mensagem na tela informando que o filósofo pegou o talher
            
        } catch (InterruptedException ex) {// captura e trata de exceções 
            //exibe uma mensagem informando qual a exceção ocorrida quando o filosofo estava pegando o talher
            System.out.println("OPS! ERRO: "+ex+" NA HORA DO FILÓSOFO "+this.nome+" PEGAR O TALHER ");          
        }
        
    }
    
    //declara o método largaTalher()
    public void largaTalher(Semaphore talher, int posicaoTalher){//recebe como parâmetro o talher e seu respectivo semáforo       
        System.out.println("Filósofo "+this.nome+" largou o talher "+posicaoTalher);//mostra mensagem na tela informando que o filósofo largou o talher
        talher.release();//desliga (desbloqueia) o semáforo do talher             
    }
    
    //declara o método talherOcupado()
    public void talherOcupado(int talher){//recebe como parâmetro o talher    
        //mostra mensagem na tela informando que o filósofo tantou pegar o talher mas ele estava ocupado
        System.out.println("Filósofo "+this.nome+" tentou pegar o talher "+talher+" que está ocupado");        
    }
    
    @Override//sobreescreve método run() responsável pela execução da Thread
    public void run() {
        
        while(true){//cria um loop infinito
            this.existe();//executa o método existe() infinitamente
        }
        
    }
    
}
