import java.util.Random;
import java.util.Vector;

public class Casella {
    private int totalFiles;
    private int totalColumnes;
    private int columna;
    private int fila;
    private boolean menjar;
    private Tauler tauler;

    private Vector<Cargol> cargolsReproduir = new Vector<Cargol>();
    private Vector<Cargol> cargols = new Vector<Cargol>();
    private Random rand = new Random();

    public Casella(Tauler tauler, int files, int columnes, int fila, int columna){
        this.tauler = tauler;
        this.totalFiles = files;
        this.totalColumnes = columnes;
        this.fila = fila;
        this.columna = columna;
        menjar = false;
    }

    public int getTotalFiles(){
        return this.totalFiles;
    }
    public int getTotalColumnes(){
        return this.totalColumnes;
    }

    public int getFila(){
        return this.fila;
    }
    public int getColumna(){
        return this.columna;
    }

    public void addNewCargol(){
        Cargol cargol = new Cargol(this, fila, columna, totalFiles, totalColumnes, 20);
        cargols.add(cargol);
    }

    public void addCargol(Cargol cargol){
        cargols.add(cargol);
    }

    public boolean addMenjar(){
        if (menjar == false) {
            menjar = true;
            return true;
        }
        else{
            return false;
        }
    }

    public void eliminarMenjar(){
        menjar = false;
    }

    public void moure(Cargol cargol, int fila, int columna) {
        cargols.removeElement(cargol);
        tauler.moureCargol(cargol, fila, columna);
    }

    public Casella getPosicioMenjar(){
        return tauler.getPosicioMenjar();
    }

    public void torn(){
        Vector<Cargol> cargolAux = (Vector<Cargol>) cargols.clone();
        while(!cargolAux.isEmpty()){
            cargolAux.get(0).torn();
            if (cargolAux.firstElement().estaViu() == false){
                tauler.cargolMort();
            }
            cargolAux.remove(0);
        }

        while (cargolsReproduir.size() > 1) {
            reproduir(cargolsReproduir.get(0), cargolsReproduir.get(1));
            cargolsReproduir.remove(0);
            cargolsReproduir.remove(1);
        }
        if (!cargolsReproduir.isEmpty()){
            boolean trobat = false;
            int i = -1;
            while (!trobat && i <= 1){
                int j = -1;
                 while (!trobat && j <= 1){
                     if (i != 0 && j != 0) {
                         if(this.potMoure(this.fila + i, this.columna + j)) {
                             if ((tauler.getCasella(this.fila + i, this.columna + j) != null)) {
                                 if (tauler.getCasella(this.fila + i, this.columna + j).sizeCargolsReproduir() == true) {
                                     reproduir(this.lastCargolReproduir(), tauler.getCasella(this.fila + i, this.columna + j).lastCargolReproduir());
                                     trobat = true;
                                 }
                             }
                         }
                     }
                     j++;
                 }
                i++;
            }
        }
    }

    public Cargol lastCargolReproduir(){
        Cargol cargolAux = null;
        if(cargolsReproduir.size() == 1) {
            cargolAux = cargolsReproduir.firstElement();
            cargolsReproduir.clear();
        }
        else{
            System.out.println("Error en reproducció adjacent");
        }
        return cargolAux;
    }

    public boolean sizeCargolsReproduir(){
        return cargolsReproduir.size() == 1;
    }

    public void reproduir(Cargol cargolA, Cargol cargolB){
        int gensFill[] = new int[cargolA.getGens().length];
        for(int i = 0; i < gensFill.length; i++){
            if (this.rand.nextInt(2) == 0){
                gensFill[i] = cargolA.getGens()[i];
            }
            else{
                gensFill[i] = cargolB.getGens()[i];
            }
        }
        Cargol cargolFill = new Cargol(this, this.fila, this.columna, this.totalFiles, this.totalColumnes, 20, gensFill);
        cargols.add(cargolFill);
    }

    public void addReproduir(Cargol cargol){
        this.cargolsReproduir.add(cargol);
    }

    public boolean potMoure(int fila, int columna){
        if(fila >= 0 && fila < totalFiles && columna >= 0 && columna < totalColumnes){
            return true;
        }
        else{
            return false;
        }
    }
}
