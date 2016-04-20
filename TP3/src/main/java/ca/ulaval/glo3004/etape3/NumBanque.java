// Équipe #1
// Antoine Giasson (111 044 655), GLO
// Maxime Charron (111 038 084), GLO
// Maxime Roussin-Bélanger (111 018 750), IFT
// Julien Duchesne (111 042 624), GLO
package ca.ulaval.glo3004.etape3;

public class NumBanque {
    private int number;

    public NumBanque(int number) {
        if (number > Constants.maxNum) {
            throw new IllegalArgumentException();
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != NumBanque.class) {
            return false;
        }
        NumBanque numCompte = (NumBanque) obj;
        return numCompte.number == this.number;
    }
}

