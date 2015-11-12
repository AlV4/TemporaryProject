
public class BattleField{
    private String[][] battleField = { { " ", "B", " ", " ", "B", " ", "B", " ", "B" },
            { "B", "B", "B", " ", "B", " ", "B", " ", "B" },
            { "B", "B", "B", " ", "B", " ", "B", " ", "B" },
            { "B", " ", "B", " ", "B", " ", "B", "B", "B" },
            { "B", " ", "B", " ", "B", " ", " ", "B", " " },
            { "B", "B", "B", " ", "B", " ", "B", "B", "B" },
            { "B", " ", "B", " ", "B", " ", "B", " ", "B" },
            { "B", " ", "B", " ", "B", " ", "B", " ", "B" },
            { "B", " ", "B", " ", "B", "B", "B", " ", "B" } };


    public BattleField()  {

    }


    public String[][] getBattleField() {
        return battleField;
    }

}
