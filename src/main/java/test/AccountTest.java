package main.java.test;

import main.java.models.Account;
import main.java.models.AccountType;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AccountTest {
    @Test
    public void dummyTest() {
        Account a;

        int id = 11;
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String hashedPassword = "testPass";
        String mail = "testMail";
        String displayName = "testDisplayName";
        String imgSrc = "testImgSrc";
        String fbLink = "testFbLink";
        String twitterLink = "testTwitterLink";
        String googleLink = "testGoogleLink";
        String country = "testCountry";
        String city = "testCity";
        String aboutMe = "testAboutMe";
        AccountType type = AccountType.USER;
        boolean isAdmin = false;
        boolean isBanned = false;

        a= new Account(id, firstName, lastName, hashedPassword, mail, displayName, imgSrc, fbLink, twitterLink,
                googleLink, country, city, aboutMe, type, isAdmin, isBanned);

        assertEquals(a.toString(), "Account{" +
                "id=" + 11 +
                ", firstName='testFirstName" + '\'' +
                ", lastName='testLastName" + '\'' +
                ", hashedPassword='testPass" + '\'' +
                ", mail='testMail" + '\'' +
                ", displayName='testDisplayName" + '\'' +
                ", imgSrc='testImgSrc" + '\'' +
                ", fbLink='testFbLink" + '\'' +
                ", twitterLink='testTwitterLink" + '\'' +
                ", googleLink='testGoogleLink" + '\'' +
                ", country='testCountry" + '\'' +
                ", city='testCity" + '\'' +
                ", aboutMe='testAboutMe" + '\'' +
                ", type=USER" +
                ", isAdmin=false" +
                ", isBanned=false" +
                '}');
    }
}
