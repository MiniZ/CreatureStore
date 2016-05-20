package main.java.models;

public class Account {

    private long id;

    private String firstName;

    private String lastName;

    private String hashedPassword;

    private String mail;

    private String displayName;

    private String imgSrc;

    private String fbLink;

    private String twitterLink;

    private String googleLink;

    private String country;

    private String city;

    private String aboutMe;

    private AccountType type;

    private boolean isBanned;

    public Account() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", mail='" + mail + '\'' +
                ", displayName='" + displayName + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", fbLink='" + fbLink + '\'' +
                ", twitterLink='" + twitterLink + '\'' +
                ", googleLink='" + googleLink + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", type=" + type +
                ", isBanned=" + isBanned +
                '}';
    }
}
