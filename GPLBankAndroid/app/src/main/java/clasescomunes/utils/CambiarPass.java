package clasescomunes.utils;

/**
 * Created by JoséMaría on 29/05/2016.
 */
public class CambiarPass {

    private String currentP;
    private String newP;

    public CambiarPass(){

    }

    public CambiarPass(String currentP, String newP) {
        this.currentP = currentP;
        this.newP = newP;
    }

    public String getCurrentP() {
        return currentP;
    }

    public void setCurrentP(String currentP) {
        this.currentP = currentP;
    }

    public String getNewP() {
        return newP;
    }

    public void setNewP(String newP) {
        this.newP = newP;
    }
}
