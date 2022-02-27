import java.util.Random;

public class Tauler {
    private Casella[][] tauler;
    private int files;
    private int columnes;
    private Random rand = new Random();
    private Casella casellaMenjar;
    private int cargolsVius;

    public Tauler(int files, int columnes, int cargols){
        this.files = files;
        this.columnes = columnes;
        this.cargolsVius = cargols;
        this.tauler = new Casella[files][columnes];
    }

    public void generarCargols() {
        int fila;
        int columna;
        for (int i = 0; i < this.cargolsVius; i++) {
            fila = this.rand.nextInt(this.files);
            columna = this.rand.nextInt(this.columnes);
            tauler[fila][columna] = new Casella(this, files, columnes, fila, columna);
            tauler[fila][columna].addNewCargol();
        }
    }

    public void generarMenjar(){
        int fila = this.rand.nextInt(this.files);
        int columna = this.rand.nextInt(this.columnes);
        if(tauler[fila][columna] == null){
            tauler[fila][columna] = new Casella(this, this.files, this.columnes, fila, columna);
            tauler[fila][columna].addMenjar();
            casellaMenjar = tauler[fila][columna];
        }
        else {
            if (tauler[fila][columna].addMenjar() == false) { //En cas de que ja hi hagi menjar en aquella posició, s'insereix en una altre
                this.generarMenjar();
            }
            casellaMenjar = tauler[fila][columna];
        }
    }

    public void eliminarMenjar(){
        casellaMenjar.eliminarMenjar();
    }

    public void moureCargol(Cargol cargol, int fila, int columna){
        if(tauler[fila][columna] == null) {
            tauler[fila][columna] = new Casella(this, this.files, this.columnes, fila, columna);
        }
        tauler[fila][columna].addCargol(cargol);
    }

    public Casella getPosicioMenjar(){
        return casellaMenjar;
    }

    public Casella getCasella(int fila, int columna){
        return tauler[fila][columna];
    }

    public void torn(){
        for (int i = 0; i < this.files; i++){
            for (int j = 0; j < this.columnes; j++){
                if(tauler[i][j] != null) {
                    this.tauler[i][j].torn();
                }
            }
        }
    }

    public boolean jocAcabat(){
        if (cargolsVius == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void cargolMort(){
        this.cargolsVius--;
    }
}
