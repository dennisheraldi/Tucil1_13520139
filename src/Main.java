import java.io.*;
import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Main {


    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
    try{
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println(" Penyelesaian Word Search Puzzle");
        System.out.println("  dengan Algortima Brute Force");
        System.out.println("--------------------------------");
        System.out.println("           Menu Utama           ");
        System.out.println("--------------------------------");
        System.out.println("1. Selesaikan Puzzle");
        System.out.println("2. Credit");
        System.out.println("3. Exit");
        System.out.println("--------------------------------");
        System.out.print("Pilih menu: ");
        int choice = input.nextInt();
        switch (choice){
            case 1:
                System.out.print("Masukkan nama file (.txt) : ");
                String fileName = input.next();
                readFile(fileName);
                solvePuzzle();
                break;
            case 2:
                System.out.println("--------------------------------");
                System.out.println("   Program ini dibuat sebagai   ");
                System.out.println("    pemenuhan Tugas Kecil 1     ");
                System.out.println("   IF2211 Strategi Algoritma    ");
                System.out.println("   Semester 2 Tahun 2021/2022   ");
                System.out.println("         Dibuat oleh:           ");
                System.out.println("     Fachry Dennis Heraldi      ");
                System.out.println("           13520139             ");
                System.out.println("--------------------------------");
                System.out.println("[ENTER] Kembali ke menu utama...");
                BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
                String line = buf.readLine ();
                if (line.equals ("")) mainMenu();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Masukkan menu tidak valid, ulangi kembali.");
                mainMenu();
                break;
        }
    } catch (Exception e){
        System.out.println("Masukkan menu tidak valid, ulangi kembali.");
        mainMenu();
    }
        
    }

    public static char[][] map;
    public static String[] words;
    public static int[][] colorMap;

    public static void readFile(String FileName){
        try {
            int i,j;
            int barisPuzzle = 0;
            int kolomPuzzle = 0;
            int lengthWords = 0;
            int blankIdx = 0;
            String dir = "..//test//" + FileName;
            File file = new File(dir);
            Scanner input = new Scanner(file);
            String readData[];

            int countLine = 0;
            while (input.hasNextLine()){
                countLine++;
                input.nextLine();
            }
            input.close();

            readData = new String[countLine];
            input = new Scanner(file);
            for (i=0; i < countLine; i++){
                readData[i] = input.nextLine();
            }
            input.close();

            // pengolahan data yang berhasil terbaca

            // mencari spasi antara puzzle dengan words
            for (i=0; i < countLine; i++){
                if (readData[i] == "") {
                    blankIdx = i;
                }
            }
            
            barisPuzzle = blankIdx+1;
            String checkKolom = readData[0].replace(" ","");
            kolomPuzzle = checkKolom.length();
            
            lengthWords = countLine-blankIdx-1;

            map = new char[barisPuzzle][kolomPuzzle];
            colorMap = new int[barisPuzzle][kolomPuzzle];
            words = new String[lengthWords];

            String line;

            for (i=0; i < blankIdx; i++){
                line = readData[i].replace(" ","");
                for (j=0; j < line.length(); j++){
                    map[i][j] = line.charAt(j);
                    colorMap[i][j] = 0;
                }
            }
           
            for (i=blankIdx+1; i<countLine; i++){
                words[i-blankIdx-1]=readData[i];
            }

            System.out.println("\nPuzzle berhasil terbaca!");
            System.out.println("\nPuzzle yang akan diselesaikan: \n");
            for (i=0; i< map.length; i++){
                for(j=0; j<map[0].length; j++){
                    System.out.print(map[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println("Kata-kata yang akan dicari di dalam puzzle:");
            for (i=0; i<words.length;i++){
                System.out.print((i+1)+". ");System.out.println(words[i]);
            }


        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan. Periksa kembali nama file.");
        }
    }

    public static void solvePuzzle() {
        int i;
        int comparison = 0;
        boolean checkUp;
        boolean checkUpR;
        boolean checkR;
        boolean checkDownR;
        boolean checkDown;
        boolean checkDownL;
        boolean checkL;
        boolean checkUpL;

        long start1 = System.nanoTime();
        for (i=0; i < words.length; i++) {
            boolean foundTheAnswer = false;
            int barisMap = 0;
            int kolomMap = 0;
            checkUp = true;
            checkUpR = true;
            checkR = true;
            checkDownR = true;
            checkDown = true;
            checkDownL = true;
            checkL = true;
            checkUpL = true;
            while (!foundTheAnswer && barisMap >= 0 && kolomMap >= 0 && barisMap < map.length && kolomMap < map[0].length) {
                comparison++;
                if (words[i].charAt(0) == map[barisMap][kolomMap]) {
                    int pos;
                    checkUp = true;
                    checkUpR = true;
                    checkR = true;
                    checkDownR = true;
                    checkDown = true;
                    checkDownL = true;
                    checkL = true;
                    checkUpL = true;
                    for (pos = 1 ; pos < words[i].length(); pos++){
                        // Ke atas
                        if (checkUp && isIdxValid(map,barisMap-pos,kolomMap) && map[barisMap-pos][kolomMap]!=words[i].charAt(pos)){
                            checkUp = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap-pos,kolomMap)){
                            checkUp = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kanan atas
                        if (checkUpR && isIdxValid(map,barisMap-pos,kolomMap+pos) && map[barisMap-pos][kolomMap+pos]!=words[i].charAt(pos)){
                            checkUpR = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap-pos,kolomMap+pos)){
                            checkUpR = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kanan
                        if (checkR && isIdxValid(map,barisMap,kolomMap+pos) && map[barisMap][kolomMap+pos]!=words[i].charAt(pos)){
                            checkR = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap,kolomMap+pos)){
                            checkR = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kanan bawah
                        if (checkDownR && isIdxValid(map,barisMap+pos,kolomMap+pos) && map[barisMap+pos][kolomMap+pos]!=words[i].charAt(pos)){
                            checkDownR = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap+pos,kolomMap+pos)){
                            checkDownR = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke bawah
                        if (checkDown && isIdxValid(map,barisMap+pos,kolomMap) && map[barisMap+pos][kolomMap]!=words[i].charAt(pos)){
                            checkDown = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap+pos,kolomMap)) {
                            checkDown = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kiri bawah
                        if (checkDownL && isIdxValid(map,barisMap+pos,kolomMap-pos) && map[barisMap+pos][kolomMap-pos]!=words[i].charAt(pos)){
                            checkDownL = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap+pos,kolomMap-pos)){
                            checkDownL = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kiri
                        if (checkL && isIdxValid(map,barisMap,kolomMap-pos) && map[barisMap][kolomMap-pos]!=words[i].charAt(pos)){
                            checkL = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap,kolomMap-pos)){
                            checkL = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        // Ke kiri atas
                        if (checkUpL && isIdxValid(map,barisMap-pos,kolomMap-pos) && map[barisMap-pos][kolomMap-pos]!=words[i].charAt(pos)){
                            checkUpL = false;
                            comparison++;
                        } else if (!isIdxValid(map,barisMap-pos,kolomMap-pos)){
                            checkUpL = false;
                            comparison++;
                        } else {
                            comparison++;
                        }
                        if ( pos == words[i].length()-1 && ( checkUp || checkUpR || checkR || checkDownR || checkDown || checkDownL || checkL || checkUpL )){
                            foundTheAnswer = true;
                        } 
                    }
                }

                if (!foundTheAnswer){
                    kolomMap++;
                    if (kolomMap == map[0].length){
                        barisMap++;
                        kolomMap = 0;
                    }
                }
                
            }

            // pewarnaan puzzle
            if (checkUp) {
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (q==kolomMap && barisMap-p < words[i].length() && barisMap-p >= 0 ){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkUpR) {
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (barisMap-p < words[i].length() && barisMap-p >= 0 && q-kolomMap < words[i].length() && q-kolomMap >= 0 && barisMap-p == q-kolomMap){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkR) {
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (p==barisMap && q-kolomMap < words[i].length() && q-kolomMap >= 0){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkDownR) {
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (p-barisMap < words[i].length() && p-barisMap >= 0 && q-kolomMap < words[i].length() && q-kolomMap >= 0 && p-barisMap == q-kolomMap ){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkDown){
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (q==kolomMap && p-barisMap < words[i].length() && p-barisMap >= 0){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkDownL){
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (p-barisMap < words[i].length() && p-barisMap >= 0 && kolomMap-q < words[i].length() && kolomMap-q > 0 && p-barisMap==kolomMap-q){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkL){
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (p==barisMap && kolomMap-q < words[i].length() && kolomMap-q >= 0){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
            if (checkUpL){
                for (int p=0; p<map.length;p++){
                    for(int q=0; q<map[0].length; q++){
                        if (barisMap-p < words[i].length() && barisMap-p >= 0 && kolomMap-q < words[i].length() && kolomMap-q >= 0 && barisMap-p == kolomMap-q){
                            colorMap[p][q] = i+1;
                        }
                    }
                }
            }
        }
        long end1 = System.nanoTime(); 

        System.out.println();
        System.out.println("Penyelesaian puzzle: \n ");
        for (i=0;i<colorMap.length;i++){
            for(int j=0; j < colorMap[0].length; j++){
                if (colorMap[i][j] == 0) {
                    System.out.print(map[i][j]+" ");
                } else {
                    printColored(map[i][j], colorMap[i][j]); System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("Waktu eksekusi program: " + (end1-start1) +" nanosekon");
        System.out.println("Total perbandingan huruf yang dilakukan: " + comparison + " kali");
        
    }

    public static boolean isIdxValid(char[][] Matrix, int IdxRow, int IdxCol){
        int ROW = Matrix.length;
        int COL = Matrix[0].length;
        return (0 <= IdxRow) && (IdxRow < ROW) && (0 <= IdxCol) && (IdxCol < COL);
    }

    public static void printColored(char text, int color) {
        int palette[] = {1,2,3,4,5,6,9,10,11,12,13,14,25,27,31,35,39,41,46,51,57,75,81,95,99,100
            ,106,117,119,127,137,154,157,163,165,196,200,206,208,220,221,229};
        color = color%(palette.length);
        System.out.print("\033[38;5;"+String.valueOf(palette[color])+"m"+text+"\033[0m");
    }
}
