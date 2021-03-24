package kolokviumski2code.FileSystem;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
class File {
    String name;
    int size;
    LocalDateTime createdAt;
    static Comparator<File> comparator = Comparator
            .comparing(File::getCreatedAt)
            .thenComparing(File::getName)
            .thenComparing(File::getSize);

    public File(String name, int size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public int getYear() {
        return createdAt.getYear();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        //%-10[name] %5[size]B %[createdAt]
        return String.format("%-10s %5dB %s", name, size, createdAt.toString());
    }
}

class FileSystem {
    Map<Character, Set<File>> filesystem;

    public FileSystem() {
        filesystem = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        filesystem.putIfAbsent(folder, new TreeSet<>(File.comparator));
        filesystem.get(folder).add(new File(name, size, createdAt));
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return filesystem.values().stream()
                .flatMap(Collection::stream)
                .filter(file -> file.getName().startsWith(".")&&file.size < size)
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        int sum = 0;
        for (Character c : folders) {
            sum += filesystem.get(c).stream().mapToInt(f -> f.size).sum();
        }
        return sum;
    }

    public Map<Integer, Set<File>> byYear() {
        /*  List<File> sitefajlovi = filesystem.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
          Set<Integer> years=new HashSet<>();
          sitefajlovi.stream().forEach(f->years.add(f.createdAt.getYear()));

          Map<Integer, Set<File>> result =new HashMap<>();
          for(Integer year:years){
              result.putIfAbsent(year,new TreeSet<>(File.comparator));
              sitefajlovi.stream().filter(f->f.getCreatedAt().getYear()==year).forEach(f->result.get(year).add(f));
          }
          return result;

          */


        return filesystem.values().stream().flatMap(Collection::stream)
                .collect(Collectors.groupingBy(File::getYear, Collectors.toSet()));
    }

    public Map<String, Long> sizeByMonthAndDay() {
        Map<String, Long> result = new HashMap<>();
        filesystem.values().stream()
                .flatMap(Collection::stream)
                .forEach(file -> {
                    String key = file.getCreatedAt().getMonth().toString() + "-" + file.getCreatedAt().getDayOfMonth();
                    result.putIfAbsent(key, (long) 0);
                    result.put(key, result.get(key) + (long) file.getSize());
                });
        return result;

    }
}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}



