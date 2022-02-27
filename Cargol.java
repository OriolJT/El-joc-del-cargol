import java.util.Random;

public class Cargol {
    private Random rand = new Random();
    final int numGens = 4;
    private int gens[] = new int[numGens]; // 0 = menjar, 1 = passejar, 2 = reproduir-se.
    private int percentatges[] = new int[numGens];
    private boolean haMenjat = false; //En cas de que menji, es ficarà a true i tindrà 10 torns més de vida.
    private Casella casellaPare;
    private int fila;
    private int columna;
    private int totalFiles;
    private int totalColumnes;
    private int tornsTotals;

    public Cargol(Casella casella, int fila, int columna, int totalFiles, int totalColumnes, int tornsTotals){
        this.fila = fila;
        this.columna = columna;
        this.casellaPare = casella;
        this.totalFiles = totalFiles;
        this.totalColumnes = totalColumnes;
        this.tornsTotals = tornsTotals;
        for (int i = 0; i < numGens; i++){
           this.gens[0] = this.rand.nextInt(3);
        }
        this.percentatges[0] = 60;
        this.percentatges[1] = 25;
        this.percentatges[2] = 10;
        this.percentatges[3] = 5;
    }

    public Cargol(Casella casella, int fila, int columna, int totalFiles, int totalColumnes, int tornsTotals, int[] gens){
        this.fila = fila;
        this.columna = columna;
        this.casellaPare = casella;
        this.totalFiles = totalFiles;
        this.totalColumnes = totalColumnes;
        this.tornsTotals = tornsTotals;
        this.gens = gens;
        this.percentatges[0] = 60;
        this.percentatges[1] = 25;
        this.percentatges[2] = 10;
        this.percentatges[3] = 5;
    }

    public int[] getGens(){
        return this.gens;
    }

    public void torn(){
        this.tornsTotals -= 1;
        int numero = this.rand.nextInt(100);
        boolean trobat = false;
        int i = 0;
        int maxim = percentatges[0];
        while (!trobat){
            if(i > numGens){
                System.out.println("Error en distribució de torns.");
            }
            if (numero < maxim){
                trobat = true;
            }
            else{
                i++;
                maxim += percentatges[i];
            }
        }
        accioTorn(i);
    }

    public void accioTorn(int numeroGen){
        int accio = gens[numeroGen];
        switch(accio){
            case 0: menjar();
                break;
            case 1: passejar();
                break;
            case 2: reproduirse();
                break;

            default: System.out.println("Error en accioTorn");
        }
    }

    public void menjar(){
        Casella menjar = casellaPare.getPosicioMenjar();
        int filaMenjar = menjar.getFila();
        int columnaMenjar = menjar.getColumna();

        if (this.fila == filaMenjar) {
            if (this.columna == columnaMenjar) {
                this.haMenjat = true;
                tornsTotals += 10;
            } else {
                if (this.columna < columnaMenjar) {
                    casellaPare.moure(this, this.fila, this.columna + 1);
                } else {
                    casellaPare.moure(this, this.fila, this.columna - 1);
                }
            }
        }
        else{
            if(this.fila < filaMenjar){
                casellaPare.moure(this, this.fila+1, this.columna);
            }
            else{
                casellaPare.moure(this, this.fila-1, this.columna);
            }
        }
    }

    public void passejar(){
        boolean trobat = false;
        int novaFila;
        int novaColumna;
        while(!trobat) {
            int move = this.rand.nextInt(4); //0 = Oest, 1 = Nord, 2 = Est, 3 = Sud.
            novaFila = this.fila;
            novaColumna = this.columna;
            switch (move){
                case 0: novaFila = this.fila-1;
                    break;
                case 1: novaColumna = this.columna-1;
                    break;
                case 2: novaFila = this.fila+1;
                    break;
                case 3: novaColumna = this.columna+1;
                    break;
                default:
            }
            if(this.casellaPare.potMoure(novaFila, novaColumna)) {
                trobat = true;
                casellaPare.moure(this, novaFila, novaColumna);
            }

        }
    }

    public void reproduirse(){
        casellaPare.addReproduir(this);
    }

    public boolean estaViu(){
        if(tornsTotals == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
