package kolokviumski1.Arhiva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception {
    int id;

    public NonExistingItemException(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Item with id %s doesn't exist", this.id);
    }
}

abstract class Archive {
    int id;
    Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public abstract String getType();
}

class LockedArchive extends Archive {
    Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }

    public void setDateToOpen(Date dateToOpen) {
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String getType() {
        return "LockedArchive";
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int openedTillNow;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.openedTillNow = 0;
    }

    public void open() {
        openedTillNow++;
    }

    public int getOpenedTillNow() {
        return openedTillNow;
    }

    public void setOpenedTillNow(int openedTillNow) {
        this.openedTillNow = openedTillNow;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public void setMaxOpen(int maxOpen) {
        this.maxOpen = maxOpen;
    }

    @Override
    public String getType() {
        return "SpecialArchive";
    }
}

class ArchiveStore {
    List<Archive> arhivi;
    StringBuilder sb;

    public ArchiveStore() {
        arhivi = new ArrayList<>();
        sb = new StringBuilder();
    }

    void archiveItem(Archive item, Date date) {
        item.setDateArchived(date);
        arhivi.add(item);
        sb.append("Item " + item.id + " archived at " + item.dateArchived + "\n");
    }

    void openItem(int id, Date date) throws NonExistingItemException {
        if (arhivi.stream().noneMatch(a -> a.getId() == id)) {
            throw new NonExistingItemException(id);
        }
        for (Archive a : arhivi) {
            if (a.getId() == id) {
                if (a.getType() == "LockedArchive") {
                    LockedArchive la = (LockedArchive) a;
                    if (date.before(la.getDateToOpen()))
                        sb.append("Item " + la.id + " cannot be opened before " + la.getDateToOpen() + "\n");
                    else
                        sb.append("Item " + la.id + " opened at " + date + "\n");
                } else if (a.getType() == "SpecialArchive") {
                    SpecialArchive sa = (SpecialArchive) a;
                    if (sa.openedTillNow == sa.maxOpen) {
                        sb.append("Item " + sa.id + " cannot be opened more than " + sa.maxOpen + " times"+"\n");
                    } else {
                        sb.append("Item " + sa.id + " opened at " + date + "\n");
                        sa.open();
                    }
                }
            }
        }
    }

    public String getLog() {
        return sb.toString().replaceAll("GMT","UTC");
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде


