package kolokviumski1.NaslovnaStranica;

import java.util.*;
import java.util.stream.Collectors;

class CategoryNotFoundException extends Exception {
    String name;

    public CategoryNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("Category %s was not found", this.name);
    }
}

class Category {
    String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return this.name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

abstract class NewsItem {
    String naslov;
    Date publishedOnDate;
    Category category;

    public NewsItem(String naslov, Date publishedOnDate, Category category) {
        this.naslov = naslov;
        this.publishedOnDate = publishedOnDate;
        this.category = category;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Date getPublishedOnDate() {
        return publishedOnDate;
    }

    public void setPublishedOnDate(Date publishedOnDate) {
        this.publishedOnDate = publishedOnDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public abstract String getTeaser();

}

class TextNewsItem extends NewsItem {
    String text;

    public TextNewsItem(String naslov, Date publishedOnDate, Category category, String text) {
        super(naslov, publishedOnDate, category);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.naslov + "\n");
        Date now = new Date();
        long minutes = (now.getTime() - this.publishedOnDate.getTime()) / 60000;
        sb.append(minutes + "\n");
        if (text.length() > 80)
            sb.append(text.substring(0, 80) + "\n");
        else
            sb.append(text + "\n");
        return sb.toString();
    }
}

class MediaNewsItem extends NewsItem {
    String url;
    int views;

    public MediaNewsItem(String naslov, Date publishedOnDate, Category category, String url, int views) {
        super(naslov, publishedOnDate, category);
        this.url = url;
        this.views = views;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.naslov + "\n");
        Date now = new Date();
        long minutes = (now.getTime() - this.publishedOnDate.getTime()) / 60000;
        sb.append(minutes + "\n");
        sb.append(url + "\n");
        sb.append(views + "\n");
        return sb.toString();
    }
}

class FrontPage {
    List<NewsItem> vesti;
    Category[] categories;

    public FrontPage(Category[] categories) {
        vesti = new ArrayList<>();
        this.categories = categories;
    }

    void addNewsItem(NewsItem newsItem) {
        vesti.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category) {
        return vesti.stream().filter(vest -> vest.category.equals(category)).collect(Collectors.toList());
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if (Arrays.stream(categories).noneMatch(n -> n.name.equals(category)))
            throw new CategoryNotFoundException(category);
        Category c = new Category(category);
        return listByCategory(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (NewsItem n : vesti) {
            sb.append(n.getTeaser());
        }
        return sb.toString();
    }
}

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}