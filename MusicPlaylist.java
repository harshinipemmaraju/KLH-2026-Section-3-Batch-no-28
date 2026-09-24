import java.util.Scanner;

public class MusicPlaylist {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] songs = new String[5];
        int[] plays = new int[5];

        System.out.println("Enter 5 songs:");

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter song " + (i + 1) + ": ");
            songs[i] = sc.nextLine();

            System.out.print("Enter number of plays: ");
            plays[i] = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("\n--- Music Playlist ---");

        for (int i = 0; i < 5; i++) {
            System.out.println(songs[i] + " - " + plays[i] + " plays");
        }

        // Find the most played song
        int max = plays[0];
        int position = 0;

        for (int i = 1; i < 5; i++) {
            if (plays[i] > max) {
                max = plays[i];
                position = i;
            }
        }

        System.out.println("\nMost Played Song: " + songs[position]);
        System.out.println("Number of Plays: " + max);

        // Calculate total plays
        int total = 0;

        for (int i = 0; i < 5; i++) {
            total = total + plays[i];
        }

        System.out.println("Total Plays: " + total);

        // Average plays
        double average = total / 5.0;

        System.out.println("Average Plays: " + average);

        sc.close();
    }
}