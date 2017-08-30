package backend;

import java.io.Serializable;
import java.util.Date;

/**
 * UserInfo class stores user information - name, address, phone,
 * email, emergency contacts and installation date
 */
public class UserInfo implements Serializable {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String[] emergencyContact = new String[2];
    private Date installDate;

    /**
     * Create a new UserInfo
     * @param name user name
     * @param address user address
     * @param phone user phone number
     * @param email user email
     * @param contacts an array of emergency contacts
     * @param installTime installation time
     */
    public UserInfo(String name, String address,  String email, String phone, String[] contacts, Date installTime) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.emergencyContact = contacts;
        this.installDate = installTime;
    }

    /**
     * Get user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Get user address
     * @return user address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get user phone number
     * @return user phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get user email
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get user emergency contact numbers
     * @return array of emergency contacts
     */
    public String[] getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Get date of the user installation
     * @return date of installation.
     */
    public Date getInstallTime() {
        return installDate;
    }

}
