package ca.ulaval.glo3004.etape3;

class NumCompte {
    private int number;

    public NumCompte(int number) {
        if (number > Constants.maxNum) {
            throw new IllegalArgumentException();
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

