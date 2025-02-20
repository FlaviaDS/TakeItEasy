package org.example.model;

public class BoardUtils {

    public static int getIndexFromRowCol(int r, int c) {
        switch (r) {
            case 0:
                if (c == 2) return 0;
                if (c == 3) return 1;
                if (c == 4) return 2;
                break;
            case 1:
                if (c == 1) return 3;
                if (c == 2) return 4;
                if (c == 3) return 5;
                if (c == 4) return 6;
                break;
            case 2:
                if (c == 0) return 7;
                if (c == 1) return 8;
                if (c == 2) return 9;
                if (c == 3) return 10;
                if (c == 4) return 11;
                break;
            case 3:
                if (c == 1) return 12;
                if (c == 2) return 13;
                if (c == 3) return 14;
                if (c == 4) return 15;
                break;
            case 4:
                if (c == 2) return 16;
                if (c == 3) return 17;
                if (c == 4) return 18;
                break;
        }
        return -1;
    }
}