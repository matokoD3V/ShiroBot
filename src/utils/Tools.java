package utils;

public class Tools {

    public int getRequiredExp(int level) {
        int requiredExp = 0;
        for(int i = 0; i < level; i++)
            requiredExp += level * 1.6;
        return (int)requiredExp*50;
    }
}
