package Framework;

public enum Priority {
    ZER0(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private int numericalPriority;

    Priority(int numericalPriority){
        this.numericalPriority = numericalPriority;

    }

    public int getPriority(){
        return numericalPriority;
    }
}
