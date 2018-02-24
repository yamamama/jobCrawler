package us.codecraft.jobhunter.model;

public class BaseJobInfo {
    private Long id;
    private String title;
    private String salary;
    private String company;
    private String description;
    private String city;
    private String url;
    private String educationRequire;
    private String urlmd5;
    private String source;
    private String createTime;
    private String experience;
    private String companySize;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public String getEducationRequire() {
        return educationRequire;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public String getSource() {
        return source;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getExperience() {
        return experience;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEducationRequire(String educationRequire) {
        this.educationRequire = educationRequire;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }
}
