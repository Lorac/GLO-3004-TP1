// Équipe #1
// Antoine Giasson (111 044 655), GLO
// Maxime Charron (111 038 084), GLO
// Maxime Roussin-Bélanger (111 018 750), IFT
// Julien Duchesne (111 042 624), GLO
package ca.ulaval.glo3004.etape3;


import static ca.ulaval.glo3004.etape3.Constants.maxNum;

class NumCompte {
    private int number;

    public NumCompte(int number) {
        if (number > maxNum || number <= 0) {
            throw new IllegalArgumentException("Le numéro de compte est invalide");
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != NumCompte.class) {
            return false;
        }
        NumCompte numCompte = (NumCompte) obj;
        return numCompte.number == this.number;
    }
}

