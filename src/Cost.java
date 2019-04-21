public class Cost {
    private final double tva;
    private final double fee;


    public Cost(double tva, double fee) {
        this.tva = tva;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "tva=" + tva +
                ", fee=" + fee +
                '}';
    }
}
