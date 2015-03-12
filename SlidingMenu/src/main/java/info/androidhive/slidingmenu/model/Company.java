package info.androidhive.slidingmenu.model;


public class Company {
    private String title, thumbnailUrl, branch,rating,category;
    

    public Company() {
    }

    public Company(String name, String thumbnailUrl,String branch, String rating,
                 String category) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.branch = branch;
        this.rating = rating;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCategory() {
        return rating;
    }

    public void setCategory(String rating) {
        this.rating = rating;
    }

    public String getDescp() {
        return category;
    }

    public void setDescp(String category) {
        this.category = category;
    }

}
