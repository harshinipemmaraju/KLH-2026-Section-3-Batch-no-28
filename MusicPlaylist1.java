import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.stream.*;

public class MusicPlaylist1{

    // Song class
    static class Song {
        private String title;
        private String artist;
        private int plays;

        Song(String title, String artist, int plays) {
            if (title == null || title.trim().isEmpty()
                    || artist == null || artist.trim().isEmpty()
                    || plays < 0) {
                throw new IllegalArgumentException(
                    "Invalid song details");
            }

            this.title = title.trim();
            this.artist = artist.trim();
            this.plays = plays;
        }

        String getTitle() {
            return title;
        }

        String getArtist() {
            return artist;
        }

        int getPlays() {
            return plays;
        }

        @Override
        public String toString() {
            return title + "|" + artist + "|" + plays;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Song> playlist = new ArrayList<>();

        // CO1-CO3: Read five songs
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("\nEnter details for song "
                        + (i + 1));

                System.out.print("Song title: ");
                String title = sc.nextLine();

                System.out.print("Artist: ");
                String artist = sc.nextLine();

                System.out.print("Number of plays: ");
                int plays = Integer.parseInt(
                        sc.nextLine().trim());

                playlist.add(new Song(title, artist, plays));

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Invalid input. Song skipped.");
            }
        }

        Path file = Paths.get("playlist.txt");

        // CO5: Load saved songs
        try {
            if (Files.exists(file)) {
                for (String line : Files.readAllLines(file)) {
                    String[] data = line.split("\\|", -1);

                    if (data.length == 3) {
                        try {
                            Song song = new Song(
                                    data[0],
                                    data[1],
                                    Integer.parseInt(
                                            data[2].trim()));

                            playlist.add(song);

                        } catch (IllegalArgumentException e) {
                            System.out.println(
                                    "Skipped invalid saved record.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(
                    "Could not read saved playlist.");
        }

        // Display playlist and calculate total
        System.out.println("\n--- Music Playlist ---");

        int total = 0;

        for (Song song : playlist) {
            System.out.println(
                    song.getTitle() + " - "
                    + song.getArtist() + " - "
                    + song.getPlays() + " plays");

            total += song.getPlays();
        }

        // Find the most-played song
        if (!playlist.isEmpty()) {
            Song mostPlayed = playlist.get(0);

            for (Song song : playlist) {
                if (song.getPlays()
                        > mostPlayed.getPlays()) {
                    mostPlayed = song;
                }
            }

            System.out.println(
                    "\nMost Played Song: "
                    + mostPlayed.getTitle());

            System.out.println(
                    "Number of Plays: "
                    + mostPlayed.getPlays());
        }

        // CO6: Sort songs by play count
        playlist.sort(
                Comparator.comparingInt(Song::getPlays)
                        .reversed());

        System.out.println("\n--- Songs Sorted by Plays ---");

        playlist.forEach(System.out::println);

        // Calculate statistics using streams
        int streamTotal = playlist.stream()
                .mapToInt(Song::getPlays)
                .sum();

        double average = playlist.isEmpty()
                ? 0.0
                : (double) streamTotal / playlist.size();

        long popularCount = playlist.stream()
                .filter(song -> song.getPlays() >= 10)
                .count();

        System.out.println("\n--- Listening Statistics ---");
        System.out.println("Total Plays: " + streamTotal);
        System.out.println("Average Plays: " + average);
        System.out.println(
                "Songs with 10 or more plays: "
                + popularCount);

        // CO5: Save playlist to a file
        List<String> lines = playlist.stream()
                .map(Song::toString)
                .collect(Collectors.toList());

        try {
            Files.write(file, lines);
            System.out.println(
                    "Playlist saved to playlist.txt");
        } catch (IOException e) {
            System.out.println(
                    "Could not save playlist.");
        }

        // CO5-CO6: Search for a song
        System.out.print("\nSearch song title: ");
        String query = sc.nextLine().trim();

        List<Song> matches = playlist.stream()
                .filter(song -> song.getTitle()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\n--- Search Results ---");

        if (matches.isEmpty()) {
            System.out.println("No matching songs found.");
        } else {
            matches.forEach(System.out::println);
        }

        sc.close();
    }
}